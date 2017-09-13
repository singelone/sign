package com.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.weixin.sign.domain.Department;
import com.weixin.sign.domain.Sign;
import com.weixin.sign.domain.User;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * OA企业号接口开发 打卡
 * 详情见:https://work.weixin.qq.com/api/doc
 * @author yangjing
 *
 */
public class OASignUtil {

	/**
	 * 企业号获取token请求地址
	 * 详情见：https://work.weixin.qq.com/api/doc#10013/第三步：获取access_token
	 */
	private static String gettoken = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+Constants.Weixin.OACorpID+"&corpsecret="+Constants.Weixin.OAcorpSecret;

	/**
	 * 获取OA企业号accessToken信息
	 * @return
	 */
	public static String getAccessToken(){
		JSONObject tokenJson = HttpClientUtil.httpRequest(gettoken, "GET",null);
		if(0!=tokenJson.getIntValue("errcode")){
			return null;
		}
		return tokenJson.getString("access_token");
	}

	/**
	 * 获取部门信息请求路径
	 */
	private static String deptUrl = "https://qyapi.weixin.qq.com/cgi-bin/department/list?id=&access_token=";
	/**
	 * 获取OA企业号部门信息
	 * 详情见：https://work.weixin.qq.com/api/doc#10093
	 * @return
	 */
	public static List<Department> getDepartmentList(){
		List<Department> deptList = new ArrayList<Department>();
		String token = getAccessToken();
		if(null==token){
			return deptList;
		}
		JSONObject deptJson = HttpClientUtil.httpRequest(deptUrl+token, "GET", null);
		if(!"ok".equals(deptJson.getString("errmsg"))){
			return deptList;
		}
		JSONArray deptArray = deptJson.getJSONArray("department");
		for(int i =0;i<deptArray.size();i++){
			JSONObject object = deptArray.getJSONObject(i);
			Department departments = JSON.toJavaObject(object, Department.class);
			System.out.println(departments.toString());
			deptList.add(departments);
		}
		return deptList;
	}

	/**
	 * 获取部门成员详情
	 * 详情见:https://work.weixin.qq.com/api/doc#10063
	 * 请求方式：GET（HTTPS）
	 * fetch_child	1/0：是否递归获取子部门下面的成员
	 * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID&fetch_child=FETCH_CHILD
	 */
	private static String deptUserUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=ACCESS_TOKEN&department_id=DEMPID&fetch_child=1";

	/**
	 * 获取所有员工信息
	 * @param deptid
	 * @return
	 */
	public static List<User> getUserList(String deptid){
		List<User> userList = new ArrayList<User>();
		String token = getAccessToken();
		if(null==token){
			return userList;
		}
		String url = deptUserUrl.replace("ACCESS_TOKEN", token);
		url = url.replace("DEMPID", deptid);
		JSONObject userJson = HttpClientUtil.httpRequest(url, "GET", null);
		if(!"ok".equals(userJson.getString("errmsg"))){
			return userList;
		}
		JSONArray userArray = userJson.getJSONArray("userlist");
		for(int i =0;i<userArray.size();i++){
			JSONObject object = userArray.getJSONObject(i);
			User user = JSON.toJavaObject(object, User.class);
			System.out.println(user.toString());
			userList.add(user);
		}
		System.out.println(userList.size());
		return userList;
	}

	/**
	 * 签到信息请求路径
	 */
	private static String souqurl = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=";
	/**
	 * 获取所有员工打卡信息
	 * 详情见:https://work.weixin.qq.com/api/doc#11196
	 * @param start 获取打卡记录的开始时间。UTC时间戳
	 * @param end 获取打卡记录的结束时间。UTC时间戳
	 * @param userIdList 员工id集合
	 * @param opencheckindatatype 打卡类型。1：上下班打卡；2：外出打卡；3：全部打卡
	 * @return
	 */
	public static List<Sign> getSignList(Date start,Date end,List<String> userIdList,Integer opencheckindatatype){
		List<Sign> SignList =new ArrayList<Sign>();
		String token = getAccessToken();
		if (null == token) {
			return SignList;
		}
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("opencheckindatatype", opencheckindatatype);
		map.put("starttime", start.getTime()/1000);
		map.put("endtime", end.getTime()/1000);
		map.put("useridlist", userIdList);
		JSONObject jsonObject1 = HttpClientUtil.httpRequest(souqurl+token, "POST", JSON.toJSONString(map));
		String errmsg = jsonObject1.getString("errmsg");
		if("ok".equals(errmsg)){
			JSONArray jsonArr = jsonObject1.getJSONArray("checkindata");
			for(int i =0 ;i<jsonArr.size();i++){
				JSONObject json = jsonArr.getJSONObject(i);
				Sign sign = JSON.toJavaObject(json, Sign.class);
				SignList.add(sign);
				System.out.println(sign.toString());
			}
		}
		return SignList;
	}

	public static void getSignList() throws ParseException {
//		List<Department> departmentList = getDepartmentList();
		/*Department department = new Department();
		department.setId(468);
		List<Department> departmentList = new ArrayList<>();
		departmentList.add(department);
		for (Department d : departmentList) {
			Integer deptid = d.getId();
			List<User> userList = getUserList(deptid+"");
			List<String> userIdList = new ArrayList<>();
			for (User u : userList) {
				userIdList.add(u.getUserid());
			}*/
		List<String> userIdList = new ArrayList<>();
			userIdList.add("791445086243474336");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startdate = sdf.parse("2017-08-12 00:00:00");
			Date enddate = sdf.parse("2017-08-13 04:00:01");
			UTCUtil utcUtil = new UTCUtil();
			List<Sign> signList = getSignList(startdate, enddate, userIdList, 1);


//		}
//
	}

	private  String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=ww0201f76d06ec7507&redirect_uri=https%3A%2F%2F192.168.6.100:8080&response_type=code&scope=snsapi_privateinfo&agentid=3010011&state=123456#wechat_redirect";

	public static int daysBetween(String smdate,String bdate) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	public static void main(String[] args) throws ParseException, UnsupportedEncodingException {
//		getDepartmentList();
//		getUserList("");
		getSignList();
		/*Long time = 1499421600L * 1000+8*60*60*1000L;
		System.out.println(new Date(time));*/

		/*String encode = URLEncoder.encode("tangqian.tunnel.qydev.com", "utf-8");
		System.out.println(encode+"   -------------");*/
//		System.out.println(new Date(1503448607*1000L));
		/*DateTime dateTime1 = new DateTime("2017-08-25");
		DateTime dateTime2 = new DateTime("2017-08-27");
		System.out.println(dateTime2.compareTo(dateTime1));
		System.out.println(dateTime1.isBefore(dateTime2));

		String s = "2017-08-23";
		System.out.println(s.substring(9));

		System.out.println(daysBetween("2017-08-25", "2018-09-25"));*/

	}

}
