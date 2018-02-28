package com.jeecg.p3.activitybookrecord.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.activitybookrecord.service.ActivitybookrecordService;
import com.jeecg.p3.activitybookrecord.entity.Activitybookrecord;
import com.jeecg.p3.activitybookrecord.dao.ActivitybookrecordDao;

@Service("activitybookrecordService")
public class ActivitybookrecordServiceImpl implements ActivitybookrecordService {
	@Resource
	private ActivitybookrecordDao activitybookrecordDao;

	@Override
	public void doAdd(Activitybookrecord activitybookrecord) {
		activitybookrecordDao.add(activitybookrecord);
	}

	@Override
	public void doEdit(Activitybookrecord activitybookrecord) {
		activitybookrecordDao.update(activitybookrecord);
	}

	@Override
	public void doDelete(String id) {
		activitybookrecordDao.delete(id);
	}

	@Override
	public Activitybookrecord queryById(String id) {
		Activitybookrecord activitybookrecord  = activitybookrecordDao.get(id);
		return activitybookrecord;
	}

	@Override
	public PageList<Activitybookrecord> queryPageList(
		PageQuery<Activitybookrecord> pageQuery) {
		PageList<Activitybookrecord> result = new PageList<Activitybookrecord>();
		Integer itemCount = activitybookrecordDao.count(pageQuery);
		List<Activitybookrecord> list = activitybookrecordDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public Integer queryListCount(Activitybookrecord pageQuery) {
		// TODO Auto-generated method stub
		return activitybookrecordDao.queryListCount(pageQuery);
	}

	@Override
	public List<Activitybookrecord> queryList(Activitybookrecord pageQuery) {
		// TODO Auto-generated method stub
		return activitybookrecordDao.queryList(pageQuery);
	}

	@Override
	public PageList<Activitybookrecord> queryBookedPageList(
			PageQuery<Activitybookrecord> pageQuery) {
		// TODO Auto-generated method stub
		PageList<Activitybookrecord> result = new PageList<Activitybookrecord>();
		Integer itemCount = activitybookrecordDao.count(pageQuery);
		List<Activitybookrecord> list = activitybookrecordDao.queryBookedPageList(pageQuery, itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
