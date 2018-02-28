package com.jeecg.p3.jlb.service;

import javax.servlet.http.HttpServletRequest;

import org.jeecgframework.p3.core.utils.common.PageList;
import org.jeecgframework.p3.core.utils.common.PageQuery;

import com.jeecg.p3.activity.entity.Activity;

/**
 * 描述：</b>ActivityService<br>
 * @author：chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
public interface JlbService {
	
	   public String getSignature(final HttpServletRequest request,String noncestr,String timestamp,String jwid);
	
}

