package com.jeecg.p3.dicttype.entity;

import java.util.Date;
import java.math.BigDecimal;

import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Dicttype:数据字典类型表<br>
 * @author chao.hua
 * @since：2017年07月25日 16时50分49秒 星期二 
 * @version:1.0
 */
public class Dicttype implements Entity<Long> {
	private static final long serialVersionUID = 1L;
		/**	 *类型ID	 */	private Long id;	/**	 *类型名称	 */	private String typename;	/**	 *	 */	private String dicttypecol0;	/**	 *	 */	private String dicttypecol1;	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public String getTypename() {	    return this.typename;	}	public void setTypename(String typename) {	    this.typename=typename;	}	public String getDicttypecol0() {	    return this.dicttypecol0;	}	public void setDicttypecol0(String dicttypecol0) {	    this.dicttypecol0=dicttypecol0;	}	public String getDicttypecol1() {	    return this.dicttypecol1;	}	public void setDicttypecol1(String dicttypecol1) {	    this.dicttypecol1=dicttypecol1;	}
}

