package org.jeecg.modules.activiti.entity;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/10.
 */
public class TaskInfoEntity implements Serializable {
    private static final long serialVersionUID = 2210021542439541182L;

    private String taskId;

    private String taskCode;

    private String taskName;

    private String opCode;

    private Date createDt;

    private String taskStatus;

    private String remark;

    private String examOrderId;

    private String sampleCode;

    private String taskType;

    private List<TaskInfoEntity> traceList; //任务处理过程跟踪信息

    public TaskInfoEntity(String taskId, String taskName, String taskStatus, String opCode, Timestamp createDt, String remark) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.createDt = createDt;
        this.opCode = opCode;
        this.taskStatus = taskStatus;
        this.remark = remark;
    }

    public TaskInfoEntity(Task task, Map<String, String> map) {
        this.taskId = task.getId();
        this.taskName = task.getName();
        this.taskType = task.getTaskDefinitionKey();
        this.createDt = task.getCreateTime();
        this.opCode = task.getAssignee();
        this.taskStatus = map.get("taskStatus");
        this.remark = map.get("remark");
    }

    public TaskInfoEntity(Task task, String taskStatus, List<TaskInfoEntity> list) {
        this.taskId = task.getId();
        this.taskType = task.getTaskDefinitionKey();
        this.taskStatus = taskStatus;
        this.taskCode = task.getTaskDefinitionKey();
        this.taskName = task.getName();
        this.createDt = task.getCreateTime();
        this.opCode = task.getAssignee();
        this.traceList = list;
    }

    public TaskInfoEntity(DelegateTask task, Map<String, String> map) {
        this.taskId = task.getId();
        this.taskType = task.getTaskDefinitionKey();
        this.taskName = task.getName();
        this.examOrderId = (String) task.getVariable("examOrderId");
        this.sampleCode = null == task.getVariable("sampleInfo") ? null : ((SampleInfoEntity) task.getVariable("sampleInfo")).getSampleCode();
        this.createDt = new Date();
        this.taskStatus = map.get("taskStatus");
        this.opCode = task.getAssignee();
        this.remark = map.get("remark");
    }

    public String getExamOrderId() {
        return examOrderId;
    }

    public void setExamOrderId(String examOrderId) {
        this.examOrderId = examOrderId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<TaskInfoEntity> getTraceList() {
        return traceList;
    }

    public void setTraceList(List<TaskInfoEntity> traceList) {
        this.traceList = traceList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
