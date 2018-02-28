package com.jeecg.p3.messageHelp.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.messageHelp.dao.MessagesendtaskDao;
import com.jeecg.p3.messageHelp.entity.Messagesendtask;

/**
 * 描述：</b>MessagesendtaskDaoImpl<br>
 * @author：chao.hua
 * @since：2017年08月18日 13时08分48秒 星期五 
 * @version:1.0
 */
@Repository("messagesendtaskDao")
public class MessagesendtaskDaoImpl extends GenericDaoDefault<Messagesendtask> implements MessagesendtaskDao{

	@Override
	public Integer count(PageQuery<Messagesendtask> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messagesendtask> queryPageList(
			PageQuery<Messagesendtask> pageQuery,Integer itemCount) {
		PageQueryWrapper<Messagesendtask> wrapper = new PageQueryWrapper<Messagesendtask>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Messagesendtask>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messagesendtask> getPendingTaskList(Messagesendtask pageQuery) {
		// TODO Auto-generated method stub
		return (List<Messagesendtask>)super.query("getPendingTaskList",pageQuery);
	}


}

