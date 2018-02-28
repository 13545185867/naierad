package com.jeecg.p3.messageHelp.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Messagesendtask:<br>
 * @author chao.hua
 * @since：2017年08月18日 13时08分48秒 星期五 
 * @version:1.0
 */
public class Messagesendtask implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Integer id;	/**	 *	 */	private String senduser;	/**	 *发送时间	 */	private Date sendtime;	/**	 *0待发送 1发送成功  2发送失败	 */	private Integer sendtype;
	private Integer isdelete;	public Integer getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	/**	 *电话号码	 */	private String phonenum;	/**	 *短信类型：0通知短信，1营销短信	 */	private Integer messagetype;	/**	 *营销短信内容	 */	private String messagetxt;	/**	 *短信签名	 */	private String messagesign;	/**	 *	 */	private Integer messagetemplateid;	/**	 *0未审核 1审核通过  2审核未通过	 */	private Integer checkresult;	/**	 *	 */	private String attr1;	/**	 *	 */	private String attr2;	/**	 *	 */	private String attr3;	/**	 *	 */	private String attr4;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getSenduser() {	    return this.senduser;	}	public void setSenduser(String senduser) {	    this.senduser=senduser;	}	public Date getSendtime() {	    return this.sendtime;	}	public void setSendtime(Date sendtime) {	    this.sendtime=sendtime;	}	public Integer getSendtype() {	    return this.sendtype;	}	public void setSendtype(Integer sendtype) {	    this.sendtype=sendtype;	}	public String getPhonenum() {	    return this.phonenum;	}	public void setPhonenum(String phonenum) {	    this.phonenum=phonenum;	}	public Integer getMessagetype() {	    return this.messagetype;	}	public void setMessagetype(Integer messagetype) {	    this.messagetype=messagetype;	}	public String getMessagetxt() {	    return this.messagetxt;	}	public void setMessagetxt(String messagetxt) {	    this.messagetxt=messagetxt;	}	public String getMessagesign() {	    return this.messagesign;	}	public void setMessagesign(String messagesign) {	    this.messagesign=messagesign;	}	public Integer getMessagetemplateid() {	    return this.messagetemplateid;	}	public void setMessagetemplateid(Integer messagetemplateid) {	    this.messagetemplateid=messagetemplateid;	}	public Integer getCheckresult() {	    return this.checkresult;	}	public void setCheckresult(Integer checkresult) {	    this.checkresult=checkresult;	}	public String getAttr1() {	    return this.attr1;	}	public void setAttr1(String attr1) {	    this.attr1=attr1;	}	public String getAttr2() {	    return this.attr2;	}	public void setAttr2(String attr2) {	    this.attr2=attr2;	}	public String getAttr3() {	    return this.attr3;	}	public void setAttr3(String attr3) {	    this.attr3=attr3;	}	public String getAttr4() {	    return this.attr4;	}	public void setAttr4(String attr4) {	    this.attr4=attr4;	}
}

