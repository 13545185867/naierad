package com.jeecg.p3.website.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.website.entity.ColContent;

/**
 * 描述：</b>ColContentDao<br>
 * @author：chao.hua
 * @since：2017年11月04日 13时51分37秒 星期六 
 * @version:1.0
 */
public interface ColContentDao extends GenericDao<ColContent>{
	
	public Integer count(PageQuery<ColContent> pageQuery);
	
	public List<ColContent> queryPageList(PageQuery<ColContent> pageQuery,Integer itemCount);
	
}

