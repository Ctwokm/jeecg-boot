package org.jeecg.modules.activiti.util;

/**
 * Created by Administrator on 2018/9/7.
 */
public class FlowException extends Exception {
    private String code;

    public FlowException(String code, String message) {
        super("发生异常！编码：" + code + "。异常信息：" + message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
