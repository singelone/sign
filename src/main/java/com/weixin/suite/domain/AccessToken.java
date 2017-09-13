package com.weixin.suite.domain;

import java.io.Serializable;
import java.util.Date;

import com.ly.dao.base.BaseDomain;
import com.ly.dao.base.Table;
import com.ly.dao.base.TableField;

/**
 * 套件token
 * @author yangjing
 *
 */
@Table(name="accessToken")
public class AccessToken extends BaseDomain implements Serializable{
	
	@TableField
	private static final long serialVersionUID = 1038800532741872182L;

	private String id;
	//套件id
	private String suiteId;
	// 获取到的凭证  
    private String token;  
    // 凭证有效时间，单位：秒  
    private Integer expiresIn;
    //过期时间
    private Date overdueDate;
  
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSuiteId() {
		return suiteId;
	}

	public void setSuiteId(String suiteId) {
		this.suiteId = suiteId;
	}

	public String getToken() {  
        return token;  
    }  
  
    public void setToken(String token) {  
        this.token = token;  
    }  
  
    public Integer getExpiresIn() {  
        return expiresIn;  
    }  
  
    public void setExpiresIn(Integer expiresIn) {  
        this.expiresIn = expiresIn;  
    }

	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}  
    
}
