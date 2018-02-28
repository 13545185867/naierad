package com.jeecg.p3.dicttype.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.dicttype.entity.Dicttype;

/**
 * 描述：</b>DicttypeDao<br>
 * @author：chao.hua
 * @since：2017年07月25日 16时50分49秒 星期二 
 * @version:1.0
 */
public interface DicttypeDao extends GenericDao<Dicttype>{
	
	public Integer count(PageQuery<Dicttype> pageQuery);
	
	public List<Dicttype> queryPageList(PageQuery<Dicttype> pageQuery,Integer itemCount);
	
}

