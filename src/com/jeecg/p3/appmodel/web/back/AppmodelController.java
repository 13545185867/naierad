package com.jeecg.p3.appmodel.web.back;

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

import com.jeecg.p3.appmodel.entity.Appmodel;
import com.jeecg.p3.appmodel.service.AppmodelService;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.dicttype.entity.Dicttype;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>AppmodelController<br>应用模块表
 * @author chao.hua
 * @since：2017年07月25日 17时05分57秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/appmodel/back/appmodel")
public class AppmodelController extends BaseController{
  @Autowired
  private AppmodelService appmodelService;
  @Autowired
  private DictinfoService dictinfoService;
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Appmodel query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Appmodel> pageQuery = new PageQuery<Appmodel>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("appmodel",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(appmodelService.queryPageList(pageQuery)));
		
		 PageQuery<Dictinfo> pageQuery1 = new PageQuery<Dictinfo>();
		 pageQuery1.setPageNo(0);
		 pageQuery1.setPageSize(20);
		 Dictinfo dictinfo = new Dictinfo();
		 dictinfo.setTypeid(1);  
			pageQuery1.setQuery(dictinfo);
		PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery1);
		 velocityContext.put("idctInfos", SystemTools.convertPaginatedList(dictlist));
		String viewName = "appmodel/back/appmodel-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void appmodelDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "appmodel/back/appmodel-detail.vm";
		Appmodel appmodel = appmodelService.queryById(id);
		velocityContext.put("appmodel",appmodel);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "appmodel/back/appmodel-add.vm";
	 
	 PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
	 pageQuery.setPageNo(0);
	 pageQuery.setPageSize(20);
	 Dictinfo dictinfo = new Dictinfo();
	 dictinfo.setTypeid(1);  
		pageQuery.setQuery(dictinfo);
	PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery);
	 velocityContext.put("pageInfos", SystemTools.convertPaginatedList(dictlist));
	 ViewVelocity.view(request,response,viewName,velocityContext);
}
 
/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute Appmodel appmodel){
	AjaxJson j = new AjaxJson();
	try {
		appmodelService.doAdd(appmodel);
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
		 Appmodel appmodel = appmodelService.queryById(id);
		 velocityContext.put("appmodel",appmodel);
		 String viewName = "appmodel/back/appmodel-edit.vm";
		 PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
		 pageQuery.setPageNo(0);
		 pageQuery.setPageSize(20);
		 Dictinfo dictinfo = new Dictinfo();
		 dictinfo.setTypeid(1);  
			pageQuery.setQuery(dictinfo);
		PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery);
		 velocityContext.put("pageInfos", SystemTools.convertPaginatedList(dictlist));
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Appmodel appmodel){
	AjaxJson j = new AjaxJson();
	try {
		appmodelService.doEdit(appmodel);
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
			appmodelService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

