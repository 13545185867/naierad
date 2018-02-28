package com.jeecg.p3.activity.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.activity.entity.Activity;

/**
 * 描述：</b>ActivityDao<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
public interface ActivityDao extends GenericDao<Activity>{
	
	public Integer count(PageQuery<Activity> pageQuery);
	
	public List<Activity> queryPageList(PageQuery<Activity> pageQuery,Integer itemCount);
	
}

