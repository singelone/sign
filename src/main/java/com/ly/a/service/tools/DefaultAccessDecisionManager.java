package com.ly.a.service.tools;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * desc：缺省决策管理器
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：10:01
 */
public class DefaultAccessDecisionManager implements AccessDecisionManager {
	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (null == configAttributes) {
			return;
		}
		Iterator<ConfigAttribute> cons = configAttributes.iterator();
		while (cons.hasNext()) {
			ConfigAttribute ca = cons.next();
			String needRole = ((SecurityConfig)ca).getAttribute();
			// gra 为用户所被赋予的权限，needRole为访问相应的资源应具有的权限
			for (GrantedAuthority gra : authentication.getAuthorities()) {
				if (needRole.trim().equals(gra.getAuthority().trim())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("没有权限");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}