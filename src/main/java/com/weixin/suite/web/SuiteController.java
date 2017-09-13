package com.weixin.suite.web;

import com.common.util.*;
import com.weixin.authorize.service.AuthorizeService;
import com.weixin.mp.aes.AesException;
import com.weixin.mp.aes.WXBizMsgCrypt;
import com.weixin.suite.service.SuiteService;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/suite")
public class SuiteController {
	
	@Resource
	private AuthorizeService authorizeService;
	@Resource
	private SuiteService suiteService;
	
	@RequestMapping("/ticket")
	@ResponseBody
	public void ticket(HttpServletRequest request,HttpServletResponse response,
			String msg_signature,String timestamp,
			String nonce,String echostr ,
			ModelMap modelMap){
		
        System.out.println("GET " + request.getRequestURL() + " "  
                + request.getQueryString());  
  
        Map<String, String[]> params = request.getParameterMap();  
        String queryString = "";  
        for (String key : params.keySet()) {  
            String[] values = params.get(key);  
            for (int i = 0; i < values.length; i++) {  
                String value = values[i];  
                queryString += key + "=" + value + "&";  
            }  
        }
        // 去掉最后一个空格  
        queryString = queryString.substring(0, queryString.length() - 1);
        System.out.println("GET " + request.getRequestURL() + " " + queryString);
        if(null!=echostr){
        	String result = DecryptAndEncryptUtil.decrypt(msg_signature, timestamp, nonce, echostr);
        	System.out.println(result);
        	try {
        		response.getWriter().write(result);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }else{
        	Map<String, String> encyptMap = RequestPostXmlUtil.getPostXmlMap(request);
	    	WXBizMsgCrypt pc;
			try {
				pc = new WXBizMsgCrypt(Constants.Weixin.suite_token,Constants.Weixin.suite_encodingAesKey, Constants.Weixin.suiteID);
				String result = pc.decrypt(encyptMap.get("encrypt"));
				System.out.println(result);
				Map<String, String> decyptMap = XMLUtil.xmlChangeMap(result);
				String infoType = decyptMap.get("InfoType");
				//授权成功
				if("create_auth".equals(infoType)){
					String authCode = decyptMap.get("AuthCode");
					String timeStamp = decyptMap.get("TimeStamp");
					String suiteId = decyptMap.get("SuiteId");
					authorizeService.save(suiteId,authCode,timeStamp);
				//取消授权
				}else if("cancel_auth".equals(infoType)){
					System.out.println(decyptMap.get("AuthCorpId"));
				
				//变更授权
				}else if("change_auth".equals(infoType)){
					System.out.println(decyptMap.get("AuthCorpId"));
					
				//suite_ticket协议
				}else if("suite_ticket".equals(infoType)){
					decyptMap.get("SuiteTicket");
				}
		    	
		    	response.getWriter().write("success");
			} catch (AesException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

        }
	}
	
	
	@RequestMapping("/menu")
	public void menu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

  
//        if (null != at) {  
//            // 调用接口创建菜单  
//            int result = WeixinUtil.createMenu(MenuUtil.getMenu(), at.getToken());
//  
//            // 判断菜单创建结果  
//            if (0 == result){
//            	response.setContentType("text/html;charset=UTF-8");  
//            	PrintWriter pw = response.getWriter();  
//	            pw.println("菜单创建成功！");  
//	            pw.flush();     
//            }else{
//            	response.setContentType("text/html;charset=UTF-8");  
//            	PrintWriter pw = response.getWriter();  
//	            pw.println("菜单创建失败，错误码：" + result);  
//	            pw.flush();     
//            }   
//        }
//		
	}

	/*@RequestMapping("/create")
	@ResponseBody
	public void testWXSaaSCallback( HttpServletRequest req, ServletResponse res) {
		//常量值
		String CORP_ID = "wx23ed1347520b93b8";    //CorpId, 企业号的普通管理组中可查看
		String SUITE_ID = "tja2312bedd5086xxx";    //套件ID，在套件信息中查看
		String SUITE_SECRET = "LGVT0BrN2DZ7VMTuIPOsudaWuXOE0iM67yHpIs1ofESS4l-jSC8LN9nOnmilhXXX";
		String SUITE_TOKEN = "9FWRxM82G4pklylhFl2zoJjAQwdv2";
		String SUITE_ENCODING_AES_KEY = "v6pgkNgnw1a2b1VOxLd47g7xVgIIK7BOOfkr553ARwf";

		//获取参数
		String msgSignature = req.getParameter("msg_signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");    //创建套件时验证回调url时传入

		String result = "";
		try {
			if (StringUtils.isNotBlank(echostr)) {    //=======验证回调url有效性=======
				WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(SUITE_TOKEN,
						SUITE_ENCODING_AES_KEY, CORP_ID);//注意是CORP_ID
				result = wxBizMsgCrypt.VerifyURL(msgSignature, timestamp, nonce, echostr);
				System.out.println(result);
				res.getWriter().write(result);    //对echostr参数解密并原样返回echostr明文(不能加引号，不能带bom头，不能带换行符)
			} else {
				//其他操作
				res.getWriter().write("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
