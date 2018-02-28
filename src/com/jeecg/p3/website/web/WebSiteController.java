package com.jeecg.p3.website.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.system.entity.TreeNode;
import com.jeecg.p3.website.entity.ColContent;
import com.jeecg.p3.website.entity.ColPage;
import com.jeecg.p3.website.service.ColContentService;
import com.jeecg.p3.website.service.ColPageService;

import org.jeecgframework.p3.core.web.BaseController;

import redis.clients.jedis.Jedis;


 /**
 * 描述：</b>ColPageController<br>页面栏目表
 * @author chao.hua
 * @since：2017年11月04日 14时47分40秒 星期六 
 * @version:1.0
 */
@Controller
@RequestMapping("/")
public class WebSiteController extends BaseController{
  @Autowired
  private ColPageService colPageService;
  
  @Value("#{sysconfig['redis.ip']}")
  private String redisip;
  
  @Autowired
  private ColContentService colContentService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="index",method = {RequestMethod.GET,RequestMethod.POST})
public void list( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	 	 
	 	VelocityContext velocityContext = new VelocityContext();
	 	String rootnavtree = "rootnavtree";
	 	ServletContext context = request.getServletContext();
	 	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	 	if(menuTree == null){
	 		menuTree = colPageService.getSubMenuTree(null);
	 		context.setAttribute(rootnavtree,menuTree);
	 	}
	 	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/index.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="product",method = {RequestMethod.GET,RequestMethod.POST})
public void product( HttpServletResponse response,HttpServletRequest request,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "6") int pageSize) throws Exception{
	 	 String pid = request.getParameter("pid");
	 	 String colid = request.getParameter("colid");
	 	VelocityContext velocityContext = new VelocityContext();
	 	ServletContext context = request.getServletContext();
 		String navtree = pid+"navtree";
	 	List<ColPage> selectmenuTree = (List<ColPage>) context.getAttribute(navtree);
	 	if(pid != null && !pid.equals("")){
		 	if(selectmenuTree == null){
		 		selectmenuTree = colPageService.queryNav(pid);
		 		context.setAttribute(navtree,selectmenuTree);
		 	}
	 	}
	 	String rootnavtree = "rootnavtree";

	 	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	 	if(menuTree == null){
	 		menuTree = colPageService.getSubMenuTree(null);
	 		context.setAttribute(rootnavtree,menuTree);
	 	}
		ColContent query = new ColContent();
		query.setColId(Integer.parseInt(colid));
		query.setStatus(1);
	 	PageQuery<ColContent> pageQuery = new PageQuery<ColContent>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
		pageQuery.setQuery(query);
		velocityContext.put("colContent",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(colContentService.queryPageList(pageQuery)));
	 	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
	 	velocityContext.put("OPERATE_WEB_MENU_TREE_SEL", selectmenuTree);
	 	velocityContext.put("SEL_NAV", colid);
	 	velocityContext.put("SEL_Parent", pid);
		String viewName = "website/back/product.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="resource",method = {RequestMethod.GET,RequestMethod.POST})
public void resource( HttpServletResponse response,HttpServletRequest request,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 String pid = request.getParameter("pid");
 	 String colid = request.getParameter("colid");
 	VelocityContext velocityContext = new VelocityContext();
 	ServletContext context = request.getServletContext();
		String navtree = pid+"navtree";
 	List<ColPage> selectmenuTree = (List<ColPage>) context.getAttribute(navtree);
 	if(pid != null && !pid.equals("")){
	 	if(selectmenuTree == null){
	 		selectmenuTree = colPageService.queryNav(pid);
	 		context.setAttribute(navtree,selectmenuTree);
	 	}
 	}
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	ColContent query = new ColContent();
	query.setColId(Integer.parseInt(colid));
	query.setStatus(1);
 	PageQuery<ColContent> pageQuery = new PageQuery<ColContent>();
 	pageQuery.setPageNo(pageNo);
 	pageQuery.setPageSize(pageSize);
	pageQuery.setQuery(query);
	velocityContext.put("colContent",query);
	velocityContext.put("pageInfos",SystemTools.convertPaginatedList(colContentService.queryPageList(pageQuery)));
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/resource.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="case",method = {RequestMethod.GET,RequestMethod.POST})
public void caselist( HttpServletResponse response,HttpServletRequest request,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "6") int pageSize) throws Exception{
	 String colid = request.getParameter("colid");
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	ColContent query = new ColContent();
	query.setColId(Integer.parseInt(colid));
	query.setStatus(1);
 	PageQuery<ColContent> pageQuery = new PageQuery<ColContent>();
 	pageQuery.setPageNo(pageNo);
 	pageQuery.setPageSize(pageSize);
	pageQuery.setQuery(query);
	velocityContext.put("colContent",query);
	velocityContext.put("pageInfos",SystemTools.convertPaginatedList(colContentService.queryPageList(pageQuery)));
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
 	velocityContext.put("SEL_NAV", colid);
		String viewName = "website/back/case.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="news",method = {RequestMethod.GET,RequestMethod.POST})
public void news( HttpServletResponse response,HttpServletRequest request,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "6") int pageSize) throws Exception{
	 String colid = request.getParameter("colid");
	 String pid = request.getParameter("pid");
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String navtree = pid+"navtree";
	List<ColPage> selectmenuTree = (List<ColPage>) context.getAttribute(navtree);
	if(pid != null && !pid.equals("")){
	 	if(selectmenuTree == null){
	 		selectmenuTree = colPageService.queryNav(pid);
	 		context.setAttribute(navtree,selectmenuTree);
	 	}
	}
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	ColContent query = new ColContent();
	query.setColId(Integer.parseInt(colid));
	query.setStatus(1);
 	PageQuery<ColContent> pageQuery = new PageQuery<ColContent>();
 	pageQuery.setPageNo(pageNo);
 	pageQuery.setPageSize(pageSize);
	pageQuery.setQuery(query);
	velocityContext.put("colContent",query);
	velocityContext.put("pageInfos",SystemTools.convertPaginatedList(colContentService.queryPageList(pageQuery)));
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
	velocityContext.put("OPERATE_WEB_MENU_TREE_SEL", selectmenuTree);
	velocityContext.put("SEL_NAV", colid);
		String viewName = "website/back/news.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="aboutus",method = {RequestMethod.GET,RequestMethod.POST})
public void aboutus( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/aboutus.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="ourmachine",method = {RequestMethod.GET,RequestMethod.POST})
public void ourmachine( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/machine.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="ourteam",method = {RequestMethod.GET,RequestMethod.POST})
public void ourteam( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/team.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="adposition",method = {RequestMethod.GET,RequestMethod.POST})
public void adposition( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/adposition.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="adcar",method = {RequestMethod.GET,RequestMethod.POST})
public void adcar( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/adcar.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="adev",method = {RequestMethod.GET,RequestMethod.POST})
public void adev( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	ServletContext context = request.getServletContext();
	String rootnavtree = "rootnavtree";

	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	if(menuTree == null){
		menuTree = colPageService.getSubMenuTree(null);
		context.setAttribute(rootnavtree,menuTree);
	}
	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/adev.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}
@RequestMapping(value="contactus",method = {RequestMethod.GET,RequestMethod.POST})
public void contactus( HttpServletResponse response,HttpServletRequest request ) throws Exception{
	 	 
	 	VelocityContext velocityContext = new VelocityContext();
	 	String rootnavtree = "rootnavtree";
	 	ServletContext context = request.getServletContext();
	 	LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
	 	if(menuTree == null){
	 		menuTree = colPageService.getSubMenuTree(null);
	 		context.setAttribute(rootnavtree,menuTree);
	 	}
	 	velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		String viewName = "website/back/contactus.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

	@RequestMapping(value="detail",method = {RequestMethod.GET,RequestMethod.POST})
	public void detail( HttpServletResponse response,HttpServletRequest request ) throws Exception{
		 String id = request.getParameter("id");
		VelocityContext velocityContext = new VelocityContext();
		ServletContext context = request.getServletContext();
		String rootnavtree = "rootnavtree";
		LinkedHashMap menuTree = (LinkedHashMap) context.getAttribute(rootnavtree);
		if(menuTree == null){
			menuTree = colPageService.getSubMenuTree(null);
			context.setAttribute(rootnavtree,menuTree);
		}
		ColContent colContent = colContentService.queryById(id);
		velocityContext.put("OPERATE_WEB_MENU_TREE", menuTree);
		velocityContext.put("colContent",colContent);
			String viewName = "website/back/detail.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
	}

}