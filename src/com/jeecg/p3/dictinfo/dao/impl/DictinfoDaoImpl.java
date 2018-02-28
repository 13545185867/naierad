package com.jeecg.p3.dictinfo.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.dictinfo.dao.DictinfoDao;
import com.jeecg.p3.dictinfo.entity.Dictinfo;

/**
 * 描述：</b>DictinfoDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时04分33秒 星期二 
 * @version:1.0
 */
@Repository("dictinfoDao")
public class DictinfoDaoImpl extends GenericDaoDefault<Dictinfo> implements DictinfoDao{

	@Override
	public Integer count(PageQuery<Dictinfo> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictinfo> queryPageList(
			PageQuery<Dictinfo> pageQuery,Integer itemCount) {
		PageQueryWrapper<Dictinfo> wrapper = new PageQueryWrapper<Dictinfo>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Dictinfo>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dictinfo> queryList( Dictinfo  pageQuery) {
		// TODO Auto-generated method stub
		return (List<Dictinfo>)super.query("queryList",pageQuery);
	}


}

