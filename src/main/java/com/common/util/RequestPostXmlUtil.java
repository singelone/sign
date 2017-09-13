package com.common.util;

import com.weixin.suite.domain.ReceivedMsg;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析post请求发送XML参数工具类
 * @author
 *
 */
public class RequestPostXmlUtil {

	public static Map<String, String> getPostXmlMap(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		/*
		 * 该部分我们获取用户发送的信息，并且解析成<K,V>的形式进行显示
		 */
		// 解析用户发送过来的信息
    	InputStream is = null;
		try {
			is = request.getInputStream();
			// 拿取请求流
    		// 将解析结果存储在HashMap中
    		// 解析xml，将获取到的返回结果xml进行解析成我们习惯的文字信息
    		SAXReader reader = new SAXReader();// 第三方jar:dom4j
    		Document document = reader.read(is);
    		// 得到xml根元素
    		Element root = document.getRootElement();
    		// 得到根元素的所有子节点
    		List<Element> elementList = root.elements();
    		// 遍历所有子节点
    		for (Element e : elementList)
    		    map.put(e.getName(), e.getText());
    		
    		// 测试输出
    		Set<String> keySet = map.keySet();
    		// 测试输出解析后用户发过来的信息
    		System.out.println("解析用户发送过来的信息开始");
    		for (String key : keySet) {
    		    System.out.println(key + ":" + map.get(key));
    		}
		}catch (IOException e2) {
			e2.printStackTrace();
		}catch (DocumentException e1) {
		    e1.printStackTrace();
		}catch (Exception e) {    
            e.printStackTrace();    
        }finally {
			if(is!=null){
				try {
					is.close();
					return map;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
		
	}

	public static Map<String, String> getPostRequestXmlMap(HttpServletRequest request){
		Map<String, String> map = new HashMap<String, String>();
		InputStream is = null;
		try {
			is = request.getInputStream();
			// 拿取请求流
			// 将解析结果存储在HashMap中
			// 解析xml，将获取到的返回结果xml进行解析成我们习惯的文字信息
			//SAXReader reader = new SAXReader();// 第三方jar:dom4j
			LineNumberReader bufferedReader = new LineNumberReader(new InputStreamReader(is));

			StringBuilder sb = new StringBuilder();
			String xml ;
			while ((xml = bufferedReader.readLine()) != null) {
				sb.append(xml);
			}
			ReceivedMsg receivedMsg = WeixinReceiveXmlUtil.parseWeChatEventReceivedMsg(sb.toString());
			String[] fieldNames = WeixinReceiveXmlUtil.getFieldName(ReceivedMsg.class);
			for (String fieldName : fieldNames) {
				map.put(fieldName,receivedMsg.getName(fieldName));
			}
		}catch (IOException e2) {
			e2.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(is!=null){
				try {
					is.close();
					return map;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 安全模式解析消息体
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXMLCrypt(HttpServletRequest request)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		/**
		 * 第一步：从inputStream中读取XML文件
		 */
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// 没次读取的内容
		String line = null;
		// 最终读取的内容
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		is.close();

		/**
		 * 第二部：解密
		 */
		String msgSignature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String fromXML = DecryptAndEncryptUtil.decryptMsg(msgSignature, timestamp, nonce, sb.toString());

		/**
		 * 第三步，解析XML，获取请求参数
		 */
		// 通过IO得到Document
		Document doc = DocumentHelper.parseText(fromXML);
		// 得到跟节点
		Element root = doc.getRootElement();
		recursiveParseXml(root, map);
		return map;
	}

	@SuppressWarnings({ "unchecked"})
	private static void recursiveParseXml(Element root, Map<String, String> map) {
		List<Element> elementList = root.elements();
		if (elementList.size() == 0) {
			map.put(root.getName(),root.getTextTrim());
		} else {
			for (Element e : elementList) {
				recursiveParseXml(e, map);
			}
		}
	}
}
