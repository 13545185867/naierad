package com.jeecg.p3.viewcount.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.viewcount.service.ViewcountService;
import com.jeecg.p3.viewcount.entity.Viewcount;
import com.jeecg.p3.viewcount.dao.ViewcountDao;

@Service("viewcountService")
public class ViewcountServiceImpl implements ViewcountService {
	@Resource
	private ViewcountDao viewcountDao;

	@Override
	public void doAdd(Viewcount viewcount) {
		viewcountDao.add(viewcount);
	}

	@Override
	public void doEdit(Viewcount viewcount) {
		viewcountDao.update(viewcount);
	}

	@Override
	public void doDelete(String id) {
		viewcountDao.delete(id);
	}

	@Override
	public Viewcount queryById(String id) {
		Viewcount viewcount  = viewcountDao.get(id);
		return viewcount;
	}

	@Override
	public PageList<Viewcount> queryPageList(
		PageQuery<Viewcount> pageQuery) {
		PageList<Viewcount> result = new PageList<Viewcount>();
		Integer itemCount = viewcountDao.count(pageQuery);
		List<Viewcount> list = viewcountDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public void updateCount(Viewcount vc) {
		// TODO Auto-generated method stub
		viewcountDao.updateCount(vc);
	}
	
}
