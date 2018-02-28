package com.jeecg.p3.activitybookrecord.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.activitybookrecord.entity.Activitybookrecord;

/**
 * 描述：</b>ActivitybookrecordDao<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时59分43秒 星期一 
 * @version:1.0
 */
public interface ActivitybookrecordDao extends GenericDao<Activitybookrecord>{
	
	public Integer count(PageQuery<Activitybookrecord> pageQuery);
	
	public List<Activitybookrecord> queryPageList(PageQuery<Activitybookrecord> pageQuery,Integer itemCount);
	
	public Integer  queryListCount(Activitybookrecord pageQuery);
	
	public List<Activitybookrecord> queryList(Activitybookrecord pageQuery);
	
	public List<Activitybookrecord> queryBookedPageList(PageQuery<Activitybookrecord> pageQuery,Integer itemCount);
	
}

