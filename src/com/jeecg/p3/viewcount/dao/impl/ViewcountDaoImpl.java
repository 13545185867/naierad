package com.jeecg.p3.viewcount.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.viewcount.dao.ViewcountDao;
import com.jeecg.p3.viewcount.entity.Viewcount;

/**
 * 描述：</b>ViewcountDaoImpl<br>
 * @author：chao.hua
 * @since：2017年09月11日 12时01分51秒 星期一 
 * @version:1.0
 */
@Repository("viewcountDao")
public class ViewcountDaoImpl extends GenericDaoDefault<Viewcount> implements ViewcountDao{

	@Override
	public Integer count(PageQuery<Viewcount> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Viewcount> queryPageList(
			PageQuery<Viewcount> pageQuery,Integer itemCount) {
		PageQueryWrapper<Viewcount> wrapper = new PageQueryWrapper<Viewcount>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Viewcount>)super.query("queryPageList",wrapper);
	}

	@Override
	public void updateCount(Viewcount vc) {
		// TODO Auto-generated method stub
		super.update("updateCount", vc);
		
	}


}

