package com.jeecg.p3.dictinfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.dao.DictinfoDao;

@Service("dictinfoService")
public class DictinfoServiceImpl implements DictinfoService {
	@Resource
	private DictinfoDao dictinfoDao;

	@Override
	public void doAdd(Dictinfo dictinfo) {
		dictinfoDao.add(dictinfo);
	}

	@Override
	public void doEdit(Dictinfo dictinfo) {
		dictinfoDao.update(dictinfo);
	}

	@Override
	public void doDelete(String id) {
		dictinfoDao.delete(id);
	}

	@Override
	public Dictinfo queryById(String id) {
		Dictinfo dictinfo  = dictinfoDao.get(id);
		return dictinfo;
	}

	@Override
	public PageList<Dictinfo> queryPageList(
		PageQuery<Dictinfo> pageQuery) {
		PageList<Dictinfo> result = new PageList<Dictinfo>();
		Integer itemCount = dictinfoDao.count(pageQuery);
		List<Dictinfo> list = dictinfoDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<Dictinfo> queryList( Dictinfo  pageQuery) {
		// TODO Auto-generated method stub
		return  dictinfoDao.queryList(pageQuery);
	}
	
}
