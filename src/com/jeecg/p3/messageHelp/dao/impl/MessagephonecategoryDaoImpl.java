package com.jeecg.p3.messageHelp.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.messageHelp.dao.MessagephonecategoryDao;
import com.jeecg.p3.messageHelp.entity.Messagephonecategory;

/**
 * 描述：</b>MessagephonecategoryDaoImpl<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
@Repository("messagephonecategoryDao")
public class MessagephonecategoryDaoImpl extends GenericDaoDefault<Messagephonecategory> implements MessagephonecategoryDao{

	@Override
	public Integer count(PageQuery<Messagephonecategory> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messagephonecategory> queryPageList(
			PageQuery<Messagephonecategory> pageQuery,Integer itemCount) {
		PageQueryWrapper<Messagephonecategory> wrapper = new PageQueryWrapper<Messagephonecategory>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Messagephonecategory>)super.query("queryPageList",wrapper);
	}


}

