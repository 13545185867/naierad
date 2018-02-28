package com.jeecg.p3.activitybookrecord.web.back;

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
import org.jeecgframework.p3.core.utils.common.PageQuery;
import com.jeecg.p3.activitybookrecord.entity.Activitybookrecord;
import com.jeecg.p3.activitybookrecord.service.ActivitybookrecordService;
import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>ActivitybookrecordController<br>活动报名记录
 * @author chao.hua
 * @since：2017年09月11日 11时59分43秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/activitybookrecord/back/activitybookrecord")
public class ActivitybookrecordController extends BaseController{
  @Autowired
  private ActivitybookrecordService activitybookrecordService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Activitybookrecord query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Activitybookrecord> pageQuery = new PageQuery<Activitybookrecord>();
	 	String actid = request.getParameter("activityId");
	 	query.setActivityid(actid);
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("activitybookrecord",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(activitybookrecordService.queryBookedPageList(pageQuery)));
		String viewName = "activitybookrecord/back/activitybookrecord-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void activitybookrecordDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "activitybookrecord/back/activitybookrecord-detail.vm";
		Activitybookrecord activitybookrecord = activitybookrecordService.queryById(id);
		velocityContext.put("activitybookrecord",activitybookrecord);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "activitybookrecord/back/activitybookrecord-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute Activitybookrecord activitybookrecord){
	AjaxJson j = new AjaxJson();
	try {
		activitybookrecordService.doAdd(activitybookrecord);
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
		 Activitybookrecord activitybookrecord = activitybookrecordService.queryById(id);
		 velocityContext.put("activitybookrecord",activitybookrecord);
		 String viewName = "activitybookrecord/back/activitybookrecord-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Activitybookrecord activitybookrecord){
	AjaxJson j = new AjaxJson();
	try {
		activitybookrecordService.doEdit(activitybookrecord);
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
			activitybookrecordService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

