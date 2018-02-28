package com.jeecg.p3.dicttype.service;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.dicttype.entity.Dicttype;

/**
 * 描述：</b>DicttypeService<br>
 * @author：chao.hua
 * @since：2017年07月25日 16时50分49秒 星期二 
 * @version:1.0
 */
public interface DicttypeService {
	
	
	public void doAdd(Dicttype dicttype);
	
	public void doEdit(Dicttype dicttype);
	
	public void doDelete(String id);
	
	public Dicttype queryById(String id);
	
	public PageList<Dicttype> queryPageList(PageQuery<Dicttype> pageQuery);
}

