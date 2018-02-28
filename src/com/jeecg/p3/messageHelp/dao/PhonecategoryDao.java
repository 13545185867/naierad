package com.jeecg.p3.messageHelp.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.messageHelp.entity.Phonecategory;

/**
 * 描述：</b>PhonecategoryDao<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
public interface PhonecategoryDao extends GenericDao<Phonecategory>{
	
	public Integer count(PageQuery<Phonecategory> pageQuery);
	
	public List<Phonecategory> queryPageList(PageQuery<Phonecategory> pageQuery,Integer itemCount);
	
}

