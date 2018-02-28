package com.jeecg.p3.viewcount.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Viewcount:浏览统计<br>
 * @author chao.hua
 * @since：2017年09月11日 12时01分51秒 星期一 
 * @version:1.0
 */
public class Viewcount implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Integer id;	/**	 *浏览次数	 */	private Integer viewcount;	/**	 *资源ID	 */	private String resourceid;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public Integer getViewcount() {	    return this.viewcount;	}	public void setViewcount(Integer viewcount) {	    this.viewcount=viewcount;	}	public String getResourceid() {	    return this.resourceid;	}	public void setResourceid(String resourceid) {	    this.resourceid=resourceid;	}
}

