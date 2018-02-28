package com.jeecg.p3.leavemessage.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.leavemessage.entity.Leavemessage;

/**
 * 描述：</b>LeavemessageDao<br>
 * @author：chao.hua
 * @since：2017年09月17日 14时30分22秒 星期日 
 * @version:1.0
 */
public interface LeavemessageDao extends GenericDao<Leavemessage>{
	
	public Integer count(PageQuery<Leavemessage> pageQuery);
	
	public List<Leavemessage> queryPageList(PageQuery<Leavemessage> pageQuery,Integer itemCount);
	
}

