package com.jeecg.p3.serviceprice.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.serviceprice.service.ServicepriceService;
import com.jeecg.p3.serviceprice.entity.Serviceprice;
import com.jeecg.p3.serviceprice.dao.ServicepriceDao;

@Service("servicepriceService")
public class ServicepriceServiceImpl implements ServicepriceService {
	@Resource
	private ServicepriceDao servicepriceDao;

	@Override
	public void doAdd(Serviceprice serviceprice) {
		servicepriceDao.add(serviceprice);
	}

	@Override
	public void doEdit(Serviceprice serviceprice) {
		servicepriceDao.update(serviceprice);
	}

	@Override
	public void doDelete(String id) {
		servicepriceDao.delete(id);
	}

	@Override
	public Serviceprice queryById(String id) {
		Serviceprice serviceprice  = servicepriceDao.get(id);
		return serviceprice;
	}

	@Override
	public PageList<Serviceprice> queryPageList(
		PageQuery<Serviceprice> pageQuery) {
		PageList<Serviceprice> result = new PageList<Serviceprice>();
		Integer itemCount = servicepriceDao.count(pageQuery);
		List<Serviceprice> list = servicepriceDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
