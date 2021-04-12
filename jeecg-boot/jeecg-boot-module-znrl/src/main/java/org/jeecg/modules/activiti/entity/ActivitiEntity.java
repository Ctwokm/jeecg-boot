package org.jeecg.modules.activiti.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xieyt on 2017-3-28.
 */
@Data
public class ActivitiEntity implements Serializable {

    private static final long serialVersionUID = 2210021542439541182L;

    public String id;

    public String processInstanceId;

    public String taskId;

    public String taskName;

    public String taskTodoTag;

    public String processKey;

    public String processName;

    public String taskKey;

    public String paramJsonString;

    public String createTime;

    public String taskDefinitionKey;

    public String deployTime;

    public String res;//排他网关使用该属性决定走哪个支路，YES同意  NO不同意

    public String jsonString;

    public String assignType;

    public String assignee;

    public String candidateUsers;

    public String taskDo;

    public String taskType;

    public String resCode;

    public String resMsg;

    public String comment;

    public String opCode;

    public String opName;
    /**
     * 出参 返回业务信息
     */
    private String resInfo;
}
