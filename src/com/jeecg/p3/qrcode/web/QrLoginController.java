package com.jeecg.p3.qrcode.web;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.cg.util.HttpsGetUtil;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeecg.p3.system.entity.JwSystemLogoTitle;
import com.jeecg.p3.system.entity.JwSystemRole;
import com.jeecg.p3.system.entity.JwSystemUser;
import com.jeecg.p3.system.service.JwSystemAuthService;
import com.jeecg.p3.system.service.JwSystemLogoTitleService;
import com.jeecg.p3.system.service.JwSystemRoleService;
import com.jeecg.p3.system.service.JwSystemUserService;
import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.service.JwWebJwidService;

import redis.clients.jedis.Jedis;

@Controller
@RequestMapping("/qrLogin")
public class QrLoginController extends BaseController {

	@Autowired
	  private JwSystemUserService jwSystemUserService;
	
	@Autowired
	  private JwSystemLogoTitleService jwSystemLogoTitleService;
	
	  @Autowired
	  private JwSystemAuthService jwSystemAuthService;
	  
	  @Autowired

	  @Value("#{sysconfig['sys.jwsso.flg']}")
	  private String sysJwssoFlag;
	  
	  @Value("#{sysconfig['appid']}")
	  private String wxappid;
	  
	  @Value("#{sysconfig['scanlogin.url']}")
	  private String scanloginurl;
	  
	  @Value("#{sysconfig['secret']}")
	  private String wxsecret;
	
	  @Value("#{sysconfig['redis.ip']}")
	  private String redisip;
	  
	  @Value("#{sysconfig['openid.password']}")
	  private String password;
	  
	  @Autowired
	  private JwWebJwidService jwWebJwidService;
	
	@RequestMapping(value="index",method = {RequestMethod.GET,RequestMethod.POST})
	public void getIndex(
			HttpServletResponse response
			,HttpServletRequest request,
			ModelMap model) throws Exception{
		
		VelocityContext velocityContext = new VelocityContext();
	     
	      LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	      JwSystemLogoTitle logoTitle = (JwSystemLogoTitle)this.jwSystemLogoTitleService.queryLogoTitle().get(0);
	      velocityContext.put("logoTitle", logoTitle);

	      if (user != null)
	      {
	       String  viewName = "base/back/main/index.vm";
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
		
		String uuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("uuid", uuid);
		String appid = wxappid;
		String redirectUrl = scanloginurl+uuid;
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+redirectUrl+"&response_type=code&scope=snsapi_userinfo#wechat_redirect";
		url = URLEncoder.encode(url);
		  String viewName = "login/index.vm";
		  velocityContext.put("url", url);
		  velocityContext.put("uuid", uuid);
		  ViewVelocity.view(response, viewName, velocityContext);
	}
	
	 @RequestMapping(value={"reg"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
	  public void toreg(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    VelocityContext velocityContext = new VelocityContext();
		String messageuuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("messageuuid", messageuuid);
		velocityContext.put("messageuuid", messageuuid);
	    String viewName = "login/reg.vm";
	    ViewVelocity.view(request, response, viewName, velocityContext);
	  }
	
	@RequestMapping(value="doLogin",method = {RequestMethod.GET,RequestMethod.POST})
	public void doLogin(
			HttpServletResponse response
			,HttpServletRequest request,
			ModelMap model) throws Exception{
		String uuid = request.getParameter("uuid");
		String code = request.getParameter("code");
		 String opid =  (String) request.getSession().getAttribute("openid");
		  VelocityContext velocityContext = new VelocityContext();

			String appid = wxappid;
			String secret = wxsecret;
		      String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
		                + "appid="
		                + appid
		                + "&secret="
		                + secret
		                + "&code=CODE&grant_type=authorization_code";
		        
		        String get_userinfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

		        get_access_token_url = get_access_token_url.replace("CODE", code);

		        String json = HttpsGetUtil.doHttpsGetJson(get_access_token_url);

		        JSONObject jsonObject = JSONObject.fromObject(json);
		        String access_token = jsonObject.getString("access_token");
		        String openid = jsonObject.getString("openid");

		        get_userinfo = get_userinfo.replace("ACCESS_TOKEN", access_token);
		        get_userinfo = get_userinfo.replace("OPENID", openid);

		        String userInfoJson = HttpsGetUtil.doHttpsGetJson(get_userinfo);

		        JSONObject userInfoJO = JSONObject.fromObject(userInfoJson);

		        String user_openid = userInfoJO.getString("openid");
		        String user_nickname = userInfoJO.getString("nickname");
		        String user_sex = userInfoJO.getString("sex");
		        String user_province = userInfoJO.getString("province");
		        String user_city = userInfoJO.getString("city");
		        String user_country = userInfoJO.getString("country");
		        String user_headimgurl = userInfoJO.getString("headimgurl");

		        request.getSession().setAttribute("openid", user_openid);
		        request.getSession().setAttribute("userInfoJO", userInfoJO);
		        velocityContext.put("openid", user_openid);
				  velocityContext.put("nickname", user_nickname);
				  velocityContext.put("province", user_province);
				  velocityContext.put("uuid", uuid);
				  Jedis resource = 	 new Jedis(redisip,6379);
				  resource.set(uuid, openid);
				  resource.set(openid, userInfoJson);
					resource.close();
	        String viewName = "login/loginResult.vm";
			  ViewVelocity.view(response, viewName, velocityContext);
	    }
	
	
	@RequestMapping(value = "/getUUID",method ={RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public AjaxJson getUUID(@RequestParam(required = true, value = "uuid" ) String uuid
			,HttpServletResponse response
			,HttpServletRequest request)throws Exception{
		String openid = null;
		if(uuid != null && !uuid.equals("") ){
			 Jedis resource = 	 new Jedis(redisip,6379);
			  openid =  resource.get(uuid);
			  resource.close();
		}
		
		AjaxJson j = new AjaxJson();
		if(openid !=null && !"".equals(openid)){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
			return j;
	}
	
	
	@RequestMapping(value="autoLogin",method = {RequestMethod.GET,RequestMethod.POST})
	public void autoLogin(
			HttpServletResponse response
			,HttpServletRequest request,
			ModelMap model) throws Exception{
		String uuid = request.getParameter("uuid");
		String openid = null;
	    JSONObject userInfoJO = null;
		String uui = (String) request.getSession().getAttribute("uuid");
        VelocityContext velocityContext = new VelocityContext();
		if(uuid.equals(uui) ){
			 Jedis resource = 	 new Jedis(redisip,6379);
			  openid =  resource.get(uuid);
			  userInfoJO =   new JSONObject().fromObject(resource.get(openid));
			  resource.del(uuid);
			  resource.close();
		}else{
		      
		}
		  LoginUser loguser = this.jwSystemUserService.queryUserByUserId(openid);
	  
	      
	      if (loguser == null)
	        {
	          JwSystemUser jwSystemUser = new JwSystemUser();
		        String user_openid = userInfoJO.getString("openid");
		        String user_nickname = userInfoJO.getString("nickname");
		        String user_sex = userInfoJO.getString("sex");
		        String user_province = userInfoJO.getString("province");
		        String user_city = userInfoJO.getString("city");
		        String user_country = userInfoJO.getString("country");
		        String user_headimgurl = userInfoJO.getString("headimgurl");
	          
	          jwSystemUser.setUserId(user_openid);
	          jwSystemUser.setUserName(user_nickname);
	          jwSystemUser.setPassword(password);
	          jwSystemUser.setCreateDt(new Date());
	          jwSystemUser.setUserStat("NORMAL");
	          jwSystemUser.setUserIcon(user_headimgurl);
	          List roleIds = new ArrayList();
	          roleIds.add("01");
	          this.jwSystemUserService.doAdd(jwSystemUser, roleIds);
		  }

	      String viewName = "base/back/common/login.vm";
          try {
            LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
            JwSystemLogoTitle logoTitle = (JwSystemLogoTitle)this.jwSystemLogoTitleService.queryLogoTitle().get(0);
            velocityContext.put("logoTitle", logoTitle);
//如果不 为空表明之前登录过，那么就直接进入首页
            if (user != null)
            {
              viewName = "base/back/main/index.vm";
              velocityContext.put("jwidname", (String)request.getSession().getAttribute("jwidname"));
              velocityContext.put("userid", user.getUserId());
              try
              {
                LinkedHashMap menuTree = jwSystemAuthService.getSubMenuTree(user.getUserId(), null);
                velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
                ViewVelocity.view(request, response, viewName, velocityContext);
              } catch (Exception e) {
                e.printStackTrace();
              }
              return;
            }
//如果为空，就要根据 当前openid查找 有没有绑定公众号
            List jwids = new ArrayList();
            
            //account表根据 用户名查找 绑定公众号，得出原始ID
            jwids= jwWebJwidService.getByUsername(openid);
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
            user = this.jwSystemUserService.queryUserByUserId(openid);
            if (user != null) {
              if (  ("NORMAL".equals(user.getUserStat())))
              {
                if (jw != null) {
                  request.getSession().setAttribute("jwid", jw.getJwid());
                  request.getSession().setAttribute("jwidname", jw.getName());
                  request.getSession().setAttribute("isbind", isbind);
                  request.getSession().setAttribute("OPERATE_WEB_LOGIN_USER", user);
                  List<String> roleIds = new ArrayList();
                  roleIds = jwSystemUserService.queryUserRoles(openid);
                  request.getSession().setAttribute("roleIds", roleIds);
                  velocityContext.put("jwidname", jw.getName());
                  velocityContext.put("userid", user.getUserId());
                  velocityContext.put("username", user.getUserName());
                  velocityContext.put("isbind", isbind);
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
                LOG.info("登录失败：jwid【" + "jwid" + "】不属于用户【" + "username" + "】");
              }
            }
            else {
              LOG.info("登录失败：用户【" + "username" + "】不存在");
            }
          }
          catch (Exception e)
          {
            LOG.info("登录失败：用户【" + "username" + "】" + e.getMessage());
          }
          ViewVelocity.view(request, response, viewName, velocityContext);
	}
}
