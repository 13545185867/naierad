package com.jeecg.p3.activity.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Activity:会员活动<br>
 * @author chao.hua
 * @since：2017年09月11日 11时58分40秒 星期一 
 * @version:1.0
 */
public class Activity implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *活动标题	 */	private String title;	/**	 *活动地址	 */	private String addr;	/**	 *限定人数	 */	private Integer totalpepole;
	
	private Integer viewcount;	/**	 *剩余名额	 */	private Integer remainpepole;	/**	 *是否已删除	 */	private String isdelete;	/**	 *报名截止时间	 */	private Date endtime;
	
	private Date activitytime;	/**	 *活动内容	 */	private String content;	/**	 *是否VIP才能查看	 */	private String isvipview;	/**	 *是否VIP才能参加	 */	private String isvipparty;	/**	 *	 */	private Date createtime;	/**	 *	 */	private String creatuser;	/**	 *活动要求	 */	private String ruletip;	/**	 *所属区域ID	 */	private Integer areaid;	/**	 *主题图片	 */	private String leadpic;	/**	 *主题图显示方式（0为小图，1为大图）	 */	private String leadpicviewtype;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getTitle() {	    return this.title;	}	public void setTitle(String title) {	    this.title=title;	}	public String getAddr() {	    return this.addr;	}	public void setAddr(String addr) {	    this.addr=addr;	}	public Integer getTotalpepole() {	    return this.totalpepole;	}	public void setTotalpepole(Integer totalpepole) {	    this.totalpepole=totalpepole;	}	public Integer getRemainpepole() {	    return this.remainpepole;	}	public void setRemainpepole(Integer remainpepole) {	    this.remainpepole=remainpepole;	}	public String getIsdelete() {	    return this.isdelete;	}	public void setIsdelete(String isdelete) {	    this.isdelete=isdelete;	}	public Date getEndtime() {	    return this.endtime;	}	public void setEndtime(Date endtime) {	    this.endtime=endtime;	}	public String getContent() {	    return this.content;	}	public void setContent(String content) {	    this.content=content;	}	public String getIsvipview() {	    return this.isvipview;	}	public void setIsvipview(String isvipview) {	    this.isvipview=isvipview;	}	public String getIsvipparty() {	    return this.isvipparty;	}	public void setIsvipparty(String isvipparty) {	    this.isvipparty=isvipparty;	}	public Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(Date createtime) {	    this.createtime=createtime;	}	public String getCreatuser() {	    return this.creatuser;	}	public void setCreatuser(String creatuser) {	    this.creatuser=creatuser;	}	public String getRuletip() {	    return this.ruletip;	}	public void setRuletip(String ruletip) {	    this.ruletip=ruletip;	}	public Integer getAreaid() {	    return this.areaid;	}	public void setAreaid(Integer areaid) {	    this.areaid=areaid;	}	public String getLeadpic() {	    return this.leadpic;	}	public void setLeadpic(String leadpic) {	    this.leadpic=leadpic;	}	public String getLeadpicviewtype() {	    return this.leadpicviewtype;	}	public void setLeadpicviewtype(String leadpicviewtype) {	    this.leadpicviewtype=leadpicviewtype;	}
	public Date getActivitytime() {
		return activitytime;
	}
	public void setActivitytime(Date activitytime) {
		this.activitytime = activitytime;
	}
	public Integer getViewcount() {
		return viewcount;
	}
	public void setViewcount(Integer viewcount) {
		this.viewcount = viewcount;
	}
}

