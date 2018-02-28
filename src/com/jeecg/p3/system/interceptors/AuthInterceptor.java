package com.jeecg.p3.system.interceptors;

import com.jeecg.p3.system.service.JwSystemAuthService;
import com.jeecg.p3.system.vo.LoginUser;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jeecgframework.p3.core.logger.Logger;
import org.jeecgframework.p3.core.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor
  implements HandlerInterceptor
{
  public static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

  @Autowired
  private JwSystemAuthService jwSystemAuthService;
  private List<String> excludeUrls;
  private String mode;

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
      return true;
    }
    String requestPath = getRequestPath(request);
    String basePath = request.getContextPath();
    request.setAttribute("basePath", basePath);
    if (this.excludeUrls.contains(requestPath))
    {
      return true;
    }if ((requestPath != null) && (requestPath.indexOf(".html") > -1)) {
      if ((requestPath != null) && (requestPath.indexOf("/back/") > -1)) {
        LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
        if (user == null) {
          String url = basePath + "/qrLogin/index.html";
          response.sendRedirect(url);
          return false;
        }

        if (!checkUriAuth(requestPath, user.getUserId())) {
          logger.debug("无操作权限！");
          response.setStatus(401);
          return false;
        }
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

  private boolean checkUriAuth(String requestPath, String userId)
  {
    requestPath = "/" + requestPath;
    List list = this.jwSystemAuthService.queryAuthByAuthContr(requestPath);
    if ((list == null) || (list.size() <= 0)) {
      return true;
    }
    List authList = this.jwSystemAuthService.queryAuthByUserIdAndAuthContr(userId, requestPath);
    if ((authList != null) && (authList.size() > 0)) {
      return true;
    }
    return false;
  }
}