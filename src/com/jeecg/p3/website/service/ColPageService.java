package com.jeecg.p3.website.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.website.entity.ColPage;

/**
 * 描述：</b>ColPageService<br>
 * @author：chao.hua
 * @since：2017年11月04日 14时47分40秒 星期六 
 * @version:1.0
 */
public interface ColPageService {
	
	
	public void doAdd(ColPage colPage);
	
	public void doEdit(ColPage colPage);
	
	public void doDelete(String id);
	
	public ColPage queryById(String id);
	
	public PageList<ColPage> queryPageList(PageQuery<ColPage> pageQuery);
	
	public List<ColPage> queryColToTree();
	
	public List<ColPage> queryNav(String pageQuery);
	 public LinkedHashMap<ColPage, ArrayList<ColPage>> getSubMenuTree(String parentAuthId);
	 
}

