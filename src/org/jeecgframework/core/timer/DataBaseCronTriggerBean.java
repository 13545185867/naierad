package org.jeecgframework.core.timer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerBean;

import com.jeecg.p3.timetask.entity.TSTimetask;
import com.jeecg.p3.timetask.service.TSTimetaskService;
/**
 * 在原有功能的基础上面增加数据库的读取
 * @author JueYue
 * @date 2013-9-22
 * @version 1.0
 */
public class DataBaseCronTriggerBean extends CronTriggerBean{

	private static final long serialVersionUID = 1L;
	
	
	  @Autowired
	  private TSTimetaskService tSTimetaskService;
	/**
	 * 读取数据库更新文件
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		List<TSTimetask>  task1 = tSTimetaskService.getByTaskID(this.getName());
		TSTimetask task = task1.get(0)	;
		if(task!=null&&task.getIsEffect().equals("1")
				&&!task.getCronExpression().equals(this.getCronExpression())){
			this.setCronExpression(task.getCronExpression());
			DynamicTask.updateSpringMvcTaskXML(this,task.getCronExpression());
		}
	}

}
