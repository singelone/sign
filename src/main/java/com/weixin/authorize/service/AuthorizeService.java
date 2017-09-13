package com.weixin.authorize.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.util.Constants;
import com.common.util.WeixinUtil;
import com.weixin.authorize.dao.AuthorizeDao;
import com.weixin.authorize.domain.Authorize;
import com.weixin.suite.dao.*;
import com.weixin.suite.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorizeService {

	@Resource
	private AuthorizeDao authorizeDao;
	
	@Resource
	private AccessTokenDao accessTokenDao;
	
	@Resource
	private SuiteDao suiteDao;
	
	@Resource
	private CorpDao corpDao;
	
	@Resource
	private AgentDao agentDao;
	
	@Resource
	private PrivilegeDao privilegeDao;

	/**
	 * 授权成功保存企业信息
	 * @param suiteId
	 * @param authCode
	 * @param timeStamp
	 */
	@Transactional
	public void save(String suiteId,String authCode, String timeStamp) {
		//查询套件token
		List<AccessToken> list = accessTokenDao.getList(suiteId);
		Date date = new Date();
		Long now = date.getTime();
		String tokenStr = "";
		if(list.size()==1){//已有token值时
			AccessToken token = list.get(0);
			Long tokenTime = token.getOverdueDate().getTime();
			tokenStr = token.getToken();
			//判断套件token值是否过期
			if(tokenTime<=now){//已过期 重新获取套件token值
				Suite suite = new Suite();
				suite.setSuiteid(suiteId);
				//获取套件Ticket
				List<Suite> suiteList = suiteDao.getSuiteList(suite);
				if(suiteList.size()==1){//套件信息微信服务器每间隔10分钟推送一次，理论上数据库绝对有suite的信息
					Suite oleSuite = suiteList.get(0);
					//调用微信接口获取token值，并且更新数据库
					JSONObject jsonObject = WeixinUtil.getSuiteToken(suiteId, Constants.Weixin.suite_secret, oleSuite.getTicket());
					tokenStr = jsonObject.getString("suite_access_token");
					Integer expiresIn = jsonObject.getInteger("expires_in");
					token.setToken(tokenStr);
					token.setExpiresIn(expiresIn);
					Long time = now + expiresIn*1000L;
					token.setOverdueDate(new Date(time));
					accessTokenDao.put(token);
				}
			}
		}else{//数据库未保存token
			Suite suite = new Suite();
			suite.setSuiteid(suiteId);
			List<Suite> suiteList = suiteDao.getSuiteList(suite);
			if(suiteList.size()==1){//套件信息微信服务器每间隔10分钟推送一次，理论上数据库绝对有suite的信息
				Suite oleSuite = suiteList.get(0);
				//调用微信接口获取token值，并且保存到数据库
				JSONObject jsonObject = WeixinUtil.getSuiteToken(suiteId, Constants.Weixin.suite_secret, oleSuite.getTicket());
				tokenStr = jsonObject.getString("suite_access_token");
				Integer expiresIn = jsonObject.getInteger("expires_in");
				UUID uuid = UUID.randomUUID();
				AccessToken accessToken = new AccessToken();
				accessToken.setToken(tokenStr);
				accessToken.setExpiresIn(expiresIn);
				accessToken.setId(uuid.toString());
				accessToken.setSuiteId(suiteId);
				Long time = now + expiresIn*1000L;
				accessToken.setOverdueDate(new Date(time));
				accessTokenDao.post(accessToken);
			}
			
		}
		//获取授权企业信息
		JSONObject jsonObject = WeixinUtil.getPermanentCode(authCode, suiteId,tokenStr);
		JSONObject corpInfo = jsonObject.getJSONObject("auth_corp_info");
		UUID corpid = UUID.randomUUID();
		Corp corp = new Corp();
		corp.setId(corpid.toString());
		corp.setCorpid(corpInfo.getString("corpid"));
		corp.setCorp_name(corpInfo.getString("corp_name"));
		corp.setCorp_type(corpInfo.getString("corp_type"));
		corp.setCorp_round_logo_url(corpInfo.getString("corp_round_logo_url"));
		corp.setCorp_square_logo_url(corpInfo.getString("corp_square_logo_url"));
		corp.setCorp_user_max(corpInfo.getLong("corp_user_max"));
		corp.setCorp_agent_max(corpInfo.getLong("corp_agent_max"));
		corp.setCorp_full_name(corpInfo.getString("corp_full_name"));
		corp.setVerified_end_time(corpInfo.getLong("verified_end_time"));
		corp.setSubject_type(corpInfo.getInteger("subject_type"));
		corp.setCorp_wxqrcode(corpInfo.getString("corp_wxqrcode"));
		JSONObject userInfo = jsonObject.getJSONObject("auth_user_info");
		corp.setManager_email(userInfo.getString("email"));
		corp.setManager_mobile(userInfo.getString("mobile"));
		corp.setManager_userid(userInfo.getString("userid"));
		corpDao.post(corp);
		
		JSONObject agentInfo = jsonObject.getJSONObject("auth_info");
		JSONArray agentArr = agentInfo.getJSONArray("agent");
		//解析授权企业应用信息
		for(int i = 0;i<agentArr.size();i++){
			JSONObject agentJson = agentArr.getJSONObject(i);
			UUID agentid = UUID.randomUUID();
			Agent agent = new Agent(); 
			agent.setId(agentid.toString());
			agent.setAgentid(agentJson.getInteger("agentid"));
			agent.setName(agentJson.getString("name"));
			agent.setRound_logo_url(agentJson.getString("round_logo_url"));
			agent.setSquare_logo_url(agentJson.getString("square_logo_url"));
			agent.setAppid(agentJson.getInteger("appid"));
			agent.setCorpId(corpid.toString());
			agentDao.post(agent);
			JSONObject privilegeJson = agentJson.getJSONObject("privilege");
			if(null!=privilegeJson){//应用权限
				UUID privilegeid = UUID.randomUUID();
				Privilege privilege = new Privilege();
				privilege.setAgentId(agentid.toString());
				privilege.setId(privilegeid.toString());
				privilege.setLevel(privilegeJson.getInteger("level")+"");
				JSONArray partyArr = privilegeJson.getJSONArray("allow_party");
				String allow_party = "";
				for(Object ine: partyArr){
					allow_party = allow_party + ine +",";
				}
				if(allow_party.length()>0){
					allow_party = allow_party.substring(0, allow_party.length()-1);
				}
				privilege.setAllow_party(allow_party);
				JSONArray user = privilegeJson.getJSONArray("allow_user");
				String allow_user = "";
				for(Object ine: user){
					allow_user = allow_user + ine +",";
				}
				if(allow_user.length()>0){
					allow_user = allow_user.substring(0, allow_user.length()-1);
				}
				privilege.setAllow_user(allow_user);
				JSONArray tag = privilegeJson.getJSONArray("allow_tag");
				String allow_tag = "";
				for(Object ine: tag){
					allow_tag = allow_tag + ine +",";
				}
				if(allow_tag.length()>0){
					allow_tag = allow_tag.substring(0, allow_tag.length()-1);
				}
				privilege.setAllow_tag(allow_tag);
				
				JSONArray extraParty = privilegeJson.getJSONArray("extra_party");
				String extra_party = "";
				for(Object ine: extraParty){
					extra_party = extra_party + ine +",";
				}
				if(extra_party.length()>0){
					extra_party = extra_party.substring(0, extra_party.length()-1);
				}
				privilege.setExtra_party(extra_party);
				
				JSONArray extraUser = privilegeJson.getJSONArray("allow_user");
				String extra_user = "";
				for(Object ine: extraUser){
					extra_user = extra_user + ine +",";
				}
				if(extra_user.length()>0){
					extra_user = extra_user.substring(0, extra_user.length()-1);
				}
				privilege.setExtra_user(extra_user);
				
				JSONArray extraTag = privilegeJson.getJSONArray("extra_tag");
				String extra_tag = "";
				for(Object ine: extraTag){
					extra_tag = extra_tag + ine +",";
				}
				if(extra_tag.length()>0){
					extra_tag = extra_tag.substring(0, extra_tag.length()-1);
				}
				privilege.setExtra_tag(extra_tag);
				privilegeDao.post(privilege);
			}
		}
		//企业授权码信息
		String permanentCode = jsonObject.getString("permanent_code");
		UUID authorizeid = UUID.randomUUID();
		Authorize authorize = new Authorize();
		authorize.setAuthCode(authCode);
		authorize.setTimeStamp(timeStamp);
		authorize.setPermanentCode(permanentCode);
		authorize.setCreateTime(date);
		authorize.setCorpId(corpid.toString());
		authorize.setId(authorizeid.toString());
		authorize.setStatus(1);
		authorizeDao.post(authorize);
		
		
	}
	
	
	public void token(String suiteId,String tokenStr,Long now){
		
	}

	public void updateAuthorize(String suiteId, String authCode, String timeStamp) {

	}
}
