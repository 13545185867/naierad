package com.jeecg.p3.timetask.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.timetask.entity.TSTimetask;

/**
 * 描述：</b>TSTimetaskService<br>
 * @author：chao.hua
 * @since：2017年07月21日 20时42分33秒 星期五 
 * @version:1.0
 */
public interface TSTimetaskService {
	
	
	public void doAdd(TSTimetask tSTimetask);
	
	public void doEdit(TSTimetask tSTimetask);
	
	public void doDelete(String id);
	
	public TSTimetask queryById(String id);
	
	public PageList<TSTimetask> queryPageList(PageQuery<TSTimetask> pageQuery);
	
	public List<TSTimetask> getByTaskID(String TST);
	
	public List<TSTimetask> getAllTask();
}

