package com.jeecg.p3.order.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.order.entity.Order;

/**
 * 描述：</b>OrderDao<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时07分06秒 星期二 
 * @version:1.0
 */
public interface OrderDao extends GenericDao<Order>{
	
	public Integer count(PageQuery<Order> pageQuery);
	
	public List<Order> queryPageList(PageQuery<Order> pageQuery,Integer itemCount);
	
}

