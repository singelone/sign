package com.ly.a.service;


import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.a.dao.CommSessionDao;
import com.ly.a.domain.CommSession;
import com.ly.service.base.ReflectionUtil;

/**
 * desc：公共Session Service
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:49
 */
@Service
public class CommSessionService {
	@Autowired
	private CommSessionDao commSessionDao;

	/***
	 * 查找一个
	 * @param sessionId Long
	 * @return CommSession
     */
	public CommSession getOne(Long sessionId) {
		CommSession entity = new CommSession();
		entity.setSessionId(sessionId);
		entity.setIdName("sessionId");
		return commSessionDao.getOne(entity);
	}

	/***
	 * 分页查询所有数据
	 * @param pageable Pageable
	 * @return Page
     */
	public Page<CommSession> get(Pageable pageable) {
		return commSessionDao.get(new CommSession(),pageable);
	}

	/***
	 * 增加
	 * @param entity CommSession
     */
	@Async
	@Transactional
	public void postForPasswd(CommSession entity) {
		entity.setSessionId(commSessionDao.getKey("comm_session", new CommSession()));
		entity.setType("password");
		entity.setCount(0);	
		DateTime date = new DateTime();
		entity.setStartDate(date.toDate());
		entity.setEndDate(date.plusDays(1).toDate());
		entity.setUrl("");
		entity.setContent("修改密码激活链接.");
		commSessionDao.post(entity);
	}

	/***
	 * 修改
	 * @param entity CommSession
	 * @return int
     */
	public int put(CommSession entity) {
		entity.setIdName("sessionId");
		return commSessionDao.put( entity);
	}

	/***
	 * 删除
	 * @param sessionId Long
	 * @return int
     */
	public int delete(Long sessionId) {
		CommSession entity = new CommSession();
		entity.setSessionId(sessionId);
		entity.setIdName("sessionId");
		return commSessionDao.delete( entity);
	}

	/***
	 * 根据字段查找对象
	 * @param field String
	 * @param value String
     * @return CommSession
     */
	public CommSession findOneByFiled(String field,String value) {
		CommSession entity = new CommSession();
		ReflectionUtil.setMethod(entity, field, value);
		entity.setIdName(field);
		return commSessionDao.getOne(entity);
	}
}
