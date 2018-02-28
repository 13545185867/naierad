package com.jeecg.p3.jlb.service.impl;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weixin.guanjia.core.util.WeixinUtil;

import com.jeecg.p3.jlb.service.JlbService;
import com.jeecg.p3.weixinaccount.entity.JwWebJwid;
import com.jeecg.p3.weixinaccount.service.JwWebJwidService;

@Service("jlbService")
public class JlbServiceImpl implements JlbService {
	  
	  @Autowired
	  private JwWebJwidService jwWebJwidServicenew;


	@Override
	public String getSignature(HttpServletRequest request,String noncestr,String timestamp,String jwid) {
		// TODO Auto-generated method stub
        JwWebJwid jw = jwWebJwidServicenew.getByJWid(jwid).get(0);
        String jsapi_ticket = jw.getJsapiticket();  
        String url= null ;
        if(request.getQueryString() !=null){
         url = (Object)request.getRequestURL() + "?" + request.getQueryString();
        }else{
        	url = request.getRequestURL().toString();
        }
        if (url.indexOf("#") != -1) {
            url = url.substring(0, url.indexOf("#"));
        }
        String str = "jsapi_ticket="+jsapi_ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url; 
        String signature =WeixinUtil.SHA1(str);  
		return signature;
	}
	
}
