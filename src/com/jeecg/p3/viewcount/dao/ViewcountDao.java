package com.jeecg.p3.viewcount.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.viewcount.entity.Viewcount;

/**
 * 描述：</b>ViewcountDao<br>
 * @author：chao.hua
 * @since：2017年09月11日 12时01分51秒 星期一 
 * @version:1.0
 */
public interface ViewcountDao extends GenericDao<Viewcount>{
	
	public Integer count(PageQuery<Viewcount> pageQuery);
	
	public List<Viewcount> queryPageList(PageQuery<Viewcount> pageQuery,Integer itemCount);
	
	public void updateCount(Viewcount vc );
	
}

