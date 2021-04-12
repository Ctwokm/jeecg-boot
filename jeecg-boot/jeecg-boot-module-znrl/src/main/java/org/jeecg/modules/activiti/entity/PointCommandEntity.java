package org.jeecg.modules.activiti.entity;

/**
 * Created by xieyt on 2018-9-20.
 * 各种系统无法归类的公用的变量载体
 */
public class PointCommandEntity {

    public String resCode;

    public String resMsg;

    public String commandJason;

    public String sendResJson;

    public String getSendResJson() {
        return sendResJson;
    }

    public void setSendResJson(String sendResJson) {
        this.sendResJson = sendResJson;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getCommandJason() {
        return commandJason;
    }

    public void setCommandJason(String commandJason) {
        this.commandJason = commandJason;
    }
}
