package org.jeecgframework.core.timer;

import java.util.List;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.jeecg.p3.timetask.entity.TSTimetask;
import com.jeecg.p3.timetask.service.TSTimetaskService;
/**
 * 读取数据库 然后判断是否启动任务
 * @author JueYue
 * @date 2013-9-22
 * @version 1.0
 */
public class DataBaseSchedulerFactoryBean extends SchedulerFactoryBean {
	
	  @Autowired
	  private TSTimetaskService tSTimetaskService;
	/**
	 * 读取数据库判断是否开始定时任务
	 */
	
	
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		String[] trigerrNames = this.getScheduler().getTriggerNames(Scheduler.DEFAULT_GROUP);
		TSTimetask  task = null ;
		for (String trigerrName : trigerrNames) {	
			List<TSTimetask>  task1 = tSTimetaskService.getByTaskID(trigerrName);
			 task = task1.get(0);
			if(task==null||!task.getIsEffect().equals("1") ){
				this.getScheduler().pauseTrigger(trigerrName,Scheduler.DEFAULT_GROUP);
			}
		}
	}

}
