package com.jeecg.p3.messageHelp.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Messagetemplate:<br>
 * @author chao.hua
 * @since：2017年08月18日 12时42分12秒 星期五 
 * @version:1.0
 */
public class Messagetemplate implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *短信内容ID	 */	private Integer id;	/**	 *阿里短信签名ID	 */	private String alimessagesinid;	/**	 *阿里短信模板ID	 */	private String alimessagetempid;	/**	 *创建时间	 */	private String templatename;	/**	 *是否已删除0,1代表已删除	 */	private Integer isdelete;	/**	 *0通知短信
1营销短信	 */	private Integer type;	/**	 *	 */	private String messagetemplatecol;	/**	 *	 */	private String templatecontent;	/**	 *	 */	private String attr1;	/**	 *	 */	private String attr2;	/**	 *	 */	private String attr3;	/**	 *	 */	private String attr4;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getAlimessagesinid() {	    return this.alimessagesinid;	}	public void setAlimessagesinid(String alimessagesinid) {	    this.alimessagesinid=alimessagesinid;	}	public String getAlimessagetempid() {	    return this.alimessagetempid;	}	public void setAlimessagetempid(String alimessagetempid) {	    this.alimessagetempid=alimessagetempid;	}	public String getTemplatename() {	    return this.templatename;	}	public void setTemplatename(String templatename) {	    this.templatename=templatename;	}	public Integer getIsdelete() {	    return this.isdelete;	}	public void setIsdelete(Integer isdelete) {	    this.isdelete=isdelete;	}	public Integer getType() {	    return this.type;	}	public void setType(Integer type) {	    this.type=type;	}	public String getMessagetemplatecol() {	    return this.messagetemplatecol;	}	public void setMessagetemplatecol(String messagetemplatecol) {	    this.messagetemplatecol=messagetemplatecol;	}	public String getTemplatecontent() {	    return this.templatecontent;	}	public void setTemplatecontent(String templatecontent) {	    this.templatecontent=templatecontent;	}	public String getAttr1() {	    return this.attr1;	}	public void setAttr1(String attr1) {	    this.attr1=attr1;	}	public String getAttr2() {	    return this.attr2;	}	public void setAttr2(String attr2) {	    this.attr2=attr2;	}	public String getAttr3() {	    return this.attr3;	}	public void setAttr3(String attr3) {	    this.attr3=attr3;	}	public String getAttr4() {	    return this.attr4;	}	public void setAttr4(String attr4) {	    this.attr4=attr4;	}
}

