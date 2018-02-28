package com.jeecg.p3.timetask.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.timetask.entity.TSTimetask;

/**
 * 描述：</b>TSTimetaskDao<br>
 * @author：chao.hua
 * @since：2017年07月21日 20时42分33秒 星期五 
 * @version:1.0
 */
public interface TSTimetaskDao extends GenericDao<TSTimetask>{
	
	public Integer count(PageQuery<TSTimetask> pageQuery);
	
	public List<TSTimetask> queryPageList(PageQuery<TSTimetask> pageQuery,Integer itemCount);
	
	public  List<TSTimetask> getByTaskID( String  TST);
	
	public   List<TSTimetask> getAllTask();
}

