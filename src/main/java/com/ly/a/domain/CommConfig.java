package com.ly.a.domain;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * desc：公共_配置
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：10:00
 */
@Table(name = "comm_config")
public class CommConfig extends BaseDomain implements java.io.Serializable {

	// Fields
	@TableField
	private static final long serialVersionUID = -1617098956850572599L;

	private String key1;
	private String value;
	private String name;
	private String description;
	private String type;

	// Constructors

	/** default constructor */
	public CommConfig() {
	}

	/** full constructor */
	public CommConfig(String key1, String value, String name,
			String description, String type) {
		this.key1 = key1;
		this.value = value;
		this.name = name;
		this.description = description;
		this.type = type;
	}

	// Property accessors


	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}