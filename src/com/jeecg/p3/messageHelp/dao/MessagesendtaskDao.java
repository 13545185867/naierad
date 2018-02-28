package com.jeecg.p3.messageHelp.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.messageHelp.entity.Messagesendtask;

/**
 * 描述：</b>MessagesendtaskDao<br>
 * @author：chao.hua
 * @since：2017年08月18日 13时08分48秒 星期五 
 * @version:1.0
 */
public interface MessagesendtaskDao extends GenericDao<Messagesendtask>{
	
	public Integer count(PageQuery<Messagesendtask> pageQuery);
	
	public List<Messagesendtask> queryPageList(PageQuery<Messagesendtask> pageQuery,Integer itemCount);
	
	public List<Messagesendtask> getPendingTaskList(Messagesendtask pageQuery);
	
}

