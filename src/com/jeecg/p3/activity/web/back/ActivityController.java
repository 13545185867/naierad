package com.jeecg.p3.activity.web.back;

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
import com.jeecg.p3.activity.service.ActivityService;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.system.vo.LoginUser;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>ActivityController<br>会员活动
 * @author chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/activity/back/activity")
public class ActivityController extends BaseController{
  @Autowired
  private ActivityService activityService;
  
  @Autowired
  private DictinfoService dictinfoService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Activity query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Activity> pageQuery = new PageQuery<Activity>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
	    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	    query.setCreatuser(user.getUserId());
	    if(query.getIsdelete() ==null){
	    	query.setIsdelete("0");
	    }
	    //如果是管理员角色可以查看所有人发的
	    if(BaihuaUtil.isadmin((List<String>) request.getSession().getAttribute("roleIds"))){
	    	query.setCreatuser(null);
	    }
		velocityContext.put("activity",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(activityService.queryPageList(pageQuery)));
		
		 PageQuery<Dictinfo> pageQuery1 = new PageQuery<Dictinfo>();
		 pageQuery1.setPageNo(0);
		 pageQuery1.setPageSize(20);
		 Dictinfo dictinfo = new Dictinfo();
		 dictinfo.setTypeid(5);  
			pageQuery1.setQuery(dictinfo);
		PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery1);
		 velocityContext.put("areaInfos", SystemTools.convertPaginatedList(dictlist));
		 
		String viewName = "activity/back/activity-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void activityDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "activity/back/activity-detail.vm";
		Activity activity = activityService.queryById(id);
		velocityContext.put("activity",activity);
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
	 
	 String viewName = "activity/back/activity-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(HttpServletRequest request,@ModelAttribute Activity activity){
	AjaxJson j = new AjaxJson();
	try {
		activity.setRemainpepole(activity.getTotalpepole());
		activity.setIsdelete("0");
		activity.setCreatetime(new Date());
		activity.setCreatuser(((LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER")).getUserId());
		activityService.doAdd(activity);
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
		 Activity activity = activityService.queryById(id);
		 velocityContext.put("activity",activity);
		 String viewName = "activity/back/activity-edit.vm";
		 
		 PageQuery<Dictinfo> pageQuery = new PageQuery<Dictinfo>();
		 pageQuery.setPageNo(0);
		 pageQuery.setPageSize(20);
		 Dictinfo dictinfo = new Dictinfo();
		 dictinfo.setTypeid(5);  
			pageQuery.setQuery(dictinfo);
		PageList<Dictinfo> dictlist = dictinfoService.queryPageList(pageQuery);
		 velocityContext.put("areaInfos", SystemTools.convertPaginatedList(dictlist));
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value="doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Activity activity ,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
    activity.setCreatuser(user.getUserId());
	try {
		activityService.doEdit(activity);
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
			activityService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

/**
 * 删除
 * @return
 */
@RequestMapping(value="doDel",method = RequestMethod.GET)
@ResponseBody
public AjaxJson doDel(@RequestParam(required = true, value = "id" ) String id,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		Activity activity = new Activity();
		activity.setId(id);
		activity.setIsdelete("1");
	    LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
	    activity.setCreatuser(user.getUserId());
		try {
			activityService.doEdit(activity);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}

}

