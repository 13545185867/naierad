package com.jeecg.p3.appmodel.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.appmodel.dao.AppmodelDao;
import com.jeecg.p3.appmodel.entity.Appmodel;

/**
 * 描述：</b>AppmodelDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时05分57秒 星期二 
 * @version:1.0
 */
@Repository("appmodelDao")
public class AppmodelDaoImpl extends GenericDaoDefault<Appmodel> implements AppmodelDao{

	@Override
	public Integer count(PageQuery<Appmodel> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appmodel> queryPageList(
			PageQuery<Appmodel> pageQuery,Integer itemCount) {
		PageQueryWrapper<Appmodel> wrapper = new PageQueryWrapper<Appmodel>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Appmodel>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Appmodel> queryAppList( Appmodel  pageQuery) {
		// TODO Auto-generated method stub
		return (List<Appmodel>)super.query("queryAppList",pageQuery);
	}


}

