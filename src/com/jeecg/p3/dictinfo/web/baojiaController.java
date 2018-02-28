package com.jeecg.p3.dictinfo.web;

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

import com.jeecg.p3.appmodel.entity.Appmodel;
import com.jeecg.p3.appmodel.service.AppmodelService;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.dicttype.entity.Dicttype;
import com.jeecg.p3.dicttype.service.DicttypeService;
import com.jeecg.p3.serviceprice.entity.Serviceprice;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>DictinfoController<br>数据字典信息表
 * @author chao.hua
 * @since：2017年07月25日 17时04分33秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/dictinfo")
public class baojiaController extends BaseController{
  @Autowired
  private DictinfoService dictinfoService;
  
  @Autowired
  private DicttypeService dicttypeService;
  
  @Autowired
  private AppmodelService appmodelService;
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Dictinfo query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "id" ) String id ) throws Exception{
 	VelocityContext velocityContext = new VelocityContext();
	Appmodel appmodel = new Appmodel();
	Dictinfo dic = new Dictinfo();
	dic.setTypeid(Integer.parseInt("1"));

	List<Dictinfo> dictlist = dictinfoService.queryList(dic);
	velocityContext.put("dictlist",dictlist);
	List<Appmodel> applist = null;
	if(id ==null || id.equals("")){
		 applist = appmodelService.queryAppList(appmodel);
	}else{
		appmodel.setDictinfoid(Integer.parseInt(id));
		applist = appmodelService.queryAppList(appmodel);
	}
	velocityContext.put("applist",applist);
	velocityContext.put("selectedid",id);
		String viewName = "dictinfo/back/dictinfolist.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="yflist",method = {RequestMethod.GET,RequestMethod.POST})
public void yflist(@ModelAttribute Dictinfo query,HttpServletResponse response,HttpServletRequest request) throws Exception{
 	VelocityContext velocityContext = new VelocityContext();
	Appmodel appmodel = new Appmodel();
	List<Appmodel> applist = null;
		appmodel.setCode("huiyuanhuifei");
		applist = appmodelService.queryAppList(appmodel);

	velocityContext.put("applist",applist);
		String viewName = "jlb/taocanlist.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

}

