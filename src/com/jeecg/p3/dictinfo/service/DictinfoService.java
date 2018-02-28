package com.jeecg.p3.dictinfo.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.dictinfo.entity.Dictinfo;

/**
 * 描述：</b>DictinfoService<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时04分33秒 星期二 
 * @version:1.0
 */
public interface DictinfoService {
	
	
	public void doAdd(Dictinfo dictinfo);
	
	public void doEdit(Dictinfo dictinfo);
	
	public void doDelete(String id);
	
	public Dictinfo queryById(String id);
	
	public PageList<Dictinfo> queryPageList(PageQuery<Dictinfo> pageQuery);
	
	public List<Dictinfo> queryList( Dictinfo  pageQuery);
}

