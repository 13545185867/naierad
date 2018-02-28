package com.jeecg.p3.appmodel.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.appmodel.service.AppmodelService;
import com.jeecg.p3.appmodel.entity.Appmodel;
import com.jeecg.p3.appmodel.dao.AppmodelDao;

@Service("appmodelService")
public class AppmodelServiceImpl implements AppmodelService {
	@Resource
	private AppmodelDao appmodelDao;

	@Override
	public void doAdd(Appmodel appmodel) {
		appmodelDao.add(appmodel);
	}

	@Override
	public void doEdit(Appmodel appmodel) {
		appmodelDao.update(appmodel);
	}

	@Override
	public void doDelete(String id) {
		appmodelDao.delete(id);
	}

	@Override
	public Appmodel queryById(String id) {
		Appmodel appmodel  = appmodelDao.get(id);
		return appmodel;
	}

	@Override
	public PageList<Appmodel> queryPageList(
		PageQuery<Appmodel> pageQuery) {
		PageList<Appmodel> result = new PageList<Appmodel>();
		Integer itemCount = appmodelDao.count(pageQuery);
		List<Appmodel> list = appmodelDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<Appmodel> queryAppList( Appmodel  pageQuery) {
		// TODO Auto-generated method stub
		return appmodelDao.queryAppList(pageQuery);
	}
	
}
