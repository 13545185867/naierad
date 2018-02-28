package com.jeecg.p3.userinfo.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Userinfo:<br>
 * @author chao.hua
 * @since：2017年09月11日 12时01分29秒 星期一 
 * @version:1.0
 */
public class Userinfo implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *微信ID	 */	private String openid;	/**	 *图像	 */	private String headpic;	/**	 *微信名称	 */	private String nickname;	/**	 *省份	 */	private String province;	/**	 *城市	 */	private String city;	/**	 *性别	 */	private String sex;	/**	 *国家	 */	private String country;	/**	 *关注状态 	 */	private String subscribe;	/**	 *关注公众号时间	 */	private Date subscribeTime;	/**	 *真实姓名	 */	private String realname;	/**	 *联系地址	 */	private String addr;	/**	 *会员编号	 */	private Integer code;	/**	 *职业	 */	private String profession;	/**	 *	 */	private Date createtime;	/**	 *手机号码	 */	private String mobile;
	public String getIskf() {
		return iskf;
	}
	public void setIskf(String iskf) {
		this.iskf = iskf;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private String iskf;
	private String status;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getOpenid() {	    return this.openid;	}	public void setOpenid(String openid) {	    this.openid=openid;	}	public String getHeadpic() {	    return this.headpic;	}	public void setHeadpic(String headpic) {	    this.headpic=headpic;	}	public String getNickname() {	    return this.nickname;	}	public void setNickname(String nickname) {	    this.nickname=nickname;	}	public String getProvince() {	    return this.province;	}	public void setProvince(String province) {	    this.province=province;	}	public String getCity() {	    return this.city;	}	public void setCity(String city) {	    this.city=city;	}	public String getSex() {	    return this.sex;	}	public void setSex(String sex) {	    this.sex=sex;	}	public String getCountry() {	    return this.country;	}	public void setCountry(String country) {	    this.country=country;	}	public String getSubscribe() {	    return this.subscribe;	}	public void setSubscribe(String subscribe) {	    this.subscribe=subscribe;	}	public Date getSubscribeTime() {	    return this.subscribeTime;	}	public void setSubscribeTime(Date subscribeTime) {	    this.subscribeTime=subscribeTime;	}	public String getRealname() {	    return this.realname;	}	public void setRealname(String realname) {	    this.realname=realname;	}	public String getAddr() {	    return this.addr;	}	public void setAddr(String addr) {	    this.addr=addr;	}	public Integer getCode() {	    return this.code;	}	public void setCode(Integer code) {	    this.code=code;	}	public String getProfession() {	    return this.profession;	}	public void setProfession(String profession) {	    this.profession=profession;	}	public Date getCreatetime() {	    return this.createtime;	}	public void setCreatetime(Date createtime) {	    this.createtime=createtime;	}	public String getMobile() {	    return this.mobile;	}	public void setMobile(String mobile) {	    this.mobile=mobile;	}
}

