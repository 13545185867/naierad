package com.jeecg.p3.oauth2.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.jeecgframework.p3.cg.util.MyTask;
import org.springframework.beans.factory.annotation.Autowired;

import weixin.guanjia.core.util.LogUtil;

import com.jeecg.p3.messageHelp.entity.Messagesendtask;
import com.jeecg.p3.messageHelp.service.MessagesendtaskService;

public class ProductSendMessageTime {
	  @Autowired
	  private MessagesendtaskService messagesendtaskService;
	
	public void productTimer() {
		  LogUtil.info("===================定时任务【设置短信发送定时器】开始===================");
		Messagesendtask messagetask = new Messagesendtask();
		messagetask.setCheckresult(0);
		Calendar todayEnd = Calendar.getInstance();
		todayEnd.set(Calendar.HOUR, 23);
		todayEnd.set(Calendar.MINUTE, 59);
		todayEnd.set(Calendar.SECOND, 59);
		todayEnd.set(Calendar.MILLISECOND, 999);
		messagetask.setSendtime(todayEnd.getTime());
		messagetask.setSendtype(0);
		messagetask.setCheckresult(1);
		messagetask.setAttr4("1");
		List<Messagesendtask> tasklist = messagesendtaskService.getPendingTaskList(messagetask);
		Timer timer = new Timer();  
		if(tasklist.size() >= 0){
			for(Messagesendtask mt:tasklist){
				if(mt.getSendtime().getTime()<= (new Date().getTime())){
					timer.schedule(new MyTask(mt.getId()), new Date(new Date().getTime() + 6000));
				}else{
					 timer.schedule(new MyTask(mt.getId()), mt.getSendtime().getTime());
				}
			}
		}
		  LogUtil.info("===================定时任务【设置短信发送定时器】结束===================");
	}
}
