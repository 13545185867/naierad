package com.jeecg.p3.appmodel.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.appmodel.entity.Appmodel;

/**
 * 描述：</b>AppmodelService<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时05分57秒 星期二 
 * @version:1.0
 */
public interface AppmodelService {
	
	
	public void doAdd(Appmodel appmodel);
	
	public void doEdit(Appmodel appmodel);
	
	public void doDelete(String id);
	
	public Appmodel queryById(String id);
	
	public PageList<Appmodel> queryPageList(PageQuery<Appmodel> pageQuery);
	
	public List<Appmodel> queryAppList(  Appmodel  pageQuery);
}

