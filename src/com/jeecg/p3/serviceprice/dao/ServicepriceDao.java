package com.jeecg.p3.serviceprice.dao;

import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.persistence.GenericDao;

import com.jeecg.p3.serviceprice.entity.Serviceprice;

/**
 * 描述：</b>ServicepriceDao<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时07分59秒 星期二 
 * @version:1.0
 */
public interface ServicepriceDao extends GenericDao<Serviceprice>{
	
	public Integer count(PageQuery<Serviceprice> pageQuery);
	
	public List<Serviceprice> queryPageList(PageQuery<Serviceprice> pageQuery,Integer itemCount);
	
}

