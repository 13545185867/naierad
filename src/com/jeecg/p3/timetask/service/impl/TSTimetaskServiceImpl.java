package com.jeecg.p3.timetask.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.timetask.service.TSTimetaskService;
import com.jeecg.p3.timetask.entity.TSTimetask;
import com.jeecg.p3.timetask.dao.TSTimetaskDao;

@Service("tSTimetaskService")
public class TSTimetaskServiceImpl implements TSTimetaskService {
	@Resource
	private TSTimetaskDao tSTimetasknewDao;

	@Override
	public void doAdd(TSTimetask tSTimetask) {
		tSTimetasknewDao.add(tSTimetask);
	}

	@Override
	public void doEdit(TSTimetask tSTimetask) {
		tSTimetasknewDao.update(tSTimetask);
	}

	@Override
	public void doDelete(String id) {
		tSTimetasknewDao.delete(id);
	}

	@Override
	public TSTimetask queryById(String id) {
		TSTimetask tSTimetask  = tSTimetasknewDao.get(id);
		return tSTimetask;
	}

	@Override
	public PageList<TSTimetask> queryPageList(
		PageQuery<TSTimetask> pageQuery) {
		PageList<TSTimetask> result = new PageList<TSTimetask>();
		Integer itemCount = tSTimetasknewDao.count(pageQuery);
		List<TSTimetask> list = tSTimetasknewDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<TSTimetask> getByTaskID(String  TST) {
		// TODO Auto-generated method stub
		List<TSTimetask> tSTimetask =tSTimetasknewDao.getByTaskID(TST);
		return tSTimetask;
	}

	@Override
	public List<TSTimetask> getAllTask() {
		// TODO Auto-generated method stub
		return tSTimetasknewDao.getAllTask();
	}
	
}
