package com.jeecg.p3.weixinaccount.web.back;

import java.util.Date;

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

import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.service.JwWebJwidService;

import org.jeecgframework.p3.core.web.BaseController;

import weixin.util.DateUtils;

 /**
 * 描述：</b>JwWebJwidController<br>微信公众号字典表
 * @author chao.hua
 * @since：2017年07月21日 18时09分15秒 星期五 
 * @version:1.0
 */
@Controller
@RequestMapping("/weixinaccount/back/jwWebJwid")
public class JwWebJwidnewController extends BaseController{
  @Autowired
  private JwWebJwidService jwWebJwidServicenew;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute JwWebJwid query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<JwWebJwid> pageQuery = new PageQuery<JwWebJwid>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("jwWebJwid",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(jwWebJwidServicenew.queryPageList(pageQuery)));
		String viewName = "weixinaccount/back/jwWebJwid-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void jwWebJwidDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "weixinaccount/back/jwWebJwid-detail.vm";
		JwWebJwid jwWebJwid = jwWebJwidServicenew.queryById(id);
		velocityContext.put("jwWebJwid",jwWebJwid);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "weixinaccount/back/jwWebJwid-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 */
@RequestMapping(value = "/doAdd",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doAdd(@ModelAttribute JwWebJwid jwWebJwid,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
	try {
		long currentTime = new Date().getTime() - 1000 * 3600 * 10;
		LoginUser user = (LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER");
		if(user != null){
		jwWebJwid.setUsername(user.getUserId());
		jwWebJwid.setAddtoekntime(DateUtils.getDate(currentTime));
		jwWebJwidServicenew.doAdd(jwWebJwid);
		j.setMsg("保存成功");
		}else{
			j.setSuccess(false);
			j.setMsg("请先登录！");
		}
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
		 JwWebJwid jwWebJwid = jwWebJwidServicenew.queryById(id);
		 velocityContext.put("jwWebJwid",jwWebJwid);
		 String viewName = "weixinaccount/back/jwWebJwid-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute JwWebJwid jwWebJwid){
	AjaxJson j = new AjaxJson();
	try {
		jwWebJwidServicenew.doEdit(jwWebJwid);
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
			jwWebJwidServicenew.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}


}

