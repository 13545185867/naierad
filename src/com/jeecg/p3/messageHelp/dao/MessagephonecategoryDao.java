package com.jeecg.p3.messageHelp.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.messageHelp.entity.Messagephonecategory;

/**
 * 描述：</b>MessagephonecategoryDao<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
public interface MessagephonecategoryDao extends GenericDao<Messagephonecategory>{
	
	public Integer count(PageQuery<Messagephonecategory> pageQuery);
	
	public List<Messagephonecategory> queryPageList(PageQuery<Messagephonecategory> pageQuery,Integer itemCount);
	
}

