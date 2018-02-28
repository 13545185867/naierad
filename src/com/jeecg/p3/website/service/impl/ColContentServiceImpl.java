package com.jeecg.p3.website.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.website.service.ColContentService;
import com.jeecg.p3.website.entity.ColContent;
import com.jeecg.p3.website.dao.ColContentDao;

@Service("colContentService")
public class ColContentServiceImpl implements ColContentService {
	@Resource
	private ColContentDao colContentDao;

	@Override
	public void doAdd(ColContent colContent) {
		colContentDao.add(colContent);
	}

	@Override
	public void doEdit(ColContent colContent) {
		colContentDao.update(colContent);
	}

	@Override
	public void doDelete(String id) {
		colContentDao.delete(id);
	}

	@Override
	public ColContent queryById(String id) {
		ColContent colContent  = colContentDao.get(id);
		return colContent;
	}

	@Override
	public PageList<ColContent> queryPageList(
		PageQuery<ColContent> pageQuery) {
		PageList<ColContent> result = new PageList<ColContent>();
		Integer itemCount = colContentDao.count(pageQuery);
		List<ColContent> list = colContentDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
