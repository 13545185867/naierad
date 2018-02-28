package com.jeecg.p3.timetask.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.timetask.dao.TSTimetaskDao;
import com.jeecg.p3.timetask.entity.TSTimetask;

/**
 * 描述：</b>TSTimetaskDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月21日 20时42分33秒 星期五 
 * @version:1.0
 */
@Repository("tSTimetasknewDao")
public class TSTimetaskDaoImpl extends GenericDaoDefault<TSTimetask> implements TSTimetaskDao{

	@Override
	public Integer count(PageQuery<TSTimetask> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TSTimetask> queryPageList(
			PageQuery<TSTimetask> pageQuery,Integer itemCount) {
		PageQueryWrapper<TSTimetask> wrapper = new PageQueryWrapper<TSTimetask>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<TSTimetask>)super.query("queryPageList",wrapper);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TSTimetask> getByTaskID(String TST) {
		// TODO Auto-generated method stub
		return  super.query("getByTaskID",TST);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TSTimetask> getAllTask() {
		// TODO Auto-generated method stub
		return (List<TSTimetask>)super.query("getAll");
	}



}

