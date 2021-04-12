package org.jeecg.modules.activiti.core;

import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.SuspensionState;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/21.
 */
public class ExecutionEntityClone extends ExecutionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Object> variables = new HashMap<>();

    protected String activityId;

    protected String processInstanceId;

    protected int suspensionState = SuspensionState.ACTIVE.getStateCode();

    protected String id ;

    public ExecutionEntityClone(ExecutionEntity task) {
        this.activityId = task.getActivityId();
        this.processInstanceId = task.getProcessInstanceId();
        this.id = task.getId();
        Map<String, Object> variables = task.getVariables();
        for (String key : variables.keySet()) {
            this.setVariable(key, variables.get(key));
        }
    }

    @Override
    public String getCurrentActivityId() {
        return this.activityId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    @Override
    public Object getVariable(String variableName) {
        return this.variables.get(variableName);
    }

    @Override
    public Map<String, Object> getVariables() {
        return this.variables;
    }

    @Override
    public void setVariable(String variableName, Object value) {
        this.variables.put(variableName, value);
    }

    @Override
    public String getActivityId() {
        return this.activityId;
    }

    @Override
    public int getSuspensionState() {
        return this.suspensionState;
    }

    @Override
    public void setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
