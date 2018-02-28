package com.jeecg.p3.messageLog.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.messageLog.dao.SendMessageLogDao;
import com.jeecg.p3.messageLog.entity.SendMessageLog;

/**
 * 描述：</b>SendMessageLogDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月23日 20时27分22秒 星期日 
 * @version:1.0
 */
@Repository("sendMessageLogDao")
public class SendMessageLogDaoImpl extends GenericDaoDefault<SendMessageLog> implements SendMessageLogDao{

	@Override
	public Integer count(PageQuery<SendMessageLog> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SendMessageLog> queryPageList(
			PageQuery<SendMessageLog> pageQuery,Integer itemCount) {
		PageQueryWrapper<SendMessageLog> wrapper = new PageQueryWrapper<SendMessageLog>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<SendMessageLog>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SendMessageLog> queryByIPMACPHONE(SendMessageLog SML) {
		// TODO Auto-generated method stub

		return (List<SendMessageLog>)super.query("queryByIPMACPHONE",SML);
	}


}

