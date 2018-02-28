package com.jeecg.p3.userinfo.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userservice.entity.Vipinfo;

/**
 * 描述：</b>UserinfoDao<br>
 * @author：chao.hua
 * @since：2017年09月11日 12时01分29秒 星期一 
 * @version:1.0
 */
public interface UserinfoDao extends GenericDao<Userinfo>{
	
	public Integer count(PageQuery<Userinfo> pageQuery);
	
	public List<Userinfo> queryPageList(PageQuery<Userinfo> pageQuery,Integer itemCount);
	
	public void updateByopenid(Userinfo user);
	
	public List<Userinfo> getByOpenId(String openid);
	
	public List<Userinfo> queryList(PageQuery<Userinfo> pageQuery);
	
	public List<Vipinfo> queryVip(PageQuery<Vipinfo> pageQuery);
	
	public List<Vipinfo> queryVipPageList(PageQuery<Vipinfo> pageQuery,Integer itemCount);
	
	public Integer vipcount(PageQuery<Vipinfo> pageQuery);
	
}

