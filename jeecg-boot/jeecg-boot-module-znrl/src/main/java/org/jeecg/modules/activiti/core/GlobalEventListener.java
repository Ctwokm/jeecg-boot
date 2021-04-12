package org.jeecg.modules.activiti.core;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiVariableEvent;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by admin on 2014/5/15.
 */
@Slf4j
@Service
@Transactional
public class GlobalEventListener implements ActivitiEventListener {


    @Override
    public void onEvent(ActivitiEvent event) {
        switch (event.getType()) {

            case JOB_EXECUTION_SUCCESS:
                log.debug("------------------------A job well done!");
                break;

            case ENGINE_CREATED:
                log.debug("------------------------ENGINE_CREATED done!");
                break;

            case JOB_EXECUTION_FAILURE:
                log.debug("------------------------A job has failed...");
                break;

            case VARIABLE_CREATED:
                ActivitiVariableEvent variableEvent = (ActivitiVariableEvent) event;
                log.debug("------------------------创建了变量: " + variableEvent.getVariableName() + ", 值：" + variableEvent.getVariableValue());
                break;

            default:
                System.out.println("Event received: " + event.getType());
                log.debug("------------------------Event received: " + event.getType());
        }
    }

    @Override
    public boolean isFailOnException() {
        // The logic in the onEvent method of this listener is not critical, exceptions
        // can be ignored if logging fails...
        return false;
    }
}
