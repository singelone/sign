package com.common.util;

import com.weixin.mp.aes.AesException;
import com.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 微信加解密工具类
 * @author
 *
 */
public class DecryptAndEncryptUtil {
    
    
    
    public static String decrypt(String msgSignature, String timeStamp,String nonce,String echoStr){
    	WXBizMsgCrypt wxcpt;
		try {
			wxcpt = new WXBizMsgCrypt(Constants.Weixin.address_suite_token, Constants.Weixin.address_suite_encodingAesKey, Constants.Weixin.OACorpID);
			return wxcpt.VerifyURL(msgSignature, timeStamp, nonce, echoStr);
		} catch (AesException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    
    /**
     * 解密微信发过来的密文
     * 
     * @return 加密后的内容
     */
    public static String decryptMsg(String msgSignature,String timeStamp,String nonce,String encrypt_msg) {
        WXBizMsgCrypt pc;
        String result ="";
        try {
            pc = new WXBizMsgCrypt(Constants.Weixin.suite_token, Constants.Weixin.suite_encodingAesKey, Constants.Weixin.corpID);
            result = pc.DecryptMsg(msgSignature, timeStamp, nonce, encrypt_msg);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加密给微信的消息内容
     * @param replayMsg
     * @param timeStamp
     * @param nonce
     * @return
     */
    public static String ecryptMsg(String replayMsg,String timeStamp, String nonce) {
        WXBizMsgCrypt pc;
        String result ="";
        try {
            pc = new WXBizMsgCrypt(Constants.Weixin.suite_token, Constants.Weixin.suite_encodingAesKey, Constants.Weixin.corpID);
            result = pc.EncryptMsg(replayMsg, timeStamp, nonce);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return result;
    }
}
