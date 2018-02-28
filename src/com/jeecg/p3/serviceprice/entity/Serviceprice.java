package com.jeecg.p3.serviceprice.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Serviceprice:服务报价表<br>
 * @author chao.hua
 * @since：2017年07月25日 17时07分59秒 星期二 
 * @version:1.0
 */
public class Serviceprice implements Entity<Long> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Long id;	/**	 *模块ID	 */	private String modelid;	/**	 *购买类型	 */	private String servicetype;	/**	 *价格	 */	private BigDecimal price;	/**	 *	 */	private String servicepricecol0;	/**	 *	 */	private String servicepricecol1;	/**	 *	 */	private String servicepricecol2;	public Long getId() {	    return this.id;	}	public void setId(Long id) {	    this.id=id;	}	public String getModelid() {	    return this.modelid;	}	public void setModelid(String modelid) {	    this.modelid=modelid;	}	public String getServicetype() {	    return this.servicetype;	}	public void setServicetype(String servicetype) {	    this.servicetype=servicetype;	}	public BigDecimal getPrice() {	    return this.price;	}	public void setPrice(BigDecimal price) {	    this.price=price;	}	public String getServicepricecol0() {	    return this.servicepricecol0;	}	public void setServicepricecol0(String servicepricecol0) {	    this.servicepricecol0=servicepricecol0;	}	public String getServicepricecol1() {	    return this.servicepricecol1;	}	public void setServicepricecol1(String servicepricecol1) {	    this.servicepricecol1=servicepricecol1;	}	public String getServicepricecol2() {	    return this.servicepricecol2;	}	public void setServicepricecol2(String servicepricecol2) {	    this.servicepricecol2=servicepricecol2;	}
}

