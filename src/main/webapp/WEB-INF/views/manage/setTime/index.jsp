<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="这是主页">

      <script type="text/javascript" src="<c:url value="http://res.wx.qq.com/open/js/jweixin-1.2.0.js" ></c:url>"></script>
      <script>
          var signUrl = "http://work.weixin.qq.com/api/jsapidemo";
          var ticket = "";

          /*
           * 注意：
           * 所有的JS接口只能在应用配置的安全域名下面使用。
           *
           */
          wx.config({
              debug: false,
              appId: 'ww0201f76d06ec7507',
              timestamp: 1502439508380,
              nonceStr: 'fmz9uhqvenopqfr',
              signature: '4a9c50348279164887e2f6febe313ff9c98a2b96',
              jsApiList: [
                  'checkJsApi',
                  'onMenuShareAppMessage',
                  'onMenuShareWechat',
                  'startRecord',
                  'stopRecord',
                  'onVoiceRecordEnd',
                  'playVoice',
                  'pauseVoice',
                  'stopVoice',
                  'uploadVoice',
                  'downloadVoice',
                  'chooseImage',
                  'previewImage',
                  'uploadImage',
                  'downloadImage',
                  'getNetworkType',
                  'openLocation',
                  'getLocation',
                  'hideOptionMenu',
                  'showOptionMenu',
                  'hideMenuItems',
                  'showMenuItems',
                  'hideAllNonBaseMenuItem',
                  'showAllNonBaseMenuItem',
                  'closeWindow',
                  'scanQRCode'
              ]
          });

          wx.ready(function() {
              wx.showAllNonBaseMenuItem();
              // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

          });

          wx.error(function(a) {
//              alert(a.errMsg);
          });
      </script>
  </head>
  
  <body>
    这是首页<br>
    <h1><a href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww0201f76d06ec7507&redirect_uri=http://tangqian.tunnel.qydev.com/manage/setTime/del&response_type=code&scope=snsapi_privateinfo&agentid=1000036&state=1#wechat_redirect">测试授权获取code</a></h1>
  </body>
</html>

