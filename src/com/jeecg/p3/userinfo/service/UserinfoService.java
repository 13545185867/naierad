package com.jeecg.p3.userinfo.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userservice.entity.Vipinfo;

/**
 * 描述：</b>UserinfoService<br>
 * @author：chao.hua
 * @since：2017年09月11日 12时01分29秒 星期一 
 * @version:1.0
 */
public interface UserinfoService {
	
	
	public void doAdd(Userinfo userinfo);
	
	public void doEdit(Userinfo userinfo);
	
	public void doDelete(String id);
	
	public Userinfo queryById(String id);
	
	public PageList<Userinfo> queryPageList(PageQuery<Userinfo> pageQuery);
	
	public Integer count(PageQuery<Userinfo> pageQuery);
	
	public void updateByopenid(Userinfo user);
	
	public List<Userinfo> getByOpenId(String openid);
	
	public List<Userinfo> queryList(PageQuery<Userinfo> pageQuery);
	
	public List<Vipinfo> queryVip(PageQuery<Vipinfo> pageQuery);
	
	public PageList<Vipinfo> queryPageVip(PageQuery<Vipinfo> pageQuery);
}

