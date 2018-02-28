package org.jeecgframework.core.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jeecgframework.p3.cg.util.MyTask;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import weixin.guanjia.core.util.LogUtil;

import com.jeecg.p3.messageHelp.entity.Messagesendtask;
import com.jeecg.p3.messageHelp.service.MessagesendtaskService;

public class TimerListener implements ServletContextListener{  
    private ServletContext context = null;  
    public TimerListener(){  
    }  

    //这个方法在ServletContext将要关闭的时候调用  
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
    //该方法在ServletContext启动之后调用，并准备好处理客户端请求  
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		  LogUtil.info("===================启动监听【设置当天短信发送定时任务】开始===================");
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		MessagesendtaskService messagesendtaskService = (MessagesendtaskService) wac.getBean("messagesendtaskService");
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
				 timer.schedule(new MyTask(mt.getId()), mt.getSendtime().getTime());
			}
		}
		  LogUtil.info("===================启动监听【设置当天短信发送定时任务】结束===================");
	}  
} 
