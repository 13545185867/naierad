package com.jeecg.p3.noticeandnews.web.back;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.jeecgframework.p3.cg.util.BaihuaUtil;
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

import com.jeecg.p3.activity.entity.Activity;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.noticeandnews.entity.Noticeandnews;
import com.jeecg.p3.noticeandnews.service.NoticeandnewsService;
import com.jeecg.p3.system.vo.LoginUser;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>NoticeandnewsController<br>公告与资讯
 * @author chao.hua
 * @since：2017年09月20日 15时38分14秒 星期三 
 * @version:1.0
 */
@Controller
@RequestMapping("/noticeandnews/back/noticeandnews")
public class NoticeandnewsController extends BaseController{
  @Autowired
  private NoticeandnewsService noticeandnewsService;
  
  @Autowired
  private DictinfoService dictinfoService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Noticeandnews query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Noticeandnews> pageQuery = new PageQuery<Noticeandnews>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
	    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	    query.setCreateuser(user.getUserId());;
	    if(query.getIsdelete() ==null){
	    	query.setIsdelete("0");
	    }
	    //如果是管理员角色可以查看所有人发的
	    if(BaihuaUtil.isadmin((List<String>) request.getSession().getAttribute("roleIds"))){
	    	query.setCreateuser(null);
	    }
		pageQuery.setQuery(query);
		velocityContext.put("noticeandnews",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(noticeandnewsService.queryPageList(pageQuery)));
		
		
		 PageQuery<Dictinfo> pageQuery0 = new PageQuery<Dictinfo>();
		 pageQuery0.setPageNo(0);
		 pageQuery0.setPageSize(20);
		 Dictinfo dictinfo = new Dictinfo();
		 dictinfo.setTypeid(5);  
			pageQuery0.setQuery(dictinfo);
		PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery0);
		 velocityContext.put("areaInfos", SystemTools.convertPaginatedList(dictlist));
		 
		 
		 PageQuery<Dictinfo> pageQuery1 = new PageQuery<Dictinfo>();
		 pageQuery1.setPageNo(0);
		 pageQuery1.setPageSize(20);
		 Dictinfo dictinfo1 = new Dictinfo();
		 dictinfo1.setTypeid(6);  
			pageQuery1.setQuery(dictinfo1);
		PageList<Dictinfo> dictlist1 = dictinfoService.queryPageList(pageQuery1);
		 velocityContext.put("typeInfos", SystemTools.convertPaginatedList(dictlist1));
		 
		String viewName = "noticeandnews/back/noticeandnews-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void noticeandnewsDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "noticeandnews/back/noticeandnews-detail.vm";
		Noticeandnews noticeandnews = noticeandnewsService.queryById(id);
		velocityContext.put("noticeandnews",noticeandnews);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 
	 PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
	 pageQuery.setPageNo(0);
	 pageQuery.setPageSize(20);
	 Dictinfo dictinfo = new Dictinfo();
	 dictinfo.setTypeid(5);  
		pageQuery.setQuery(dictinfo);
	PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery);
	 velocityContext.put("areaInfos", SystemTools.convertPaginatedList(dictlist));
	 
	 
	 PageQuery<Dictinfo> pageQuery1 = new PageQuery<Dictinfo>();
	 pageQuery1.setPageNo(0);
	 pageQuery1.setPageSize(20);
	 Dictinfo dictinfo1 = new Dictinfo();
	 dictinfo1.setTypeid(6);  
		pageQuery1.setQuery(dictinfo1);
	PageList<Dictinfo> dictlist1 = dictinfoService.queryPageList(pageQuery1);
	 velocityContext.put("typeInfos", SystemTools.convertPaginatedList(dictlist1));
	 
	 
	 String viewName = "noticeandnews/back/noticeandnews-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute Noticeandnews noticeandnews,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
	try {
		noticeandnews.setCreateuser(((LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER")).getUserId());
		noticeandnews.setIsdelete("0");
		noticeandnews.setCreatetime(new Date());
		noticeandnewsService.doAdd(noticeandnews);
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
		 Noticeandnews noticeandnews = noticeandnewsService.queryById(id);
		 velocityContext.put("noticeandnews",noticeandnews);
		 String viewName = "noticeandnews/back/noticeandnews-edit.vm";
		 
		 PageQuery<Dictinfo> pageQuery0 = new PageQuery<Dictinfo>();
		 pageQuery0.setPageNo(0);
		 pageQuery0.setPageSize(20);
		 Dictinfo dictinfo = new Dictinfo();
		 dictinfo.setTypeid(5);  
			pageQuery0.setQuery(dictinfo);
		PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery0);
		 velocityContext.put("areaInfos", SystemTools.convertPaginatedList(dictlist));
		 
		 
		 PageQuery<Dictinfo> pageQuery1 = new PageQuery<Dictinfo>();
		 pageQuery1.setPageNo(0);
		 pageQuery1.setPageSize(20);
		 Dictinfo dictinfo1 = new Dictinfo();
		 dictinfo1.setTypeid(6);  
			pageQuery1.setQuery(dictinfo1);
		PageList<Dictinfo> dictlist1 = dictinfoService.queryPageList(pageQuery1);
		 velocityContext.put("typeInfos", SystemTools.convertPaginatedList(dictlist1));
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Noticeandnews noticeandnews,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
	try {
	    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	    noticeandnews.setCreateuser(user.getUserId());
		noticeandnewsService.doEdit(noticeandnews);
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
			noticeandnewsService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

@RequestMapping(value="doDel",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDel(@RequestParam(required = true, value = "id" ) String id,HttpServletRequest request){
	Noticeandnews noticeandnews = new Noticeandnews();
	noticeandnews.setId(id);
	noticeandnews.setIsdelete("1");
	    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	    noticeandnews.setCreateuser(user.getUserId());
		AjaxJson j = new AjaxJson();
		try {
			noticeandnewsService.doEdit(noticeandnews);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

