package org.jeecg.modules.activiti.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 流程节点执行的任务步骤Dto
 * Created by Administrator on 2018/9/5.
 */
public class ActStepConfigEntity implements Serializable {

    private static final long serialVersionUID = 2210021542439541182L;

    private String configId; // 主键

    private String processId; // 流程定义id

    private String activityId; // 流程节点ID

    private String activityName; // 流程节点名称

    private String stepId; // 节点执行任务步骤ID

    private String stepName; // 步骤名称

    private String stepSeq; // 步骤顺序

    private String isShow; // 是否在前台显示

    private String stepStatus; // 步骤执行状态

    private List<StepMethodDefEntity> stepMethodList; // 步骤执行方法列表

    public List<StepMethodDefEntity> getStepMethodList() {
        return stepMethodList;
    }

    public void setStepMethodList(List<StepMethodDefEntity> stepMethodList) {
        this.stepMethodList = stepMethodList;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

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

    public String getStepSeq() {
        return stepSeq;
    }

    public void setStepSeq(String stepSeq) {
        this.stepSeq = stepSeq;
    }

    public String getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(String stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
}
