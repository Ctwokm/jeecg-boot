package org.jeecg.modules.activiti.core;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.jeecg.modules.activiti.entity.ActStepConfigEntity;
import org.jeecg.modules.activiti.entity.StepMethodDefEntity;
import org.jeecg.modules.activiti.service.IStepMethodService;
import org.jeecg.modules.activiti.util.ActLogger;
import org.jeecg.modules.activiti.util.Constant;
import org.jeecg.modules.activiti.util.FlowException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/4.
 */
public class EndActListener implements JavaDelegate {


    private static final long serialVersionUID = -7361265613667934493L;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    TaskService taskService;
    //todo 此处需要完善一下，不能再使用公司封装的包直接调数据库sql
    //public IMISBaseDaoService misBaseDaoService;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    IStepMethodService stepMethodService;

    public EndActListener() {
        //本类的对象是执行时activiti创建的，非spring生成，无法自动加载容器里的bean，所以只能强行获取
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        this.taskService = (TaskService) wac.getBean("taskService");
        this.runtimeService = (RuntimeService) wac.getBean("runtimeService");
        this.repositoryService = (RepositoryService) wac.getBean("repositoryService");
        //todo 此处需要完善一下，不能再使用公司封装的包直接调数据库sql
        //this.misBaseDaoService = (IMISBaseDaoService) wac.getBean("misBaseDaoService");
        this.stepMethodService = (IStepMethodService) wac.getBean("stepMethodService");
    }

    @Override
    public void execute(DelegateExecution task) {
        logger.info(task.getCurrentActivityId() + "receiveTask End！");
        String activityId = task.getCurrentActivityId();
        String processInstId = task.getProcessInstanceId();
        Constant.setCache(processInstId + "lastStepFinished", false); // 环节步骤是否执行完
        List<ActStepConfigEntity> actStepConfigEntityList = (List<ActStepConfigEntity>) task.getVariable("actStepConfigEntityList");
        Map<String, Object> stepInfoMap = (Map<String, Object>) task.getVariable("stepInfoMap");
        stepInfoMap.put("lastStepFinished", false);
        String freq = task.getVariable("freq").toString();
        // 如果是故障恢复或暂停恢复，则取已执行的步骤信息
        List<Map<String, String>> existStepList = null == stepInfoMap.get(activityId) ? new ArrayList<Map<String, String>>() : (List<Map<String, String>>) stepInfoMap.get(activityId);
        List<Map<String, String>> stepList = new ArrayList<>();
        for (Map<String, String> step : existStepList) {
            // 去掉执行失败或被暂停的步骤；因为他们在流程恢复时会被重新执行
            if ("0".equals(step.get("status"))) {
                stepList.add(step);
            }
        }
        stepInfoMap.put(activityId, stepList);
        boolean hasFailure = false;
        boolean isPause = false;
        for (ActStepConfigEntity actStepConfigEntity : actStepConfigEntityList) {
            // 适配当前流程节点时。需要执行的步骤列表
            if (activityId.equals(actStepConfigEntity.getActivityId())) {
                ActLogger.info(processInstId, actStepConfigEntity.getActivityName() + "环节: 第" + actStepConfigEntity.getStepSeq() + "步骤。" + actStepConfigEntity.getStepName());
                // 检查该步骤是否已执行（故障恢复、手工处理完成时需判此分支；跳过已经处理完成的步骤）
                if ("0".equals(actStepConfigEntity.getStepStatus())) {
                    ActLogger.info(processInstId, "此步骤已完成，跳过。" + actStepConfigEntity.getActivityName() + "环节: 第" + actStepConfigEntity.getStepSeq() + "步骤。" + actStepConfigEntity.getStepName());
                    continue;
                }
                // 步骤执行状态：成功、失败
                String errInfo = ""; // 步骤失败时，记录失败提示信息
                Map<String, String> stepInfo = new HashMap<>();
                stepInfo.put("stepName", actStepConfigEntity.getStepName());
                stepInfo.put("isShow", actStepConfigEntity.getIsShow());
                stepInfo.put("status", "1"); //0-处理成功；1-处理中；2-处理失败；
                // 反射方式调用步骤执行的原子方法
                List<StepMethodDefEntity> stepMethodList = actStepConfigEntity.getStepMethodList();
                for (StepMethodDefEntity stepMethod : stepMethodList) {
                    ActLogger.info(processInstId, actStepConfigEntity.getActivityName() + "环节: 第" + actStepConfigEntity.getStepSeq() + "步骤。第" + stepMethod.getInvokeSeq() + "方法。" + stepMethod.getInvokeMethod() + "," + stepMethod.getMethodDesc());
                    // 开始调用。先检查有无影响本步骤的异常产生
                    try {
                        checkStepFailure(stepMethod, task.getVariable("traineeGroupId").toString());
                        checkStepPause(processInstId, stepMethod);
                        Map<String, Object> invokeParam = task.getVariables();
                        invokeParam.put("processInstId", processInstId);
                        invokeMethod(stepMethod.getInvokeMethod(), invokeParam);
                        ActLogger.info(processInstId, stepMethod.getInvokeMethod() + "方法执行完成，sleep" + freq +
                                "秒。");
                        Thread.sleep(Long.parseLong(freq) * 1000);
                    } catch (FlowException e1) {
                        ActLogger.err(processInstId, "执行步骤：" + stepMethod.getStepName() + "时，检测到故障！故障编号：" + e1.getCode() + "；故障信息：" + e1.getMessage());
                        hasFailure = true;
                        errInfo = e1.getMessage();
                        if ("8888".equals(e1.getCode())) {
                            isPause = true;
                        }
                        break; // 不再执行后续方法
                    } catch (Exception e) {
                        ActLogger.err(processInstId, "执行步骤：" + stepMethod.getStepName() + "时，发生异常：" + ((InvocationTargetException) e).getTargetException().getMessage());
                        hasFailure = true;
                        errInfo = ((InvocationTargetException) e).getTargetException().getMessage();
//                        e.printStackTrace();
                        break; // 不再执行后续方法
                    }
                }
                String status = hasFailure ? "2" : "0";
                if (isPause) {
                    status = "1"; // 暂停显示处理中
                }
                stepInfo.put("status", status);
                stepInfo.put("errInfo", errInfo);
                stepList.add(stepInfo);
                Constant.setCache(processInstId + "lastStepFinished", hasFailure ? false : true);
                stepInfoMap.put("lastStepFinished", hasFailure ? false : true);
                Constant.setCache(processInstId + "stepInfoMap", stepInfoMap);
                task.setVariable("stepInfoMap", stepInfoMap);
                // 如果是流程恢复时的task对象，需要更新流程变量
                if (task instanceof ExecutionEntityClone) {
                    runtimeService.setVariable(task.getId(), "stepInfoMap", stepInfoMap);
                }
                actStepConfigEntity.setStepStatus(status);
                if (hasFailure) {
                    if (((ExecutionEntity) task).getSuspensionState() == 1) {
                        runtimeService.suspendProcessInstanceById(processInstId);//流程挂起
                    }
                    Constant.setCache(processInstId + "hasFailure", hasFailure);
                    break; // 不再执行后续步骤
                } else {
                    Constant.setCache(processInstId + "hasFailure", hasFailure);
                }
            }
        }
        // 把task对象持久化到流程变量里，以便流程恢复时使用
        ExecutionEntity taskObj = new ExecutionEntityClone((ExecutionEntity) task);
        Constant.setCache(processInstId + "taskObj", taskObj);
        task.setVariable(processInstId + "taskObj", taskObj);
        logger.info(task.getCurrentActivityId() + "receiveTask End！");
    }

    // 检查前台是否发起了“暂停”命令
    private void checkStepPause(String processInstId, StepMethodDefEntity stepMethod) throws Exception {
        if ("P".equals(Constant.getCache(processInstId + "status"))) {
            throw new FlowException("8888", "前台发起暂停命令，processInstId=" + processInstId);
        }
    }

    // 检查是否设置了影响此步骤的异常
    public void checkStepFailure(StepMethodDefEntity stepMethod, String traineeGroupId) throws Exception {
        JSONObject paramObj = new JSONObject();
        paramObj.put("stepId", stepMethod.getStepId());
        paramObj.put("traineeGroupId", traineeGroupId);
        //todo 这玩意儿直接调存储过程然后调用存储的sql，何必呢？为何不直接使用mybatis自定义sql、
        //JSONObject failureList = misBaseDaoService.query("flow.qryFailureList", paramObj);
        //JSONArray rows = (JSONArray) failureList.get("rows");
        /*if (null != rows && rows.size() > 0) {
            for (Object obj : rows) {
                JSONObject jsonObj = (JSONObject) obj;
                String failureCode = null == jsonObj.get("failure_code") ? "9999" : jsonObj.get("failure_code").toString();
                String failureMsg = null == jsonObj.get("failure_name") ? "" : jsonObj.get("failure_name").toString();
                throw new FlowException(failureCode, failureMsg);
            }
        }*/
    }

    /**
     * 以反射的方式调用Java方法。固定调用类：com.gxz.activiti.service.impl.ActivitiServiceImpl中的方法；固定传JSONObject参数。
     *
     * @param methodName
     * @throws Exception
     */
    private Map<String, Object> invokeMethod(String methodName, Map<String, Object> map) throws Exception {
        Class<?> class1 = Class.forName("com.gxz.activiti.service.impl.StepMethodServiceImpl");
        Method method = class1.getMethod(methodName, Map.class);
        return (Map<String, Object>) method.invoke(stepMethodService, map);
    }
}
