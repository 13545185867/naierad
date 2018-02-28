package com.jeecg.p3.messageHelp.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.messageHelp.entity.Messagephonebook;

/**
 * 描述：</b>MessagephonebookService<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分01秒 星期三 
 * @version:1.0
 */
public interface MessagephonebookService {
	
	
	public void doAdd(Messagephonebook messagephonebook);
	
	public void doEdit(Messagephonebook messagephonebook);
	
	public void doDelete(String id);
	
	public Messagephonebook queryById(String id);
	
	public PageList<Messagephonebook> queryPageList(PageQuery<Messagephonebook> pageQuery);
}

