package com.jeecg.p3.serviceprice.web.back;

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
import com.jeecg.p3.serviceprice.entity.Serviceprice;
import com.jeecg.p3.serviceprice.service.ServicepriceService;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>ServicepriceController<br>服务报价表
 * @author chao.hua
 * @since：2017年07月25日 17时07分59秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/serviceprice/back/serviceprice")
public class ServicepriceController extends BaseController{
  @Autowired
  private ServicepriceService servicepriceService;
  
  @Autowired
  private DictinfoService dictinfoService;
  
  
  @Autowired
  private AppmodelService appmodelService;
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Serviceprice query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Serviceprice> pageQuery = new PageQuery<Serviceprice>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("serviceprice",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(servicepriceService.queryPageList(pageQuery)));
		String viewName = "serviceprice/back/serviceprice-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void servicepriceDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "serviceprice/back/serviceprice-detail.vm";
		Serviceprice serviceprice = servicepriceService.queryById(id);
		velocityContext.put("serviceprice",serviceprice);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/** 
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 String viewName = "serviceprice/back/serviceprice-add.vm";
	 	PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
	 	pageQuery.setPageNo(0);
	 	pageQuery.setPageSize(20);
	 	VelocityContext velocityContext = new VelocityContext();
	 	Dictinfo query = new Dictinfo();
	 	query.setTypeid(2);
		pageQuery.setQuery(query);
		velocityContext.put("dictinfpageInfos",SystemTools.convertPaginatedList(dictinfoService.queryPageList(pageQuery)));
	 
	 	PageQuery<Appmodel> pageQuery1 = new PageQuery<Appmodel>();
	 	pageQuery1.setPageNo(0);
	 	pageQuery1.setPageSize(20);
	 	Appmodel query1 = new Appmodel();
		pageQuery1.setQuery(query1);
		velocityContext.put("appmodelpageInfos",SystemTools.convertPaginatedList(appmodelService.queryPageList(pageQuery1)));
		
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute Serviceprice serviceprice){
	AjaxJson j = new AjaxJson();
	try {
		servicepriceService.doAdd(serviceprice);
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
		 Serviceprice serviceprice = servicepriceService.queryById(id);
		 velocityContext.put("serviceprice",serviceprice);
		 String viewName = "serviceprice/back/serviceprice-edit.vm";
		 
			PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
		 	pageQuery.setPageNo(0);
		 	pageQuery.setPageSize(20);
		 	Dictinfo query = new Dictinfo();
		 	query.setTypeid(2);
			pageQuery.setQuery(query);
			velocityContext.put("dictinfpageInfos",SystemTools.convertPaginatedList(dictinfoService.queryPageList(pageQuery)));
		 
		 	PageQuery<Appmodel> pageQuery1 = new PageQuery<Appmodel>();
		 	pageQuery1.setPageNo(0);
		 	pageQuery1.setPageSize(20);
		 	Appmodel query1 = new Appmodel();
			pageQuery1.setQuery(query1); 
			velocityContext.put("appmodelpageInfos",SystemTools.convertPaginatedList(appmodelService.queryPageList(pageQuery1)));
			
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Serviceprice serviceprice){
	AjaxJson j = new AjaxJson();
	try {
		servicepriceService.doEdit(serviceprice);
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
			servicepriceService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

