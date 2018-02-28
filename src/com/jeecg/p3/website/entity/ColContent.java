package com.jeecg.p3.website.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>ColContent:栏目内容表<br>
 * @author chao.hua
 * @since：2017年11月04日 13时51分37秒 星期六 
 * @version:1.0
 */
public class ColContent implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *内容ID	 */	private Integer id;	/**	 *	 */	private Integer colId;	/**	 *标题	 */	private String title;	/**	 *内容	 */	private String content;	/**	 *描述	 */	private String descipt;	/**	 *创建时间	 */	private Date createdate;	/**	 *0 不可用 ,1 为可用	 */	private Integer status;	/**	 *来源	 */	private String source;	/**	 *	 */	private String creatuser;	/**	 *主题缩略图	 */	private String leadpic;	/**	 *	 */	private String colCon1;	/**	 *	 */	private String colCon2;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getColId() {	    return this.colId;	}	public void setColId(Integer colId) {	    this.colId=colId;	}	public String getTitle() {	    return this.title;	}	public void setTitle(String title) {	    this.title=title;	}	public String getContent() {	    return this.content;	}	public void setContent(String content) {	    this.content=content;	}	public String getDescipt() {	    return this.descipt;	}	public void setDescipt(String descipt) {	    this.descipt=descipt;	}	public Date getCreatedate() {	    return this.createdate;	}	public void setCreatedate(Date createdate) {	    this.createdate=createdate;	}	public Integer getStatus() {	    return this.status;	}	public void setStatus(Integer status) {	    this.status=status;	}	public String getSource() {	    return this.source;	}	public void setSource(String source) {	    this.source=source;	}	public String getCreatuser() {	    return this.creatuser;	}	public void setCreatuser(String creatuser) {	    this.creatuser=creatuser;	}	public String getLeadpic() {	    return this.leadpic;	}	public void setLeadpic(String leadpic) {	    this.leadpic=leadpic;	}	public String getColCon1() {	    return this.colCon1;	}	public void setColCon1(String colCon1) {	    this.colCon1=colCon1;	}	public String getColCon2() {	    return this.colCon2;	}	public void setColCon2(String colCon2) {	    this.colCon2=colCon2;	}
}

