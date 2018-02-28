package com.jeecg.p3.order.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.order.entity.Order;

/**
 * 描述：</b>OrderService<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时07分06秒 星期二 
 * @version:1.0
 */
public interface OrderService {
	
	
	public void doAdd(Order order);
	
	public void doEdit(Order order);
	
	public void doDelete(String id);
	
	public Order queryById(String id);
	
	public PageList<Order> queryPageList(PageQuery<Order> pageQuery);
}

