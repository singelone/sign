package com.weixin.suite.domain;

import java.util.Date;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * 套件信息类
 * @author yangjing
 *
 */
@Table(name = "suite")
public class Suite  extends BaseDomain implements java.io.Serializable {
	@TableField
	private static final long serialVersionUID = -18929252187164783L;
	
	private String id;
	private String ticket;			//	Ticket内容
	private String suiteid;			//	应用套件的SuiteId
	private String timeStamp;		//	时间戳
	private Date updateTime;		//	更新时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getSuiteid() {
		return suiteid;
	}
	public void setSuiteid(String suiteid) {
		this.suiteid = suiteid;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Override
	public String toString() {
		return "Suite [id=" + id + ", ticket=" + ticket + ", suiteid=" + suiteid + ", timeStamp=" + timeStamp
				+ ", updateTime=" + updateTime + "]";
	}
	
	
}
