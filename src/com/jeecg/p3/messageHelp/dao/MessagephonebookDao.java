package com.jeecg.p3.messageHelp.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.messageHelp.entity.Messagephonebook;

/**
 * 描述：</b>MessagephonebookDao<br>
 * @author：chao.hua
 * @since：2017年08月16日 13时08分01秒 星期三 
 * @version:1.0
 */
public interface MessagephonebookDao extends GenericDao<Messagephonebook>{
	
	public Integer count(PageQuery<Messagephonebook> pageQuery);
	
	public List<Messagephonebook> queryPageList(PageQuery<Messagephonebook> pageQuery,Integer itemCount);
	
}

