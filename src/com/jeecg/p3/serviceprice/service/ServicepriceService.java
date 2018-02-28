package com.jeecg.p3.serviceprice.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.serviceprice.entity.Serviceprice;

/**
 * 描述：</b>ServicepriceService<br>
 * @author：chao.hua
 * @since：2017年07月25日 17时07分59秒 星期二 
 * @version:1.0
 */
public interface ServicepriceService {
	
	
	public void doAdd(Serviceprice serviceprice);
	
	public void doEdit(Serviceprice serviceprice);
	
	public void doDelete(String id);
	
	public Serviceprice queryById(String id);
	
	public PageList<Serviceprice> queryPageList(PageQuery<Serviceprice> pageQuery);

}

