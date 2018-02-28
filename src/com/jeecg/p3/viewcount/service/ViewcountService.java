package com.jeecg.p3.viewcount.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.viewcount.entity.Viewcount;

/**
 * 描述：</b>ViewcountService<br>
 * @author：chao.hua
 * @since：2017年09月11日 12时01分51秒 星期一 
 * @version:1.0
 */
public interface ViewcountService {
	
	
	public void doAdd(Viewcount viewcount);
	
	public void doEdit(Viewcount viewcount);
	
	public void doDelete(String id);
	
	public Viewcount queryById(String id);
	
	public PageList<Viewcount> queryPageList(PageQuery<Viewcount> pageQuery);
	
	public void updateCount(Viewcount vc );
}

