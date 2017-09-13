package com.weixin.manage.web;

import com.alibaba.fastjson.JSONObject;
import com.common.util.Constants;
import com.common.util.RequestPostXmlUtil;
import com.common.util.WeixinUtil;
import com.common.util.XMLUtil;
import com.weixin.authorize.service.AuthorizeService;
import com.weixin.mp.aes.AesException;
import com.weixin.mp.aes.WXBizMsgCrypt;
import com.weixin.suite.service.SuiteService;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/manage/setTime/")
public class SetTimeController {
	
	@Resource
	private SuiteService suiteService;
	@Resource
	private AuthorizeService authorizeService;
	
	/**
	 * 解析事件推送消息
	 * 详情见：http://qydev.weixin.qq.com/wiki/index.php?title=接收事件
	 * @param request
	 * @param response
	 * @param msg_signature
	 * @param timestamp
	 * @param nonce
	 * @param modelMap
	 */
	@RequestMapping("/index")
	public void getList(HttpServletRequest request,HttpServletResponse response,
			String msg_signature,String timestamp,
			String nonce,ModelMap modelMap){
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
        // 去掉最后一个&
        queryString = queryString.substring(0, queryString.length() - 1);
        System.out.println("GET " + request.getRequestURL() + " " + queryString);
        

		WXBizMsgCrypt pc;
		try {
			Map<String, String> encyptMap = RequestPostXmlUtil.parseXMLCrypt(request);
			pc = new WXBizMsgCrypt(Constants.Weixin.suite_token,Constants.Weixin.suite_encodingAesKey, Constants.Weixin.corpID);
			String result = pc.decrypt(encyptMap.get("encrypt"));
			System.out.println(result);
			Map<String, String> decyptMap = XMLUtil.xmlChangeMap(result);
			String msgType = decyptMap.get("msgType");
			System.out.println("msgType=="+msgType);
			if("text".equals(msgType)){
				System.out.println(decyptMap.get("Content"));
			}else if("image".equals(msgType)){
			
				
			}else if("voice".equals(msgType)){
				
				
			}else if("shortvideo".equals(msgType)){
				
				
			}else if("location".equals(msgType)){
				System.out.println("Location_X="+decyptMap.get("Location_X"));
				System.out.println("Location_Y="+decyptMap.get("Location_Y"));
				System.out.println("Label="+decyptMap.get("Label"));
				
			}else if("link".equals(msgType)){
				System.out.println(decyptMap.get("PicUrl"));
				
			}else if("event".equals(msgType)){
				
				
			}
		    	
		} catch (AesException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@RequestMapping("/put")
	public String put(){
		System.out.println("设置签到签退时间");
		return "manage/setTime/put";
	}
	
	@RequestMapping("/del")
	public String del(Model model, String code){
		try {
			model.addAttribute("code", code);

			JSONObject suiteAccessToken = WeixinUtil.getSuiteToken("tj4761fca223421777", "kCmojwjyK3cXxEPqBKlbpGZqYqPKLosrgoeew-ZmbQM", "svMHrh6W0PJFcnao92nJ8KaivhFGQtz9b5XDKGMEUapbnq9CdnLpzM3muH4xanQt");
			String suite_access_token = suiteAccessToken.getString("suite_access_token");
			JSONObject corpToken = WeixinUtil.getCorpToken("tj4761fca223421777", "ww0201f76d06ec7507", "7SuQLkDrpfwuAmdyluj8LDdX9yfxEH62M13k1VnZ0S8", suite_access_token);
			String access_token = corpToken.getString("access_token");
			JSONObject userMsg = WeixinUtil.getUserMsg(access_token, code);
			System.out.println(userMsg.toJSONString());
			String user_ticket = userMsg.getString("user_ticket");
			JSONObject jsonUser = WeixinUtil.getUser(access_token, user_ticket);
			System.out.println(jsonUser.toJSONString()+"-------------");
			model.addAttribute("name", jsonUser.getString("name"));
			model.addAttribute("position", jsonUser.getString("position"));
			model.addAttribute("mobile", jsonUser.getString("mobile"));
			model.addAttribute("gender", jsonUser.getString("gender"));
			model.addAttribute("email", jsonUser.getString("email"));
			model.addAttribute("avatar", jsonUser.getString("avatar"));
			System.out.println("设置签到签退时间");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "manage/setTime/del";
	}

}
