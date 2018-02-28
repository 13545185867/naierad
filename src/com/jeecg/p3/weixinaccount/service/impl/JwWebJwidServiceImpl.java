package com.jeecg.p3.weixinaccount.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;

import com.jeecg.p3.weixinaccount.service.JwWebJwidService;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.dao.JwWebJwidDao;

@Service("jwWebJwidServicenew")
public class JwWebJwidServiceImpl implements JwWebJwidService {
	@Resource
	private JwWebJwidDao jwWebJwidnewDao;

	@Override
	public void doAdd(JwWebJwid jwWebJwid) {
		jwWebJwidnewDao.add(jwWebJwid);
	}

	@Override
	public void doEdit(JwWebJwid jwWebJwid) {
		jwWebJwidnewDao.update(jwWebJwid);
	}

	@Override
	public void doDelete(String id) {
		jwWebJwidnewDao.delete(id);
	}

	@Override
	public JwWebJwid queryById(String id) {
		JwWebJwid jwWebJwid  = jwWebJwidnewDao.get(id);
		return jwWebJwid;
	}

	@Override
	public PageList<JwWebJwid> queryPageList(
		PageQuery<JwWebJwid> pageQuery) {
		PageList<JwWebJwid> result = new PageList<JwWebJwid>();
		Integer itemCount = jwWebJwidnewDao.count(pageQuery);
		List<JwWebJwid> list = jwWebJwidnewDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<JwWebJwid> getByAddTicketTime(Date date) {
		// TODO Auto-generated method stub
		return jwWebJwidnewDao.getByAddTicketTime(date);
	}

	@Override
	public List<JwWebJwid> getByUsername(String username) {
		// TODO Auto-generated method stub
		return jwWebJwidnewDao.getByUsername(username);
	}

	@Override
	public List<JwWebJwid> getByJWid(String username) {
		// TODO Auto-generated method stub
		return jwWebJwidnewDao.getByJWid(username);
	}
	
}
