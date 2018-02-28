package com.jeecg.p3.messageHelp.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.messageHelp.entity.Messagephonecategory;

/**
 * 描述：</b>MessagephonecategoryService<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
public interface MessagephonecategoryService {
	
	
	public void doAdd(Messagephonecategory messagephonecategory);
	
	public void doEdit(Messagephonecategory messagephonecategory);
	
	public void doDelete(String id);
	
	public Messagephonecategory queryById(String id);
	
	public PageList<Messagephonecategory> queryPageList(PageQuery<Messagephonecategory> pageQuery);
}

