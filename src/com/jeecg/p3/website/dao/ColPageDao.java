package com.jeecg.p3.website.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.website.entity.ColPage;

/**
 * 描述：</b>ColPageDao<br>
 * @author：chao.hua
 * @since：2017年11月04日 14时47分40秒 星期六 
 * @version:1.0
 */
public interface ColPageDao extends GenericDao<ColPage>{
	
	public Integer count(PageQuery<ColPage> pageQuery);
	
	public List<ColPage> queryPageList(PageQuery<ColPage> pageQuery,Integer itemCount);
	
	public List<ColPage> queryColToTree();
	
	public List<ColPage> queryNav(String parentid);
	
}

