package org.jeecg.modules.activiti.core;

import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.activiti.entity.ActivitiEntity;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.ActivitiConstant;
import org.jeecg.modules.activiti.util.SpringUtil;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 处理自动任务的服务，本系统内通常用于审批不通过后，自动结束审批流程等，任务可以配置
 * Created by xieyt on 2017-7-14.
 */
@Component
public class AutoServiceTask implements JavaDelegate {

//
//    public AutoServiceTask () {
//        //本类的对象是执行时activiti创建的，非spring生成，无法自动加载容器里的bean，所以只能强行获取
//        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//        this.taskService = (TaskService)wac.getBean("taskService");
//        this.activitiService = (IActivitiService)wac.getBean("activitiService");
//        this.repositoryService = (RepositoryService)wac.getBean("repositoryService");
//    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

        TaskService taskService = SpringUtil.getObject(TaskService.class);

        IActivitiService activitiService = SpringUtil.getObject(IActivitiService.class);

        RepositoryService repositoryService = SpringUtil.getObject(RepositoryService.class);
        try {
            //获取任务的业务参数bean和流程任务的key
            ActivitiEntity paramActivitiEntity = (ActivitiEntity)execution.getVariable(ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME); //execution.getVariables() 可获取创建任务时的Map

            //设置任务的流程key和任务节点key到参数bean里
            paramActivitiEntity.setProcessKey(repositoryService.getProcessDefinition(execution.getProcessDefinitionId()).getKey());
            paramActivitiEntity.setTaskKey(execution.getCurrentActivityId());

            //获取该任务节点配置的任务内容
            List<ActivitiEntity> taskConfigList = activitiService.qryActivitiConfig(paramActivitiEntity);
            ActivitiEntity taskConfigEntity = taskConfigList.get(0);

            //把任务的内容放到参数bean里
            paramActivitiEntity.setTaskDo(taskConfigEntity.getTaskDo());
            paramActivitiEntity.setTaskType(taskConfigEntity.getTaskType());

            //任务执行人放到参数bean里
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            String opCode = sysUser.getId();
            paramActivitiEntity.setAssignee(opCode);
//            paramActivitiEntity.setAssignee("e9ca23d68d884d4ebb19d07889727dae");

            //执行任务
            activitiService.executeBusiness(paramActivitiEntity);

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ActivitiException("任务执行监听发生异常："+e.getMessage());
        }

    }
}
