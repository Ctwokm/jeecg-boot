package org.jeecg.modules.activiti.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.activiti.entity.ActivitiEntity;
import org.jeecg.modules.activiti.entity.TaskInfoEntity;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.ActivitiConstant;
import org.jeecg.modules.activiti.util.SpringUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 完成任务节点的实质性的工作类
 * Created by xieyt on 2017-4-10.
 */

@Component
@Transactional
public class DoTaskListener implements TaskListener {

	private static final long serialVersionUID = -5493009044631388678L;

    @Override
    public void notify(DelegateTask task) {
        TaskService taskService = SpringUtil.getObject(TaskService.class);

        IActivitiService activitiService = SpringUtil.getObject(IActivitiService.class);

        RepositoryService repositoryService = SpringUtil.getObject(RepositoryService.class);
        try {
            //获取任务的业务参数bean和流程任务的key
            ActivitiEntity paramActivitiEntity = (ActivitiEntity)taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME);

            if (paramActivitiEntity == null) {
                throw new ActivitiException("执行该任务时未找到有效业务参数");
            }

            //设置任务的流程key和任务节点key到参数bean里
            paramActivitiEntity.setProcessKey(repositoryService.getProcessDefinition(task.getProcessDefinitionId()).getKey());
            paramActivitiEntity.setTaskKey(task.getTaskDefinitionKey());
            paramActivitiEntity.setTaskId(task.getId());

            //把任务ID放进参数，做其它用处
            String jsonString = paramActivitiEntity.getJsonString();
            JSONObject jo = JSON.parseObject(jsonString);
            jo.put("taskId", task.getId());
            jo.put("comment", taskService.getComment(task.getId()));
            paramActivitiEntity.setJsonString(jo.toJSONString());


            //获取该任务节点配置的任务内容
            List<ActivitiEntity> taskConfigList = activitiService.qryActivitiConfig(paramActivitiEntity);
            ActivitiEntity taskConfigEntity = taskConfigList.get(0);

            //把任务的内容放到参数bean里
            paramActivitiEntity.setTaskDo(taskConfigEntity.getTaskDo());
            paramActivitiEntity.setTaskType(taskConfigEntity.getTaskType());

            //任务执行人放到参数bean里
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            String opCode = sysUser.getId();
            paramActivitiEntity.setOpCode(opCode);
//            paramActivitiEntity.setOpCode("e9ca23d68d884d4ebb19d07889727dae");

            //执行任务
            activitiService.executeBusiness(paramActivitiEntity);
            // 保存任务跟踪信息ACT_BZ_TASK_TRACE
            Map<String,String> map = new HashMap<>();
            map.put("taskStatus","C");
            map.put("remark","任务已完成。");
            TaskInfoEntity taskInfoEntity = new TaskInfoEntity(task,map);
            activitiService.saveActBzTaskTrace(taskInfoEntity);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new ActivitiException("任务执行监听发生异常："+e.getMessage());
        }

    }

}
