package com.weixin.suite.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * 获取企业授权信息
 * @author Administrator
 *
 */
@Table(name="corp")
public class Corp extends BaseDomain implements java.io.Serializable {
	@TableField
	private static final long serialVersionUID = -2119067964196942070L;
	private String id;
	private String corpid;
	private String corp_name;
	private String corp_type;
	private String corp_round_logo_url;
	private String corp_square_logo_url;
	private Long corp_user_max;
	private Long corp_agent_max;
	private String corp_full_name;
	private Long verified_end_time;
	private Integer subject_type;
	private String corp_wxqrcode;
	private String manager_email;
	private String manager_mobile;
	private String manager_userid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCorpid() {
		return corpid;
	}
	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}
	public String getCorp_name() {
		return corp_name;
	}
	public void setCorp_name(String corp_name) {
		this.corp_name = corp_name;
	}
	public String getCorp_type() {
		return corp_type;
	}
	public void setCorp_type(String corp_type) {
		this.corp_type = corp_type;
	}
	public String getCorp_round_logo_url() {
		return corp_round_logo_url;
	}
	public void setCorp_round_logo_url(String corp_round_logo_url) {
		this.corp_round_logo_url = corp_round_logo_url;
	}
	public String getCorp_square_logo_url() {
		return corp_square_logo_url;
	}
	public void setCorp_square_logo_url(String corp_square_logo_url) {
		this.corp_square_logo_url = corp_square_logo_url;
	}
	public Long getCorp_user_max() {
		return corp_user_max;
	}
	public void setCorp_user_max(Long corp_user_max) {
		this.corp_user_max = corp_user_max;
	}
	public Long getCorp_agent_max() {
		return corp_agent_max;
	}
	public void setCorp_agent_max(Long corp_agent_max) {
		this.corp_agent_max = corp_agent_max;
	}
	public String getCorp_full_name() {
		return corp_full_name;
	}
	public void setCorp_full_name(String corp_full_name) {
		this.corp_full_name = corp_full_name;
	}
	public Long getVerified_end_time() {
		return verified_end_time;
	}
	public void setVerified_end_time(Long verified_end_time) {
		this.verified_end_time = verified_end_time;
	}
	public Integer getSubject_type() {
		return subject_type;
	}
	public void setSubject_type(Integer subject_type) {
		this.subject_type = subject_type;
	}
	public String getCorp_wxqrcode() {
		return corp_wxqrcode;
	}
	public void setCorp_wxqrcode(String corp_wxqrcode) {
		this.corp_wxqrcode = corp_wxqrcode;
	}
	public String getManager_email() {
		return manager_email;
	}
	public void setManager_email(String manager_email) {
		this.manager_email = manager_email;
	}
	public String getManager_mobile() {
		return manager_mobile;
	}
	public void setManager_mobile(String manager_mobile) {
		this.manager_mobile = manager_mobile;
	}
	public String getManager_userid() {
		return manager_userid;
	}
	public void setManager_userid(String manager_userid) {
		this.manager_userid = manager_userid;
	}
	
}
