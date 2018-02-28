package com.jeecg.p3.dicttype.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.dicttype.dao.DicttypeDao;
import com.jeecg.p3.dicttype.entity.Dicttype;

/**
 * 描述：</b>DicttypeDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月25日 16时50分49秒 星期二 
 * @version:1.0
 */
@Repository("dicttypeDao")
public class DicttypeDaoImpl extends GenericDaoDefault<Dicttype> implements DicttypeDao{

	@Override
	public Integer count(PageQuery<Dicttype> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dicttype> queryPageList(
			PageQuery<Dicttype> pageQuery,Integer itemCount) {
		PageQueryWrapper<Dicttype> wrapper = new PageQueryWrapper<Dicttype>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Dicttype>)super.query("queryPageList",wrapper);
	}


}

