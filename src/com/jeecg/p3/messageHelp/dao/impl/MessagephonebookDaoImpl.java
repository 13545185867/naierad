package com.jeecg.p3.messageHelp.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.messageHelp.dao.MessagephonebookDao;
import com.jeecg.p3.messageHelp.entity.Messagephonebook;

/**
 * 描述：</b>MessagephonebookDaoImpl<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分01秒 星期三 
 * @version:1.0
 */
@Repository("messagephonebookDao")
public class MessagephonebookDaoImpl extends GenericDaoDefault<Messagephonebook> implements MessagephonebookDao{

	@Override
	public Integer count(PageQuery<Messagephonebook> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messagephonebook> queryPageList(
			PageQuery<Messagephonebook> pageQuery,Integer itemCount) {
		PageQueryWrapper<Messagephonebook> wrapper = new PageQueryWrapper<Messagephonebook>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Messagephonebook>)super.query("queryPageList",wrapper);
	}


}

