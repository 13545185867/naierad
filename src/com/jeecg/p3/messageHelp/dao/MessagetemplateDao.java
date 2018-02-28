package com.jeecg.p3.messageHelp.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.messageHelp.entity.Messagetemplate;

/**
 * 描述：</b>MessagetemplateDao<br>
 * @author：chao.hua
 * @since：2017年08月18日 12时42分12秒 星期五 
 * @version:1.0
 */
public interface MessagetemplateDao extends GenericDao<Messagetemplate>{
	
	public Integer count(PageQuery<Messagetemplate> pageQuery);
	
	public List<Messagetemplate> queryPageList(PageQuery<Messagetemplate> pageQuery,Integer itemCount);
	
}

