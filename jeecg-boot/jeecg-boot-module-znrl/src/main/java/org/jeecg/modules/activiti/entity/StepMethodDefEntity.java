package org.jeecg.modules.activiti.entity;

import java.io.Serializable;

/**
 * 步骤执行方法配置数据Dto
 * Created by Administrator on 2018/9/5.
 */
public class StepMethodDefEntity implements Serializable {

    private static final long serialVersionUID = 2210021542439541182L;

    private String stepId; // 节点执行任务步骤ID

    private String stepName; // 步骤名称

    private String invokeMethod; // 调用方法

    private String invokeSeq; // 调用顺序

    private String methodDesc; // 方法描述

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getInvokeMethod() {
        return invokeMethod;
    }

    public void setInvokeMethod(String invokeMethod) {
        this.invokeMethod = invokeMethod;
    }

    public String getInvokeSeq() {
        return invokeSeq;
    }

    public void setInvokeSeq(String invokeSeq) {
        this.invokeSeq = invokeSeq;
    }

    public String getMethodDesc() {
        return methodDesc;
    }

    public void setMethodDesc(String methodDesc) {
        this.methodDesc = methodDesc;
    }
}
