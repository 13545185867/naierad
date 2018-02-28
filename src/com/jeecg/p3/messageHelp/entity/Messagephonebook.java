package com.jeecg.p3.messageHelp.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>Messagephonebook:<br>
 * @author chao.hua
 * @since：2017年08月16日 13时08分01秒 星期三 
 * @version:1.0
 */
public class Messagephonebook implements Entity<Integer> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private Integer id;	/**	 *用户姓名	 */	private String username;	/**	 *用户联系地址	 */	private String useraddr;	/**	 *生日	 */	private Date birthday;	/**	 *会员日	 */	private Date memberday;	/**	 *创建人	 */	private String creatuser;	/**	 *0 不可用 1可用)	 */	private Integer status;	public Integer getId() {	    return this.id;	}	public void setId(Integer id) {	    this.id=id;	}	public String getUsername() {	    return this.username;	}	public void setUsername(String username) {	    this.username=username;	}	public String getUseraddr() {	    return this.useraddr;	}	public void setUseraddr(String useraddr) {	    this.useraddr=useraddr;	}	public Date getBirthday() {	    return this.birthday;	}	public void setBirthday(Date birthday) {	    this.birthday=birthday;	}	public Date getMemberday() {	    return this.memberday;	}	public void setMemberday(Date memberday) {	    this.memberday=memberday;	}	public String getCreatuser() {	    return this.creatuser;	}	public void setCreatuser(String creatuser) {	    this.creatuser=creatuser;	}	public Integer getStatus() {	    return this.status;	}	public void setStatus(Integer status) {	    this.status=status;	}
}

