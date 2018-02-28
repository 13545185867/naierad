package com.jeecg.p3.jlb.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;
import org.jeecgframework.p3.cg.util.BaihuaUtil;
import org.jeecgframework.p3.core.common.utils.AjaxJson;
import org.jeecgframework.p3.core.util.SystemTools;
import org.jeecgframework.p3.core.util.plugin.ViewVelocity;
import org.jeecgframework.p3.core.utils.common.PageQuery;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.wxpay.sdk.WXPayConstants.SignType;
import com.jeecg.p3.activity.entity.Activity;
import com.jeecg.p3.activity.service.ActivityService;
import com.jeecg.p3.activitybookrecord.entity.Activitybookrecord;
import com.jeecg.p3.activitybookrecord.service.ActivitybookrecordService;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.jlb.entity.Jlb;
import com.jeecg.p3.jlb.service.JlbService;
import com.jeecg.p3.leavemessage.entity.Leavemessage;
import com.jeecg.p3.leavemessage.service.LeavemessageService;
import com.jeecg.p3.messageLog.entity.SendMessageLog;
import com.jeecg.p3.noticeandnews.entity.Noticeandnews;
import com.jeecg.p3.noticeandnews.service.NoticeandnewsService;
import com.jeecg.p3.order.entity.Order;
import com.jeecg.p3.order.service.OrderService;
import com.jeecg.p3.serviceprice.entity.Serviceprice;
import com.jeecg.p3.serviceprice.service.ServicepriceService;
import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userinfo.service.UserinfoService;
import com.jeecg.p3.userservice.entity.Userservice;
import com.jeecg.p3.userservice.service.UserserviceService;
import com.jeecg.p3.viewcount.entity.Viewcount;
import com.jeecg.p3.viewcount.service.ViewcountService;
import com.weixin.pay.WXPay;
import com.weixin.pay.WXPayConfig;
import com.weixin.pay.WXPayUtil;

import org.jeecgframework.p3.core.web.BaseController;

import redis.clients.jedis.Jedis;
import weixin.util.DateUtils;

 /**
 * 描述：</b>ActivityController<br>会员活动
 * @author chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
@Controller
@RequestMapping("/jlb")
public class JlbController extends BaseController{
  @Autowired
  private JlbService jlbService;
  @Value("#{sysconfig['appid']}")
  private String appid;
  
  @Value("#{sysconfig['jwid']}")
  private String jwid;
  
  @Value("#{sysconfig['redis.ip']}")
  private String redisip;
  
  @Value("#{sysconfig['shanghu.key']}")
  private String shanghukey;
  
  @Value("#{sysconfig['wxpay.notify.url']}")
  private String notifyurl;
  @Value("#{sysconfig['spbill_create_ip']}")
  private String billcreateip;
  @Value("#{sysconfig['jlbid']}")
  private String jlbid;
  
  @Autowired
  private LeavemessageService leavemessageService;
  @Autowired
  private UserinfoService userinfoService;
  
  @Autowired
  private OrderService orderService;
  
  @Autowired
  private ServicepriceService servicepriceService;
  
  @Autowired
  private UserserviceService userserviceService;
  
  @Autowired
  private NoticeandnewsService noticeandnewsService;
  
  @Autowired
  private ActivityService activityService;
  @Autowired
  private ActivitybookrecordService activitybookrecordService;
  
  @Autowired
  private ViewcountService viewcountService;
  
  @Autowired
  private DictinfoService dictinfoService;
  
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="index",method = {RequestMethod.GET,RequestMethod.POST})
public void index(HttpServletResponse response,HttpServletRequest request) throws Exception{
    String noncestr = UUID.randomUUID().toString().replace("-", "").substring(0, 16);//随机字符串  
    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
	 String signature = jlbService.getSignature(request, noncestr, timestamp,jwid);
	 	VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("appid",appid);
		velocityContext.put("noncestr",noncestr);
		velocityContext.put("signature",signature);
		velocityContext.put("timestamp",timestamp);
		String viewName = "jlb/index.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="tomember",method = {RequestMethod.GET,RequestMethod.POST})
public void tomember(HttpServletResponse response,HttpServletRequest request) throws Exception{
    Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
    if(user !=null && user.getMobile()==null){
    	response.sendRedirect("toreg.html");
    }
	 	VelocityContext velocityContext = new VelocityContext();
	 	
	 	PageQuery<Noticeandnews> pageQuery = new PageQuery<Noticeandnews>();
	 	pageQuery.setPageNo(0);
	 	pageQuery.setPageSize(5);
	 	Noticeandnews n = new Noticeandnews();
	 	pageQuery.setQuery(n);
		velocityContext.put("actnewInfos",noticeandnewsService.queryActAndNEWList(pageQuery).getValues());
		
		
		List<Jlb> jlblist = new ArrayList<Jlb>();
		Dictinfo dic = new Dictinfo();
		dic.setTypeid(Integer.parseInt("5"));

		List<Dictinfo> dictlist = dictinfoService.queryList(dic);
		
		for(Dictinfo dict:dictlist){
			PageQuery<Noticeandnews> pageQuery0 = new PageQuery<Noticeandnews>();
		 	pageQuery0.setPageNo(0);
		 	pageQuery0.setPageSize(5);
		 	Noticeandnews n0 = new Noticeandnews();
		 	n0.setAreaid(Integer.parseInt(dict.getDictcode()));
		 	pageQuery0.setQuery(n0);
		 	Jlb jlb = new Jlb();
		 	jlb.setDictcode(dict.getDictcode());
		 	jlb.setDictname(dict.getDictname());
		 	jlb.setActNews(noticeandnewsService.queryActAndNEWList(pageQuery0).getValues());
		 	jlblist.add(jlb);
		}
		velocityContext.put("dictlist",dictlist);
		velocityContext.put("actnewInfosByArea",jlblist);
		String viewName = "jlb/member.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="doreg",method = {RequestMethod.GET,RequestMethod.POST})
@ResponseBody
public AjaxJson doreg(HttpServletResponse response,HttpServletRequest request) throws Exception{
	AjaxJson j = new AjaxJson();
    Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
    if(user !=null && user.getMobile()!=null&& !user.getMobile().equals("")){
    	response.sendRedirect("tomember.html");
    }
	String realname = request.getParameter("username");
	String mobile = request.getParameter("mobile");
	String verify = request.getParameter("verify");
	if(realname !=null && mobile != null && verify!=null && !realname.equals("") && !mobile.equals("") && !verify.equals("")){
		String messageuuid = (String) request.getSession().getAttribute("messageuuid");
		Jedis resource = 	 new Jedis(redisip,6379);
		 String uuid =  resource.get(messageuuid);
		 resource.close();
		 if(uuid !=null && uuid.equals(verify)){
			 user.setMobile(mobile);
			 user.setRealname(realname);
			 userinfoService.updateByopenid(user);
				j.setSuccess(true);
				j.setMsg("恭喜您，注册成功");
				request.getSession().setAttribute("OPERATE_ACTIVITY_USER",user);
				return j ;
		 }
	}

	j.setSuccess(false);
	j.setMsg("很抱歉，注册失败");
	return j ;
}


@RequestMapping(value="toreg",method = {RequestMethod.GET,RequestMethod.POST})
public void toreg(HttpServletResponse response,HttpServletRequest request) throws Exception{

    Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
    if(user !=null && user.getMobile()!=null && !user.getMobile().equals("")){
    	response.sendRedirect("tomember.html");
    }
    
	 	VelocityContext velocityContext = new VelocityContext();
		String messageuuid = UUID.randomUUID().toString();
		request.getSession().setAttribute("messageuuid", messageuuid);
		velocityContext.put("messageuuid", messageuuid);
		String viewName = "jlb/reg.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="updateinfo",method = {RequestMethod.GET,RequestMethod.POST})
public void updateinfo(HttpServletResponse response,HttpServletRequest request) throws Exception{
	 	VelocityContext velocityContext = new VelocityContext();
		String viewName = "jlb/updateinfo.vm";
		Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
		user = userinfoService.queryById(user.getId());
		velocityContext.put("user", user);
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="doupdateInfo",method = {RequestMethod.GET,RequestMethod.POST})
@ResponseBody
public AjaxJson doupdateInfo(HttpServletResponse response,HttpServletRequest request) throws Exception{
	
	String addr = request.getParameter("addr");
	String profession = request.getParameter("profession");
	String sex = request.getParameter("sex");
	Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
	Userinfo usernew  = new Userinfo();
	usernew.setAddr(addr);
	usernew.setProfession(profession);
	usernew.setSex(sex);
	usernew.setId(user.getId());
	user.setAddr(addr);
	user.setProfession(profession);
	user.setSex(sex);
	request.getSession().setAttribute("OPERATE_ACTIVITY_USER",user);
	userinfoService.doEdit(usernew);
	AjaxJson j = new AjaxJson();
	j.setSuccess(true);
	j.setMsg("修改成功");
	return j ;
}

@RequestMapping(value="tovip",method = {RequestMethod.GET,RequestMethod.POST})
public void tovip(HttpServletResponse response,HttpServletRequest request) throws Exception{

    Userinfo user = (Userinfo) request.getSession().getAttribute("OPERATE_ACTIVITY_USER");

    Userservice userservice = new Userservice();
    userservice.setUserid(user.getOpenid());
    userservice.setUserservicecol0(jlbid);
    boolean vipuser  = false;
    List<Userservice> uslist = userserviceService.queryByRecord(userservice);
    if(uslist.size()>0){
        userservice = uslist.get(0);
        vipuser = true;
    }
    request.getSession().setAttribute("vipuser",vipuser);
	 	VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("starttime", userservice.getStarttime());
		velocityContext.put("endttime", userservice.getEndtime());
		StringBuffer sb = new StringBuffer();
		for(int i=8; i>user.getCode().toString().length();i--){
			sb.append("0");
		}
		request.getSession().setAttribute("mobile", user.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
		request.getSession().setAttribute("code",sb+user.getCode().toString());
		String viewName = "jlb/vip.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value = "/checkMobile",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson checkMobile(HttpServletRequest request,@ModelAttribute SendMessageLog sendMessageLog) {
	AjaxJson j = new AjaxJson();
	String mobile = request.getParameter("mobile");
	PageQuery<Userinfo> pagequery = new PageQuery<Userinfo>();
	Userinfo user = new Userinfo();
	user.setMobile(mobile);
	pagequery.setQuery(user);
	
	int count = userinfoService.count(pagequery);
	if(count >0){
		j.setSuccess(false);
		j.setMsg("该用户已被注册");
	}
	j.setSuccess(true);
	j.setMsg("该用户可以注册");
	return j ;
	
}

@RequestMapping(value="toDetail",method = RequestMethod.GET)
public void orderDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "order/back/order-yf.vm";
		Order order = orderService.queryById(id);
		Serviceprice serviceprice =servicepriceService.queryById(order.getServiceid().toString());
		WXPayConfig wxpc= new WXPayConfig();
		WXPay wxp = new WXPay(wxpc, notifyurl, false, true);
		// 获取发起电脑 ip  
        String spbill_create_ip = billcreateip;  
        // 回调接口   
        String trade_type = "JSAPI";  
        String nonce_str = WXPayUtil.generateUUID();
        Map<String,String> packageParams = new TreeMap<String,String>(); 
        packageParams.put("body", serviceprice.getServicepricecol0());  
        packageParams.put("openid", ((Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER")).getOpenid());  
        packageParams.put("out_trade_no", order.getId().toString());  
        packageParams.put("total_fee",  (order.getPrice().multiply(new BigDecimal(100))).toString().split("\\.")[0]);  
        packageParams.put("spbill_create_ip", spbill_create_ip);  
        packageParams.put("trade_type", trade_type);
        packageParams.put("nonce_str", nonce_str); 
        //Map<String,String> requestMap =wxp.fillRequestData(packageParams);
        Map<String,String> responseMap = wxp.unifiedOrder(packageParams); 
        if(responseMap.get("result_code").equals("SUCCESS")){
        	order.setPaycodeurl(responseMap.get("code_url"));
    		velocityContext.put("prepay_id",responseMap.get("prepay_id"));
        }
		order.setExpiretime(DateUtils.getDate(((new Date().getTime())+1000*3600*2)));
		orderService.doEdit(order);
	    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);//时间戳
        Map<String, String> payMap = new HashMap<String, String>();  
        payMap.put("appId", appid);  
        payMap.put("timeStamp", timestamp);  
        payMap.put("nonceStr", nonce_str);  
        payMap.put("signType", "MD5");  
        payMap.put("package", "prepay_id=" + responseMap.get("prepay_id")); 
        String paySign = WXPayUtil.generateSignature(payMap, shanghukey,SignType.MD5 ) ;
		velocityContext.put("prepay_id",responseMap.get("prepay_id"));
		velocityContext.put("appId",appid);
		velocityContext.put("price",order.getPrice());
		
		velocityContext.put("timeStamp",timestamp);
		velocityContext.put("paySign",paySign);
		velocityContext.put("nonceStr",nonce_str);
		velocityContext.put("body", serviceprice.getServicepricecol0());
		velocityContext.put("signType", "MD5");
		velocityContext.put("out_trade_no", order.getId().toString());
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
		order.setCreator(((Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER")).getOpenid());
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

@RequestMapping(value="contact",method = {RequestMethod.GET,RequestMethod.POST})
public void contact(HttpServletResponse response,HttpServletRequest request) throws Exception{
	 	VelocityContext velocityContext = new VelocityContext();
	 	PageQuery<Userinfo> pageQuery = new PageQuery<Userinfo>();
	 	Userinfo user  = new Userinfo();
	 	user.setStatus("1");
	 	user.setIskf("1");
	 	pageQuery.setQuery(user);
	 	List<Userinfo> userlist =  userinfoService.queryList(pageQuery);
	 	velocityContext.put("userlist", userlist);
		String viewName = "jlb/contact.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}
/**
 * 列表页面
 * @return
 */
@RequestMapping(value="orderlist",method = {RequestMethod.GET,RequestMethod.POST})
public void orderlist(@ModelAttribute Order query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Order> pageQuery = new PageQuery<Order>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	String userid = ((Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER")).getOpenid();
	 	query.setCreator(userid);
	 	VelocityContext velocityContext = new VelocityContext();
		pageQuery.setQuery(query);
		velocityContext.put("order",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(orderService.queryPageList(pageQuery)));
		String viewName = "jlb/order-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

/**
 * 列表页面
 * @return
 */
@RequestMapping(value="messagelist",method = {RequestMethod.GET,RequestMethod.POST})
public void list(@ModelAttribute Leavemessage query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Leavemessage> pageQuery = new PageQuery<Leavemessage>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
	 	String userid = ((Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER")).getOpenid();
	 	query.setToopenid(userid);
		pageQuery.setQuery(query);
		velocityContext.put("leavemessage",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(leavemessageService.queryPageList(pageQuery)));
		String viewName = "jlb/leavemessage-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="hotlist",method = {RequestMethod.GET,RequestMethod.POST})
public void hotlist(@ModelAttribute Noticeandnews query,HttpServletResponse response,HttpServletRequest request,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	VelocityContext velocityContext = new VelocityContext();
	 	PageQuery<Noticeandnews> pageQuery = new PageQuery<Noticeandnews>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	Noticeandnews n = new Noticeandnews();
	 	n.setIsdelete("0");
	 	pageQuery.setQuery(n);
		velocityContext.put("actnewInfos",SystemTools.convertPaginatedList(noticeandnewsService.queryActAndNEWList(pageQuery)));
	 	
		String viewName = "jlb/hot-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="areaMoreList",method = {RequestMethod.GET,RequestMethod.POST})
public void areaMoreList(@RequestParam(required = true, value = "type" ) String type,HttpServletResponse response,HttpServletRequest request,
		@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
		@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
 	VelocityContext velocityContext = new VelocityContext();
	Integer typetemp = Integer.parseInt(type);
	String areaid = request.getParameter("areaid");
	if(typetemp == 0){
		PageQuery<Activity> query = new PageQuery<Activity>();
		Activity act = new Activity();
		act.setIsdelete("0");
		query.setPageNo(pageNo);
		query.setPageSize(pageSize);
		if(areaid != null && !"".equals(areaid)){
			act.setAreaid(Integer.parseInt(areaid));
		}
		query.setQuery(act);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(activityService.queryPageList(query)));
	}else{

	 	PageQuery<Noticeandnews> pageQuery = new PageQuery<Noticeandnews>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	Noticeandnews n = new Noticeandnews();
	 	n.setIsdelete("0");
	 	n.setType(type);
		if(areaid != null && !"".equals(areaid)){
			n.setAreaid(Integer.parseInt(areaid));
		}
	 	pageQuery.setQuery(n);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(noticeandnewsService.queryPageList(pageQuery)));
	}
	velocityContext.put("type",type);
	velocityContext.put("areaid",areaid);
		String viewName = "jlb/areamore-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="activitydetail",method = {RequestMethod.GET,RequestMethod.POST})
public void activitydetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
	VelocityContext velocityContext = new VelocityContext();
	String viewName = "jlb/activity_detail.vm";
	String openid = ((Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER")).getOpenid();
	boolean vipuser =     (Boolean) request.getSession().getAttribute("vipuser");
	Activity activity = activityService.queryById(id);
	if(activity == null ||(!vipuser &&  activity.getIsvipview().equals("1"))){
		viewName =  "jlb/noauth.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
	}
	if(activity.getViewcount() == null){
		activity.setViewcount(1);
	}else{
		activity.setViewcount(activity.getViewcount()+1);
	}
	velocityContext.put("info",activity);
	if(new Date().getTime() >activity.getActivitytime().getTime() || new Date().getTime() > activity.getEndtime().getTime()){
		velocityContext.put("expire","1");
	}else{
		velocityContext.put("expire","0");	
	}

	Activitybookrecord ab = new Activitybookrecord();
	ab.setActivityid(id);
	ab.setOpenid(openid);
	if(activitybookrecordService.queryListCount(ab)>0){
		velocityContext.put("booked","1");
	}else{
		velocityContext.put("booked","0");	
	}
	ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value="tonoticeDetail",method = RequestMethod.GET)
public void noticeandnewsDetail(@RequestParam(required = true, value = "id" ) String id,HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "jlb/noticenew_detail.vm";
		boolean vipuser =     (Boolean) request.getSession().getAttribute("vipuser");

		Noticeandnews noticeandnews = noticeandnewsService.queryById(id);
		if(!vipuser &&  noticeandnews.getIsvipview().equals("1")){
			viewName =  "jlb/noauth.vm";
			ViewVelocity.view(request,response,viewName,velocityContext);
		}
		if(noticeandnews.getViewcount() == null){
			noticeandnews.setViewcount(1);
		}else{
			noticeandnews.setViewcount(noticeandnews.getViewcount()+1);
		}
		velocityContext.put("noticeandnews",noticeandnews);
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="logout",method = RequestMethod.GET)
public void logout(HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "jlb/logout.vm";
		  request.getSession().removeAttribute("OPERATE_ACTIVITY_USER");   
		  request.getSession().removeAttribute("vipuser");  
		  request.getSession().invalidate();
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value="book",method = RequestMethod.GET)
public void book(HttpServletResponse response,HttpServletRequest request)throws Exception{
		VelocityContext velocityContext = new VelocityContext();
		String viewName = "jlb/activity_book.vm";
		String activityid = request.getParameter("id");
		velocityContext.put("activityid",activityid);
		ViewVelocity.view(request,response,viewName,velocityContext);
}


@RequestMapping(value = "/dobook",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson dobook(HttpServletRequest request,@ModelAttribute SendMessageLog sendMessageLog) {
	AjaxJson j = new AjaxJson();
	String activityid = request.getParameter("activityid");
	String familynum = request.getParameter("familynum");
	String remark = request.getParameter("remark");
	Userinfo user = (Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
	if(user.getMobile() != null && activityid != null && familynum != null ){
		Activity act = activityService.queryById(activityid);
		
		Activitybookrecord ab = new Activitybookrecord();
		ab.setActivityid(activityid);
		ab.setOpenid(user.getOpenid());
		if(activitybookrecordService.queryListCount(ab)>0){
			j.setSuccess(false);
			j.setMsg("报名失败");
			return j ;
		}
		if(act.getRemainpepole()>0  && act.getEndtime().getTime()>new Date().getTime() && act.getActivitytime().getTime()> new Date().getTime()){
			Activitybookrecord abr = new Activitybookrecord();
			abr.setActivityid(activityid);
			abr.setActivitytitle(act.getTitle());
			abr.setBookpersoncount(Integer.parseInt(familynum));
			abr.setBooktime(new Date());
			abr.setDeatilurl("jlb/activitydetail.html?id="+act.getId());
			abr.setOpenid(user.getOpenid());
			activitybookrecordService.doAdd(abr);
			act.setRemainpepole(act.getRemainpepole()-Integer.parseInt(familynum));
			activityService.doEdit(act);
			j.setSuccess(true);
			j.setMsg("报名成功");
			return j ;
		}
	}
		j.setSuccess(false);
		j.setMsg("报名失败");
	return j ;
}
@RequestMapping(value = "/cancelbook",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson cancelbook(HttpServletRequest request) {
	AjaxJson j = new AjaxJson();
	String activityid = request.getParameter("activityid");
	Userinfo user = (Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER");
	if(user.getMobile() != null && activityid != null ){
		Activity act = activityService.queryById(activityid);
		
		Activitybookrecord ab = new Activitybookrecord();
		ab.setActivityid(activityid);
		ab.setOpenid(user.getOpenid());
		if(activitybookrecordService.queryListCount(ab)<=0){
			j.setSuccess(false);
			j.setMsg("取消失败");
			return j ;
		}
		if(act.getEndtime().getTime()>new Date().getTime() && act.getActivitytime().getTime()> new Date().getTime()){
			Activitybookrecord abr = new Activitybookrecord();
			abr.setActivityid(activityid);
			abr.setOpenid(user.getOpenid());
			abr = activitybookrecordService.queryList(abr).get(0);
			activitybookrecordService.doDelete(abr.getId().toString());
			act.setRemainpepole(act.getRemainpepole()+abr.getBookpersoncount());
			activityService.doEdit(act);
			j.setSuccess(true);
			j.setMsg("取消报名成功");
			return j ;
		}
	}
		j.setSuccess(false);
		j.setMsg("取消失败");
	return j ;
}

@RequestMapping(value="bookedlist",method = {RequestMethod.GET,RequestMethod.POST})
public void bookedlist(@ModelAttribute Activitybookrecord query,HttpServletResponse response,HttpServletRequest request,
			@RequestParam(required = false, value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize) throws Exception{
	 	PageQuery<Activitybookrecord> pageQuery = new PageQuery<Activitybookrecord>();
	 	pageQuery.setPageNo(pageNo);
	 	pageQuery.setPageSize(pageSize);
	 	VelocityContext velocityContext = new VelocityContext();
	 	String userid = ((Userinfo)request.getSession().getAttribute("OPERATE_ACTIVITY_USER")).getOpenid();
	 	query.setOpenid(userid);
		pageQuery.setQuery(query);
		velocityContext.put("bookedlist",query);
		velocityContext.put("pageInfos",SystemTools.convertPaginatedList(activitybookrecordService.queryPageList(pageQuery)));
		String viewName = "jlb/booked-list.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

@RequestMapping(value = "/addViewCount",method ={RequestMethod.GET, RequestMethod.POST})
@ResponseBody
public AjaxJson addViewCount(HttpServletRequest request) {
	Viewcount viewcount = new Viewcount();
	AjaxJson j = new AjaxJson();
	String id = request.getParameter("id");
	viewcount.setResourceid(id);

	Integer count = Integer.parseInt(request.getParameter("count"));
	if(count ==1){
		viewcount.setViewcount(1);
		viewcountService.doAdd(viewcount);
	}else{
		viewcountService.updateCount(viewcount);
	}
	j.setSuccess(true);
	j.setMsg(" ");
	return j ;
	
}

@RequestMapping(value="areaindex",method = {RequestMethod.GET,RequestMethod.POST})
public void areaindex(HttpServletResponse response,HttpServletRequest request) throws Exception{
	Integer areaid = Integer.parseInt(request.getParameter("areaid"));
 	VelocityContext velocityContext = new VelocityContext();
 	PageQuery<Activity> pageQuery0 = new PageQuery<Activity>();
 	PageQuery<Noticeandnews> pageQuery1 = new PageQuery<Noticeandnews>();
 	pageQuery0.setPageNo(0);
 	pageQuery0.setPageSize(5);
 	pageQuery1.setPageNo(0);
 	pageQuery1.setPageSize(5);
 	Activity act = new Activity();
 	act.setAreaid(areaid);
 	act.setIsdelete("0");
 	pageQuery0.setQuery(act);
	velocityContext.put("actInfos",activityService.queryList(pageQuery0));
 	
 	Noticeandnews notice = new Noticeandnews();
 	notice.setAreaid(areaid);
 	notice.setIsdelete("0");
 	notice.setType("3");
 	pageQuery1.setQuery(notice);
	velocityContext.put("noticeInfos",noticeandnewsService.queryList(pageQuery1));
 	
 	Noticeandnews vedio = new Noticeandnews();
 	vedio.setAreaid(areaid);
 	vedio.setIsdelete("0");
 	vedio.setType("2");
 	pageQuery1.setQuery(vedio);
	velocityContext.put("vedioInfos",noticeandnewsService.queryList(pageQuery1));
 	
 	Noticeandnews news = new Noticeandnews();
 	news.setAreaid(areaid);
 	news.setIsdelete("0");
 	news.setType("1");
	pageQuery1.setQuery(news);
	velocityContext.put("newsInfos",noticeandnewsService.queryList(pageQuery1));
	velocityContext.put("areaid",areaid);
		String viewName = "jlb/area_index.vm";
		ViewVelocity.view(request,response,viewName,velocityContext);
}

}

