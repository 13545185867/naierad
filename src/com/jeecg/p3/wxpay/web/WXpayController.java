package com.jeecg.p3.wxpay.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jeecg.p3.appmodel.entity.Appmodel;
import com.jeecg.p3.appmodel.service.AppmodelService;
import com.jeecg.p3.dictinfo.entity.Dictinfo;
import com.jeecg.p3.dictinfo.service.DictinfoService;
import com.jeecg.p3.dicttype.entity.Dicttype;
import com.jeecg.p3.order.entity.Order;
import com.jeecg.p3.order.service.OrderService;
import com.jeecg.p3.serviceprice.entity.Serviceprice;
import com.jeecg.p3.serviceprice.service.ServicepriceService;
import com.jeecg.p3.system.vo.LoginUser;
import com.jeecg.p3.userinfo.entity.Userinfo;
import com.jeecg.p3.userservice.entity.Userservice;
import com.jeecg.p3.userservice.service.UserserviceService;
import com.weixin.pay.WXPay;
import com.weixin.pay.WXPayConfig;

import org.jeecgframework.p3.core.web.BaseController;

 /**
 * 描述：</b>AppmodelController<br>应用模块表
 * @author chao.hua
 * @since：2017年07月25日 17时05分57秒 星期二 
 * @version:1.0
 */
@Controller
@RequestMapping("/wxpay")
public class WXpayController extends BaseController{

	  @Autowired
	  private OrderService orderService;
	  
	  @Autowired
	  private ServicepriceService servicepriceService;
	  
	  @Autowired
	  private DictinfoService dictinfoService;
	  
	  @Autowired
	  private UserserviceService userserviceService;
	
/**
  * 列表页面
  * @return
  */
@RequestMapping(value="notify",method = {RequestMethod.GET,RequestMethod.POST})
public void list(HttpServletResponse response,HttpServletRequest request ) throws Exception{
 
	//读取参数  
    InputStream inputStream ;  
    StringBuffer sb = new StringBuffer();  
    inputStream = request.getInputStream();  
    String s ;  
    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
    while ((s = in.readLine()) != null){  
        sb.append(s);  
    }  
	WXPayConfig wxpc= new WXPayConfig();
	WXPay wxp = new WXPay(wxpc,null,true,true);
	Map<String, String> m =  wxp.processResponseXml(sb.toString())	;
	boolean a = wxp.isPayResultNotifySignatureValid(m);
	String resXml = null ;
	Order order = new Order();
	String out_trade_no =(String)m.get("out_trade_no");
	String openid = (String)m.get("openid"); 
    String is_subscribe = (String)m.get("is_subscribe"); 
    order.setId(out_trade_no);
	if(a && m.get("result_code").equals("SUCCESS")){
   
        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"  
                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";  
        order.setPaystatus(1);
        order.setPaytime(new Date());
        order.setPayresult("支付成功");
        order.setPayuserid(openid);
	}else{
		 resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"  
                 + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
	        order.setPaystatus(2);
	        order.setPaytime(new Date());
	        order.setPayresult("支付失败");
	        order.setPayuserid(openid);
	}

	
	
	if(order.getPaystatus().equals(1)){
		Order or = orderService.queryById(out_trade_no);
		if(!or.getPaystatus().equals(1) ){
			Serviceprice serviceprice = servicepriceService.queryById(or.getServiceid()+"");
			Dictinfo dictinfo = dictinfoService.queryById(serviceprice.getServicetype());
			Userservice userservice = new Userservice();
		     Date startdate = new Date(System.currentTimeMillis());
			userservice.setStarttime(startdate);
		     userservice.setUserid(openid);
		     userservice.setServiceid(order.getServiceid());
		     
		     JsonParser parser=new JsonParser();  //创建JSON解析器
	         JsonObject object=(JsonObject) parser.parse(dictinfo.getDictcode()); 
	         JsonArray array=object.get("ids").getAsJsonArray();   
	         for(int i=0;i<array.size();i++){
	        	 JsonObject subObject=array.get(i).getAsJsonObject();
	    	     //月套餐 值 为：n,month ,n代表月个数 {ids:[{id:1,count:2,servictype:month},{id:2,count:2,servictype:count},{id:3,count:0,servictype:count}]}
	        	 if(subObject.get("servictype").getAsString().equals("year")){
	        		//根据Dictcode来计算服务 开始时间 与结束时间
	    			 Calendar calendar = Calendar.getInstance();
	    		     calendar.setTime(startdate);
	    		     calendar.add(Calendar.YEAR, subObject.get("count").getAsInt());
	    		     Date enddate = calendar.getTime();
	    		     userservice.setCreatecount(0);
	    		     userservice.setEndtime(enddate);
	        	 }
	        	 
	        	 if(subObject.get("servictype").getAsString().equals("month")){
	        		//根据Dictcode来计算服务 开始时间 与结束时间
	    			 Calendar calendar = Calendar.getInstance();
	    		     calendar.setTime(startdate);
	    		     calendar.add(Calendar.MONTH, subObject.get("count").getAsInt());
	    		     Date enddate = calendar.getTime();
	    		     userservice.setCreatecount(0);
	    		     userservice.setEndtime(enddate);
	        	 }
	        	 
	        	 if(subObject.get("servictype").getAsString().equals("count")){
	    		     userservice.setCreatecount(subObject.get("count").getAsInt());
	        	 }
	        	 //该服务是拿银子买的
	        	 userservice.setServiceid(or.getServiceid());
	        	 userservice.setUserservicecol0(serviceprice.getModelid());
	     		userserviceService.doAdd(userservice);	
	         }
		}
		

	}
	orderService.doEdit(order);
	BufferedOutputStream out = new BufferedOutputStream(  
            response.getOutputStream());  
    out.write(resXml.getBytes());  
	    in.close();  
	    inputStream.close();  
	}
	

}