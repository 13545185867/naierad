package org.jeecgframework.p3.core.util.plugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;
import org.jeecgframework.p3.core.common.utils.DataDictTool;
import org.springframework.beans.factory.InitializingBean;

public class ViewVelocity
  implements InitializingBean
{
  private static final String PAGE_ROOT = "content/";
  static final Logger logger = Logger.getLogger(ViewVelocity.class);
  private String propertiesFile;
  private Properties prop;

  public void setPropertiesFile(String propertiesFile)
  {
    this.propertiesFile = propertiesFile;
  }

  public void setProperties(Properties prop)
  {
    this.prop = prop;
  }

  public void afterPropertiesSet()
    throws Exception
  {
    try
    {
      Properties p = new Properties();
      if (this.propertiesFile != null)
        p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(this.propertiesFile));
      if (this.prop != null)
        p.putAll(this.prop);
      Velocity.init(p);
      if (logger.isInfoEnabled()) {
        logger.info("Loading VelocityEngine....");
        Enumeration en = p.propertyNames();
        while (en.hasMoreElements()) {
          String key = en.nextElement().toString();
          logger.info("\t" + key + "=" + p.getProperty(key));
        }
      }
    } catch (Exception e) {
      throw new Exception("启动Velocity失败", e);
    }
  }

  public static void view(HttpServletResponse response, String template)
    throws Exception
  {
    view(response, template, null);
  }

  @Deprecated
  public static void view(HttpServletResponse response, String template, VelocityContext velocityContext)
    throws Exception
  {
    if (logger.isDebugEnabled()) {
      logger.debug("Velocity loading：" + template);
    }
    HttpServletRequest request = ContextHolderUtils.getRequest();

    if (velocityContext == null) {
      velocityContext = new VelocityContext();
    }
    velocityContext.put("Format", new SimpleFormat());
    velocityContext.put("dictTool", new DataDictTool());
    velocityContext.put("dateTool", new DateTool());
    Enumeration<String> sessionattr =  request.getSession().getAttributeNames();
    // 遍历enumeration中的  
    while (sessionattr.hasMoreElements()) {  
    	// 获取session键值  
    	String name = sessionattr.nextElement().toString();  
        // 根据键值取session中的值  
        Object value = request.getSession().getAttribute(name);  
        velocityContext.put(name, value);
     }
    String basePath = request.getContextPath();
    logger.info("---------------basePath--------------" + basePath);
    velocityContext.put("basePath", basePath);

    StringWriter writer = new StringWriter();
    Velocity.mergeTemplate("content/" + template, "UTF-8", velocityContext, writer);

    outputToPage(request, response, writer.toString());
  }

  public static void view(HttpServletRequest request, HttpServletResponse response, String template, VelocityContext velocityContext) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Velocity loading：" + template);
    }
    if (velocityContext == null) {
      velocityContext = new VelocityContext();
    }
    velocityContext.put("Format", new SimpleFormat());
    velocityContext.put("dictTool", new DataDictTool());
    velocityContext.put("dateTool", new DateTool());
    Enumeration<String> sessionattr =  request.getSession().getAttributeNames();
    // 遍历enumeration中的  
    while (sessionattr.hasMoreElements()) {  
    	// 获取session键值  
    	String name = sessionattr.nextElement().toString();  
        // 根据键值取session中的值  
        Object value = request.getSession().getAttribute(name);  
        velocityContext.put(name, value);
     }
    
    String basePath = request.getContextPath();
    logger.info("---------------basePath--------------" + basePath);
    velocityContext.put("basePath", basePath);

    StringWriter writer = new StringWriter();
    Velocity.mergeTemplate("content/" + template, "UTF-8", velocityContext, writer);

    outputToPage(request, response, writer.toString());
  }

  private static void outputToPage(HttpServletRequest request, HttpServletResponse response, String content)
    throws Exception
  {
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-store");
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.println(content);
      writer.flush();
      writer.close();
    } catch (Exception e) {
      e.printStackTrace();
      try
      {
        if (writer != null)
          writer.close();
      }
      catch (Exception localException1)
      {
      }
    }
    finally
    {
      try
      {
        if (writer != null)
          writer.close();
      }
      catch (Exception localException2)
      {
      }
    }
  }
}