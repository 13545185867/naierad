package com.jeecg.p3.noticeandnews.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;

import com.jeecg.p3.activity.dao.ActivityDao;
import com.jeecg.p3.activity.entity.Activity;
import com.jeecg.p3.noticeandnews.service.NoticeandnewsService;
import com.jeecg.p3.noticeandnews.entity.Noticeandnews;
import com.jeecg.p3.noticeandnews.dao.NoticeandnewsDao;

@Service("noticeandnewsService")
public class NoticeandnewsServiceImpl implements NoticeandnewsService {
	@Resource
	private NoticeandnewsDao noticeandnewsDao;
	@Resource
	private ActivityDao activityDao;

	@Override
	public void doAdd(Noticeandnews noticeandnews) {
		noticeandnewsDao.add(noticeandnews);
	}

	@Override
	public void doEdit(Noticeandnews noticeandnews) {
		noticeandnewsDao.update(noticeandnews);
	}

	@Override
	public void doDelete(String id) {
		noticeandnewsDao.delete(id);
	}

	
	
	@Override
	public Noticeandnews queryById(String id) {
		Noticeandnews noticeandnews  = noticeandnewsDao.get(id);
		return noticeandnews;
	}

	@Override
	public PageList<Noticeandnews> queryPageList(
		PageQuery<Noticeandnews> pageQuery) {
		PageList<Noticeandnews> result = new PageList<Noticeandnews>();
		Integer itemCount = noticeandnewsDao.count(pageQuery);
		List<Noticeandnews> list = noticeandnewsDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public PageList<Noticeandnews> queryActAndNEWList(
			PageQuery<Noticeandnews> pageQuery) {
		// TODO Auto-generated method stub
		PageList<Noticeandnews> result = new PageList<Noticeandnews>();
		Integer itemCount = noticeandnewsDao.count(pageQuery);
		PageQuery<Activity> pageQuery1 = new PageQuery<Activity>();
		Activity a = new Activity();
		a.setIsdelete("0");
		pageQuery1.setQuery(a);
		
		itemCount = itemCount +activityDao.count(pageQuery1);

		List<Noticeandnews> list = noticeandnewsDao.queryActAndNEWList(pageQuery, itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<Noticeandnews> queryList(PageQuery<Noticeandnews> pageQuery) {
		// TODO Auto-generated method stub
		return noticeandnewsDao.queryPageList(pageQuery,0);
	}
	
}
