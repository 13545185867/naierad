package com.jeecg.p3.messageHelp.web.back;

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
import com.jeecg.p3.messageHelp.entity.Messagetemplate;
import com.jeecg.p3.messageHelp.service.MessagetemplateService;

import org.jeecgframework.p3.core.web.BaseController;

/**
 * 描述：</b>MessagetemplateController<br>
 * 
 * @author chao.hua
 * @since：2017年08月18日 12时42分12秒 星期五
 * @version:1.0
 */
@Controller
@RequestMapping("/messageHelp/back/messagetemplate")
public class MessagetemplateController extends BaseController {
	@Autowired
	private MessagetemplateService messagetemplateService;

	@Autowired
	private DictinfoService dictinfoService;

	/**
	 * 列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void list(
			@ModelAttribute Messagetemplate query,
			HttpServletResponse response,
			HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize)
			throws Exception {
		PageQuery<Messagetemplate> pageQuery = new PageQuery<Messagetemplate>();
		pageQuery.setPageNo(pageNo);
		pageQuery.setPageSize(pageSize);
		VelocityContext velocityContext = new VelocityContext();
		query.setIsdelete(0);
		pageQuery.setQuery(query);
		velocityContext.put("messagetemplate", query);
		velocityContext.put("pageInfos", SystemTools
				.convertPaginatedList(messagetemplateService
						.queryPageList(pageQuery)));
		
		Dictinfo dictinfo = new Dictinfo();
		dictinfo.setTypeid(4);
		List<Dictinfo> dictlist = dictinfoService.queryList(dictinfo);
		velocityContext.put("dictlist", dictlist); 
		String viewName = "messageHelp/back/messagetemplate-list.vm";
		ViewVelocity.view(request, response, viewName, velocityContext);
	}

	/**
	 * 详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "toDetail", method = RequestMethod.GET)
	public void messagetemplateDetail(
			@RequestParam(required = true, value = "id") String id,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "messageHelp/back/messagetemplate-detail.vm";
		Messagetemplate messagetemplate = messagetemplateService.queryById(id);
		velocityContext.put("messagetemplate", messagetemplate);
		ViewVelocity.view(request, response, viewName, velocityContext);
	}

	/**
	 * 跳转到添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/toAdd", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void toAddDialog(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws Exception {
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "messageHelp/back/messagetemplate-add.vm";
		ViewVelocity.view(request, response, viewName, velocityContext);
	}

	/**
	 * 保存信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doAdd", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson doAdd(@ModelAttribute Messagetemplate messagetemplate) {
		AjaxJson j = new AjaxJson();
		try {
			messagetemplate.setIsdelete(0);
			messagetemplateService.doAdd(messagetemplate);
			j.setMsg("保存成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("保存失败");
		}
		return j;
	}

	/**
	 * 跳转到编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "toEdit", method = RequestMethod.GET)
	public void toEdit(@RequestParam(required = true, value = "id") String id,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		VelocityContext velocityContext = new VelocityContext();
		Messagetemplate messagetemplate = messagetemplateService.queryById(id);
		velocityContext.put("messagetemplate", messagetemplate);
		String viewName = "messageHelp/back/messagetemplate-edit.vm";
		ViewVelocity.view(request, response, viewName, velocityContext);
	}

	/**
	 * 编辑
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doEdit", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public AjaxJson doEdit(@ModelAttribute Messagetemplate messagetemplate) {
		AjaxJson j = new AjaxJson();
		try {
			messagetemplateService.doEdit(messagetemplate);
			
			j.setMsg("编辑成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("编辑失败");
		}
		return j;
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "doDelete", method = RequestMethod.GET)
	@ResponseBody
	public AjaxJson doDelete(
			@RequestParam(required = true, value = "id") String id) {
		AjaxJson j = new AjaxJson();
		try {
			Messagetemplate messagetemplate = new Messagetemplate();
			messagetemplate.setId(Integer.parseInt(id));
			messagetemplate.setIsdelete(1);
			messagetemplateService.doEdit(messagetemplate);
			//messagetemplateService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
	}

	@RequestMapping(value = "getAllTemplate", method = { RequestMethod.GET,
			RequestMethod.POST })
	@ResponseBody
	public String getAllTemplate(@ModelAttribute Messagetemplate messagetemplate) {

		Dictinfo dictinfo = new Dictinfo();
		dictinfo.setTypeid(4);
		List<Dictinfo> dictlist = dictinfoService.queryList(dictinfo);
		net.sf.json.JSONObject js = new net.sf.json.JSONObject();
		net.sf.json.JSONArray ja = new net.sf.json.JSONArray();
		net.sf.json.JSONArray ja1 = new net.sf.json.JSONArray(); 
		try {
			for (Dictinfo df : dictlist) {
				net.sf.json.JSONObject js1 = new net.sf.json.JSONObject();
				PageQuery<Messagetemplate> pageQuery = new PageQuery<Messagetemplate>();
				Messagetemplate mt = new Messagetemplate();
				mt.setAttr1(df.getDictcode());
				mt.setIsdelete(0);
				pageQuery.setPageNo(1); 
				pageQuery.setPageSize(7);
				pageQuery.setQuery(mt);
				PageList<Messagetemplate> pagelist = messagetemplateService
						.queryPageList(pageQuery);
				js1.put("curpage", 1);
				js1.put("maxpage", pagelist.getPagenation().getPageCount());
				js1.put("templatelist", ja.fromObject(pagelist.getValues()));
				ja1.add(js1);
			}
			js.put("success", true);
			js.put("obj", ja1);
			return js.toString();
		} catch (Exception e) {
			js.put("success", false);
		}
		return js.toString();
	}
}
