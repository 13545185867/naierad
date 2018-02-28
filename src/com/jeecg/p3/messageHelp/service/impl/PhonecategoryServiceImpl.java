package com.jeecg.p3.messageHelp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.messageHelp.service.PhonecategoryService;
import com.jeecg.p3.messageHelp.entity.Phonecategory;
import com.jeecg.p3.messageHelp.dao.PhonecategoryDao;

@Service("phonecategoryService")
public class PhonecategoryServiceImpl implements PhonecategoryService {
	@Resource
	private PhonecategoryDao phonecategoryDao;

	@Override
	public void doAdd(Phonecategory phonecategory) {
		phonecategoryDao.add(phonecategory);
	}

	@Override
	public void doEdit(Phonecategory phonecategory) {
		phonecategoryDao.update(phonecategory);
	}

	@Override
	public void doDelete(String id) {
		phonecategoryDao.delete(id);
	}

	@Override
	public Phonecategory queryById(String id) {
		Phonecategory phonecategory  = phonecategoryDao.get(id);
		return phonecategory;
	}

	@Override
	public PageList<Phonecategory> queryPageList(
		PageQuery<Phonecategory> pageQuery) {
		PageList<Phonecategory> result = new PageList<Phonecategory>();
		Integer itemCount = phonecategoryDao.count(pageQuery);
		List<Phonecategory> list = phonecategoryDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
