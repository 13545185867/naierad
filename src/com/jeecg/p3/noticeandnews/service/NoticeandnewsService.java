package com.jeecg.p3.noticeandnews.service;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.noticeandnews.entity.Noticeandnews;

/**
 * 描述：</b>NoticeandnewsService<br>
 * @author：chao.hua
 * @since：2017年09月20日 15时38分14秒 星期三 
 * @version:1.0
 */
public interface NoticeandnewsService {
	
	
	public void doAdd(Noticeandnews noticeandnews);
	
	public void doEdit(Noticeandnews noticeandnews);
	
	public void doDelete(String id);
	
	public Noticeandnews queryById(String id);
	
	public PageList<Noticeandnews> queryPageList(PageQuery<Noticeandnews> pageQuery);
	
	public PageList<Noticeandnews> queryActAndNEWList(PageQuery<Noticeandnews> pageQuery);
	
	public List<Noticeandnews> queryList(PageQuery<Noticeandnews> pageQuery);
	
}

