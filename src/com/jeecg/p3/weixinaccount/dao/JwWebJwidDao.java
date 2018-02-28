package com.jeecg.p3.weixinaccount.dao;

import java.util.Date;
import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.weixinaccount.entity.JwWebJwid;

/**
 * 描述：</b>JwWebJwidDao<br>
 * @author：chao.hua
 * @since：2017年07月21日 18时09分15秒 星期五 
 * @version:1.0
 */
public interface JwWebJwidDao extends GenericDao<JwWebJwid>{
	
	public Integer count(PageQuery<JwWebJwid> pageQuery);
	
	public List<JwWebJwid> queryPageList(PageQuery<JwWebJwid> pageQuery,Integer itemCount);
	
	public List<JwWebJwid> getByAddTicketTime(Date addtickettime);
	
	public List<JwWebJwid> getByUsername(String username);
	
	public List<JwWebJwid>   getByJWid(String username);
	
	
}

