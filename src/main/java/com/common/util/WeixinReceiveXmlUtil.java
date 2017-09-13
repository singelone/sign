package com.common.util;

import com.common.util.weixin.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.weixin.suite.domain.ReceivedMsg;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/9.
 */
public class WeixinReceiveXmlUtil {

    public static void main(String[] args) {
        String wxMsgXml = "<xml><ToUserName><![CDATA[tj4761fca223421777]]></ToUserName><Encrypt><![CDATA[PpcyiliTlxpDLUXDROW+Hv8GIZpQzLttl6jGOX6m6Z76+SQafYp0Fonm+wzOXRJmSHqhWTgMub4eCC0cYBitM0hWa3psKyN6GPO++HOKfRoY4uuUW90bCc0zzuWVfQX8FrgWBANcrU1k6MjeyfURdVOebSpfUdkoqW0P/ABYM2qFZWeT6rSKSqXwyMRbZsIOraBfVwCA5bj31khEVmzrcvfPFQIPiSNWdCnYx/xE3jm6b+fOwnztlKIHhIi6vxUviDEVPBaaWX8+PXBF7HGnXc6xmjy69C6S3uRnZiWVhsNsPNjXD9CKtuZEtiaj4jATlgrQLtZ6FGLS9kvIFBQKZUeW4VpUbsnGKycJ09B03AnzH5pNq1JlzgwSGVokP]]></Encrypt><AgentID><![CDATA[]]></AgentID></xml>";
		 ReceivedMsg eventReceivedMsg= WeixinReceiveXmlUtil.parseWeChatEventReceivedMsg(wxMsgXml);
         System.out.println(parseWeChatEventReceivedMsg(wxMsgXml).getToUserName());
		 System.out.println(eventReceivedMsg.getAgentID());
    }

    @SuppressWarnings("rawtypes")
    public static ReceivedMsg parseWeChatEventReceivedMsg(String xml) {
        Class clazz = ReceivedMsg.class;
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", clazz);
        String[] fields = getFieldName(clazz);
        for (String field : fields) {
            String alias = field.substring(0, 1).toUpperCase()
                    + field.substring(1);
            xstream.aliasField(alias, clazz, field);
        }
        return (ReceivedMsg) xstream.fromXML(xml);

    }

    @SuppressWarnings("rawtypes")
    public static String[] getFieldName(Class clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            if (clazz.getSuperclass() != null) {
                fields = (Field[]) ArrayUtils.addAll(fields, clazz
                        .getSuperclass().getDeclaredFields());
            }

            String[] fieldNames = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                fieldNames[i] = fields[i].getName();
            }
            return fieldNames;
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return null;
    }

    public static void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 处理接收消息
        ServletInputStream in = request.getInputStream();
        // 将POST流转换为XStream对象
        XStream xs = SerializeXmlUtil.createXstream();
        xs.processAnnotations(InputMessage.class);
        xs.processAnnotations(OutputMessage.class);
        // 将指定节点下的xml节点数据映射为对象
        xs.alias("xml", InputMessage.class);
        // 将流转换为字符串
        StringBuilder xmlMsg = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            xmlMsg.append(new String(b, 0, n, "UTF-8"));
        }
        // 将xml内容转换为InputMessage对象
        InputMessage inputMsg = (InputMessage) xs.fromXML(xmlMsg.toString());

        String servername = inputMsg.getToUserName();// 服务端
        String custermname = inputMsg.getFromUserName();// 客户端
        long createTime = inputMsg.getCreateTime();// 接收时间
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间

        // 取得消息类型
        String msgType = inputMsg.getMsgType();
        // 根据消息类型获取对应的消息内容
        if (msgType.equals(MsgType.Text.toString())) {
            // 文本消息
            System.out.println("开发者微信号：" + inputMsg.getToUserName());
            System.out.println("发送方帐号：" + inputMsg.getFromUserName());
            System.out.println("消息创建时间：" + inputMsg.getCreateTime() + new Date(createTime * 1000l));
            System.out.println("消息内容：" + inputMsg.getContent());
            System.out.println("消息Id：" + inputMsg.getMsgId());

            StringBuffer str = new StringBuffer();
            str.append("<xml>");
            str.append("<ToUserName><![CDATA[" + custermname + "]]></ToUserName>");
            str.append("<FromUserName><![CDATA[" + servername + "]]></FromUserName>");
            str.append("<CreateTime>" + returnTime + "</CreateTime>");
            str.append("<MsgType><![CDATA[" + msgType + "]]></MsgType>");
            str.append("<Content><![CDATA[你说的是：" + inputMsg.getContent() + "，吗？]]></Content>");
            str.append("</xml>");
            System.out.println(str.toString());
            response.getWriter().write(str.toString());
        }
        // 获取并返回多图片消息
        if (msgType.equals(MsgType.Image.toString())) {
            System.out.println("获取多媒体信息");
            System.out.println("多媒体文件id：" + inputMsg.getMediaId());
            System.out.println("图片链接：" + inputMsg.getPicUrl());
            System.out.println("消息id，64位整型：" + inputMsg.getMsgId());

            OutputMessage outputMsg = new OutputMessage();
            outputMsg.setFromUserName(servername);
            outputMsg.setToUserName(custermname);
            outputMsg.setCreateTime(returnTime);
            outputMsg.setMsgType(msgType);
            ImageMessage images = new ImageMessage();
            images.setMediaId(inputMsg.getMediaId());
            outputMsg.setImage(images);
            System.out.println("xml转换：/n" + xs.toXML(outputMsg));
            response.getWriter().write(xs.toXML(outputMsg));

        }
    }


}
