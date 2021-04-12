package org.jeecg.modules.activiti.entity;

import java.io.Serializable;

/**
 * 流程步骤执行日志信息。
 * Created by Administrator on 2018/9/14.
 */
public class ActExecLogEntity implements Serializable {

    private static final long serialVersionUID = 2210021542439541182L;

    private String logMethod; // 步骤方法

    private Long logTime; // 日志时间

    private String logInfo; // 日志信息

    private String logType; // 日志类型：info、error

    private String activityId; // 流程节点ID

    private String activityName; // 流程节点名称

    private String stepId; // 节点执行任务步骤ID

    private String stepName; // 步骤名称

    private String processInstId; // 流程实例ID

    public ActExecLogEntity() {
    }

    public ActExecLogEntity(ActStepConfigEntity actStepConfigEntity, StepMethodDefEntity stepMethod, String logInfo, String logType, String processInstId) {
        this.logMethod = stepMethod.getInvokeMethod();
        this.logTime = System.currentTimeMillis();
        this.logInfo = logInfo;
        this.logType = logType;
        this.activityId = actStepConfigEntity.getActivityId();
        this.activityName = actStepConfigEntity.getActivityName();
        this.stepId = stepMethod.getStepId();
        this.stepName = stepMethod.getStepName();
        this.processInstId = processInstId;
    }

    public String getProcessInstId() {
        return processInstId;
    }

    public void setProcessInstId(String processInstId) {
        this.processInstId = processInstId;
    }

    public String getLogMethod() {
        return logMethod;
    }

    public void setLogMethod(String logMethod) {
        this.logMethod = logMethod;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
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
}
