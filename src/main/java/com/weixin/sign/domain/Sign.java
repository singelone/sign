package com.weixin.sign.domain;

import java.io.Serializable;
import java.util.Arrays;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * 签到信息
 * @author yangjing
 *
 */
@Table
public class Sign extends BaseDomain implements Serializable{

	@TableField
	private static final long serialVersionUID = 1505215304341771667L;
	
	private String id;
	private String userid;
    private String groupname;
    private String checkin_type;
    private String exception_type;
    private Long checkin_time;
    private String location_title;
    private String location_detail;
    private String wifiname;
    private String notes;
    private String wifimac;
    private Object[] medialist;
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getCheckin_type() {
		return checkin_type;
	}
	public void setCheckin_type(String checkin_type) {
		this.checkin_type = checkin_type;
	}
	public String getException_type() {
		return exception_type;
	}
	public void setException_type(String exception_type) {
		this.exception_type = exception_type;
	}
	public Long getCheckin_time() {
		return checkin_time;
	}
	public void setCheckin_time(Long checkin_time) {
		this.checkin_time = checkin_time;
	}
	public String getLocation_title() {
		return location_title;
	}
	public void setLocation_title(String location_title) {
		this.location_title = location_title;
	}
	public String getLocation_detail() {
		return location_detail;
	}
	public void setLocation_detail(String location_detail) {
		this.location_detail = location_detail;
	}
	public String getWifiname() {
		return wifiname;
	}
	public void setWifiname(String wifiname) {
		this.wifiname = wifiname;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getWifimac() {
		return wifimac;
	}
	public void setWifimac(String wifimac) {
		this.wifimac = wifimac;
	}
	public Object[] getMedialist() {
		return medialist;
	}
	public void setMedialist(Object[] medialist) {
		this.medialist = medialist;
	}
	@Override
	public String toString() {
		return "UserInfo [userid=" + userid + ", groupname=" + groupname + ", checkin_type=" + checkin_type
				+ ", exception_type=" + exception_type + ", checkin_time=" + checkin_time + ", location_title="
				+ location_title + ", location_detail=" + location_detail + ", wifiname=" + wifiname + ", notes="
				+ notes + ", wifimac=" + wifimac + ", medialist=" + Arrays.toString(medialist) + "]";
	}
    
}
