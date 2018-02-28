package com.jeecg.p3.leavemessage.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.leavemessage.dao.LeavemessageDao;
import com.jeecg.p3.leavemessage.entity.Leavemessage;

/**
 * 描述：</b>LeavemessageDaoImpl<br>
 * @author：chao.hua
 * @since：2017年09月17日 14时30分22秒 星期日 
 * @version:1.0
 */
@Repository("leavemessageDao")
public class LeavemessageDaoImpl extends GenericDaoDefault<Leavemessage> implements LeavemessageDao{

	@Override
	public Integer count(PageQuery<Leavemessage> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Leavemessage> queryPageList(
			PageQuery<Leavemessage> pageQuery,Integer itemCount) {
		PageQueryWrapper<Leavemessage> wrapper = new PageQueryWrapper<Leavemessage>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Leavemessage>)super.query("queryPageList",wrapper);
	}


}

