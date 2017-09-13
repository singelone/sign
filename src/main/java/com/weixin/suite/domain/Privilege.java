package com.weixin.suite.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * 套件应用权限
 * @author yangjing
 *
 */
@Table(name="privilege")
public class Privilege extends BaseDomain implements java.io.Serializable{
	@TableField
	private static final long serialVersionUID = 8861453377229345145L;
	
	private String id;
	private String level;
	private String allow_party;
	private String allow_user;
	private String allow_tag;
	private String extra_party;
	private String extra_user;
	private String extra_tag;
	private String agentId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAllow_party() {
		return allow_party;
	}
	public void setAllow_party(String allow_party) {
		this.allow_party = allow_party;
	}
	public String getAllow_user() {
		return allow_user;
	}
	public void setAllow_user(String allow_user) {
		this.allow_user = allow_user;
	}
	public String getAllow_tag() {
		return allow_tag;
	}
	public void setAllow_tag(String allow_tag) {
		this.allow_tag = allow_tag;
	}
	public String getExtra_party() {
		return extra_party;
	}
	public void setExtra_party(String extra_party) {
		this.extra_party = extra_party;
	}
	public String getExtra_user() {
		return extra_user;
	}
	public void setExtra_user(String extra_user) {
		this.extra_user = extra_user;
	}
	public String getExtra_tag() {
		return extra_tag;
	}
	public void setExtra_tag(String extra_tag) {
		this.extra_tag = extra_tag;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

}
