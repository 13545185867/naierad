package com.jeecg.p3.leavemessage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.leavemessage.service.LeavemessageService;
import com.jeecg.p3.leavemessage.entity.Leavemessage;
import com.jeecg.p3.leavemessage.dao.LeavemessageDao;

@Service("leavemessageService")
public class LeavemessageServiceImpl implements LeavemessageService {
	@Resource
	private LeavemessageDao leavemessageDao;

	@Override
	public void doAdd(Leavemessage leavemessage) {
		leavemessageDao.add(leavemessage);
	}

	@Override
	public void doEdit(Leavemessage leavemessage) {
		leavemessageDao.update(leavemessage);
	}

	@Override
	public void doDelete(String id) {
		leavemessageDao.delete(id);
	}

	@Override
	public Leavemessage queryById(String id) {
		Leavemessage leavemessage  = leavemessageDao.get(id);
		return leavemessage;
	}

	@Override
	public PageList<Leavemessage> queryPageList(
		PageQuery<Leavemessage> pageQuery) {
		PageList<Leavemessage> result = new PageList<Leavemessage>();
		Integer itemCount = leavemessageDao.count(pageQuery);
		List<Leavemessage> list = leavemessageDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
