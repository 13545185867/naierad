package com.jeecg.p3.serviceprice.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.serviceprice.dao.ServicepriceDao;
import com.jeecg.p3.serviceprice.entity.Serviceprice;

/**
 * 描述：</b>ServicepriceDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时07分59秒 星期二 
 * @version:1.0
 */
@Repository("servicepriceDao")
public class ServicepriceDaoImpl extends GenericDaoDefault<Serviceprice> implements ServicepriceDao{

	@Override
	public Integer count(PageQuery<Serviceprice> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Serviceprice> queryPageList(
			PageQuery<Serviceprice> pageQuery,Integer itemCount) {
		PageQueryWrapper<Serviceprice> wrapper = new PageQueryWrapper<Serviceprice>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Serviceprice>)super.query("queryPageList",wrapper);
	}


}

