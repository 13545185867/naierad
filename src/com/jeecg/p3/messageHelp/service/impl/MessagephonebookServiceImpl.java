package com.jeecg.p3.messageHelp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.messageHelp.service.MessagephonebookService;
import com.jeecg.p3.messageHelp.entity.Messagephonebook;
import com.jeecg.p3.messageHelp.dao.MessagephonebookDao;

@Service("messagephonebookService")
public class MessagephonebookServiceImpl implements MessagephonebookService {
	@Resource
	private MessagephonebookDao messagephonebookDao;

	@Override
	public void doAdd(Messagephonebook messagephonebook) {
		messagephonebookDao.add(messagephonebook);
	}

	@Override
	public void doEdit(Messagephonebook messagephonebook) {
		messagephonebookDao.update(messagephonebook);
	}

	@Override
	public void doDelete(String id) {
		messagephonebookDao.delete(id);
	}

	@Override
	public Messagephonebook queryById(String id) {
		Messagephonebook messagephonebook  = messagephonebookDao.get(id);
		return messagephonebook;
	}

	@Override
	public PageList<Messagephonebook> queryPageList(
		PageQuery<Messagephonebook> pageQuery) {
		PageList<Messagephonebook> result = new PageList<Messagephonebook>();
		Integer itemCount = messagephonebookDao.count(pageQuery);
		List<Messagephonebook> list = messagephonebookDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
