package com.jeecg.p3.messageHelp.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.messageHelp.entity.Phonecategory;

/**
 * 描述：</b>PhonecategoryService<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
public interface PhonecategoryService {
	
	
	public void doAdd(Phonecategory phonecategory);
	
	public void doEdit(Phonecategory phonecategory);
	
	public void doDelete(String id);
	
	public Phonecategory queryById(String id);
	
	public PageList<Phonecategory> queryPageList(PageQuery<Phonecategory> pageQuery);
}

