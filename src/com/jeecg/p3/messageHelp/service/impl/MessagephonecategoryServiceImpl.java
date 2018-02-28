package com.jeecg.p3.messageHelp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.messageHelp.service.MessagephonecategoryService;
import com.jeecg.p3.messageHelp.entity.Messagephonecategory;
import com.jeecg.p3.messageHelp.dao.MessagephonecategoryDao;

@Service("messagephonecategoryService")
public class MessagephonecategoryServiceImpl implements MessagephonecategoryService {
	@Resource
	private MessagephonecategoryDao messagephonecategoryDao;

	@Override
	public void doAdd(Messagephonecategory messagephonecategory) {
		messagephonecategoryDao.add(messagephonecategory);
	}

	@Override
	public void doEdit(Messagephonecategory messagephonecategory) {
		messagephonecategoryDao.update(messagephonecategory);
	}

	@Override
	public void doDelete(String id) {
		messagephonecategoryDao.delete(id);
	}

	@Override
	public Messagephonecategory queryById(String id) {
		Messagephonecategory messagephonecategory  = messagephonecategoryDao.get(id);
		return messagephonecategory;
	}

	@Override
	public PageList<Messagephonecategory> queryPageList(
		PageQuery<Messagephonecategory> pageQuery) {
		PageList<Messagephonecategory> result = new PageList<Messagephonecategory>();
		Integer itemCount = messagephonecategoryDao.count(pageQuery);
		List<Messagephonecategory> list = messagephonecategoryDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
