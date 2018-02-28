package com.jeecg.p3.userservice.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.userservice.entity.Userservice;

/**
 * 描述：</b>UserserviceService<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时09分14秒 星期二 
 * @version:1.0
 */
public interface UserserviceService {
	
	
	public void doAdd(Userservice userservice);
	
	public void doEdit(Userservice userservice);
	
	public void doDelete(String id);
	
	public Userservice queryById(String id);
	
	public PageList<Userservice> queryPageList(PageQuery<Userservice> pageQuery);
	
	public List<Userservice> queryByRecord(Userservice userservice);
}

