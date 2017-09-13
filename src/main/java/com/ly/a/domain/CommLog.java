package com.ly.a.domain;


import java.util.Date;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：公共_日志
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：10:00
 */
@Table(name = "comm_log")
public class CommLog extends BaseDomain implements java.io.Serializable {

	// Fields
	@TableField
	private static final long serialVersionUID = -2315003727438397739L;
	private Long logId;
	private String type;
	private String logUrl;
	private String logIp;
	private String logData;
	private Long userId;
	private String username;
	private Date logDate;

	// Constructors

	/** default constructor */
	public CommLog() {
	}

	/** full constructor */
	public CommLog(Long logId, String type, String logUrl, String logIp,
			String logData, Long userId, String username, Date logDate) {
		this.logId = logId;
		this.type = type;
		this.logUrl = logUrl;
		this.logIp = logIp;
		this.logData = logData;
		this.userId = userId;
		this.username = username;
		this.logDate = logDate;
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogUrl() {
		return this.logUrl;
	}

	public void setLogUrl(String logUrl) {
		this.logUrl = logUrl;
	}

	public String getLogIp() {
		return this.logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public String getLogData() {
		return this.logData;
	}

	public void setLogData(String logData) {
		this.logData = logData;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLogDate() {
		return this.logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

}