package com.ly.a.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.a.dao.CommLogDao;
import com.ly.a.domain.CommLog;

/**
 * desc：公共日志service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:49
 */
@Service
public class CommLogService {

	@Autowired
	private CommLogDao commLogDao;

	/***
	 * 查找一个
	 * @param logId Long
	 * @return CommLog
     */
	public CommLog getOne(Long logId) {
		CommLog entity = new CommLog();
		entity.setLogId(logId);
		entity.setIdName("logId");
		return commLogDao.getOne(entity);
	}

	/***
	 * 分页查询所有数据
	 * @param entity CommLog
	 * @param pageable Pageable
     * @return Page
     */
	public Page<CommLog> get(CommLog entity,Pageable pageable) {
		//String sql = "select * from comm_log where locate(:username,username)>0 or locate(:username,logIp)>0 or locate(:username,DATE_FORMAT(logDate,'%Y-%m-%d %T'))>0";
		StringBuilder sb = new StringBuilder();
		sb.append("select * from comm_log");
		if(entity!=null && entity.getUsername()!= null) {
			sb.append(" where locate(:username,username)>0 or locate(:username,logIp)>0 or locate(:username,DATE_FORMAT(logDate,'%Y-%m-%d %T'))>0");
		}
		return commLogDao.get(sb.toString(),entity, pageable);
	}

	/***
	 * 增加
	 * @param entity CommLog
	 * @return int
     */
	@Async
	@Transactional
	public int post(CommLog entity) {
		entity.setType("access");
		entity.setLogId(commLogDao.getKey("comm_log", entity));
		entity.setLogDate(new Date());
		return commLogDao.post(entity);
	}

	/***
	 * 修改
	 * 
	 * @param entity CommLog
	 * @return int
	 */

	public int update(CommLog entity) {
		entity.setIdName("logId");
		return commLogDao.put(entity);
	}

	/***
	 * 删除
	 * @param logId Long
	 * @return int
     */
	public int delete(Long logId) {
		CommLog entity = new CommLog();
		entity.setLogId(logId);
		entity.setIdName("logId");
		return commLogDao.delete("logId", entity);
	}

}
