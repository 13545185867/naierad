package com.jeecg.p3.website.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>ColPage:页面栏目表<br>
 * @author chao.hua
 * @since：2017年11月04日 14时47分40秒 星期六 
 * @version:1.0
 */
public class ColPage implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *ID	 */	private Integer id;	/**	 *栏目名称	 */	private String name;	/**	 *栏目是否可用  0为不可用, 1为可用	 */	private String status;	/**	 *类型:1标签 2 页面	 */	private Integer type;	/**	 *排序	 */	private Integer sortnum;	/**	 *父ID	 */	private String parentid;	/**	 *页面连接地址	 */	private String url;	/**	 *	 */	private String descript;	/**	 *	 */	private String column1;	/**	 *	 */	private String column2;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getName() {	    return this.name;	}	public void setName(String name) {	    this.name=name;	}	public String getStatus() {	    return this.status;	}	public void setStatus(String status) {	    this.status=status;	}	public Integer getType() {	    return this.type;	}	public void setType(Integer type) {	    this.type=type;	}	public Integer getSortnum() {	    return this.sortnum;	}	public void setSortnum(Integer sortnum) {	    this.sortnum=sortnum;	}	public String getParentid() {	    return this.parentid;	}	public void setParentid(String parentid) {	    this.parentid=parentid;	}	public String getUrl() {	    return this.url;	}	public void setUrl(String url) {	    this.url=url;	}	public String getDescript() {	    return this.descript;	}	public void setDescript(String descript) {	    this.descript=descript;	}	public String getColumn1() {	    return this.column1;	}	public void setColumn1(String column1) {	    this.column1=column1;	}	public String getColumn2() {	    return this.column2;	}	public void setColumn2(String column2) {	    this.column2=column2;	}
}

