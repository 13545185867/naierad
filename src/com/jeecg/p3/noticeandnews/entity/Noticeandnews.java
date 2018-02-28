package com.jeecg.p3.noticeandnews.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Noticeandnews:公告与资讯<br>
 * @author chao.hua
 * @since：2017年09月20日 15时38分14秒 星期三 
 * @version:1.0
 */
public class Noticeandnews implements Entity<String> {
	private static final long serialVersionUID = 1L;
	
	
	private Integer viewcount;
	public Integer getViewcount() {
		return viewcount;
	}
	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}
}
