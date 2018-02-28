package com.jeecg.p3.activity.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.activity.service.ActivityService;
import com.jeecg.p3.activity.entity.Activity;
import com.jeecg.p3.activity.dao.ActivityDao;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {
	@Resource
	private ActivityDao activityDao;

	@Override
	public void doAdd(Activity activity) {
		activityDao.add(activity);
	}

	@Override
	public void doEdit(Activity activity) {
		activityDao.update(activity);
	}

	@Override
	public void doDelete(String id) {
		activityDao.delete(id);
	}

	@Override
	public Activity queryById(String id) {
		Activity activity  = activityDao.get(id);
		return activity;
	}

	@Override
	public PageList<Activity> queryPageList(
		PageQuery<Activity> pageQuery) {
		PageList<Activity> result = new PageList<Activity>();
		Integer itemCount = activityDao.count(pageQuery);
		List<Activity> list = activityDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<Activity> queryList(PageQuery<Activity> pageQuery) {
		// TODO Auto-generated method stub
		return activityDao.queryPageList(pageQuery,0);
	}
	
}
