package com.ly.a.web.tools;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 描述：Tiles页脚，页头的数据填充
 * 
 * @author 刘咏
 * @version 1.1
 * 
 * @日期：2014-10-27
 */
public class AccessSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		sessionMap.put("LoginTimes",0);
		se.getSession().setAttribute("sessionMap", sessionMap);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

	}

}
