package com.jeecg.p3.activitybookrecord.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.activitybookrecord.dao.ActivitybookrecordDao;
import com.jeecg.p3.activitybookrecord.entity.Activitybookrecord;

/**
 * 描述：</b>ActivitybookrecordDaoImpl<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时59分43秒 星期一 
 * @version:1.0
 */
@Repository("activitybookrecordDao")
public class ActivitybookrecordDaoImpl extends GenericDaoDefault<Activitybookrecord> implements ActivitybookrecordDao{

	@Override
	public Integer count(PageQuery<Activitybookrecord> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activitybookrecord> queryPageList(
			PageQuery<Activitybookrecord> pageQuery,Integer itemCount) {
		PageQueryWrapper<Activitybookrecord> wrapper = new PageQueryWrapper<Activitybookrecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Activitybookrecord>)super.query("queryPageList",wrapper);
	}

	@Override
	public Integer queryListCount(Activitybookrecord pageQuery) {
		// TODO Auto-generated method stub
		return (Integer)super.queryOne("queryListCount",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activitybookrecord> queryList(Activitybookrecord pageQuery) {
		// TODO Auto-generated method stub
		return (List<Activitybookrecord>)super.query("queryList",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activitybookrecord> queryBookedPageList(
			PageQuery<Activitybookrecord> pageQuery, Integer itemCount) {
		// TODO Auto-generated method stub
	PageQueryWrapper<Activitybookrecord> wrapper = new PageQueryWrapper<Activitybookrecord>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
	return (List<Activitybookrecord>)super.query("queryBookedPageList",wrapper);
	}


}

