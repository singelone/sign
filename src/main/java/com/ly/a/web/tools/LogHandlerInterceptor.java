package com.ly.a.web.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogHandlerInterceptor extends HandlerInterceptorAdapter {

//	@Autowired
//	private CommLogService commLogService;

	private static final Logger log = LoggerFactory.getLogger(LogHandlerInterceptor.class);

	// 性能计数线程池
	private ThreadLocal<StopWatch> stopwatchLocal = new ThreadLocal<StopWatch>();

	private final boolean isOpen = true;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		if (isOpen && !request.getServletPath().startsWith("/resources")) {
			StopWatch stopWatch = new StopWatch(System.currentTimeMillis() + "");
			stopwatchLocal.set(stopWatch);
			stopWatch.start(handler.toString());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

//		if (SpringSecurityUtil.getUser() != null
//				&& request.getServletPath().startsWith("/admin")
//				&& !request.getServletPath().startsWith("/admin/login")
//				&& !request.getServletPath().startsWith("/admin/image")) {
//			if (request.getHeader("Referer") != null
//					&& request.getHeader("Referer").endsWith("/admin/login/")) {
//				CommLog entity = new CommLog();
//				entity.setLogIp(WebUtil.getIp(request));
//				entity.setLogUrl(request.getHeader("Referer"));
//				entity.setUserId(SpringSecurityUtil.getUser().getUserId());
//				entity.setUsername(SpringSecurityUtil.getUser().getUsername());
//				entity.setLogData("登录");
//				commLogService.post(entity);
//			} else {
//				CommLog entity = new CommLog();
//				entity.setLogIp(WebUtil.getIp(request));
//				entity.setLogUrl(request.getRequestURL().toString());
//				entity.setUserId(SpringSecurityUtil.getUser().getUserId());
//				entity.setUsername(SpringSecurityUtil.getUser().getUsername());
//				entity.setLogData("");
//				commLogService.post(entity);
//			}
//		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

		if (isOpen && !request.getServletPath().startsWith("/resources")) {
			StopWatch stopWatch = stopwatchLocal.get();
			if (stopWatch != null) {
				stopWatch.stop();
				long time = stopWatch.getTotalTimeMillis();
				if (time >= 0) {
					log.warn(request.getRequestURL().toString()
							+ request.getQueryString() + "访问时间大于1秒:" + time);
				}
				stopwatchLocal.set(null);
			}
		}
	}

}
