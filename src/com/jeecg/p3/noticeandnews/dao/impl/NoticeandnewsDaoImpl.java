package com.jeecg.p3.noticeandnews.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.noticeandnews.dao.NoticeandnewsDao;
import com.jeecg.p3.noticeandnews.entity.Noticeandnews;

/**
 * 描述：</b>NoticeandnewsDaoImpl<br>
 * @author：chao.hua
 * @since：2017年09月20日 15时38分14秒 星期三 
 * @version:1.0
 */
@Repository("noticeandnewsDao")
public class NoticeandnewsDaoImpl extends GenericDaoDefault<Noticeandnews> implements NoticeandnewsDao{

	@Override
	public Integer count(PageQuery<Noticeandnews> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Noticeandnews> queryPageList(
			PageQuery<Noticeandnews> pageQuery,Integer itemCount) {
		PageQueryWrapper<Noticeandnews> wrapper = new PageQueryWrapper<Noticeandnews>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Noticeandnews>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Noticeandnews> queryActAndNEWList(
			PageQuery<Noticeandnews> pageQuery, Integer itemCount) {
		PageQueryWrapper<Noticeandnews> wrapper = new PageQueryWrapper<Noticeandnews>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Noticeandnews>)super.query("queryActAndNEWList",wrapper);
	}


}

