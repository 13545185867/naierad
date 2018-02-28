package com.jeecg.p3.messageHelp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.messageHelp.service.MessagesendtaskService;
import com.jeecg.p3.messageHelp.entity.Messagesendtask;
import com.jeecg.p3.messageHelp.dao.MessagesendtaskDao;

@Service("messagesendtaskService")
public class MessagesendtaskServiceImpl implements MessagesendtaskService {
	@Resource
	private MessagesendtaskDao messagesendtaskDao;

	@Override
	public void doAdd(Messagesendtask messagesendtask) {
		messagesendtaskDao.add(messagesendtask);
	}

	@Override
	public void doEdit(Messagesendtask messagesendtask) {
		messagesendtaskDao.update(messagesendtask);
	}

	@Override
	public void doDelete(String id) {
		messagesendtaskDao.delete(id);
	}

	@Override
	public Messagesendtask queryById(Integer id) {
		Messagesendtask messagesendtask  = messagesendtaskDao.get(id);
		return messagesendtask;
	}

	@Override
	public PageList<Messagesendtask> queryPageList(
		PageQuery<Messagesendtask> pageQuery) {
		PageList<Messagesendtask> result = new PageList<Messagesendtask>();
		Integer itemCount = messagesendtaskDao.count(pageQuery);
		List<Messagesendtask> list = messagesendtaskDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<Messagesendtask> getPendingTaskList(Messagesendtask pageQuery) {
		// TODO Auto-generated method stub
		List<Messagesendtask> list = messagesendtaskDao.getPendingTaskList(pageQuery);
		return list;
	}
	
}
