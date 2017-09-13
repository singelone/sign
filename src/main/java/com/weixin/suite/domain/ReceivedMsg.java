package com.weixin.suite.domain;

/**
 * Created by Administrator on 2017/8/9.
 */
public class ReceivedMsg {

    private String toUserName;
    private String encrypt;
    private String agentID;

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getToUserName() {

        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getName(String fieldName) {
        if (fieldName.equals(toUserName)) {
            return toUserName;
        } else if (fieldName.equals(encrypt)) {
            return encrypt;
        } else if (fieldName.equals(agentID)) {
            return agentID;
        } else {
            return "";
        }

    }
}
