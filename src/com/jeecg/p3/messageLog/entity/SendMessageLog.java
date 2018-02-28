package com.jeecg.p3.messageLog.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>SendMessageLog:<br>
 * @author chao.hua
 * @since：2017年07月23日 20时27分22秒 星期日 
 * @version:1.0
 */
public class SendMessageLog implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *	 */	private String ip;	/**	 *	 */	private String userid;	/**	 *	 */	private String mac;	/**	 *	 */	private String phonenum;	/**	 *	 */	private Date sendtime;	/**	 *	 */	private String status;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getIp() {	    return this.ip;	}	public void setIp(String ip) {	    this.ip=ip;	}	public String getUserid() {	    return this.userid;	}	public void setUserid(String userid) {	    this.userid=userid;	}	public String getMac() {	    return this.mac;	}	public void setMac(String mac) {	    this.mac=mac;	}	public String getPhonenum() {	    return this.phonenum;	}	public void setPhonenum(String phonenum) {	    this.phonenum=phonenum;	}	public Date getSendtime() {	    return this.sendtime;	}	public void setSendtime(Date sendtime) {	    this.sendtime=sendtime;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}
}

