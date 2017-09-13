package com.weixin.suite.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ly.dao.base.BasePageDao;
import com.weixin.suite.domain.Suite;

@Repository
public class SuiteDao  extends BasePageDao<Suite, Serializable>{

	
	/**
	 * 通过套件ID 获取suitelist信息
	 * @param suite
	 * @return
	 */
	public List<Suite> getSuiteList(Suite suite){
		StringBuffer sb = new StringBuffer("select * from suite where suiteid = :suiteid ");
		return get(sb.toString(), suite);
	}
	
	/**
	 * 更新套件信息
	 */
	public int put(Suite suite){
		String sql = "update suite set ticket =:ticket,suiteid = :suiteid,updateTime = :updateTime,timeStamp=:timeStamp where  id=:id ";
		return put(sql, suite);
	}
	
	
}
