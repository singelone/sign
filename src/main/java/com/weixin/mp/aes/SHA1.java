/**
 * 对企业微信发送给企业后台的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

package com.weixin.mp.aes;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * SHA1 class
 *
 * 计算消息签名接口.
 */
class SHA1 {

	/**
	 * 用SHA1算法生成安全签名
	 * @param token 票据
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @param encrypt 密文
	 * @return 安全签名
	 * @throws AesException 
	 */
	public static String getSHA1(String token, String timestamp, String nonce, String encrypt) throws AesException
			  {
		try {
			String[] array = new String[] { token, timestamp, nonce, encrypt };
			StringBuffer sb = new StringBuffer();
			// 字符串排序
			Arrays.sort(array);
			for (int i = 0; i < 4; i++) {
				sb.append(array[i]);
			}
			String str = sb.toString();
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.ComputeSignatureError);
		}
	}

	public static void main(String[] args) throws AesException {
        String s = Long.toString(System.currentTimeMillis());
        System.out.println(s);
        String encrypt = "jsapi_ticket=HoagFKDcsGMVCIY2vOjf9vTTRmrvoIz-xovw2HBcjDfDPE2_5cBoSnXepyGVnMrDNh-X0YQywVaD8zESUAEG-A&noncestr=fmz9uhqvenopqfr&timestamp="+s+"&url=http://tangqian.tunnel.qydev.com/index";
        System.out.println(encrypt);
        String fmz9uhqvenopqfr = getSHA1("LXshVKIlXwS6NNe3RSPHiyuJLJKcXdykkCIrHW9krLc24fRUTM1whmyI-o35CFvVa3eJ4cSw-HdEaMyas2lklKhlba8ww-gtAtATJ4Moy4NqZC1KmMJqKQFTOM6LQ6E9oqeeJTCScegtQRg9ikSwLMt-bm6uGX_DfnA495LnsR_JZZCDv1hElkb-9AJQ7hDp01Kblfcf0tJ6U-5cpdkNmpGwzBRwDR5OpgXJi0SgtExRiEoOx0LSir5tWlOSMg9A3oyl5DFDy1uURv1afAzBSET4GZVVZh9SZltM6Mrs2Go",
                s, "fmz9uhqvenopqfr", encrypt);
        System.out.println(fmz9uhqvenopqfr);
    }
}
