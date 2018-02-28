package com.jeecg.p3.website.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;

import com.jeecg.p3.website.dao.ColPageDao;
import com.jeecg.p3.website.entity.ColPage;

/**
 * 描述：</b>ColPageDaoImpl<br>
 * @author：chao.hua
 * @since：2017年11月04日 14时47分40秒 星期六 
 * @version:1.0
 */
@Repository("colPageDao")
public class ColPageDaoImpl extends GenericDaoDefault<ColPage> implements ColPageDao{

	@Override
	public Integer count(PageQuery<ColPage> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColPage> queryPageList(
			PageQuery<ColPage> pageQuery,Integer itemCount) {
		PageQueryWrapper<ColPage> wrapper = new PageQueryWrapper<ColPage>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<ColPage>)super.query("queryPageList",wrapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColPage> queryColToTree() {
		// TODO Auto-generated method stub
		return (List<ColPage>)super.query("queryColToTree");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ColPage> queryNav(String pageQuery) {
		// TODO Auto-generated method stub
		  Map map = new HashMap();
		    map.put("parentid", pageQuery);
		return (List<ColPage>)super.query("queryNav",new Object[] { map });
	}


}

