package com.weixin.sign.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.TableField;

import javax.persistence.Table;
import java.io.Serializable;



/**
 * 部门信息
 * @author Administrator
 *
 */
@Table(name="department")
public class Department extends BaseDomain implements Serializable{

	@TableField
	private static final long serialVersionUID = -145469844401952529L;

	private Integer id;			//部门id
	private String name;		//部门名称
	private Integer parentid;	//上级部门id
	private Integer order;		//排序
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", parentid=" + parentid + ", order=" + order + "]";
	}
	
}
