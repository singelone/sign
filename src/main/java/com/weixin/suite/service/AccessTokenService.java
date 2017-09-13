package com.weixin.suite.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.weixin.suite.dao.AccessTokenDao;

@Service
public class AccessTokenService {

	@Resource
	private AccessTokenDao accessTokenDao;
	
}
