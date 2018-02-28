package com.jeecg.p3.activity.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.activity.dao.ActivityDao;
import com.jeecg.p3.activity.entity.Activity;

/**
 * 描述：</b>ActivityDaoImpl<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
@Repository("activityDao")
public class ActivityDaoImpl extends GenericDaoDefault<Activity> implements ActivityDao{

	@Override
	public Integer count(PageQuery<Activity> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> queryPageList(
			PageQuery<Activity> pageQuery,Integer itemCount) {
		PageQueryWrapper<Activity> wrapper = new PageQueryWrapper<Activity>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Activity>)super.query("queryPageList",wrapper);
	}


}

