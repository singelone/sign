package com.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml转换工具类
 * @author yangjing
 *
 */
public class XMLUtil {

	/**
	 * 用于判断是否有子节点,若有就将子节点也进行拼接,若无则返回""
	 * @param element
	 * @return
	 * @throws DocumentException
	 */
	public static String checkChildEle(Element element) throws DocumentException{
		String json="";
		List<Element> list = new ArrayList<Element>();
			 list=element.elements();
		if (list.size()>0) {
			for (Element ele : list) {
				json+=ele.getName()+":"+ele.getText()+","+"\r\n"+checkChildEle(ele);
			}
		}
		return json;
	}
	
	/**
	 * 这个方法是将xml字符串转成Json
	 * @param XML
	 * @return
	 * @throws DocumentException
	 */
	public static String xmlChangeJson(String XML) throws DocumentException{
		Document document=DocumentHelper.parseText(XML);
		Element root=document.getRootElement();
		Iterator it=root.elementIterator();
		String json="{";
		while (it.hasNext()) {
			Element element =(Element)it.next();
			String j=checkChildEle(element);
			if (j=="") {
				json+=element.getName()+":"+element.getText()+","+"\r\n";
			}else {
				json+=j;
			}
		}
		json+="}";
		return json;
	}
	
	/**
	 * 这个方法是将xml文件转成Json
	 * @param XML
	 * @return
	 * @throws DocumentException
	 */
	public static String xmlChangeJson(File XML) throws DocumentException{
		SAXReader reader=new SAXReader();
		Document document=reader.read(XML);
		Element root=document.getRootElement();
		Iterator it=root.elementIterator();
		String json="{";
		while (it.hasNext()) {
			Element element =(Element)it.next();
			String j=checkChildEle(element);
			if (j=="") {
				json+=element.getName()+":"+element.getText()+","+"\r\n";
			}else {
				json+=j;
			}
		}
		json+="}";
		return json;
	}
		
	/**
	 * 这个方法是将xml文件转成Map
	 * @param XML
	 * @return map
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlChangeMap(String XML) throws DocumentException{
		Document document=DocumentHelper.parseText(XML);
		Element root=document.getRootElement();
		Iterator it=root.elementIterator();
		Map<String, String> map = new HashMap<String, String>();
		while (it.hasNext()) {
			Element element =(Element)it.next();
			map.put(element.getName(), element.getText());
						
		}
		return map;
	}
}
