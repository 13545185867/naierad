package com.jeecg.p3.userinfo.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.userinfo.dao.UserinfoDao;
import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userservice.entity.Vipinfo;

/**
 * 描述：</b>UserinfoDaoImpl<br>
 * @author：chao.hua
 * @since：2017年09月11日 12时01分29秒 星期一 
 * @version:1.0
 */
@Repository("userinfoDao")
public class UserinfoDaoImpl extends GenericDaoDefault<Userinfo> implements UserinfoDao{

	@Override
	public Integer count(PageQuery<Userinfo> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Userinfo> queryPageList(
			PageQuery<Userinfo> pageQuery,Integer itemCount) {
		PageQueryWrapper<Userinfo> wrapper = new PageQueryWrapper<Userinfo>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Userinfo>)super.query("queryPageList",wrapper);
	}

	@Override
	public void updateByopenid(Userinfo user) {
		// TODO Auto-generated method stub
		super.queryOne("updateByopenid",user);
	}

	@Override
	public List<Userinfo> getByOpenId(String openid) {
		// TODO Auto-generated method stub
		return 	(List<Userinfo>) super.query("getByOpenId",openid);
	}

	@Override
	public List<Userinfo> queryList(PageQuery<Userinfo> pageQuery) {
		// TODO Auto-generated method stub
		return 	(List<Userinfo>) super.query("queryList",pageQuery);
	}

	@Override
	public List<Vipinfo> queryVip(PageQuery<Vipinfo> pageQuery) {
		// TODO Auto-generated method stub
		return 	(List<Vipinfo>) super.query("queryVip",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vipinfo> queryVipPageList(PageQuery<Vipinfo> pageQuery,
			Integer itemCount) {
		// TODO Auto-generated method stub
		PageQueryWrapper<Vipinfo> wrapper = new PageQueryWrapper<Vipinfo>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Vipinfo>)super.query("queryVip",wrapper);
	}

	@Override
	public Integer vipcount(PageQuery<Vipinfo> pageQuery) {
		// TODO Auto-generated method stub
		return (Integer) super.queryOne("countVip",pageQuery);
	}





}

