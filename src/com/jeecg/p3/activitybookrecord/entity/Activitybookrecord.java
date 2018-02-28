package com.jeecg.p3.activitybookrecord.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Activitybookrecord:活动报名记录<br>
 * @author chao.hua
 * @since：2017年09月11日 11时59分43秒 星期一 
 * @version:1.0
 */
public class Activitybookrecord implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Integer id;	/**	 *	 */	private String activityid;	/**	 *	 */	private String activitytitle;	/**	 *	 */	private String deatilurl;	/**	 *报名时间	 */	private Date booktime;	/**	 *报名人数	 */	private Integer bookpersoncount;	/**	 *	 */	private String openid;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getActivityid() {	    return this.activityid;	}	public void setActivityid(String activityid) {	    this.activityid=activityid;	}	public String getActivitytitle() {	    return this.activitytitle;	}	public void setActivitytitle(String activitytitle) {	    this.activitytitle=activitytitle;	}	public String getDeatilurl() {	    return this.deatilurl;	}	public void setDeatilurl(String deatilurl) {	    this.deatilurl=deatilurl;	}	public Date getBooktime() {	    return this.booktime;	}	public void setBooktime(Date booktime) {	    this.booktime=booktime;	}	public Integer getBookpersoncount() {	    return this.bookpersoncount;	}	public void setBookpersoncount(Integer bookpersoncount) {	    this.bookpersoncount=bookpersoncount;	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}
}

