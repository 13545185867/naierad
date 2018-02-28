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
		/**	 *	 */	private String id;	/**	 *所属区域ID	 */	private Integer areaid;
	
	private Integer viewcount;	/**	 *	 */	private String title;	/**	 *	 */	private String content;	/**	 *	 */	private String isdelete;	/**	 *	 */	private Date createtime;	/**	 *	 */	private String createuser;	/**	 *	 */	private String isvipview;	/**	 *	 */	private String isvipparty;	/**	 *主题图片	 */	private String leadpic;	/**	 *主题图显示方式	 */	private String leadpicviewtype;	/**	 *类型：1文章 2视频 3通知公告	 */	private String type;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public Integer getAreaid() {	    return this.areaid;	}	public void setAreaid(Integer areaid) {	    this.areaid=areaid;	}	public String getTitle() {	    return this.title;	}	public void setTitle(String title) {	    this.title=title;	}	public String getContent() {	    return this.content;	}	public void setContent(String content) {	    this.content=content;	}	public String getIsdelete() {	    return this.isdelete;	}	public void setIsdelete(String isdelete) {	    this.isdelete=isdelete;	}	public Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(Date createtime) {	    this.createtime=createtime;	}	public String getCreateuser() {	    return this.createuser;	}	public void setCreateuser(String createuser) {	    this.createuser=createuser;	}	public String getIsvipview() {	    return this.isvipview;	}	public void setIsvipview(String isvipview) {	    this.isvipview=isvipview;	}	public String getIsvipparty() {	    return this.isvipparty;	}	public void setIsvipparty(String isvipparty) {	    this.isvipparty=isvipparty;	}	public String getLeadpic() {	    return this.leadpic;	}	public void setLeadpic(String leadpic) {	    this.leadpic=leadpic;	}	public String getLeadpicviewtype() {	    return this.leadpicviewtype;	}	public void setLeadpicviewtype(String leadpicviewtype) {	    this.leadpicviewtype=leadpicviewtype;	}	public String getType() {	    return this.type;	}	public void setType(String type) {	    this.type=type;	}
	public Integer getViewcount() {
		return viewcount;
	}
	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}
}

