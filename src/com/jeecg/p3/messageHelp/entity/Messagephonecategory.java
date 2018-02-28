package com.jeecg.p3.messageHelp.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Messagephonecategory:<br>
 * @author chao.hua
 * @since：2017年08月16日 13时08分02秒 星期三 
 * @version:1.0
 */
public class Messagephonecategory implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Integer id;	/**	 *	 */	private String categoryname;	/**	 *0,1代表已删除)	 */	private Integer isdelete;	/**	 *用户ID	 */	private String creatuser;	/**	 *	 */	private String categorycol;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getCategoryname() {	    return this.categoryname;	}	public void setCategoryname(String categoryname) {	    this.categoryname=categoryname;	}	public Integer getIsdelete() {	    return this.isdelete;	}	public void setIsdelete(Integer isdelete) {	    this.isdelete=isdelete;	}	public String getCreatuser() {	    return this.creatuser;	}	public void setCreatuser(String creatuser) {	    this.creatuser=creatuser;	}	public String getCategorycol() {	    return this.categorycol;	}	public void setCategorycol(String categorycol) {	    this.categorycol=categorycol;	}
}

