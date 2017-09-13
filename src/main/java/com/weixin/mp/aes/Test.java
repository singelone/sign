package com.weixin.mp.aes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.HttpClientUtil;
import com.weixin.sign.domain.Department;
import com.weixin.sign.domain.Sign;

public class Test {
	
	public static void main(String[] args) throws ParseException {
//		String tokenurl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww0201f76d06ec7507&corpsecret=AUkUZhZocZ7undTuiuHGeXGYjrbGcd5ZsokMn5Qa2aE";
		String tokenurl = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wx23ed1347520b93b8&corpsecret=Qo2Fvd9XBNdKVwZ0NRPh5Ab0rji7mNktcfACNCRh8YPi_gD0m17i3leOYwmhqCu4";
		JSONObject tokenJson = HttpClientUtil.httpRequest(tokenurl, "GET",null);
		if(0!=tokenJson.getIntValue("errcode")){
			return;
		}
		String deptUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+tokenJson.getString("access_token")+"&id=";
		JSONObject deptJson = HttpClientUtil.httpRequest(deptUrl, "GET", null);
		if(!"ok".equals(deptJson.getString("errmsg"))){
			return;
		}
		Integer deptId = 0;
		JSONArray deptArray = deptJson.getJSONArray("department");
		//List<Map<String, Object>> deptList = new ArrayList<Map<String, Object>>();
		List<Department> deptList = new ArrayList<Department>();
		for(int i =0;i<deptArray.size();i++){
			JSONObject object = deptArray.getJSONObject(i);
			Department departments = JSON.toJavaObject(object, Department.class);
			System.out.println(departments.toString());
			deptList.add(departments);
//			Map<String, Object> deptMap = new HashMap<String, Object>();
//			deptMap.put("id", object.getInteger("id"));
//			deptMap.put("name", object.getString("name"));
			Integer parentId = object.getInteger("parentid");
//			deptMap.put("parentid", parentId);
//			deptMap.put("order", object.getInteger("order"));
//			deptList.add(deptMap);
			if(parentId==0){
				deptId = object.getInteger("id");
			}
			//System.out.println("id="+deptMap.get("id")+"|||||name="+deptMap.get("name")+"|||||parentid="+deptMap.get("parentid")+"|||||order="+deptMap.get("order"));
		}
		
		if(deptId == 0 ){
			return;
		}
		
		/**
		 * 获取部门成员详情
		 * 请求方式：GET（HTTPS）
		 * fetch_child	1/0：是否递归获取子部门下面的成员
		 * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD
		 */
		String deptUserUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token="+tokenJson.getString("access_token")+"&department_id="+deptId+"&fetch_child=1";
		JSONObject userJson = HttpClientUtil.httpRequest(deptUserUrl, "GET", null);
		if(!"ok".equals(userJson.getString("errmsg"))){
			return;
		}
		JSONArray userArray = userJson.getJSONArray("userlist");
		List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
		
//		List<User> userList = new ArrayList<User>();
		String[] useridArr = new String[userArray.size()];
		for(int i =0;i<userArray.size();i++){
			JSONObject object = userArray.getJSONObject(i);
//			User user = JSON.toJavaObject(object, User.class);
//			User user = new User();
			
			Map<String, Object> userMap = new HashMap<String, Object>();
//			System.out.println(user.toString());
			userMap.put("userid", object.getString("userid"));
			useridArr[i] =  object.getString("userid");
			
			userMap.put("name", object.getString("name"));
			JSONArray department = object.getJSONArray("department");
			userMap.put("department", department.get(department.size()-1));
			userMap.put("position", object.getString("position"));
			userMap.put("mobile", object.getString("mobile"));
			userMap.put("gender", object.getString("gender"));
			userMap.put("email", object.getString("email"));
			userMap.put("isleader", object.getInteger("isleader"));
			userMap.put("avatar", object.getString("avatar"));
			userMap.put("telephone", object.getString("telephone"));
			userMap.put("english_name", object.getString("english_name"));
			userMap.put("enable", object.getInteger("enable"));
			userMap.put("status", object.getInteger("status"));
			userMap.put("wxplugin_status", object.getString("wxplugin_status"));
			userMap.put("extattr", object.getString("extattr"));
//			userList.add(user);
			userList.add(userMap);
			System.out.print("name="+userMap.get("name")+"&department="+userMap.get("department")+"&mobile="+userMap.get("mobile"));
			System.out.println("&enable="+userMap.get("enable")+"&status="+userMap.get("status"));
		}
		
		String souqurl = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token="+tokenJson.getString("access_token"); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date startdate = sdf.parse("2017-06-01 00:00:00");
    	Date enddate = sdf.parse("2017-07-01 00:00:00");
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("opencheckindatatype", 3);
		Long start = startdate.getTime()/1000L;
		Long end = enddate.getTime()/1000L;
		map.put("starttime", start);
		map.put("endtime", end);
		map.put("useridlist", useridArr);
		JSONObject jsonObject1 = HttpClientUtil.httpRequest(souqurl, "POST", JSON.toJSONString(map));
		String errmsg = jsonObject1.getString("errmsg");
		List<Sign> SignList =new ArrayList<Sign>();
		if("ok".equals(errmsg)){
			JSONArray jsonArr = jsonObject1.getJSONArray("checkindata");
			for(int i =0 ;i<jsonArr.size();i++){
				JSONObject json = jsonArr.getJSONObject(i);
				SignList.add(JSON.toJavaObject(json, Sign.class));
			}
		}
		
		for(Sign userInfo : SignList){
			System.out.println(userInfo.toString());
		}
	}
}
