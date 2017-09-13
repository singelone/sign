package com.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.suite.domain.Button;
import com.weixin.suite.domain.CommonButton;
import com.weixin.suite.domain.ComplexButton;
import com.weixin.suite.domain.Menu;
import com.weixin.suite.domain.ViewButton;

/**
 * 微信菜单工具类
 * 详情见：http://qydev.weixin.qq.com/wiki/index.php?title=创建应用菜单
 * @author
 *
 */
public class MenuUtil {

	
	/** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    public static Menu getMenu() {  
        CommonButton btn11 = new CommonButton();  
        btn11.setName("个人信息查看");  
        btn11.setType("click");  
        btn11.setKey("stuInfoView");  
  
        CommonButton btn12 = new CommonButton();  
        btn12.setName("个人信息修改");  
        btn12.setType("click");  
        btn12.setKey("stuInfoEdit"); 
  
        CommonButton btn21 = new CommonButton();  
        btn21.setName("行程查看");  
        btn21.setType("click");  
        btn21.setKey("stuTravelView");  
  
        ViewButton btn22 = new ViewButton();  
        btn22.setName("百度一下");  
        btn22.setType("view");  
        btn22.setUrl("http://www.baidu.com/");
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("行程修改");  
        btn23.setType("click");  
        btn23.setKey("stuTravelEdit");  
  
        CommonButton btn31 = new CommonButton();  
        btn31.setName("操作说明");  
        btn31.setType("click");  
        btn31.setKey("help");  
  
        CommonButton btn32 = new CommonButton();  
        btn32.setName("呼叫管理员");  
        btn32.setType("click");  
        btn32.setKey("callAdmin");  
  
        CommonButton btn33 = new CommonButton();  
        btn33.setName("意见反馈");  
        btn33.setType("click");  
        btn33.setKey("suggestions");  
  
        ComplexButton mainBtn1 = new ComplexButton();  
        mainBtn1.setName("个人信息");  
        mainBtn1.setSub_button(new Button[] { btn11, btn12});  
  
        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("行程");  
        mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23});  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("帮助");  
        mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });  
  
        /** 
         * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br> 
         *  
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br> 
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br> 
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 }); 
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });  
  
        return menu;  
    }  
    
    /**
     * 企业号创建菜单请求地址
     * http://qydev.weixin.qq.com/wiki/index.php?title=创建应用菜单
     */
    private static String menuUrl = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN&agentid=AGENTID";
    
    /**
     * 企业号创建菜单请求地址
     */
    public static boolean createMenuBySuiteAgent(String accessToken,String agentId,Menu menu){
    	String url = menuUrl.replace("ACCESS_TOKEN", accessToken);
    	url = url.replace("AGENTID", agentId);
    	JSONObject jv = HttpClientUtil.httpRequest(url, "POST", JSON.toJSONString(menu));
    	String errmsg = jv.getString("errmsg");
    	if("ok".equals(errmsg)){
    		return true;
    	}
    	return false;
    }
    
    public static void main(String[] args) {
    	JSONObject json = WeixinUtil.getCorpToken("tj4761fca223421777", "ww0201f76d06ec7507", "7SuQLkDrpfwuAmdyluj8LDdX9yfxEH62M13k1VnZ0S8", "DcQW4VTCtx");
    	String token = json.getString("access_token");
    	System.out.println(createMenuBySuiteAgent(token, "1000028", getMenu()));
    	
	}
}
