package com.jeecg.p3.activitybookrecord.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.activitybookrecord.entity.Activitybookrecord;

/**
 * 描述：</b>ActivitybookrecordService<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时59分43秒 星期一 
 * @version:1.0
 */
public interface ActivitybookrecordService {
	
	
	public void doAdd(Activitybookrecord activitybookrecord);
	
	public void doEdit(Activitybookrecord activitybookrecord);
	
	public void doDelete(String id);
	
	public Activitybookrecord queryById(String id);
	
	public PageList<Activitybookrecord> queryPageList(PageQuery<Activitybookrecord> pageQuery);
	
	public Integer queryListCount( Activitybookrecord pageQuery);
	
	public List<Activitybookrecord> queryList(Activitybookrecord pageQuery);
	
	public PageList<Activitybookrecord> queryBookedPageList(PageQuery<Activitybookrecord> pageQuery);
}

