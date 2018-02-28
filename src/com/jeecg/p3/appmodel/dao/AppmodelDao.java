package com.jeecg.p3.appmodel.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.appmodel.entity.Appmodel;

/**
 * 描述：</b>AppmodelDao<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时05分57秒 星期二 
 * @version:1.0
 */
public interface AppmodelDao extends GenericDao<Appmodel>{
	
	public Integer count(PageQuery<Appmodel> pageQuery);
	
	public List<Appmodel> queryPageList(PageQuery<Appmodel> pageQuery,Integer itemCount);
	
	public List<Appmodel> queryAppList(  Appmodel  pageQuery);
	
	
}

