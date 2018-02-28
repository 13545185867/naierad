package com.jeecg.p3.dictinfo.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Dictinfo:数据字典信息表<br>
 * @author chao.hua
 * @since：2017年07月25日 17时04分33秒 星期二 
 * @version:1.0
 */
public class Dictinfo implements Entity<Long> {
	private static final long serialVersionUID = 1L;
		/**	 *字典ID	 */	private Long id;	/**	 *字典类型ID	 */	private Integer typeid;	/**	 *字典代码	 */	private String dictcode;	/**	 *字典名称	 */	private String dictname;	/**	 *	 */	private String dictinfocol0;	/**	 *	 */	private String dictinfocol1;	/**	 *	 */	private String dictinfocol2;	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public Integer getTypeid() {	    return this.typeid;	}	public void setTypeid(Integer typeid) {	    this.typeid=typeid;	}	public String getDictcode() {	    return this.dictcode;	}	public void setDictcode(String dictcode) {	    this.dictcode=dictcode;	}	public String getDictname() {	    return this.dictname;	}	public void setDictname(String dictname) {	    this.dictname=dictname;	}	public String getDictinfocol0() {	    return this.dictinfocol0;	}	public void setDictinfocol0(String dictinfocol0) {	    this.dictinfocol0=dictinfocol0;	}	public String getDictinfocol1() {	    return this.dictinfocol1;	}	public void setDictinfocol1(String dictinfocol1) {	    this.dictinfocol1=dictinfocol1;	}	public String getDictinfocol2() {	    return this.dictinfocol2;	}	public void setDictinfocol2(String dictinfocol2) {	    this.dictinfocol2=dictinfocol2;	}
}

