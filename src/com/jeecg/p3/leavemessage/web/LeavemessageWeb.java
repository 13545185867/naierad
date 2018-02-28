package com.jeecg.p3.leavemessage.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.jeecg.p3.leavemessage.entity.Leavemessage;
import com.jeecg.p3.leavemessage.service.LeavemessageService;
import com.jeecg.p3.messageLog.entity.SendMessageLog;
import com.jeecg.p3.messageLog.service.SendMessageLogService;
import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userinfo.service.UserinfoService;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>LeavemessageController<br>
 * @author chao.hua
 * @since：2017年09月17日 14时30分22秒 星期日 
 * @version:1.0
 */
@Controller
@RequestMapping("/leavemessage")
public class LeavemessageWeb extends BaseController{
  @Autowired
  private LeavemessageService leavemessageService;
  
  @Autowired
  private UserinfoService userinfoService;
  @Value("#{sysconfig['liuyansignname']}")
  private String liuyansignname;
  @Value("#{sysconfig['liuyantemplatename']}")
  private String liuyantemplatename;
  @Autowired
  private SendMessageLogService sendMessageLogService;
  
  @Value("#{sysconfig['dysms.checkmac']}")
  private String checkmac;
  
  @Value("#{sysconfig['dysms.countlimit']}")
  private String countlimit;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Leavemessage query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Leavemessage> pageQuery = new PageQuery<Leavemessage>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("leavemessage",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(leavemessageService.queryPageList(pageQuery)));
		String viewName = "leavemessage/back/leavemessage-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void leavemessageDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "leavemessage/back/leavemessage-detail.vm";
		Leavemessage leavemessage = leavemessageService.queryById(id);
		velocityContext.put("leavemessage",leavemessage);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "leavemessage/back/leavemessage-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Leavemessage leavemessage){
    Userinfo user1 = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
	AjaxJson j = new AjaxJson();
	String mobile = request.getParameter("mobile");
	String toopenid = request.getParameter("toopenid");
	String name = request.getParameter("name");
	String messagetext = request.getParameter("messagetext");
	Userinfo user = userinfoService.getByOpenId(toopenid).get(0);
	if(user != null && !user.getMobile().equals("")&& mobile !=null && name !=null & messagetext !=null){
		
		String userid   = user.getOpenid();
		SendMessageLog sendMessageLog = new SendMessageLog();

			String ip=null;
			String mac = null;
			try {
				ip = sendMessageLogService.getIpAddr(request);
				if("Y".equals(this.checkmac)){
					 mac = sendMessageLogService.getMACAddress(ip);
				}else{
					 mac = null;
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sendMessageLog.setIp(ip);
			sendMessageLog.setMac(mac);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
					0, 0, 0);
			Date beginOfDate = calendar1.getTime();
			
			sendMessageLog.setSendtime(beginOfDate);
			sendMessageLog.setUserid(userid);
			List<SendMessageLog> sendMessgeList =  sendMessageLogService.queryByIPMACPHONE(sendMessageLog);

			if(sendMessgeList.size()>=Integer.parseInt(countlimit)){
				j.setSuccess(false);
				j.setMsg("次数超出限制");
				return j ;
			}
		
		try {
			leavemessage.setToopenid(toopenid);
			leavemessage.setName(name);
			leavemessage.setMobile(mobile);
			leavemessage.setMessagetext(messagetext);
			leavemessage.setCreatetime(new Date());
			leavemessage.setFromopenid(null);
			if(user1 != null){
				leavemessage.setFromopenid(user.getOpenid());
			}
			leavemessage.setStatus("0");
			SendSmsResponse ssr = SmsDemo.sendSms(user.getMobile(), name, mobile, liuyansignname, liuyantemplatename);
			if(ssr.getCode().equals("OK")){  
				leavemessage.setIssend("1");
				sendMessageLog.setPhonenum(mobile);
				sendMessageLog.setSendtime(new Date());
				sendMessageLog.setStatus("1");
				j.setSuccess(true);
				j.setMsg("发送成功");
			}else{
				leavemessage.setIssend("0");
				j.setSuccess(false);
				j.setMsg("发送失败");
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("发送失败");
			leavemessage.setIssend("0");
		}
		sendMessageLogService.doAdd(sendMessageLog);
		leavemessageService.doAdd(leavemessage);
		return j;
	}
	j.setSuccess(false);
	j.setMsg("发送失败");
	return j;
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 Leavemessage leavemessage = leavemessageService.queryById(id);
		 velocityContext.put("leavemessage",leavemessage);
		 String viewName = "leavemessage/back/leavemessage-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Leavemessage leavemessage){
	AjaxJson j = new AjaxJson();
	try {
		leavemessageService.doEdit(leavemessage);
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
			leavemessageService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

