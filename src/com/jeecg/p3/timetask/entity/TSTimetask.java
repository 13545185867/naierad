package com.jeecg.p3.timetask.entity;

import java.util.Date;
import java.math.BigDecimal;
import org.jeecgframework.p3.core.utils.persistence.Entity;

/**
 * 描述：</b>TSTimetask:<br>
 * @author chao.hua
 * @since：2017年07月21日 20时42分33秒 星期五 
 * @version:1.0
 */
public class TSTimetask implements Entity<String> {
	private static final long serialVersionUID = 1L;
		/**	 *	 */	private String id;	/**	 *	 */	private String createBy;	/**	 *	 */	private Date createDate;	/**	 *	 */	private String createName;	/**	 *	 */	private String cronExpression;	/**	 *	 */	private String isEffect;	/**	 *	 */	private String isStart;	/**	 *	 */	private String taskDescribe;	/**	 *	 */	private String taskId;	/**	 *	 */	private String updateBy;	/**	 *	 */	private Date updateDate;	/**	 *	 */	private String updateName;	public String getId() {	    return this.id;	}	public void setId(String id) {	    this.id=id;	}	public String getCreateBy() {	    return this.createBy;	}	public void setCreateBy(String createBy) {	    this.createBy=createBy;	}	public Date getCreateDate() {	    return this.createDate;	}	public void setCreateDate(Date createDate) {	    this.createDate=createDate;	}	public String getCreateName() {	    return this.createName;	}	public void setCreateName(String createName) {	    this.createName=createName;	}	public String getCronExpression() {	    return this.cronExpression;	}	public void setCronExpression(String cronExpression) {	    this.cronExpression=cronExpression;	}	public String getIsEffect() {	    return this.isEffect;	}	public void setIsEffect(String isEffect) {	    this.isEffect=isEffect;	}	public String getIsStart() {	    return this.isStart;	}	public void setIsStart(String isStart) {	    this.isStart=isStart;	}	public String getTaskDescribe() {	    return this.taskDescribe;	}	public void setTaskDescribe(String taskDescribe) {	    this.taskDescribe=taskDescribe;	}	public String getTaskId() {	    return this.taskId;	}	public void setTaskId(String taskId) {	    this.taskId=taskId;	}	public String getUpdateBy() {	    return this.updateBy;	}	public void setUpdateBy(String updateBy) {	    this.updateBy=updateBy;	}	public Date getUpdateDate() {	    return this.updateDate;	}	public void setUpdateDate(Date updateDate) {	    this.updateDate=updateDate;	}	public String getUpdateName() {	    return this.updateName;	}	public void setUpdateName(String updateName) {	    this.updateName=updateName;	}
}

