package com.jeecg.p3.userservice.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.userservice.entity.Userservice;

/**
 * 描述：</b>UserserviceDao<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时09分14秒 星期二 
 * @version:1.0
 */
public interface UserserviceDao extends GenericDao<Userservice>{
	
	public Integer count(PageQuery<Userservice> pageQuery);
	
	public List<Userservice> queryPageList(PageQuery<Userservice> pageQuery,Integer itemCount);
	
	public List<Userservice> queryByRecord(Userservice userservice);
	
	
}

