package com.jeecg.p3.messageHelp.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.messageHelp.entity.Messagesendtask;

/**
 * 描述：</b>MessagesendtaskService<br>
 * @author：chao.hua
 * @since：2017年08月18日 13时08分48秒 星期五 
 * @version:1.0
 */
public interface MessagesendtaskService {
	
	
	public void doAdd(Messagesendtask messagesendtask);
	
	public void doEdit(Messagesendtask messagesendtask);
	
	public void doDelete(String id);
	
	public Messagesendtask queryById(Integer id);
	
	public PageList<Messagesendtask> queryPageList(PageQuery<Messagesendtask> pageQuery);
	
	public List<Messagesendtask> getPendingTaskList(Messagesendtask pageQuery);
}

