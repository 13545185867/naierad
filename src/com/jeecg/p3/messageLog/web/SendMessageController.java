package com.jeecg.p3.messageLog.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jeecgframework.p3.core.util.SystemTools;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.alicom.dysms.api.SmsDemo;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.jeecg.p3.messageLog.entity.SendMessageLog;
import com.jeecg.p3.messageLog.service.SendMessageLogService;
import com.jeecg.p3.system.vo.LoginUser;

import org.jeecgframework.p3.core.web.BaseController;

import redis.clients.jedis.Jedis;

 /**
 * 描述：</b>SendMessageLogController<br>
 * @author chao.hua
 * @since：2017年07月23日 20时27分22秒 星期日 
 * @version:1.0
 */ 
@Controller
@RequestMapping("/messageLog/")
public class SendMessageController extends BaseController{
  @Autowired
  private SendMessageLogService sendMessageLogService;
  
  @Value("#{sysconfig['redis.ip']}")
  private String redisip;

  @Value("#{sysconfig['dysms.checkmac']}")
  private String checkmac;
  
  @Value("#{sysconfig['dysms.countlimit']}")
  private String countlimit;
  
 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void sendMessageLogDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "messageLog/back/sendMessageLog-detail.vm";
		SendMessageLog sendMessageLog = sendMessageLogService.queryById(id);
		velocityContext.put("sendMessageLog",sendMessageLog);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "messageLog/back/sendMessageLog-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 * @throws Exception 
 */
@RequestMapping(value = "/sendMessge",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson sendMessge(HttpServletRequest request,@ModelAttribute SendMessageLog sendMessageLog) {
	AjaxJson j = new AjaxJson();
	
	LoginUser user = (LoginUser) request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	String userid = null ;
	if(user != null){
		userid = user.getUserId();
	}else {
		userid = "匿名";
	}
	String phonenum = request.getParameter("phonenum");
	String messageuuid = request.getParameter("messageuuid");
	try {
		if(messageuuid.equals(request.getSession().getAttribute("messageuuid")) && phonenum !=null){
			String ip;
				ip = sendMessageLogService.getIpAddr(request);
				String mac = null;
				if("Y".equals(this.checkmac)){
					 mac = sendMessageLogService.getMACAddress(ip);
				}else{
					 mac = null;
				}
				//
				
			sendMessageLog.setIp(ip);
			sendMessageLog.setMac(mac);
			sendMessageLog.setPhonenum(phonenum);
			
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
					0, 0, 0);
			Date beginOfDate = calendar1.getTime();
			
			sendMessageLog.setSendtime(beginOfDate);
			List<SendMessageLog> sendMessgeList =  sendMessageLogService.queryByIPMACPHONE(sendMessageLog);
			sendMessageLog.setSendtime(new Date());
			sendMessageLog.setUserid(userid);
			
			if(sendMessgeList.size()>=Integer.parseInt(countlimit)){
				j.setSuccess(false);
				j.setMsg("次数超出限制");
				sendMessageLog.setStatus("0");
				sendMessageLogService.doAdd(sendMessageLog);
				return j ;
			}else{
				String num= "";
				  for (int i = 0; i < 5; i++) {
					  num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
				    }
				
				
				Jedis resource = 	 new Jedis(redisip,6379);
				  resource.set(messageuuid, num);
				  resource.expire(messageuuid, 60);
				  resource.close();
				SendSmsResponse ssr = SmsDemo.sendSms(phonenum, num);
				System.out.print(num);
				if(ssr.getCode().equals("OK")){  
					sendMessageLog.setStatus("1");
					sendMessageLogService.doAdd(sendMessageLog);

					j.setSuccess(true);
					j.setMsg("发送成功");
				}else{
					sendMessageLog.setStatus("0");
					j.setSuccess(false);
					j.setMsg("发送失败");
				}
				return j ;
			}
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		j.setSuccess(false);
		j.setMsg("发送 失败");
		return j ;
	}
	j.setSuccess(false);
	j.setMsg("发送失败");
	return j ;
}

 
@RequestMapping(value = "/validMessge",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson validMessge(HttpServletRequest request,@ModelAttribute SendMessageLog sendMessageLog) {
	AjaxJson j = new AjaxJson();
	String phone = request.getParameter("phone");
	String captcha = request.getParameter("captcha");
	String messageuuid = (String) request.getSession().getAttribute("messageuuid");
	if(phone !=null && !phone.equals("") &&  captcha !=null && !captcha.equals("")&&  messageuuid !=null && !messageuuid.equals("")){
		
		Jedis resource = 	 new Jedis(redisip,6379);
		 String uuid =  resource.get(messageuuid);
		 resource.close();
		 if(uuid !=null && uuid.equals(captcha)){
				j.setSuccess(true);
				j.setMsg("验证成功");
				request.getSession().setAttribute("username",phone);
				return j ;
		 }
	}
	j.setSuccess(false);
	j.setMsg("验证失败");
	return j ;
	
}


/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 SendMessageLog sendMessageLog = sendMessageLogService.queryById(id);
		 velocityContext.put("sendMessageLog",sendMessageLog);
		 String viewName = "messageLog/back/sendMessageLog-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute SendMessageLog sendMessageLog){
	AjaxJson j = new AjaxJson();
	try {
		sendMessageLogService.doEdit(sendMessageLog);
		j.setMsg("编辑成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
	return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="doDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
			sendMessageLogService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

