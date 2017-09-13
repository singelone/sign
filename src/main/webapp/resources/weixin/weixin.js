/**
 * Created by Administrator on 2017/8/10.
 */


    wx.ready(function() {
        wx.showAllNonBaseMenuItem();
        // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
        $.get('https://qyapi.weixin.qq.com/cgi-bin/gettoken',{"corpid":'',"corpsecret":''},function () {
            
        });
    });

    wx.error(function(a) {
        alert(a.errMsg)
    });
