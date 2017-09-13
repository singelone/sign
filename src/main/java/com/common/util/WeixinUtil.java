package com.common.util;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;   

/**
 * 微信套件开发接口工具类
 * 详情见：http://qydev.weixin.qq.com/wiki/index.php?title=第三方应用授权
 * @author
 *
 */
public class WeixinUtil {
	
	/**
	 * 获取应用套件令牌请求路径
	 */
	private static String get_suite_token = "https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token";
	
	/**
	 * 获取应用套件令牌
	 * @param suiteId
	 * @param suiteSecret
	 * @param suiteTicket
	 * @return {
	 * 			"suite_access_token":"xxx",
	 * 			"expires_in":7200
	 * 			}
	 */
	public static JSONObject getSuiteToken(String suiteId,String suiteSecret,String suiteTicket){
		Map<String, String> map = new HashMap<String,String>();
		map.put("suite_id", suiteId);
		map.put("suite_secret", suiteSecret);
		map.put("suite_ticket", suiteTicket);
		JSONObject jsonObject = HttpClientUtil.httpRequest(get_suite_token, "POST", JSON.toJSONString(map));
		return jsonObject;
	}
	
	/**
	 * 获取获取预授权码请求路径
	 */
	private static String get_pre_auth_code = "https://qyapi.weixin.qq.com/cgi-bin/service/get_pre_auth_code?suite_access_token=";
		
	/**
	 * 获取获取预授权码
	 * @param suiteId
	 * @param suiteToken
	 * @return {
	 * 			"errcode":0 ,
	 * 			"errmsg":"ok" ,
	 * 			"pre_auth_code":"xxxx",
	 * 			"expires_in":1200
	 * 			}
	 */
	public static JSONObject getPreAuthCode(String suiteId,String suiteToken){
		Map<String, String> map = new HashMap<String,String>();
		map.put("suite_id", suiteId);
		JSONObject jsonObject = HttpClientUtil.httpRequest(get_pre_auth_code+suiteToken, "POST", JSON.toJSONString(map));
		return jsonObject;
	}
			
	/**
	 * 获取获取预授权码请求路径
	 */
	private static String set_session_info = "https://qyapi.weixin.qq.com/cgi-bin/service/set_session_info?suite_access_token=";
			
	/**
	 * 获取获取预授权码
	 * @param preAuthCode
	 * @param appid
	 * @param authType
	 * @return {
	 * 			"errcode":0 ,
	 * 			"errmsg":"ok" ,
	 * 			}
	 */
	public static JSONObject setSessionInfo(String preAuthCode,Integer[] appids,Integer authType,String suiteToken){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("pre_auth_code", preAuthCode);
		Map<String, Object> sessionMap = new HashMap<String,Object>();
		sessionMap.put("appid", appids);
		sessionMap.put("auth_type", authType);
		map.put("session_info", sessionMap);
		
		JSONObject jsonObject = HttpClientUtil.httpRequest(set_session_info+suiteToken, "POST", JSON.toJSONString(map));
		return jsonObject;
	}	
    
	/**
	 * 获取企业号的永久授权码请求路径
	 */
	private static String get_permanent_code = "https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code?suite_access_token=";
				
	/**
	 * 获取企业号的永久授权码
	 * @param authCode
	 * @param suiteId
	 * @param suiteToken
	 * @return http://qydev.weixin.qq.com/wiki/index.php?title=第三方应用接口说明  》》》获取企业号的永久授权码
	 */
	public static JSONObject getPermanentCode(String authCode,String suiteId,String suiteToken){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("suite_id", suiteId);
		map.put("auth_code", authCode);
		JSONObject jsonObject = HttpClientUtil.httpRequest(get_permanent_code+suiteToken, "POST", JSON.toJSONString(map));
		return jsonObject;
	}
	
	/**
	 * 获取企业号的授权信息请求路径
	 */
	private static String get_auth_info = "https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info?suite_access_token=";
					
	/**
	 * 获取企业号的授权信息
	 * @param authCode
	 * @param suiteId
	 * @param suiteToken
	 * @return http://qydev.weixin.qq.com/wiki/index.php?title=第三方应用接口说明  》》》获取企业号的授权信息
	 */
	public static JSONObject getAuthInfo(String suiteId,String corpid,String permanentCode,String suiteToken){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("suite_id", suiteId);
		map.put("auth_corpid", corpid);
		map.put("permanent_code", permanentCode);
		JSONObject jsonObject = HttpClientUtil.httpRequest(get_auth_info+suiteToken, "POST", JSON.toJSONString(map));
		return jsonObject;
	}
	
	/**
	 * 获取企业号access_token请求路径
	 */
	private static String get_corp_token = "https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token?suite_access_token=";
						
	/**
	 * 获取企业号access_token
	 * @param authCode
	 * @param suiteId
	 * @param suiteToken
	 * @return {
	 * 			"access_token": "xxxxxx", 
	 * 			"expires_in": 7200, 
	 *			}
	 */
	public static JSONObject getCorpToken(String suiteId,String corpid,String permanentCode,String suiteToken){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("suite_id", suiteId);
		map.put("auth_corpid", corpid);
		map.put("permanent_code", permanentCode);
		JSONObject jsonObject = HttpClientUtil.httpRequest(get_corp_token+suiteToken, "POST", JSON.toJSONString(map));
		return jsonObject;
	}

	/**
	 * 很久code获取成员信息请求路径
	 */
	private static String get_user_msg = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE";

	/**
	 * 根据code获取当前登录用户信息
	 * @param accessToken
	 * @param code
	 * @return
	 */
	public static JSONObject getUserMsg(String accessToken, String code) {
		String url = get_user_msg.replace("ACCESS_TOKEN",accessToken).replace("CODE", code);
        JSONObject JsonUser = HttpClientUtil.httpRequest(url, "GET", null);
        return JsonUser;
    }

    private static String get_user_by_user_ticket = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN";
    /**
     * 根据user_ticket获取用户信息
     * @param accessToken
     * @param user_ticket
     * @return
     */
    public static JSONObject getUser(String accessToken, String user_ticket) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("user_ticket", user_ticket);
        String url = get_user_by_user_ticket.replace("ACCESS_TOKEN",accessToken);
        JSONObject jsonObject = HttpClientUtil.httpRequest(url, "POST", JSON.toJSONString(map));
        return jsonObject;
    }
	
	
    public static void main(String[] args) {
//    	JSONObject jsonObject =  WeixinUtil.getSuiteToken(Constants.Weixin.suiteID, Constants.Weixin.suite_secret, "svMHrh6W0PJFcnao92nJ8NH_aE-_g1DMgBNxFgK0EgCHIQ_TICNEwpPj9eqA_A3x");
//    	System.out.println(jsonObject.toJSONString());
//    	JSONObject jsonObject1 = WeixinUtil.getPreAuthCode(Constants.Weixin.suiteID,jsonObject.getString("suite_access_token"));
//    	System.out.println(jsonObject1.toJSONString());
//    	JSONObject jsonObject2 = WeixinUtil.setSessionInfo(jsonObject1.getString("pre_auth_code"),new Integer[]{1},1,jsonObject.getString("suite_access_token"));
//    	System.out.println(jsonObject2.toJSONString());
    	getCorpToken("tj7a9c298250c7a9a0", "ww0201f76d06ec7507", "oZbd9rjs4FVKQBeUhyHV-l4mI3npAKmuI_lw7TaORMQ", "0yugCpr1UI0NYmPj5wOpcsmjbzpYsGE7RZIvFk7jT1NdrvTGyU_SNa9vh6wpn3rJ");
    }

}
