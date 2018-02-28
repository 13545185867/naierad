package com.jeecg.p3.dictinfo.web.back;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jeecgframework.p3.core.util.SystemTools;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.dicttype.entity.Dicttype;
import com.jeecg.p3.dicttype.service.DicttypeService;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>DictinfoController<br>数据字典信息表
 * @author chao.hua
 * @since：2017年07月25日 17时04分33秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/dictinfo/back/dictinfo")
public class DictinfoController extends BaseController{
  @Autowired
  private DictinfoService dictinfoService;
  
  @Autowired
  private DicttypeService dicttypeService;
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Dictinfo query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("dictinfo",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(dictinfoService.queryPageList(pageQuery)));
		String viewName = "dictinfo/back/dictinfo-list.vm";
		
		 PageQuery<Dicttype> pageQuery1 = new PageQuery<Dicttype>();
		 pageQuery1.setPageNo(0);
		 pageQuery1.setPageSize(20);
			Dicttype dicttype = new Dicttype();
			pageQuery1.setQuery(dicttype);
		PageList<Dicttype> dictlist = dicttypeService.queryPageList(pageQuery1);
		 velocityContext.put("dicttypeInfos", SystemTools.convertPaginatedList(dictlist));
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void dictinfoDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "dictinfo/back/dictinfo-detail.vm";
		Dictinfo dictinfo = dictinfoService.queryById(id);
		velocityContext.put("dictinfo",dictinfo);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 PageQuery<Dicttype> pageQuery = new PageQuery<Dicttype>();
	 pageQuery.setPageNo(0);
	 pageQuery.setPageSize(20);
		Dicttype dicttype = new Dicttype();
		pageQuery.setQuery(dicttype);
	PageList<Dicttype> dictlist = dicttypeService.queryPageList(pageQuery);
	 velocityContext.put("pageInfos", SystemTools.convertPaginatedList(dictlist));
	 String viewName = "dictinfo/back/dictinfo-add.vm";

	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute Dictinfo dictinfo){
	AjaxJson j = new AjaxJson();
	try {
		dictinfoService.doAdd(dictinfo);
		j.setMsg("保存成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("保存失败");
	}
	return j;
}
 
/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 Dictinfo dictinfo = dictinfoService.queryById(id);
		 velocityContext.put("dictinfo",dictinfo);
		 
		 PageQuery<Dicttype> pageQuery = new PageQuery<Dicttype>();
		 pageQuery.setPageNo(0);
		 pageQuery.setPageSize(20);
			Dicttype dicttype = new Dicttype();
			pageQuery.setQuery(dicttype);
		PageList<Dicttype> dictlist = dicttypeService.queryPageList(pageQuery);
		 velocityContext.put("pageInfos", SystemTools.convertPaginatedList(dictlist));
		 String viewName = "dictinfo/back/dictinfo-edit.vm";

		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Dictinfo dictinfo){
	AjaxJson j = new AjaxJson();
	try {
		dictinfoService.doEdit(dictinfo);
		j.setMsg("编辑成功");
	} catch (Exception e) {
		j.setSuccess(false);
		j.setMsg("编辑失败");
	}
	return j;
}


/**
 * 删除
 * @return
 */
@RequestMapping(value="doDelete",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDelete(@RequestParam(required = true, value = "id" ) String id){
		AjaxJson j = new AjaxJson();
		try {
			dictinfoService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/getCategroy",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public String getCategroy(@RequestParam(required = true, value = "typeid" ) Integer typeid){
	net.sf.json.JSONObject js = new net.sf.json.JSONObject();
	 net.sf.json.JSONArray ja = new net.sf.json.JSONArray();
	try {
		Dictinfo pageQuery = new Dictinfo();
		pageQuery.setTypeid(typeid);
		List<Dictinfo> dictlist = dictinfoService.queryList(pageQuery);

		 js.put("success", true);
		 js.put("obj", ja.fromObject(dictlist));
	} catch (Exception e) {
		 js.put("success", false);
	}
	return js.toString();
}



}

