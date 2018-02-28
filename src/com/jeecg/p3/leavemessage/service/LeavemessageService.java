package com.jeecg.p3.leavemessage.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.leavemessage.entity.Leavemessage;

/**
 * 描述：</b>LeavemessageService<br>
 * @author：chao.hua
 * @since：2017年09月17日 14时30分22秒 星期日 
 * @version:1.0
 */
public interface LeavemessageService {
	
	
	public void doAdd(Leavemessage leavemessage);
	
	public void doEdit(Leavemessage leavemessage);
	
	public void doDelete(String id);
	
	public Leavemessage queryById(String id);
	
	public PageList<Leavemessage> queryPageList(PageQuery<Leavemessage> pageQuery);
}

