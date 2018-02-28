package com.jeecg.p3.userservice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.userservice.service.UserserviceService;
import com.jeecg.p3.userservice.entity.Userservice;
import com.jeecg.p3.userservice.dao.UserserviceDao;

@Service("userserviceService")
public class UserserviceServiceImpl implements UserserviceService {
	@Resource
	private UserserviceDao userserviceDao;

	@Override
	public void doAdd(Userservice userservice) {
		userserviceDao.add(userservice);
	}

	@Override
	public void doEdit(Userservice userservice) {
		userserviceDao.update(userservice);
	}

	@Override
	public void doDelete(String id) {
		userserviceDao.delete(id);
	}

	@Override
	public Userservice queryById(String id) {
		Userservice userservice  = userserviceDao.get(id);
		return userservice;
	}

	@Override
	public PageList<Userservice> queryPageList(
		PageQuery<Userservice> pageQuery) {
		PageList<Userservice> result = new PageList<Userservice>();
		Integer itemCount = userserviceDao.count(pageQuery);
		List<Userservice> list = userserviceDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<Userservice> queryByRecord(Userservice userservice) {
		// TODO Auto-generated method stub
		return userserviceDao.queryByRecord(userservice);
	}
	
}
