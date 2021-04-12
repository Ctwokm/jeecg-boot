package org.jeecg.modules.activiti.core;


import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 任务监听，分配任务执行人
 * Created by xieyt on 2017-3-29.
 */
//todo 开始监听基本没有出现在bpmn文件中，目前还没有使用
@Slf4j
public class StartActListener implements JavaDelegate {

    private static final long serialVersionUID = -7361265613667934493L;

    TaskService taskService;

    IActivitiService activitiService;

    RepositoryService repositoryService;

    RuntimeService runtimeService;

    //todo 此处需要完善一下，不能再使用公司封装的包直接调数据库sql
    //public IMISBaseDaoService misBaseDaoService;

    public StartActListener() {
        //本类的对象是执行时activiti创建的，非spring生成，无法自动加载容器里的bean，所以只能强行获取
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        this.taskService = (TaskService) wac.getBean("taskService");
        this.activitiService = (IActivitiService) wac.getBean("activitiService");
        this.repositoryService = (RepositoryService) wac.getBean("repositoryService");
        this.runtimeService = (RuntimeService) wac.getBean("runtimeService");
        //todo 此处需要完善一下，不能再使用公司封装的包直接调数据库sql
        //this.misBaseDaoService = (IMISBaseDaoService) wac.getBean("misBaseDaoService");
    }

    @Override
    public void execute(DelegateExecution task) {
        log.info(task.getCurrentActivityId() + "receiveTask Start！");
        // 检查工作模式，决定是否自动流向下一个环节
        // 优先从缓存取
        String workMode = null == Constant.getCache(task.getProcessInstanceId() + "work_mode") ? null : Constant.getCache(task.getProcessInstanceId() + "work_mode").toString();
        // 缓存中没有时，从数据库取
        if (null == workMode) {
            JSONObject paramObj = new JSONObject();
            paramObj.put("processInstId", task.getProcessInstanceId());
            JSONObject workModeObj = new JSONObject();
            try {
                //todo 此处需要完善一下，不能再使用公司封装的包直接调数据库sql
                //workModeObj = misBaseDaoService.query("flow.qryWorkMode", paramObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONArray rows = (JSONArray) workModeObj.get("rows");

            if (null == rows || rows.size() == 0 || null == rows.get(0) || null == ((JSONObject) rows.get(0)).get("param_val")) {
                workMode = task.getVariable("work_mode").toString();
            } else {
                workMode = ((JSONObject) rows.get(0)).get("param_val").toString();
            }
            Constant.setCache(task.getProcessInstanceId() + "work_mode", workMode);
        }
        if ("auto".equals(workMode)) {
            // 如果是第一个start节点，需要等待流程创建完成后再发signal信号
            if (!"receivetask1".equals(task.getCurrentActivityId())) {
                // 由异步的线程来触发signal信号。否则当前环节未执行完，环节未进入等待信号状态。发送signal也没有用。
                String freq = task.getVariable("freq").toString();
                AutoSignalThread thread = new AutoSignalThread(activitiService, Long.parseLong(freq) * 1000, task.getProcessInstanceId());
                thread.start();
            }
        }
    }

}
