package com.jeecg.p3.system.web;

import com.jeecg.p3.messageLog.entity.SendMessageLog;
import com.jeecg.p3.system.entity.JwSystemLogoTitle;
import com.jeecg.p3.system.entity.JwSystemUser;
import com.jeecg.p3.system.service.JwSystemAuthService;
import com.jeecg.p3.system.service.JwSystemLogoTitleService;
import com.jeecg.p3.system.service.JwSystemUserService;
import com.jeecg.p3.system.util.JwHttpUtil;
import com.jeecg.p3.system.vo.LoginUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.logger.LoggerFactory;
import org.jeecgframework.p3.core.util.MD5Util;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.util.DateUtils;

import  com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.service.JwWebJwidService;
@Controller
@RequestMapping({"/system"})
public class loginController extends BaseController
{
  public static final Logger LOG = LoggerFactory.getLogger(loginController.class);

  @Autowired
  private JwWebJwidService jwidService;

  @Autowired
  private JwSystemAuthService jwSystemAuthService;

  @Autowired
  private JwSystemLogoTitleService jwSystemLogoTitleService;

  @Autowired
  private JwSystemUserService jwSystemUserService;
  
  @Autowired
  private JwWebJwidService jwWebJwidService;

  @Value("#{sysconfig['sys.jwsso.flg']}")
  private String sysJwssoFlag;

  @RequestMapping(value={"/noAuth"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void noAuth(HttpServletRequest request, HttpServletResponse response) throws Exception { String viewName = "base/back/common/error.vm";
    VelocityContext velocityContext = new VelocityContext();
    ViewVelocity.view(request, response, viewName, velocityContext);
  }  
  
  @RequestMapping(value={"/toLogin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void toLogin(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
    JwSystemLogoTitle logoTitle = (JwSystemLogoTitle)this.jwSystemLogoTitleService.queryLogoTitle().get(0);
    velocityContext.put("logoTitle", logoTitle);

    if (user != null)
    {
      velocityContext.put("jwidname", (String)request.getSession().getAttribute("jwidname"));
      velocityContext.put("userid", user.getUserId());
      try
      {
        LinkedHashMap menuTree = this.jwSystemAuthService.getSubMenuTree(user.getUserId(), null);
        velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
        String viewName = "base/back/main/index.vm";
        ViewVelocity.view(request, response, viewName, velocityContext);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }
    String viewName = "base/back/common/login.vm";
    ViewVelocity.view(request, response, viewName, velocityContext);
  }

  @RequestMapping(value={"/checkUser"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson checkUser(String username, String password, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    AjaxJson j = new AjaxJson();
    try
    {
      LoginUser user = this.jwSystemUserService.queryUserByUserId(username);
      boolean isAuth = false;
      String passwordEncode = MD5Util.MD5Encode(password, "utf-8");

      if (user != null) {
        if ((passwordEncode != null) && (passwordEncode.equals(user.getPassword())) && ("NORMAL".equals(user.getUserStat())))
        {
          isAuth = true;
        }
        else LOG.info("登录验证失败：用户【" + username + "】登录信息验证不通过");

      }
      else if ("Y".equals(this.sysJwssoFlag))
      {
        user = jwSSO(username, password);
        if (user != null)
        {
          JwSystemUser jwSystemUser = new JwSystemUser();
          jwSystemUser.setUserId(username);
          jwSystemUser.setUserName(user.getUserName());
          jwSystemUser.setPassword(passwordEncode);
          jwSystemUser.setCreateDt(new Date());
          jwSystemUser.setUserStat("NORMAL");
          List roleIds = new ArrayList();
          roleIds.add("01");
          this.jwSystemUserService.doAdd(jwSystemUser, roleIds);
          isAuth = true;
        } else {
          LOG.info("登录验证失败：用户【" + username + "】捷微平台权限验证不通过");
        }
      } else {
        LOG.info("登录验证失败：用户【" + username + "】不存在");
      }

      if (isAuth) {
        LOG.info("登录验证成功：用户【" + username + "】权限验证通过");
        j.setSuccess(true);
        j.setMsg("登录验证成功");
        return j;
      }
      LOG.info("登录验证失败：用户【" + username + "】权限验证不通过");

      j.setSuccess(false);
      j.setMsg("登录验证失败");
    } catch (Exception e) {
      LOG.info("登录验证失败：用户【" + username + "】" + e.getMessage());
      j.setSuccess(false);
      j.setMsg("登录验证失败");
    }
    return j;
  }

  @RequestMapping(value={"/preLogin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void preLogin(String username, String password, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    String viewName = "base/back/common/prelogin.vm";
    try
    {
      LoginUser user = this.jwSystemUserService.queryUserByUserId(username);
      boolean isAuth = false;
      String passwordEncode = MD5Util.MD5Encode(password, "utf-8");

      if (user != null) {
        if ((passwordEncode != null) && (passwordEncode.equals(user.getPassword())) && ("NORMAL".equals(user.getUserStat())))
        {
          isAuth = true;
        }
        else LOG.info("登录验证失败：用户【" + username + "】登录信息验证不通过");
      }

      List jwids = new ArrayList();
      if (isAuth)
      {
        jwids = this.jwidService.getByUsername(username);
      }
      else LOG.info("登录验证失败：用户【" + username + "】权限验证不通过");

      velocityContext.put("jwids", jwids);
      velocityContext.put("sysJwssoFlag", this.sysJwssoFlag);
    } catch (Exception e) {
      LOG.info("登录验证失败：用户【" + username + "】" + e.getMessage());
    }
    ViewVelocity.view(request, response, viewName, velocityContext);
  }

  private LoginUser jwSSO(String username, String password)
  {
    return JwHttpUtil.jwSSO(username, password);
  }

  private void syncUserJwid(String username)
  {
    List jwidList = JwHttpUtil.getJwids(username);
  }

  @RequestMapping(value={"/syncJwid"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void syncJwid(String username, String password, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    String viewName = "base/back/common/prelogin.vm";
    try {
      syncUserJwid(username);
      List jwids = this.jwidService.getByUsername(username);
      if ((jwids != null) && (jwids.size() > 0))
        velocityContext.put("jwid_select_flag", "jwid_select_flag_success");
      else {
        velocityContext.put("jwid_select_flag", "jwid_select_flag_fail");
      }
      velocityContext.put("jwids", jwids);
    } catch (Exception e) {
      LOG.info("同步微信公众号失败：用户【" + username + "】" + e.getMessage());
    }
    ViewVelocity.view(request, response, viewName, velocityContext);
  }

  @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void login(String jwid, String username, String password, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String viewName = "login/index.vm";
    VelocityContext velocityContext = new VelocityContext();
    try {
      LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
      JwSystemLogoTitle logoTitle = (JwSystemLogoTitle)this.jwSystemLogoTitleService.queryLogoTitle().get(0);
      velocityContext.put("logoTitle", logoTitle);
      if (user != null)
      {
        viewName = "base/back/main/index.vm";
        velocityContext.put("jwidname", (String)request.getSession().getAttribute("jwidname"));
        velocityContext.put("userid", user.getUserId());
        velocityContext.put("username", user.getUserName());
        velocityContext.put("isbind", (Boolean)request.getSession().getAttribute("isbind"));
        try
        {
          LinkedHashMap menuTree = this.jwSystemAuthService.getSubMenuTree(user.getUserId(), null);
          velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
          ViewVelocity.view(request, response, viewName, velocityContext);
        } catch (Exception e) {
          e.printStackTrace();
        }
        return;
      }
      validateLoginParam("admin", username, password);

      List jwids = new ArrayList();
      
      //account表根据 用户名查找 绑定公众号，得出原始ID
      jwids= jwWebJwidService.getByUsername(username);
        //jwids = this.jwidService.queryJwWebJwidByUserId(openid);
    		  
      JwWebJwid jw =null;
      boolean isbind =false;
      if(jwids.size()>=1){
      	jw =(JwWebJwid) jwids.get(0);
      	isbind =true ;
      }else{
      	//jwids =  jwidService.queryJwWebJwidByUserId("admin");
          jwids= jwWebJwidService.getByUsername("admin");
      	jw =(JwWebJwid) jwids.get(0);
      }
      
      
      user = this.jwSystemUserService.queryUserByUserId(username);
      if (user != null) {
        String passwordEncode = MD5Util.MD5Encode(password, "utf-8");
        if ((passwordEncode != null) && (passwordEncode.equals(user.getPassword())) && ("NORMAL".equals(user.getUserStat())))
        {
          if (jw != null) {
            request.getSession().setAttribute("jwid", jw.getJwid());
            request.getSession().setAttribute("jwidname", jw.getName());
            request.getSession().setAttribute("isbind", isbind);
            request.getSession().setAttribute("OPERATE_WEB_LOGIN_USER", user);
            velocityContext.put("jwidname", jw.getName());
            velocityContext.put("userid", user.getUserId());
            velocityContext.put("username", user.getUserName());
            try
            {
              LinkedHashMap menuTree = this.jwSystemAuthService.getSubMenuTree(user.getUserId(), null);
              velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
              viewName = "base/back/main/index.vm";
              ViewVelocity.view(request, response, viewName, velocityContext);
            } catch (Exception e) {
              e.printStackTrace();
            }
            return;
          }
          LOG.info("登录失败：jwid【" + jwid + "】不属于用户【" + username + "】");
        }
      }
      else {
        LOG.info("登录失败：用户【" + username + "】不存在");
        velocityContext.put("error", "登录失败：用户【" + username + "】不存在");
      }
    }
    catch (Exception e)
    {
      LOG.info("登录失败：用户【" + username + "】" + e.getMessage());
      velocityContext.put("error", "登录验证失败");
    }
    ViewVelocity.view(request, response, viewName, velocityContext);
  }

  
  @RequestMapping(value={"/checklogin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  @ResponseBody
  public AjaxJson checklogin(String username, String password, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
	    AjaxJson j = new AjaxJson();
    try {
      LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");

      if (user != null)
      {
          j.setSuccess(true);
          j.setMsg("登录验证成功");
          return j;
      }
      validateLoginParam("admin", username, password);

      List jwids = new ArrayList();
      
      //account表根据 用户名查找 绑定公众号，得出原始ID
      jwids= jwWebJwidService.getByUsername(username);
    		  
      JwWebJwid jw =null;
      boolean isbind =false;
      if(jwids.size()>=1){
      	jw =(JwWebJwid) jwids.get(0);
      	isbind =true ;
      }else{
          jwids= jwWebJwidService.getByUsername("admin");
      	jw =(JwWebJwid) jwids.get(0);
      }
      
      user = this.jwSystemUserService.queryUserByUserId(username);
      if (user != null) {
        String passwordEncode = MD5Util.MD5Encode(password, "utf-8");
        if ((passwordEncode != null) && (passwordEncode.equals(user.getPassword())) && ("NORMAL".equals(user.getUserStat())))
        {
          if (jw != null) {
              List<String> roleIds = new ArrayList();
              roleIds = jwSystemUserService.queryUserRoles(user.getUserId());
              request.getSession().setAttribute("roleIds", roleIds);
            request.getSession().setAttribute("jwid", jw.getJwid());
            request.getSession().setAttribute("jwidname", jw.getName());
            request.getSession().setAttribute("isbind", isbind);
            request.getSession().setAttribute("OPERATE_WEB_LOGIN_USER", user);
            j.setSuccess(true);
            j.setMsg("登录验证成功");
            return j;
          }
          j.setSuccess(false);
          j.setMsg("登录验证失败");
          return j;
        }
      }
      else {
        j.setSuccess(false);
        j.setMsg("登录失败：用户【" + username + "】不存在");
        return j;
      }
    }
    catch (Exception e)
    {
        j.setSuccess(false);
        j.setMsg("登录验证失败：用户【" + username + "】不存在");
        return j;
    }
    
    j.setSuccess(false);
    j.setMsg("登录验证失败：用户【" + username + "】不存在");
    return j;
  }
  
  
  private void validateLoginParam(String jwid, String username, String password) {
    if (StringUtils.isEmpty(username)) {
      throw new RuntimeException("登录用户为空");
    }
    if (StringUtils.isEmpty(password)) {
      throw new RuntimeException("用户密码为空");
    }
    if (StringUtils.isEmpty(jwid))
      throw new RuntimeException("微信公众号为空");
  }

  @RequestMapping(value={"/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
  public void logout(String jwid, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    VelocityContext velocityContext = new VelocityContext();
    JwSystemLogoTitle logoTitle = (JwSystemLogoTitle)this.jwSystemLogoTitleService.queryLogoTitle().get(0);
    velocityContext.put("logoTitle", logoTitle);
    request.getSession().removeAttribute("jwid");
    request.getSession().removeAttribute("jwidname");
    request.getSession().removeAttribute("isbind");
    request.getSession().removeAttribute("OPERATE_WEB_LOGIN_USER");
    response.sendRedirect("../qrLogin/index.html");
  }
  
  @RequestMapping(value = "/doReg",method ={RequestMethod.GET, RequestMethod.POST})
  @ResponseBody
  public AjaxJson doReg( HttpServletRequest request, HttpServletResponse response,@ModelAttribute JwSystemUser JwSystemUser)
  {
  
  AjaxJson j = new AjaxJson();
	
	String phone = request.getParameter("phone");
	String captcha = request.getParameter("captcha");
	String userid = (String) request.getSession().getAttribute("username");
	String email = request.getParameter("email");
	String username = request.getParameter("username");
	String password = request.getParameter("password");
	if(phone !=null && !phone.equals("") &&  password !=null && !password.equals("")&&  userid !=null && userid.equals(phone)
			&&  username !=null && !username.equals("")&&  email !=null && !email.equals("")){
		JwSystemUser jwSystemUser = new JwSystemUser();
		jwSystemUser.setCreateDt(new Date());
		jwSystemUser.setEmail(email);
		jwSystemUser.setUserId(userid);
		jwSystemUser.setUserName(username);
		jwSystemUser.setOpePhoneNum(phone);
		jwSystemUser.setPassword(MD5Util.MD5Encode(password, "utf-8"));
		jwSystemUser.setUserStat("NORMAL");
		LoginUser user1 = this.jwSystemUserService.queryUserByUserId(userid);
		if(user1 !=null ){
			j.setSuccess(false);
			j.setMsg("注册失败,该用户已注册"); 
			return j;
		}
        List<String> roleIds = new ArrayList();
        roleIds.add("01");
        this.jwSystemUserService.doAdd(jwSystemUser, roleIds);
	     List jwids = new ArrayList();
	     LoginUser user = new LoginUser();
	      JwWebJwid jw =null;
	      boolean isbind =false;
	        jwids= jwWebJwidService.getByUsername("admin");
	      	jw =(JwWebJwid) jwids.get(0); 
	          if (jw != null) {
	        	  isbind = true ;
	              request.getSession().setAttribute("jwid", jw.getJwid());
	              request.getSession().setAttribute("jwidname", jw.getName());
	              request.getSession().setAttribute("isbind", isbind);
	              request.getSession().setAttribute("roleIds", roleIds); 
	              user.setUserId(phone);
	              user.setUserName(username);
	              user.setUserStat("NORMAL");
	              request.getSession().setAttribute("OPERATE_WEB_LOGIN_USER", user);
	              j.setSuccess(true);
	              j.setMsg("注册成功");
	              return j;
	            }
	}
	j.setSuccess(false);
	j.setMsg("注册失败");
	return j;
  }
}
