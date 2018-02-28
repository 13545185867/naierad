package com.jeecg.p3.leavemessage.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Leavemessage:<br>
 * @author chao.hua
 * @since：2017年09月17日 14时30分22秒 星期日 
 * @version:1.0
 */
public class Leavemessage implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *留言内容	 */	private String messagetext;	/**	 *留言人手机号码	 */	private String mobile;	/**	 *留言人真实姓名	 */	private String name;	/**	 *留言人微信ID	 */	private String fromopenid;	/**	 *是否已向客户发送短信通知	 */	private String issend;	/**	 *留言时间	 */	private Date createtime;	/**	 *处理状态（0未处理，1已处理）	 */	private String status;	/**	 *留言接收人微信ID	 */	private String toopenid;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getMessagetext() {	    return this.messagetext;	}	public void setMessagetext(String messagetext) {	    this.messagetext=messagetext;	}	public String getMobile() {	    return this.mobile;	}	public void setMobile(String mobile) {	    this.mobile=mobile;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public String getFromopenid() {	    return this.fromopenid;	}	public void setFromopenid(String fromopenid) {	    this.fromopenid=fromopenid;	}	public String getIssend() {	    return this.issend;	}	public void setIssend(String issend) {	    this.issend=issend;	}	public Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(Date createtime) {	    this.createtime=createtime;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public String getToopenid() {	    return this.toopenid;	}	public void setToopenid(String toopenid) {	    this.toopenid=toopenid;	}
}

