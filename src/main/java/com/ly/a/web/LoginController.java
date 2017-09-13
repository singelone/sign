package com.ly.a.web;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.ly.a.service.tools.KaptchaService;

/**
 * desc：登录
 * ref ：
 * user：刘咏
 * date：2016/4/27
 * time：11:57
 */
@Controller
@RequestMapping("/login/*")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private KaptchaService kaptchaService;
	
	/***
	 * 登录页面
	 * 
	 * @return String
	 */
	@RequestMapping("/login")
	public String login() {
		return ".login";
	}
	
	/***
	 * 生成验证码
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param session HttpSession
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/startCaptcha", method = RequestMethod.GET)
	public void startCaptcha(HttpServletRequest request, HttpServletResponse response,HttpSession session)
			throws ServletException, IOException {
		
		response.setDateHeader("Expires", 0);  
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        response.setHeader("Pragma", "no-cache");  
        response.setContentType("image/jpeg");

		Producer kaptchaProducer = kaptchaService.createImage();
		String capText = kaptchaProducer.createText();  
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
        BufferedImage bi = kaptchaProducer.createImage(capText);  
        ServletOutputStream out = response.getOutputStream();
        ImageIO.setUseCache(false);
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();  
        } catch (IOException e) {
            log.error("写验证码错误[startCaptcha]",e);
        } finally {
            out.close();  
        }  
	}
	
	
	/***
	 * 返回500
	 * @return String
	 */
	@RequestMapping(value = "p500")
	public String P500() {			
		return "/error/500";
	}	
	
	/***
	 * 登录失败跳转页面
	 * @return String
	 */
	@RequestMapping(value = "p404")
	public String P404() {			
		return "/error/404";
	}	
}
