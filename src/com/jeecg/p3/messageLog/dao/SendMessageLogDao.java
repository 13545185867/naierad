package com.jeecg.p3.messageLog.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.messageLog.entity.SendMessageLog;

/**
 * 描述：</b>SendMessageLogDao<br>
 * @author：chao.hua
 * @since：2017年07月23日 20时27分22秒 星期日 
 * @version:1.0
 */
public interface SendMessageLogDao extends GenericDao<SendMessageLog>{
	
	public Integer count(PageQuery<SendMessageLog> pageQuery);
	
	public List<SendMessageLog> queryPageList(PageQuery<SendMessageLog> pageQuery,Integer itemCount);
	
	public List<SendMessageLog> queryByIPMACPHONE(SendMessageLog SML);
	
}

