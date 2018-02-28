package com.jeecg.p3.messageHelp.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Phonecategory:<br>
 * @author chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
public class Phonecategory implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Integer id;	/**	 *	 */	private Integer phonebookid;	/**	 *	 */	private Integer phonecategoryid;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getPhonebookid() {	    return this.phonebookid;	}	public void setPhonebookid(Integer phonebookid) {	    this.phonebookid=phonebookid;	}	public Integer getPhonecategoryid() {	    return this.phonecategoryid;	}	public void setPhonecategoryid(Integer phonecategoryid) {	    this.phonecategoryid=phonecategoryid;	}
}

