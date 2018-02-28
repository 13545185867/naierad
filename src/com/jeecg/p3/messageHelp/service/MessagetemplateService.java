package com.jeecg.p3.messageHelp.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.messageHelp.entity.Messagetemplate;

/**
 * 描述：</b>MessagetemplateService<br>
 * @author：chao.hua
 * @since：2017年08月18日 12时42分12秒 星期五 
 * @version:1.0
 */
public interface MessagetemplateService {
	
	
	public void doAdd(Messagetemplate messagetemplate);
	
	public void doEdit(Messagetemplate messagetemplate);
	
	public void doDelete(String id);
	
	public Messagetemplate queryById(String id);
	
	public PageList<Messagetemplate> queryPageList(PageQuery<Messagetemplate> pageQuery);
}

