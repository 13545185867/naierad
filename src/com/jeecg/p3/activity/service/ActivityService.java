package com.jeecg.p3.activity.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.activity.entity.Activity;

/**
 * 描述：</b>ActivityService<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
public interface ActivityService {
	
	
	public void doAdd(Activity activity);
	
	public void doEdit(Activity activity);
	
	public void doDelete(String id);
	
	public Activity queryById(String id);
	
	public PageList<Activity> queryPageList(PageQuery<Activity> pageQuery);
	
	public  List<Activity> queryList(PageQuery<Activity> pageQuery);
	
}

