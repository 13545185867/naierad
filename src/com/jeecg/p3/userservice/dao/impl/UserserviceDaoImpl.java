package com.jeecg.p3.userservice.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.userservice.dao.UserserviceDao;
import com.jeecg.p3.userservice.entity.Userservice;

/**
 * 描述：</b>UserserviceDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时09分14秒 星期二 
 * @version:1.0
 */
@Repository("userserviceDao")
public class UserserviceDaoImpl extends GenericDaoDefault<Userservice> implements UserserviceDao{

	@Override
	public Integer count(PageQuery<Userservice> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Userservice> queryPageList(
			PageQuery<Userservice> pageQuery,Integer itemCount) {
		PageQueryWrapper<Userservice> wrapper = new PageQueryWrapper<Userservice>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Userservice>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Userservice> queryByRecord(Userservice userservice) {
		// TODO Auto-generated method stub
		return (List<Userservice>)super.query("queryByRecord",userservice);
	}


}

