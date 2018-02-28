package com.jeecg.p3.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;
import com.jeecg.p3.order.service.OrderService;
import com.jeecg.p3.order.entity.Order;
import com.jeecg.p3.order.dao.OrderDao;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Resource
	private OrderDao orderDao;

	@Override
	public void doAdd(Order order) {
		orderDao.add(order);
	}

	@Override
	public void doEdit(Order order) {
		orderDao.update(order);
	}

	@Override
	public void doDelete(String id) {
		orderDao.delete(id);
	}

	@Override
	public Order queryById(String id) {
		Order order  = orderDao.get(id);
		return order;
	}

	@Override
	public PageList<Order> queryPageList(
		PageQuery<Order> pageQuery) {
		PageList<Order> result = new PageList<Order>();
		Integer itemCount = orderDao.count(pageQuery);
		List<Order> list = orderDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}
	
}
