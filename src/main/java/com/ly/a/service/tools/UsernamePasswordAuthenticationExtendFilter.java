package com.ly.a.service.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.google.code.kaptcha.Constants;

/**
 * desc：重载SECURITY3的UsernamePasswordAuthenticationFilter的attemptAuthentication,
 * 		obtainUsername,obtainPassword方法(完善逻辑) 增加验证码校验模块 添加验证码属性 添加验证码功能开关属性
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:48
 */
public class UsernamePasswordAuthenticationExtendFilter extends
		UsernamePasswordAuthenticationFilter {

	// 验证码字段
	private String validateCodeParameter = "j_code";

	// 是否开启验证码功能
	private boolean openValidateCode = true;

	private boolean postOnly = true;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {

		// 只接受POST方式传递的数据
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: "
							+ request.getMethod());
		}

		// 开启验证码功能的情况
		if (isOpenValidateCode())
			checkValidateCode(request);

		// 获取Username和Password
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		// UsernamePasswordAuthenticationToken实现Authentication校验
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// 允许子类设置详细属性
		setDetails(request, authRequest);

		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	// 匹对验证码的正确性
	public void checkValidateCode(HttpServletRequest request) {
		String sessionValidateCode = obtainSessionValidateCode(request);
		String validateCodeParameter = obtainValidateCodeParameter(request);
		if (StringUtils.isEmpty(validateCodeParameter)
				|| !sessionValidateCode.equalsIgnoreCase(validateCodeParameter)) {
			throw new AuthenticationServiceException(
					messages.getMessage("LoginAuthentication.captchaNotEquals"));

		}
	}

	protected String obtainSessionValidateCode(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute(
				Constants.KAPTCHA_SESSION_KEY);
		return null == obj ? "" : obj.toString();
	}

	public String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(getValidateCodeParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(getUsernameParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(getPasswordParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	public String getValidateCodeParameter() {
		return validateCodeParameter;
	}

	public void setValidateCodeParameter(String validateCodeParameter) {
		this.validateCodeParameter = validateCodeParameter;
	}

	public boolean isOpenValidateCode() {
		return openValidateCode;
	}

	public void setOpenValidateCode(boolean openValidateCode) {
		this.openValidateCode = openValidateCode;
	}
	
	public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }


}