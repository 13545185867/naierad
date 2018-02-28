package com.jeecg.p3.weixinaccount.service;

import java.util.Date;
import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.weixinaccount.entity.JwWebJwid;

/**
 * 描述：</b>JwWebJwidService<br>
 * @author：chao.hua
 * @since：2017年07月21日 18时09分15秒 星期五 
 * @version:1.0
 */
public interface JwWebJwidService {
	
	
	public void doAdd(JwWebJwid jwWebJwid);
	
	public void doEdit(JwWebJwid jwWebJwid);
	
	public void doDelete(String id);
	
	public JwWebJwid queryById(String id);
	
	public PageList<JwWebJwid> queryPageList(PageQuery<JwWebJwid> pageQuery);
	
	public List<JwWebJwid> getByAddTicketTime(Date date);
	public List<JwWebJwid> getByUsername(String username);
	
	public List<JwWebJwid>   getByJWid(String username);
	
	
}

