package com.jeecg.p3.userservice.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Userservice:用户服务详细表<br>
 * @author chao.hua
 * @since：2017年07月25日 17时09分14秒 星期二 
 * @version:1.0
 */
public class Userservice implements Entity<Long> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Long id;	/**	 *用户ID	 */	private String userid;	/**	 *服务ID	 */	private Integer serviceid;	/**	 *服务开始时间	 */	private Date starttime;	/**	 *服务截止时间	 */	private Date endtime;	/**	 *可创建活动次数	 */	private Integer createcount;	/**	 *	 */	private String userservicecol0;	/**	 *	 */	private String userservicecol1;	/**	 *	 */	private String userservicecol2;	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public String getUserid() {	    return this.userid;	}	public void setUserid(String userid) {	    this.userid=userid;	}	public Integer getServiceid() {	    return this.serviceid;	}	public void setServiceid(Integer serviceid) {	    this.serviceid=serviceid;	}	public Date getStarttime() {	    return this.starttime;	}	public void setStarttime(Date starttime) {	    this.starttime=starttime;	}	public Date getEndtime() {	    return this.endtime;	}	public void setEndtime(Date endtime) {	    this.endtime=endtime;	}	public Integer getCreatecount() {	    return this.createcount;	}	public void setCreatecount(Integer createcount) {	    this.createcount=createcount;	}	public String getUserservicecol0() {	    return this.userservicecol0;	}	public void setUserservicecol0(String userservicecol0) {	    this.userservicecol0=userservicecol0;	}	public String getUserservicecol1() {	    return this.userservicecol1;	}	public void setUserservicecol1(String userservicecol1) {	    this.userservicecol1=userservicecol1;	}	public String getUserservicecol2() {	    return this.userservicecol2;	}	public void setUserservicecol2(String userservicecol2) {	    this.userservicecol2=userservicecol2;	}
}

