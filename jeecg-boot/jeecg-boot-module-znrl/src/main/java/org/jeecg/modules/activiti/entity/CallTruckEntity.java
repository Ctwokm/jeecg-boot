package org.jeecg.modules.activiti.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/17.
 */
public class CallTruckEntity implements Serializable {

    private static final long serialVersionUID = 2210021542439541182L;

    private String jsonIn;

    private String jsonPublic;

    private String jsonOut;

    private String resCode;

    private String resMsg;

    private String opId;

    public CallTruckEntity() {
    }

    public CallTruckEntity(String jsonIn, String opId) {
        this.jsonIn = jsonIn;
        this.jsonPublic = "{}";
        this.jsonOut = "{}";
        this.opId = opId;
    }

    public String getJsonIn() {
        return jsonIn;
    }

    public void setJsonIn(String jsonIn) {
        this.jsonIn = jsonIn;
    }

    public String getJsonPublic() {
        return jsonPublic;
    }

    public void setJsonPublic(String jsonPublic) {
        this.jsonPublic = jsonPublic;
    }

    public String getJsonOut() {
        return jsonOut;
    }

    public void setJsonOut(String jsonOut) {
        this.jsonOut = jsonOut;
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

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }
}
