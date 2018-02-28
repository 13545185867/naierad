package com.jeecg.p3.core.interceptors;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jeecgframework.p3.cg.util.HttpsGetUtil;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.logger.LoggerFactory;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.jeecg.p3.oauth2.task.WeixinAccountTokenTask;
import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userinfo.service.UserinfoService;
import com.jeecg.p3.userservice.entity.Userservice;
import com.jeecg.p3.userservice.service.UserserviceService;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.service.JwWebJwidService;

public class FrontInterceptor
  implements HandlerInterceptor
{
  public static final Logger logger = LoggerFactory.getLogger(FrontInterceptor.class);
  private List<String> excludeUrls;
  private String mode;
  @Value("#{sysconfig['appid']}")
  private String appid;
  
  @Value("#{sysconfig['secret']}")
  private String secret;
  
  @Value("#{sysconfig['jwid']}")
  private String jwid;

  @Value("#{sysconfig['jlbid']}")
  private String jlbid;
  
  @Value("#{sysconfig['nosubscrible.page']}")
  private String page;  
  
  @Autowired
  private JwWebJwidService jwWebJwidServicenew;
  
  @Autowired
  private UserinfoService userinfoService;
  
  @Autowired
  private UserserviceService userserviceService;
  
  @Autowired
  private WeixinAccountTokenTask weixinAccountTokenTask;
  
  public List<String> getExcludeUrls()
  {
    return this.excludeUrls;
  }

  public void setExcludeUrls(List<String> excludeUrls) {
    this.excludeUrls = excludeUrls;
  }

  public String getMode() {
    return this.mode;
  }

  public void setMode(String mode) {
    this.mode = mode;
  }

  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
    throws Exception
  {
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
    throws Exception
  {
  }

  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
    throws Exception
  {
    if ("DEV".equals(this.mode)) {
	    request.getSession().setAttribute("vipuser",true);
    	Userinfo userinfo = new Userinfo();  
    	userinfo = userinfoService.getByOpenId("oHXea0xUmlWRAAfHlyiPuWIBu05Q").get(0);
    	request.getSession().setAttribute("OPERATE_ACTIVITY_USER",userinfo);
	    
      return true;
    }
    String requestPath = getRequestPath(request);
    String code = request.getParameter("code");
    if(code !=null){
    	String a ="";
    	a ="1";
    }
    String basePath = request.getContextPath();
    request.setAttribute("basePath", basePath);
    if (this.excludeUrls.contains(requestPath))
    {
      return true;
    }
    if ((requestPath != null) && (requestPath.indexOf(".html") > -1)) {
      if ((requestPath != null) && (requestPath.indexOf("/back/") > -1)) {
        return true;
      }
      Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
      if(user == null &&  code== null){
    	  String backurl = this.getRequestUrlWithParams(request);
    	  String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+backurl+"&response_type=code&scope=snsapi_base#wechat_redirect";
    	  response.sendRedirect(url);
          return false;
      }
      if(user != null && request.getSession().getAttribute("vipuser") == null ){
    	    Userservice userservice = new Userservice();
    	    userservice.setUserid(user.getOpenid());
    	    userservice.setUserservicecol0(jlbid);
    	    boolean vipuser  = false;
    	    List<Userservice> uslist = userserviceService.queryByRecord(userservice);
    	    if(uslist.size()>0){
    	        userservice = uslist.get(0);
    	        vipuser = true;
    	    }
    	    request.getSession().setAttribute("vipuser",vipuser);
      }
      if(user == null &&  code!= null){
	      String get_access_token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
	                + "appid="
	                + appid
	                + "&secret="
	                + secret
	                + "&code=CODE&grant_type=authorization_code";
	      get_access_token_url = get_access_token_url.replace("CODE", code);
	      String json = HttpsGetUtil.doHttpsGetJson(get_access_token_url);
	        net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(json);
	        String openid =null ;
	        try{
	        	 openid = jsonObject.getString("openid");
	        }catch(Exception e){
	      	  String backurl = this.getRequestUrlWithParams(request);
	      	backurl = backurl.replace("code", "c");
	      	backurl = backurl.replace(code, "");
	      	System.out.println(backurl);
	    	  String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+backurl+"&response_type=code&scope=snsapi_base#wechat_redirect";
	    	  response.sendRedirect(url);
	          return false;
	        }
	       
	        
	        JwWebJwid jw = jwWebJwidServicenew.getByJWid(jwid).get(0);
	        if(new Date().getTime() - jw.getAddtoekntime().getTime() >1000 * 3600 * 2){
	        	weixinAccountTokenTask.autoResetToken();
	        	jw = jwWebJwidServicenew.getByJWid(jwid).get(0);
	        }
			 String get_userinfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		        get_userinfo = get_userinfo.replace("ACCESS_TOKEN", jw.getAccountaccesstoken());
		        get_userinfo = get_userinfo.replace("OPENID", openid);
		        System.out.println(openid+"!"+jw.getAccountaccesstoken());
		        String userInfoJson = HttpsGetUtil.doHttpsGetJson(get_userinfo);
		      	System.out.println(userInfoJson);
		        net.sf.json.JSONObject userInfoJO = net.sf.json.JSONObject.fromObject(userInfoJson);
		        	user = new Userinfo();
		        	//以下代码是为了解决两个环境并行运行时，两个环境都获取accesstoken，导致另一个环境未到过期时间就提前过期的问题
		        	try{
			        	userInfoJO.getString("openid");
			        }catch(Exception e){
			        	weixinAccountTokenTask.resetToken(jwid);
			        	jw = jwWebJwidServicenew.getByJWid(jwid).get(0);
				        get_userinfo = get_userinfo.replace("ACCESS_TOKEN", jw.getAccountaccesstoken());
				        get_userinfo = get_userinfo.replace("OPENID", openid);
				        System.out.println(openid+"@"+jw.getAccountaccesstoken());
				         userInfoJson = HttpsGetUtil.doHttpsGetJson(get_userinfo);
				      	System.out.println("!!"+userInfoJson);
				         userInfoJO = net.sf.json.JSONObject.fromObject(userInfoJson);
			        }
		        	if(userInfoJO.getString("subscribe").equals("1")){
				        user.setOpenid(userInfoJO.getString("openid"));
				        user.setNickname(userInfoJO.getString("nickname"));
				        user.setSex(userInfoJO.getString("sex"));
				        user.setCity(userInfoJO.getString("city"));
				        user.setProvince(userInfoJO.getString("province"));
				        user.setCountry(userInfoJO.getString("country"));
				        user.setHeadpic(userInfoJO.getString("headimgurl"));
				        user.setSubscribe(userInfoJO.getString("subscribe"));
				        user.setSubscribeTime(new Date(Long.parseLong(userInfoJO.getString("subscribe_time"))));
				        user.setCreatetime(new Date());
				        PageQuery<Userinfo> pageQuery = new PageQuery<Userinfo>();
				        Userinfo u1 = new Userinfo();
				        u1.setOpenid(openid);
				        pageQuery.setQuery(u1);
				        Integer count = userinfoService.count(pageQuery);
					        if(count>0){
					        	userinfoService.updateByopenid(user);
					        }else{
					        	user.setStatus("1");
					        	userinfoService.doAdd(user);
					        }
			        }else{
				       	  response.sendRedirect(page);
				             return false;
			        }
		        	Userinfo userinfo = new Userinfo();  
		        	userinfo = userinfoService.getByOpenId(user.getOpenid()).get(0);
		        	if(userinfo.getStatus().equals("0")){
				       	  response.sendRedirect(page);
				             return false;
			        }
		  request.getSession().setAttribute("OPERATE_ACTIVITY_USER",userinfo);
          return true;
      }
      return true;
    }
    return true;
  }

  private String getRequestPath(HttpServletRequest request)
  {
    String requestPath = request.getRequestURI();
    requestPath = requestPath.substring(request.getContextPath().length() + 1);
    return requestPath;
  }

	public String getRequestUrlWithParams(HttpServletRequest request) {
		// TODO Auto-generated method stub
	   	  String backurl = request.getScheme()+"://"+request.getServerName()+request.getRequestURI();
	   	  if(request.getQueryString() !=null){
	   		backurl = backurl+"?"+request.getQueryString();
	   	  }
	   	try {
			backurl = URLEncoder.encode(backurl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	  return backurl;
	}
 
}