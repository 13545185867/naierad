package com.jeecg.p3.website.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.website.entity.ColContent;

/**
 * 描述：</b>ColContentService<br>
 * @author：chao.hua
 * @since：2017年11月04日 13时51分37秒 星期六 
 * @version:1.0
 */
public interface ColContentService {
	
	
	public void doAdd(ColContent colContent);
	
	public void doEdit(ColContent colContent);
	
	public void doDelete(String id);
	
	public ColContent queryById(String id);
	
	public PageList<ColContent> queryPageList(PageQuery<ColContent> pageQuery);
}

