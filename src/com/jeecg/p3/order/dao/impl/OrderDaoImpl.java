package com.jeecg.p3.order.dao.impl;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.PageQueryWrapper;
import org.jeecgframework.p3.core.utils.persistence.mybatis.GenericDaoDefault;
import org.springframework.stereotype.Repository;
import com.jeecg.p3.order.dao.OrderDao;
import com.jeecg.p3.order.entity.Order;

/**
 * 描述：</b>OrderDaoImpl<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时07分06秒 星期二 
 * @version:1.0
 */
@Repository("orderDao")
public class OrderDaoImpl extends GenericDaoDefault<Order> implements OrderDao{

	@Override
	public Integer count(PageQuery<Order> pageQuery) {
		return (Integer) super.queryOne("count",pageQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> queryPageList(
			PageQuery<Order> pageQuery,Integer itemCount) {
		PageQueryWrapper<Order> wrapper = new PageQueryWrapper<Order>(pageQuery.getPageNo(), pageQuery.getPageSize(),itemCount, pageQuery.getQuery());
		return (List<Order>)super.query("queryPageList",wrapper);
	}


}

