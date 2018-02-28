package com.jeecg.p3.website.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.jeecgframework.p3.core.utils.common.Pagenation;

import com.jeecg.p3.website.service.ColPageService;
import com.jeecg.p3.website.entity.ColPage;
import com.jeecg.p3.website.dao.ColPageDao;

@Service("colPageService")
public class ColPageServiceImpl implements ColPageService {
	@Resource
	private ColPageDao colPageDao;

	@Override
	public void doAdd(ColPage colPage) {
		colPageDao.add(colPage);
	}

	@Override
	public void doEdit(ColPage colPage) {
		colPageDao.update(colPage);
	}

	@Override
	public void doDelete(String id) {
		colPageDao.delete(id);
	}

	@Override
	public ColPage queryById(String id) {
		ColPage colPage  = colPageDao.get(id);
		return colPage;
	}

	@Override
	public PageList<ColPage> queryPageList(
		PageQuery<ColPage> pageQuery) {
		PageList<ColPage> result = new PageList<ColPage>();
		Integer itemCount = colPageDao.count(pageQuery);
		List<ColPage> list = colPageDao.queryPageList(pageQuery,itemCount);
		Pagenation pagenation = new Pagenation(pageQuery.getPageNo(), itemCount, pageQuery.getPageSize());
		result.setPagenation(pagenation);
		result.setValues(list);
		return result;
	}

	@Override
	public List<ColPage> queryColToTree() {
		// TODO Auto-generated method stub
		return colPageDao.queryColToTree();
	}

	@Override
	public List<ColPage> queryNav(String pageQuery) {
		// TODO Auto-generated method stub
		return colPageDao.queryNav(pageQuery);
	}
	
	 public LinkedHashMap<ColPage, ArrayList<ColPage>> getSubMenuTree(String parentAuthId)
	  {
		 //不传parentid 得到一层 
	    List<ColPage> oneLevel = getOnelevel( parentAuthId );
	    LinkedHashMap result = new LinkedHashMap();
//循环一二层，
	    for (ColPage menu : oneLevel) {
	    	//判断当前节点是否是父节点
		        ArrayList<LinkedHashMap> al = new ArrayList<LinkedHashMap>();
		        //取得当前根节点的所有子节点
	        ArrayList<ColPage> subMenuList = (ArrayList<ColPage>) getOnelevel(menu.getId().toString());
	        if(subMenuList.size()>0){
	        for(ColPage twolevel:subMenuList){
	    	    LinkedHashMap result2 = new LinkedHashMap();
	        	//得到二层，和第三层
	        	List<ColPage> twolevelSubMenuList = getOnelevel( twolevel.getId().toString());
	        	if(twolevelSubMenuList.size()>0){
	        		result2.put(twolevel, twolevelSubMenuList);
	        	}else{
	        		result2.put(twolevel, null);
	        	}
	        	//循环二三层
	        	al.add(result2);
	        }
	        result.put(menu, al);
	      } else {
	        result.put(menu, null);
	      }
	    }
	    return result;
	  }
	
	  @SuppressWarnings("unused")
	private List<ColPage> getOnelevel(String parentAuthId )
	  {
	    List<ColPage> curSubMenu = this.colPageDao.queryNav(parentAuthId);
 
	    	return curSubMenu;
	     
	  }
	  
	 
	
	 @SuppressWarnings("unused")
	private Boolean isParentMenu(ColPage curMenu, List<ColPage> subMenuList)
	  {
	    for (ColPage menu : subMenuList) {
	      if (curMenu.getId().toString().equals(menu.getParentid())) {
	        return Boolean.valueOf(true);
	      }
	    }
	    return Boolean.valueOf(false);
	  }

	  @SuppressWarnings("unused")
	private Boolean isSonMenu(ColPage curMenu, List<ColPage> subMenuList)
	  {
	    for (ColPage menu : subMenuList) {
	      if (menu.getId().toString().equals(curMenu.getParentid())) {
	        return Boolean.valueOf(true);
	      }
	    }

	    return Boolean.valueOf(false);
	  }

	  @SuppressWarnings("unused")
	private ArrayList<ColPage> getSubMenuList(List<ColPage> subMenuList, String parentId)
	  {
	    ArrayList result = new ArrayList();
	    for (ColPage menu : subMenuList) {
	      if (parentId.equals(menu.getParentid())) {
	        result.add(menu);
	      }
	    }
	    return result;
	  }
}
