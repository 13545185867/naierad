package com.jeecg.p3.weixinaccount.dao.impl;

import java.util.Date;
import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.weixinaccount.dao.JwWebJwidDao;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;

/**
 * 描述：</b>JwWebJwidDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月21日 18时09分15秒 星期五 
 * @version:1.0
 */
@Repository("jwWebJwidnewDao")
public class JwWebJwidDaoImpl extends GenericDaoDefault<JwWebJwid> implements JwWebJwidDao{

	@Override
	public Integer count(PageQuery<JwWebJwid> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JwWebJwid> queryPageList(
			PageQuery<JwWebJwid> pageQuery,Integer itemCount) {
		PageQueryWrapper<JwWebJwid> wrapper = new PageQueryWrapper<JwWebJwid>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<JwWebJwid>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JwWebJwid> getByAddTicketTime(Date addtickettime) {
		// TODO Auto-generated method stub
		return (List<JwWebJwid>)super.query("getByAddTicketTime",addtickettime);
	}

	@Override
	public List<JwWebJwid> getByUsername(String username) {
		// TODO Auto-generated method stub
		return (List<JwWebJwid>)super.query("getByUsername",username);
	}

	@Override
	public List<JwWebJwid> getByJWid(String username) {
		// TODO Auto-generated method stub
		return    (List<JwWebJwid>)super.query("getByJWid",username);
	}


}

