package com.ly.index.web;

import com.common.util.Constants;
import com.common.util.DecryptAndEncryptUtil;
import com.common.util.RequestPostXmlUtil;
import com.common.util.XMLUtil;
import com.weixin.authorize.service.AuthorizeService;
import com.weixin.mp.aes.AesException;
import com.weixin.mp.aes.WXBizMsgCrypt;
import com.weixin.suite.domain.Suite;
import com.weixin.suite.service.SuiteService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * desc：首页
 * ref ：
 * user：
 * date：
 * time：11:57
 */
@Controller
public class IndexController {
	
	@Resource
	private SuiteService suiteService;
	@Resource
	private AuthorizeService authorizeService;
	
	/**
	 * 应用首页
	 * @param
	 * @return
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request){
		try {
			String codeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx23ed1347520b93b8&redirect_uri=http%3A%2F%2Ftangqian.tunnel.qydev.com&response_type=code&scope=snsapi_base&agentid=1000028&state=#wechat_redirect";
			String code = request.getParameter("code");
			String encode = URLEncoder.encode("http://tangqian.tunnel.qydev.com/index", "utf-8");
			System.out.println(encode+"   -------------");
			System.out.println(code+"  *****************");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//菜单页
		return "/manage/setTime/index";
	}
	
	/**
	 * 微信推送消息回调接口
	 * 详情见：http://qydev.weixin.qq.com/wiki/index.php?title=第三方回调协议
	 * @param request
	 * @param response
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param modelMap
	 */
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
				String result = pc.decrypt(encyptMap.get("Encrypt"));
				System.out.println(result);
				Map<String, String> decyptMap = XMLUtil.xmlChangeMap(result);
				String infoType = decyptMap.get("InfoType");
				//授权成功通知
				if("create_auth".equals(infoType)){
					String authCode = decyptMap.get("AuthCode");
					String timeStamp = decyptMap.get("TimeStamp");
					String suiteId = decyptMap.get("SuiteId");
					authorizeService.save(suiteId,authCode,timeStamp);
				//取消授权通知
				}else if("cancel_auth".equals(infoType)){
					System.out.println(decyptMap.get("AuthCorpId"));
				
				//变更授权通知
				}else if("change_auth".equals(infoType)){
					System.out.println(decyptMap.get("AuthCorpId"));
					String authCode = decyptMap.get("AuthCode");
					String timeStamp = decyptMap.get("TimeStamp");
					String suiteId = decyptMap.get("SuiteId");
					authorizeService.updateAuthorize(suiteId, authCode, timeStamp);
					
				//每间隔10分钟微信推送suite_ticket协议
				}else if("suite_ticket".equals(infoType)){
					String suiteId = decyptMap.get("SuiteId");
					String timeStamp = decyptMap.get("TimeStamp")+"";
					String suiteTicket = decyptMap.get("SuiteTicket");
					System.out.println("suiteTicket: "+suiteTicket);
					Suite suite = new Suite();
					suite.setSuiteid(suiteId);
					suite.setTicket(suiteTicket);
					suite.setTimeStamp(timeStamp);
					suite.setUpdateTime(new Date());
					suiteService.renovateTick(suite);
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

	/**
	 * 测试微信企业号第三方应用回调协议
	 * @author:leap
	 * @MethodName: testWXSaaSCallback
	 * @Description:
	 * @param req
	 * @param res
	 * @date:2016-8-9
	 */
	@RequestMapping(value="testWXSaaSCallback")
	public void testWXSaaSCallback( HttpServletRequest req, ServletResponse res){
		System.out.println("aaa");
		//常量值
		String CORP_ID = "wx23ed1347520b93b8";    //CorpId, 企业号的普通管理组中可查看
		String SUITE_ID = "tja2312bedd5086xxx";    //套件ID，在套件信息中查看
		String SUITE_SECRET = "LGVT0BrN2DZ7VMTuIPOsudaWuXOE0iM67yHpIs1ofESS4l-jSC8LN9nOnmilhXXX";
		String SUITE_TOKEN = "DcQW4VTCtx";
		String SUITE_ENCODING_AES_KEY = "WQgpoE7F5qw5Fp4FFRBxIAd9vrJsrlAnCON3lxxiFuP";

		//获取参数
		String msgSignature = req.getParameter("msg_signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");    //创建套件时验证回调url时传入

		String result = "";
		try {
			if(StringUtils.isNotBlank(echostr)){    //=======验证回调url有效性=======
				WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(SUITE_TOKEN,
						SUITE_ENCODING_AES_KEY, CORP_ID);//注意是CORP_ID
				result = wxBizMsgCrypt.VerifyURL(msgSignature, timestamp, nonce, echostr);
				System.out.println(result);
				res.getWriter().write(result);    //对echostr参数解密并原样返回echostr明文(不能加引号，不能带bom头，不能带换行符)
			}else{
				//其他操作
				res.getWriter().write("false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addSuite(){
		
	}
	
	/*public static void main(String[] args) throws DocumentException {
		String encrpt = "02DPtirrzEWcdhAof6i6+301Jj79ulDp6R0vjT7dmXFY3BhPqgt53D4LJpL459Y27BXjHt+B8Xs0wW6DnKdIItT5kc1cgHfPzhK7R7ACtzM12NbngL3ijoJHjoDg7rfeZ7zEK60+x+HkYVhEmnEKUsyjD8WOAn+Du8ME6hM9h7D7viwjizEhcVnN83BK8CgAsSqhVNByXG6SDm7yoiMV8fRoxRNuDqLa5z5FByUsssHxmY/qaJSwdjC0rwHUz5deYnb7d0gfPjA7PBFdoMoI5PWYYQna57KXVBmCPfxM/HamxsXuU+oKhbhdJWWnP4zn/wmFf294hbeF/ReReMuWuw5xfgQzx7i9yZU5Mz/832g6Qv/WR7qoZvWDiKQlA2+u";
		 WXBizMsgCrypt pc;
	        try {
	            pc = new WXBizMsgCrypt(Constants.Weixin.suite_token,Constants.Weixin.suite_encodingAesKey, Constants.Weixin.suiteID);
	            String result = pc.decrypt(encrpt);
	            System.out.println(result);
	            ;
	            System.out.println( XMLUtil.xmlChangeMap(result).get("SuiteTicket"));
	        }catch (AesException e) {
				e.printStackTrace();
			}

	}*/

	public static void main(String[] args) throws ParseException {
		Long time = 1403610513L;
		    //        1498008999
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmsssss");
		Long newtime = date.getTime()-time;
		Date newdate = new Date(newtime);
		System.out.println(sdf.format(newdate));
	}
}
