package com.jeecg.p3.messageHelp.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.messageHelp.dao.MessagetemplateDao;
import com.jeecg.p3.messageHelp.entity.Messagetemplate;

/**
 * 描述：</b>MessagetemplateDaoImpl<br>
 * @author：chao.hua
 * @since：2017年08月18日 12时42分12秒 星期五 
 * @version:1.0
 */
@Repository("messagetemplateDao")
public class MessagetemplateDaoImpl extends GenericDaoDefault<Messagetemplate> implements MessagetemplateDao{

	@Override
	public Integer count(PageQuery<Messagetemplate> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messagetemplate> queryPageList(
			PageQuery<Messagetemplate> pageQuery,Integer itemCount) {
		PageQueryWrapper<Messagetemplate> wrapper = new PageQueryWrapper<Messagetemplate>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Messagetemplate>)super.query("queryPageList",wrapper);
	}


}

