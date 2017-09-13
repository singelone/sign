package com.weixin.suite.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ly.dao.base.BasePageDao;
import com.weixin.suite.domain.AccessToken;

@Repository
public class AccessTokenDao extends BasePageDao<AccessToken, Serializable> {

	/**
	 * 通过套件ID 获取套件tokenList信息
	 * @param suiteId
	 * @return
	 */
	public List<AccessToken> getList(String suiteId) {
		AccessToken token = new AccessToken();
		token.setSuiteId(suiteId);
		String sql = "select * from accessToken where suiteId = :suiteId ";
		return get(sql,token );
		
	}
	
	/**
	 * 更新套件信息
	 */
	public int put(AccessToken token){
		String sql = "update accessToken set token =:token,expiresIn=:expiresIn,overdueDate=:overdueDate  where id = :id ";
		return put(sql,token );
	}

}
