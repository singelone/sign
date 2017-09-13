package com.weixin.suite.service;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weixin.suite.dao.SuiteDao;
import com.weixin.suite.domain.Suite;

@Service
public class SuiteService {
	
	@Resource
	private SuiteDao suiteDao;
	
	/**
	 * 更新套件Tick信息
	 * @param suite
	 */
	@Transactional
	public void renovateTick(Suite suite){
		System.out.println(suite.toString());
		Suite select = new Suite();
		select.setSuiteid(suite.getSuiteid());
		List<Suite> list = suiteDao.getSuiteList(select);
		if(list.size()==1){
			Suite oldSuite = list.get(0);
			System.out.println(oldSuite.toString());
			oldSuite.setTicket(suite.getTicket());
			oldSuite.setTimeStamp(suite.getTimeStamp()+"");
			oldSuite.setUpdateTime(suite.getUpdateTime());
			System.out.println(oldSuite.toString());
			suiteDao.put(oldSuite);
		}else{
			UUID uuid = UUID.randomUUID();
			suite.setId(uuid.toString());
			suiteDao.post(suite);
		}
	}

}
