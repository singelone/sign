package com.ly.a.domain;

import java.util.Date;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：公共_Session
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：10:00
 */
@Table(name = "comm_session")
public class CommSession extends BaseDomain implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	@TableField
	private static final long serialVersionUID = -5107268364078313019L;
	private Long sessionId;
	private String sessionCode;
	private String ip;
	private String url;
	private String content;
	private Integer count;
	private String type;
	private Long userId;
	private Date startDate;
	private Date endDate;

	// Constructors

	/** default constructor */
	public CommSession() {
	}

	/** full constructor */
	public CommSession(Long sessionId, String sessionCode, String ip,
			String url, String content, Integer count, String type,
			Long userId, Date startDate, Date endDate) {
		this.sessionId = sessionId;
		this.sessionCode = sessionCode;
		this.ip = ip;
		this.url = url;
		this.content = content;
		this.count = count;
		this.type = type;
		this.userId = userId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	// Property accessors

	public Long getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionCode() {
		return this.sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}