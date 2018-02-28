package com.jeecg.p3.messageLog.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.messageLog.entity.SendMessageLog;

/**
 * 描述：</b>SendMessageLogService<br>
 * @author：chao.hua
 * @since：2017年07月23日 20时27分22秒 星期日 
 * @version:1.0
 */
public interface SendMessageLogService {
	
	
	public void doAdd(SendMessageLog sendMessageLog);
	
	public void doEdit(SendMessageLog sendMessageLog);
	
	public void doDelete(String id);
	
	public SendMessageLog queryById(String id);
	
	public PageList<SendMessageLog> queryPageList(PageQuery<SendMessageLog> pageQuery);
	public List<SendMessageLog> queryByIPMACPHONE(SendMessageLog SML);
	
	public String getMACAddress(String ip) throws Exception;
	
	public String getIpAddr(HttpServletRequest request) throws Exception;
}

