package com.jeecg.p3.order.web.back;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.order.entity.Order;
import com.jeecg.p3.order.service.OrderService;
import com.jeecg.p3.serviceprice.entity.Serviceprice;
import com.jeecg.p3.serviceprice.service.ServicepriceService;
import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.userservice.entity.Userservice;
import com.jeecg.p3.userservice.service.UserserviceService;
import com.weixin.pay.WXPay;
import com.weixin.pay.WXPayConfig;

import org.jeecgframework.p3.core.web.BaseController;

import weixin.util.DateUtils;

 /**
 * 描述：</b>OrderController<br>订单表
 * @author chao.hua
 * @since：2017年07月25日 17时07分06秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/order/back/order") 
public class OrderController extends BaseController{
  @Autowired
  private OrderService orderService;
  
  @Autowired
  private ServicepriceService servicepriceService;
  
  @Autowired
  private DictinfoService dictinfoService;
  
  @Autowired
  private UserserviceService userserviceService;
  
  @Value("#{sysconfig['wxpay.notify.url']}")
  private String notifyurl;
  @Value("#{sysconfig['spbill_create_ip']}")
  private String billcreateip;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="list",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Order query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Order> pageQuery = new PageQuery<Order>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	String userid = ((LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER")).getUserId();
	 	query.setCreator(userid);
	    //如果是管理员角色可以查看所有人发的
	    if(BaihuaUtil.isadmin((List<String>) request.getSession().getAttribute("roleIds"))){
	    	query.setCreator(null);
	    }
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("order",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(orderService.queryPageList(pageQuery)));
		String viewName = "order/back/order-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

 /**
  * 详情
  * @return
  */
@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void orderDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "order/back/order-detail.vm";
		Order order = orderService.queryById(id);
		Serviceprice serviceprice =servicepriceService.queryById(order.getServiceid().toString());
		
		WXPayConfig wxpc= new WXPayConfig();
		WXPay wxp = new WXPay(wxpc, notifyurl, false, false);
		// 获取发起电脑 ip  
        String spbill_create_ip = billcreateip;  
        // 回调接口   
        String trade_type = "NATIVE";  
          
        Map<String,String> packageParams = new TreeMap<String,String>(); 
        packageParams.put("body", serviceprice.getServicepricecol0());  
        packageParams.put("out_trade_no", order.getId().toString());  
        packageParams.put("total_fee",  (order.getPrice().multiply(new BigDecimal(100))).toString().split("\\.")[0]);  
        packageParams.put("spbill_create_ip", spbill_create_ip);  
        packageParams.put("trade_type", trade_type); 
        Map<String,String> requestMap =wxp.fillRequestData(packageParams);
        Map<String,String> responseMap = wxp.unifiedOrder(requestMap); 
        if(responseMap.get("result_code").equals("SUCCESS")){
        	order.setPaycodeurl(responseMap.get("code_url"));
        }
		 
		order.setExpiretime(DateUtils.getDate(((new Date().getTime())+1000*3600*2)));
		orderService.doEdit(order);
		
		velocityContext.put("order",order);
		velocityContext.put("payurl", URLDecoder.decode(order.getPaycodeurl()));
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 跳转到添加页面
 * @return
 */
@RequestMapping(value = "/toAdd",method ={RequestMethod.GET, RequestMethod.POST})
public void toAddDialog(HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
	 VelocityContext velocityContext = new VelocityContext();
	 String viewName = "order/back/order-add.vm";
	 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 保存信息
 * @return
 * @throws IOException 
 */
@RequestMapping(value = "/doAddOrder",method ={RequestMethod.GET, RequestMethod.POST})
public void doAddOrder(HttpServletRequest request,@ModelAttribute Order order,HttpServletResponse response) throws IOException{
	String id = request.getParameter("serviceid");
		Serviceprice serviceprice =servicepriceService.queryById(id); 
		order.setCreatetime(new Date());
		order.setCreator(((LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER")).getUserId());
		order.setExpiretime(DateUtils.getDate(((new Date().getTime())+1000*3600*2)));
		order.setIsdelete(0);
		order.setPaycodeurl("");
		order.setPayresult("");
		order.setPaystatus(0);
		order.setPaytime(new Date());
		order.setPayuserid("");
		order.setServiceid(Integer.parseInt(serviceprice.getId().toString()));
		order.setPrice(serviceprice.getPrice());  
		orderService.doAdd(order);
		response.sendRedirect("toDetail.html?id="+order.getId());
}

/**
 * 跳转到编辑页面
 * @return
 */
@RequestMapping(value="toEdit",method = RequestMethod.GET)
public void toEdit(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		 VelocityContext velocityContext = new VelocityContext();
		 Order order = orderService.queryById(id);
		 velocityContext.put("order",order);
		 String viewName = "order/back/order-edit.vm";
		 ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 编辑
 * @return
 */
@RequestMapping(value = "/doEdit",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson doEdit(@ModelAttribute Order order,HttpServletRequest request){
	AjaxJson j = new AjaxJson();
	try {
		boolean isadmin =false;
		List<String> rolelist = (List<String>) request.getSession().getAttribute("roleIds");
		for(String role :rolelist){
			if(role.equals("00")){
				isadmin=true;
			}
		}
		if(!isadmin){
			j.setSuccess(false);
			j.setMsg("您无权编辑");
			return j;
		}
		orderService.doEdit(order);
		
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
			orderService.doDelete(id);
			j.setMsg("删除成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("删除失败");
		}
		return j;
}
/**
 * 列表页面
 * @return
 */
@RequestMapping(value="adminlist",method = {RequestMethod.GET,RequestMethod.POST})
public void adminlist(@ModelAttribute Order query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Order> pageQuery = new PageQuery<Order>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	
	 	String userid = ((LoginUser)request.getSession().getAttribute("OPERATE_WEB_LOGIN_USER")).getUserId();
	 	query.setCreator(userid);
	    if(BaihuaUtil.isadmin((List<String>) request.getSession().getAttribute("roleIds"))){
		 	query.setCreator(request.getParameter("openid"));
	    }

	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("order",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(orderService.queryPageList(pageQuery)));
		String viewName = "order/back/order-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

}

