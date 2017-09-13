package com.weixin.suite;

import com.alibaba.fastjson.JSONObject;
import com.common.util.WeixinUtil;
import com.weixin.suite.domain.AccessToken;

public class TokenService {
	
	/**
	 * 
	 * @param requestUrl 请求获取token路径
	 * @param id 公众号ID|企业ID|套件ID
	 * @param secret 私钥
	 * @return
	 */
	public String getToken(String requestUrl,String id,String secret){
//		AccessToken at = WeixinUtil.getAccessToken(WeixinUtil.corpID,WeixinUtil.corpSecret);  
//		String requestUrl = access_token_url.replace("APPID", id).replace("APPSECRET", appsecret);  
//	    JSONObject jsonObject = httpRequest(requestUrl, "GET", null);  
//	    // 如果请求成功  
//	    if (null != jsonObject) {  
//	        try {  
//	            accessToken = new AccessToken();  
//	            accessToken.setToken(jsonObject.getString("access_token"));  
//	            accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));  
//	        } catch (Exception e) {  
//	            accessToken = null;  
//	            // 获取token失败  
//	            System.out.println("获取token失败 errcode:"+jsonObject.getIntValue("errcode")+"errmsg:"+jsonObject.getString("errmsg"));
////	            log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
//	        }  
//	    }  
//	    return accessToken;  
		return null;
	}

}
