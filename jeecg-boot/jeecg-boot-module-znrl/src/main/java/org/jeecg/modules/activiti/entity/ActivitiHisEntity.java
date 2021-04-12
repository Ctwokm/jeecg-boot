package org.jeecg.modules.activiti.entity;

import lombok.Data;
import org.jeecg.common.system.base.entity.JeecgEntity;

import java.io.Serializable;

/**
 * Created by xieyt on 2017-4-12.
 */
@Data
public class ActivitiHisEntity implements Serializable  {

	private static final long serialVersionUID = 1L;

	public String processInstanceId;

    public String taskId;

    public String taskDefinitionKey;

    public String taskName;

    public String taskType;
    ///
    public String executor;

    public String exeTime;

    public String exeResult;

    public String exeComment;

    public String exeBeginTime;

    public String exeEndTime;

    public String exeContext;

    public String apprOpName;

    public String apprEventTypeCd;

}
