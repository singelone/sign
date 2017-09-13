package com.weixin.suite.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * 套件应用信息
 * @author Administrator
 *
 */
@Table(name="agent")
public class Agent extends BaseDomain implements java.io.Serializable  {
	@TableField
	private static final long serialVersionUID = -3020588405460864944L;
	
	private String id;
	private Integer agentid;
	private String name;
	private String round_logo_url;
	private String square_logo_url;
	private Integer appid;
	private String corpId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getAgentid() {
		return agentid;
	}
	public void setAgentid(Integer agentid) {
		this.agentid = agentid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRound_logo_url() {
		return round_logo_url;
	}
	public void setRound_logo_url(String round_logo_url) {
		this.round_logo_url = round_logo_url;
	}
	public String getSquare_logo_url() {
		return square_logo_url;
	}
	public void setSquare_logo_url(String square_logo_url) {
		this.square_logo_url = square_logo_url;
	}
	public String getCorpId() {
		return corpId;
	}
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
	public Integer getAppid() {
		return appid;
	}
	public void setAppid(Integer appid) {
		this.appid = appid;
	}
	
}
