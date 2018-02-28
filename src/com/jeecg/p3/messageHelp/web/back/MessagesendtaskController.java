package com.jeecg.p3.messageHelp.web.back;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jeecgframework.p3.cg.util.BaihuaUtil;
import org.jeecgframework.p3.cg.util.MyTask;
import org.jeecgframework.p3.cg.util.PropertyUtil;
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

import com.jeecg.p3.messageHelp.entity.Messagesendtask;
import com.jeecg.p3.messageHelp.service.MessagesendtaskService;
import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.userservice.entity.Userservice;
import com.jeecg.p3.userservice.service.UserserviceService;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>MessagesendtaskController<br>
 * @author chao.hua
 * @since：2017年08月18日 13时08分48秒 星期五 
 * @version:1.0
 */
@Controller
@RequestMapping("/messageHelp/back/messagesendtask")
public class MessagesendtaskController extends BaseController{
  @Autowired
  private MessagesendtaskService messagesendtaskService;
  
  @Value("#{sysconfig['messageAppid']}")
  private String messageAppid;
  
  @Autowired
  private UserserviceService userserviceService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Messagesendtask query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	    query.setSenduser(user.getUserId());
	    //如果是管理员角色可以查看所有人发的
	    if(BaihuaUtil.isadmin((List<String>) request.getSession().getAttribute("roleIds"))){
	    	query.setSenduser(null);
	    }
	    query.setIsdelete(0);
	 	PageQuery<Messagesendtask> pageQuery = new PageQuery<Messagesendtask>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("messagesendtask",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(messagesendtaskService.queryPageList(pageQuery)));
		String viewName = "messageHelp/back/messagesendtask-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void messagesendtaskDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "messageHelp/back/messagesendtask-detail.vm";
		Messagesendtask messagesendtask = messagesendtaskService.queryById(Integer.parseInt(id));
		velocityContext.put("messagesendtask",messagesendtask);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	 Userservice userservice = new Userservice();
	 userservice.setUserservicecol0(this.messageAppid);
	 userservice.setUserid(user.getUserId());
	 List<Userservice> userlist = userserviceService.queryByRecord(userservice);
	 if(userlist.size()<=0){
		 velocityContext.put("isbuyed", false);
	 }
	 String viewName = "messageHelp/back/messagesendtask-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到短信余量页
 * @return
 */
@RequestMapping(value = "/messageserviceleft",method ={RequestMethod.GET, RequestMethod.POST})
public void messageserviceleft(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	 Userservice userservice = new Userservice();
	 userservice.setUserservicecol0(this.messageAppid);
	 userservice.setUserid(user.getUserId());
	 List<Userservice> userlist = userserviceService.queryByRecord(userservice);
	 int i = 0;
	 for(Userservice u:userlist){
		 i = i+u.getCreatecount();
	 }
	 velocityContext.put("leftcount", i);
	 String viewName = "messageHelp/back/messageserviceleft-detail.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}


/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute Messagesendtask messagesendtask,HttpServletRequest request,HttpServletResponse response){
	AjaxJson j = new AjaxJson();
	try {
		 LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
		 Userservice userservice = new Userservice();
		 userservice.setUserservicecol0(this.messageAppid);
		 userservice.setUserid(user.getUserId());
		 List<Userservice> userlist = userserviceService.queryByRecord(userservice);
		 if(userlist.size()<=0){
				j.setSuccess(false);
				j.setMsg("该项服务付费后方能使用，请在线购买！");
				return j;
		 }
		 int i = 0;
		 for(Userservice u:userlist){
			 i = i+u.getCreatecount();
		 }
		 int phonenum = messagesendtask.getPhonenum().split(",").length;
		 if(i<phonenum){
				j.setSuccess(false);
				j.setMsg("短信余量不足，请购买或减少群发号码！");
				return j;
		 }
		 if(messagesendtask.getMessagetype().equals(0)){
			 messagesendtask.setCheckresult(1);
		 }
		 messagesendtask.setSendtype(0);
		 messagesendtask.setSenduser(user.getUserId());
		 messagesendtaskService.doAdd(messagesendtask);
		 Timer timer = new Timer();    
		 if(messagesendtask.getMessagetype().equals(0)){
			 //未设置定时发送，或者设置的时间比当前时间早，就按当前时间发送
			 if(messagesendtask.getSendtime() ==null || messagesendtask.getSendtime().getTime()<new Date().getTime() ){
				 timer.schedule(new MyTask(messagesendtask.getId()), new Date(new Date().getTime() + 6000));//在10秒后执行此任务.     
			 }else{
				 //如果设置的时间不在今天，就不添加到定时器
				 if(messagesendtask.getSendtime().getDay()==new Date().getDay()){
					 timer.schedule(new MyTask(messagesendtask.getId()), new Date(messagesendtask.getSendtime().getTime() + 60000));//在原来基础上再加一分钟后执行此任务.
				 }
			 }
		 }
		 //添加短信任务后，修改短信余量
		 for(int a = 0 ;a<userlist.size();a++){
			  userserviceService.doDelete(userlist.get(0).getId().toString());
			  if(a== userlist.size()-1){
				  userservice = userlist.get(0);
				  userservice.setId(null);
				  userservice.setCreatecount(i-phonenum);
				  userserviceService.doAdd(userservice);
			  }
		 }
		 
		j.setMsg("保存成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 Messagesendtask messagesendtask = messagesendtaskService.queryById(Integer.parseInt(id));
		 velocityContext.put("messagesendtask",messagesendtask);
		 String viewName = "messageHelp/back/messagesendtask-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Messagesendtask messagesendtask,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
	boolean isadmin =false;
	List<String> rolelist = (List<String>) request.getSession().getAttribute("roleIds");
	for(String role :rolelist){
		if(role.equals("00")){
			isadmin=true;
		}
	}
	if(!isadmin){
		j.setSuccess(false);
		j.setMsg("您无权编辑");
		return j;
	}
	try {
		messagesendtaskService.doEdit(messagesendtask);
		 Timer timer = new Timer();    
		 if(messagesendtask.getCheckresult().equals(1)){
			 //未设置定时发送，或者设置的时间比当前时间早，就按当前时间发送
			 if(messagesendtask.getSendtime() ==null || messagesendtask.getSendtime().getTime()<new Date().getTime() ){
				 timer.schedule(new MyTask(messagesendtask.getId()), new Date(new Date().getTime() + 6000));//在10秒后执行此任务.     
			 }else{
				 //如果设置的时间不在今天，就不添加到定时器
				 if(messagesendtask.getSendtime().getDay()==new Date().getDay()){
					 timer.schedule(new MyTask(messagesendtask.getId()), new Date(messagesendtask.getSendtime().getTime() + 60000));//在原来基础上再加一分钟后执行此任务.
				 }
			 }
		 }
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
public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try {
			Messagesendtask query = new Messagesendtask();
			query.setId(Integer.parseInt(id));
			query.setIsdelete(1);
			//假删除，修改标识为1
			messagesendtaskService.doEdit(query);
			//如果是待发送状态，删除的要返还用户的余量
			PageQuery<Messagesendtask>  pageQuery = new PageQuery<Messagesendtask>();
			pageQuery.setPageNo(0);
			pageQuery.setPageSize(10);
			pageQuery.setQuery(query);
			//得到删除的短信任务，判断发送状态，如果sendtype为0就返还
			query = messagesendtaskService.queryPageList(pageQuery).getValues().get(0);
			if(query.getSendtype().equals(0)){
				 LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
				 Userservice userservice = new Userservice();
				 userservice.setUserservicecol0(this.messageAppid);
				 userservice.setUserid(user.getUserId());
				 //得到用户服务余量记录
				 List<Userservice> userlist = userserviceService.queryByRecord(userservice);
				 if(userlist.size()>0){
				 userservice.setCreatecount(userlist.get(0).getCreatecount()+Integer.parseInt(query.getPhonenum())); 
				 userservice.setId(userlist.get(0).getId());
				 //修改用户余量
				 userserviceService.doEdit(userservice);
				 }
			}
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="doDel",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDel(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
			messagesendtaskService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/**
 * 所有要群发的任务列表页面
 * @return
 */
@RequestMapping(value="getAllTask",method = {RequestMethod.GET,RequestMethod.POST})
public void getAllTasklist(@ModelAttribute Messagesendtask query,HttpServletResponse response,HttpServletRequest request) throws Exception{
	 	VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("messagesendtask",query);
		velocityContext.put("pageInfos",messagesendtaskService.getPendingTaskList(query));
		String viewName = "messageHelp/back/getAllTask-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

}

