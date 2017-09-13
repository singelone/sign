package com.ly.a.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.a.dao.CommConfigDao;
import com.ly.a.domain.CommConfig;

/**
 * desc：公共配置service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:49
 */
@Service
public class CommConfigService {

	@Autowired
	private CommConfigDao commConfigDao;

    /**
     * 查询所有
     * @return Map
     */
	public Map<String, String> get() {
		List<CommConfig> list = commConfigDao.get(new CommConfig());
		Map<String, String> map = new HashMap<String, String>();
		for (CommConfig config : list) {
			map.put(config.getKey1(), config.getValue());
		}
		return map;
	}
	
	
	/***
	 * 查询单个
	 * @param entity
	 * @return
	 */
	public CommConfig getOne(String key) {	
		 CommConfig commConfig=new CommConfig();
		 commConfig.setKey1(key);
		 commConfig.setIdName("key1");
		 return commConfigDao.getOne(commConfig);
	}
	
}
