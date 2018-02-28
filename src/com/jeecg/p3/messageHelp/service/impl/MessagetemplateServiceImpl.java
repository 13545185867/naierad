package com.jeecg.p3.messageHelp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.messageHelp.service.MessagetemplateService;
import com.jeecg.p3.messageHelp.entity.Messagetemplate;
import com.jeecg.p3.messageHelp.dao.MessagetemplateDao;

@Service("messagetemplateService")
public class MessagetemplateServiceImpl implements MessagetemplateService {
	@Resource
	private MessagetemplateDao messagetemplateDao;

	@Override
	public void doAdd(Messagetemplate messagetemplate) {
		messagetemplateDao.add(messagetemplate);
	}

	@Override
	public void doEdit(Messagetemplate messagetemplate) {
		messagetemplateDao.update(messagetemplate);
	}

	@Override
	public void doDelete(String id) {
		messagetemplateDao.delete(id);
	}

	@Override
	public Messagetemplate queryById(String id) {
		Messagetemplate messagetemplate  = messagetemplateDao.get(id);
		return messagetemplate;
	}

	@Override
	public PageList<Messagetemplate> queryPageList(
		PageQuery<Messagetemplate> pageQuery) {
		PageList<Messagetemplate> result = new PageList<Messagetemplate>();
		Integer itemCount = messagetemplateDao.count(pageQuery);
		List<Messagetemplate> list = messagetemplateDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
