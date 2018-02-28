package com.jeecg.p3.appmodel.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Appmodel:应用模块表<br>
 * @author chao.hua
 * @since：2017年07月25日 17时05分57秒 星期二 
 * @version:1.0
 */
public class Appmodel implements Entity<Long> {
	private static final long serialVersionUID = 1L;
		/**	 *模块ID	 */	private Long id;	/**	 *排序	 */	private Integer apporder;	/**	 *模块标签	 */	private String tag;	/**	 *模块代码	 */	private String code;	/**	 *模块所属类型ID	 */	private Integer dictinfoid;	/**	 *活动新增地址	 */	private String newacturl;	/**	 *模块是否启用	 */	private String active;	/**	 *	 */	private String appname;	/**	 *	 */	private String appmodelcol1;	/**	 *	 */	private String appmodelcol2;	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public Integer getApporder() {	    return this.apporder;	}	public void setapporder(Integer apporder) {	    this.apporder=apporder;	}	public String getTag() {	    return this.tag;	}	public void setTag(String tag) {	    this.tag=tag;	}	public String getCode() {	    return this.code;	}	public void setCode(String code) {	    this.code=code;	}	public Integer getDictinfoid() {	    return this.dictinfoid;	}	public void setDictinfoid(Integer dictinfoid) {	    this.dictinfoid=dictinfoid;	}	public String getNewacturl() {	    return this.newacturl;	}	public void setNewacturl(String newacturl) {	    this.newacturl=newacturl;	}	public String getActive() {	    return this.active;	}	public void setActive(String active) {	    this.active=active;	}	public String getAppname() {	    return this.appname;	}	public void setAppname(String appmodelcol0) {	    this.appname=appmodelcol0;	}	public String getAppmodelcol1() {	    return this.appmodelcol1;	}	public void setAppmodelcol1(String appmodelcol1) {	    this.appmodelcol1=appmodelcol1;	}	public String getAppmodelcol2() {	    return this.appmodelcol2;	}	public void setAppmodelcol2(String appmodelcol2) {	    this.appmodelcol2=appmodelcol2;	}
}

