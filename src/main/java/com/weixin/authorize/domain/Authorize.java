package com.weixin.authorize.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.TableField;

import javax.persistence.Table;
import java.util.Date;


/**
 * 企业授权码信息
 * @author yangjing
 *
 */
@Table(name = "authorize")
public class Authorize extends BaseDomain implements java.io.Serializable {

	@TableField
	private static final long serialVersionUID = 6196677296404932342L;
	
	private String id;
	private String authCode;
	private String timeStamp;
	private String corpId;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private String permanentCode;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPermanentCode() {
		return permanentCode;
	}
	public void setPermanentCode(String permanentCode) {
		this.permanentCode = permanentCode;
	}
	
}
