package org.jeecg.modules.activiti.core;


import org.jeecg.modules.activiti.entity.ActivitiEntity;
import org.jeecg.modules.activiti.entity.TaskInfoEntity;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.SpringUtil;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务监听，分配任务执行人
 * Created by xieyt on 2017-3-29.
 */
// 既然已经使用springboot就应该将TaskListener注册为springbean，不可以再强行获取
@Component
@Transactional
public class CreateTaskListener implements TaskListener {

	private static final long serialVersionUID = -7361265613667934493L;

    @Override
    public void notify(DelegateTask task) {

        TaskService taskService = SpringUtil.getObject(TaskService.class);

        IActivitiService activitiService = SpringUtil.getObject(IActivitiService.class);

        RepositoryService repositoryService = SpringUtil.getObject(RepositoryService.class);

        //SpringUtil.showClass();
        // 保存任务跟踪信息ACT_BZ_TASK_TRACE
        Map<String,String> map = new HashMap<>();
        map.put("taskStatus","W");
        map.put("remark","任务已创建，未分配执行人。");
        TaskInfoEntity taskInfoEntity = new TaskInfoEntity(task,map);
        activitiService.saveActBzTaskTrace(taskInfoEntity);
        try {
            //查询本任务的配置信息
            ActivitiEntity a = new ActivitiEntity();
            a.setProcessKey(repositoryService.getProcessDefinition(task.getProcessDefinitionId()).getKey());
            a.setTaskKey(task.getTaskDefinitionKey());
            List<ActivitiEntity> list = activitiService.qryActivitiConfig(a);

            if (list == null || list.size() == 0) {
                throw new ActivitiException("未找到该任务的业务配置信息，任务ID："+task.getId());
            }

            ActivitiEntity activitiEntity = list.get(0);
            if (activitiEntity.getCandidateUsers() == null || activitiEntity.getCandidateUsers().equals("")) {
                throw new ActivitiException("该任务的执行候选人未配置，任务ID："+task.getId());
            }

            //设置执行人
            if (activitiEntity.getAssignType().equals("0")) {
                task.setAssignee(activitiEntity.getCandidateUsers());//直接指定执行人
            } else if (activitiEntity.getAssignType().equals("1")) {
                String [] candidateUsers = activitiEntity.getCandidateUsers().split(",");
                task.addCandidateUsers(Arrays.asList(candidateUsers));//指定多个执行人，其中任意一人执行，即该任务完成
            } else {
                throw new ActivitiException("任务分配执行人方式配置异常！");
            }
            // 保存任务跟踪信息ACT_BZ_TASK_TRACE
            map.put("taskStatus","D");
            map.put("remark","任务已分配执行人，待处理。");
            taskInfoEntity = new TaskInfoEntity(task,map);
            activitiService.saveActBzTaskTrace(taskInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ActivitiException("给任务分配执行人发生异常："+e.getMessage());
        }
    }

}
