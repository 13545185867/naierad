package com.jeecg.p3.order.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Order:订单表<br>
 * @author chao.hua
 * @since：2017年07月25日 17时07分06秒 星期二 
 * @version:1.0
 */
public class Order implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *订单ID	 */	private String id;	/**	 *服务ID	 */	private Integer serviceid;	/**	 *订单生成时间	 */	private Date createtime;	/**	 *订单过期时间	 */	private Date expiretime;	/**	 *订单价格	 */	private BigDecimal price;	/**	 *订单发起人	 */	private String creator;	/**	 *是否已删除 	 */	private Integer isdelete;	/**	 *支付状态	 */	private Integer paystatus;	/**	 *支付结果	 */	private String payresult;	/**	 *支付时间	 */	private Date paytime;	/**	 *支付人openid	 */	private String payuserid;	/**	 *支付地址	 */	private String paycodeurl;	/**	 *	 */	private String ordercol0;	/**	 *	 */	private String ordercol1;	/**	 *	 */	private String ordercol2;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public Integer getServiceid() {	    return this.serviceid;	}	public void setServiceid(Integer serviceid) {	    this.serviceid=serviceid;	}	public Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(Date createtime) {	    this.createtime=createtime;	}	public Date getExpiretime() {	    return this.expiretime;	}	public void setExpiretime(Date expiretime) {	    this.expiretime=expiretime;	}	public BigDecimal getPrice() {	    return this.price;	}	public void setPrice(BigDecimal price) {	    this.price=price;	}	public String getCreator() {	    return this.creator;	}	public void setCreator(String creator) {	    this.creator=creator;	}	public Integer getIsdelete() {	    return this.isdelete;	}	public void setIsdelete(Integer isdelete) {	    this.isdelete=isdelete;	}	public Integer getPaystatus() {	    return this.paystatus;	}	public void setPaystatus(Integer paystatus) {	    this.paystatus=paystatus;	}	public String getPayresult() {	    return this.payresult;	}	public void setPayresult(String payresult) {	    this.payresult=payresult;	}	public Date getPaytime() {	    return this.paytime;	}	public void setPaytime(Date paytime) {	    this.paytime=paytime;	}	public String getPayuserid() {	    return this.payuserid;	}	public void setPayuserid(String payuserid) {	    this.payuserid=payuserid;	}	public String getPaycodeurl() {	    return this.paycodeurl;	}	public void setPaycodeurl(String paycodeurl) {	    this.paycodeurl=paycodeurl;	}	public String getOrdercol0() {	    return this.ordercol0;	}	public void setOrdercol0(String ordercol0) {	    this.ordercol0=ordercol0;	}	public String getOrdercol1() {	    return this.ordercol1;	}	public void setOrdercol1(String ordercol1) {	    this.ordercol1=ordercol1;	}	public String getOrdercol2() {	    return this.ordercol2;	}	public void setOrdercol2(String ordercol2) {	    this.ordercol2=ordercol2;	}
}

