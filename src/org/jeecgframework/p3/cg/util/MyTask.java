package org.jeecgframework.p3.cg.util;

import java.util.Date;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alicom.dysms.api.SmsDemo;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.jeecg.p3.messageHelp.entity.Messagesendtask;
import com.jeecg.p3.messageHelp.entity.Messagetemplate;
import com.jeecg.p3.messageHelp.service.MessagesendtaskService;
import com.jeecg.p3.messageHelp.service.MessagetemplateService;


public class MyTask extends TimerTask {
	private int taskid;
	
	 public MyTask(int taskid){   
	        this.taskid = taskid;  
	    }  
	
	@Override
	public void run() {
		
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		MessagesendtaskService messagesendtaskService = (MessagesendtaskService) wac.getBean("messagesendtaskService");
		MessagetemplateService messagetemplateService = (MessagetemplateService) wac.getBean("messagetemplateService");
		//查询短信TASK，判断是否取消发送，是否修改时间，是否已经发送，是否删除，如果任一为TRUE timertask.cancel
		Messagesendtask messagesendtask = messagesendtaskService.queryById(this.taskid);
		if(messagesendtask.getSendtime() == null){
			messagesendtask.setSendtime(new Date());
		}
		if(messagesendtask== null ||messagesendtask.getSendtime().getDay()!=new Date().getDay() || messagesendtask.getSendtype().equals(1) ||
				!messagesendtask.getCheckresult().equals(1)){
			this.cancel();
		}
		//如果没问题， 调用短信接口，开始发短信
		Messagetemplate messagetemplate = messagetemplateService.queryById(messagesendtask.getMessagetemplateid().toString());
		if(messagetemplate !=null){
			SendSmsResponse ssr = null;
			try {
				ssr = SmsDemo.sendSms(messagesendtask.getPhonenum(), messagesendtask.getAttr1(), messagesendtask.getAttr2(), messagesendtask.getAttr3(), messagetemplate.getAlimessagesinid(), messagetemplate.getAlimessagetempid(), messagesendtask.getMessagetype());
			} catch (ClientException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Messagesendtask messagesendtask1 = new Messagesendtask();
			messagesendtask1.setId(taskid);
			if(ssr.getCode().equals("OK")){  
				messagesendtask1.setSendtype(1);
				messagesendtaskService.doEdit(messagesendtask1);
			}else{
				messagesendtask1.setSendtype(2);
				messagesendtaskService.doEdit(messagesendtask1);
			}
		}
		this.cancel();
	}

}
