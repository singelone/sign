package com.ly.a.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.ly.a.domain.CommLog;
import com.ly.dao.base.BasePageDao;

/**
 * desc：公共日志Dao
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：9:59
 */
@Repository
public class CommLogDao extends BasePageDao<CommLog, Serializable> {

}
