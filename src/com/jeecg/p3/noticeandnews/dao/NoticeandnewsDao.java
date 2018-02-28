package com.jeecg.p3.noticeandnews.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.noticeandnews.entity.Noticeandnews;

/**
 * 描述：</b>NoticeandnewsDao<br>
 * @author：chao.hua
 * @since：2017年09月20日 15时38分14秒 星期三 
 * @version:1.0
 */
public interface NoticeandnewsDao extends GenericDao<Noticeandnews>{
	
	public Integer count(PageQuery<Noticeandnews> pageQuery);
	
	public List<Noticeandnews> queryPageList(PageQuery<Noticeandnews> pageQuery,Integer itemCount);
	
	public List<Noticeandnews> queryActAndNEWList(PageQuery<Noticeandnews> pageQuery,Integer itemCount);
	
}

