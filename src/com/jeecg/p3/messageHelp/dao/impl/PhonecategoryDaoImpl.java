package com.jeecg.p3.messageHelp.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.messageHelp.dao.PhonecategoryDao;
import com.jeecg.p3.messageHelp.entity.Phonecategory;

/**
 * 描述：</b>PhonecategoryDaoImpl<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
@Repository("phonecategoryDao")
public class PhonecategoryDaoImpl extends GenericDaoDefault<Phonecategory> implements PhonecategoryDao{

	@Override
	public Integer count(PageQuery<Phonecategory> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Phonecategory> queryPageList(
			PageQuery<Phonecategory> pageQuery,Integer itemCount) {
		PageQueryWrapper<Phonecategory> wrapper = new PageQueryWrapper<Phonecategory>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Phonecategory>)super.query("queryPageList",wrapper);
	}


}

