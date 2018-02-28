package com.jeecg.p3.website.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.website.dao.ColContentDao;
import com.jeecg.p3.website.entity.ColContent;

/**
 * 描述：</b>ColContentDaoImpl<br>
 * @author：chao.hua
 * @since：2017年11月04日 13时51分37秒 星期六 
 * @version:1.0
 */
@Repository("colContentDao")
public class ColContentDaoImpl extends GenericDaoDefault<ColContent> implements ColContentDao{

	@Override
	public Integer count(PageQuery<ColContent> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColContent> queryPageList(
			PageQuery<ColContent> pageQuery,Integer itemCount) {
		PageQueryWrapper<ColContent> wrapper = new PageQueryWrapper<ColContent>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<ColContent>)super.query("queryPageList",wrapper);
	}


}

