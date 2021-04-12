package org.jeecg.modules.activiti.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.activiti.core.AutoSignalThread;
import org.jeecg.modules.activiti.core.EndActListener;
import org.jeecg.modules.activiti.entity.*;
import org.jeecg.modules.activiti.mapper.ActivitiMapper;
import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.ActivitiConst;
import org.jeecg.modules.activiti.util.ActivitiConstant;
import org.jeecg.modules.activiti.util.Constant;
import org.jeecg.modules.activiti.util.SpringUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 工作流流转核心实现类
 * Created by xieyt on 2017-4-7.
 */
@Service("activitiService")
@Transactional
public class ActivitiServiceImpl implements IActivitiService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    protected ProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private ActivitiMapper activitiMapper;

    static final DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    //发布流程
    public Result<?> deployFlow(String processKey) throws RuntimeException {

        Deployment deployment;
        try {
            deployment = repositoryService.createDeployment().addClasspathResource("bpmn/" + processKey + ".bpmn").deploy();
        } catch (Exception e) {
            throw new RuntimeException("发布BPMN文件" + processKey + "失败:" + e.getMessage());
        }
        return Result.OK("发布BPMN文件" + processKey + "成功！ 流程ID：" + deployment.getId(), null);
    }


    /**
     * 查出bpmn资源目录下的所有 .bpmn文件，流程名称必须与文件名称相同
     *
     * @return
     */
    public List<ActivitiEntity> listToDeployBpmn() throws RuntimeException {
        List<ActivitiEntity> activitiList = new ArrayList<ActivitiEntity>();

        try {
            File f = new File(this.getClass().getResource("/").getPath() + "bpmn");
            if (f != null && f.isDirectory()) {
                File[] fileArray = f.listFiles();
                if (fileArray != null) {

                    String processKey;
                    for (File file : fileArray) {
                        String fileFullName = file.getName();
                        if (fileFullName.endsWith("bpmn") || fileFullName.endsWith("BPMN") || fileFullName.endsWith("Bpmn")) {
                            ActivitiEntity activitiEntity = new ActivitiEntity();

                            //获取流程名
                            processKey = fileFullName.split("\\.")[0];
                            activitiEntity.setProcessKey(processKey);

                            //查询已经发布情况
                            List<Deployment> deploymentList = repositoryService.createDeploymentQuery().processDefinitionKey(processKey).orderByDeploymenTime().desc().list();
                            List<ProcessDefinition> processDefList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).list();
                            if (deploymentList != null && deploymentList.size() > 0) {
                                Deployment deployment = deploymentList.get(0);
                                activitiEntity.setId(deployment.getId());
                                activitiEntity.setDeployTime(dateConverter(deployment.getDeploymentTime()));
                            }

                            if (processDefList != null && processDefList.size() > 0) {
                                ProcessDefinition processDefinition = processDefList.get(0);
                                activitiEntity.setProcessName(processDefinition.getName());
                            }

                            activitiList.add(activitiEntity);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("查看资源文件发生异常:" + e.getMessage());
        }

        //设置返回结果
        return activitiList;
    }


    /**
     * 启动流程
     *
     * @param activitiEntity 存放具体业务参数的bean
     */
    public Result<?> startProcess(ActivitiEntity activitiEntity) throws RuntimeException {
        //流程参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME, activitiEntity);

        ProcessInstance processInstance = null;
        try {
            processInstance = runtimeService.startProcessInstanceByKey(activitiEntity.getProcessKey(), paramMap);
            //把流程实例ID更新到相关业务表，可以避免专门再调用一次去更新
//            activitiEntity.setProcessInstanceId(processInstance.getId());
//            activitiMapper.callbackToWriteProcessInstId(activitiEntity);
//            if (!activitiEntity.getResCode().equals("0")) {
//                r.setSuccess(false);
//                r.setResultMsg("启动任务流程失败：回写流程实例ID到业务记录时失败！");
//            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("流程启动失败：" + e.getMessage());
        }
        return Result.OK("流程启动成功，流程实例ID为：" + processInstance.getId(),processInstance.getId());
    }

    /**
     * 启动分公司平台的流程
     *
     * @param activitiEntity 存放具体业务参数的bean
     */
    public Result<?> startProcess(Connection con, ActivitiEntity activitiEntity) throws RuntimeException {
        //流程参数
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Result result = new Result();
        paramMap.put(ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME, activitiEntity);

        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(activitiEntity.getProcessKey(), paramMap);
            result.setMessage("流程启动成功，流程实例ID为：" + processInstance.getId());
            result.setResult(processInstance.getId());
            //把流程实例ID更新到相关业务表，可以避免专门再调用一次去更新
            activitiEntity.setProcessInstanceId(processInstance.getId());
            //activitiMapper.callbackToWriteProcessInstId(activitiEntity);
            CallableStatement cs = con.prepareCall("{call pk_activiti.callbackToWriteProcessInstId(?,?,?,?,?)}");
            cs.setObject(1, activitiEntity.getJsonString());
            cs.setObject(2, activitiEntity.getProcessKey());
            cs.setObject(3, activitiEntity.getProcessInstanceId());
            cs.registerOutParameter(4, java.sql.Types.VARCHAR);           //注册返回类型(sql类型)
            cs.registerOutParameter(5, java.sql.Types.VARCHAR);           //注册返回类型(sql类型)
            cs.execute();
            String rescode = cs.getString(4);
            String resmsg = cs.getString(5);
            if (!"0".equals(rescode)) {
                result.setSuccess(false);
                result.setMessage("启动任务流程失败：回写流程实例ID到业务记录时失败！" + resmsg);
            }
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException("流程启动失败：" + e.getMessage());
        }
        return result;
    }

    //查看待办任务
    public Result<?> qryTodoTasks(String assignee, String processKey) throws RuntimeException {
        List<Task> toClaimTaskList = taskService.createTaskQuery().taskCandidateUser(assignee).list();
        List<Task> toDodoTaskList = taskService.createTaskQuery().taskAssignee(assignee).list();

        List<ActivitiEntity> activitiList = new ArrayList<ActivitiEntity>();
        ProcessDefinition processDefinition;
        ActivitiEntity en;
        //待认领任务
        for (Task task : toClaimTaskList) {
            en = new ActivitiEntity();
            en.setProcessInstanceId(task.getProcessInstanceId());

            //获取流程的key
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            processDefinition = list.get(0);
            if (processKey != null && !processDefinition.getKey().equals(processKey)) {
                continue;
            }
            en.setAssignee(assignee);
            en.setProcessKey(processDefinition.getKey());
            en.setProcessName(processDefinition.getName());
            en.setTaskTodoTag("TOCLAIM");
            en.setTaskId(task.getId());
            en.setTaskName(task.getName());
            en.setCreateTime(dateConverter(task.getCreateTime()));
            en.setTaskDefinitionKey(task.getTaskDefinitionKey());
            en.setParamJsonString(((ActivitiEntity) taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME)).getJsonString());
            en.setOpName(((ActivitiEntity) taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME)).getOpName());
            activitiList.add(en);
        }

        //待办任务
        for (Task task : toDodoTaskList) {
            en = new ActivitiEntity();
            en.setProcessInstanceId(task.getProcessInstanceId());

            //获取流程的key
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            processDefinition = list.get(0);
            if (processKey != null && !processDefinition.getKey().equals(processKey)) {
                continue;
            }
            en.setAssignee(assignee);
            en.setProcessKey(processDefinition.getKey());
            en.setProcessName(processDefinition.getName());
            en.setTaskTodoTag("TODO");
            en.setTaskId(task.getId());
            en.setTaskName(task.getName());
            en.setCreateTime(dateConverter(task.getCreateTime()));
            en.setTaskDefinitionKey(task.getTaskDefinitionKey());
            en.setParamJsonString(((ActivitiEntity) taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME)).getJsonString());
            en.setOpName(((ActivitiEntity) taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME)).getOpName());
            activitiList.add(en);
        }

        //按插入时间倒序排序
        Collections.sort(activitiList, new Comparator<ActivitiEntity>() {
            @Override
            public int compare(ActivitiEntity o1, ActivitiEntity o2) {
                Date date1 = dateFormatter.parse(o1.getCreateTime(), new ParsePosition(0));
                Date date2 = dateFormatter.parse(o2.getCreateTime(), new ParsePosition(0));
                long i = date1.getTime() - date2.getTime();
                if(i >= 0){
                    return -1;
                }else{
                    return 1;
                }
            }
        });

        return Result.OK(activitiList);
    }


    //根据任务ID查看任务的详情
    public Result<?> qryTaskById(String taskId) throws RuntimeException {
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();

        List<ActivitiEntity> activitiList = new ArrayList<ActivitiEntity>();
        ProcessDefinition processDefinition;
        ActivitiEntity en;
        //待认领任务
        for (Task task : taskList) {
            en = new ActivitiEntity();
            en.setProcessInstanceId(task.getProcessInstanceId());

            //获取流程的key
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).list();
            processDefinition = list.get(0);
            en.setProcessKey(processDefinition.getKey());
            //
            en.setProcessName(processDefinition.getName());
            en.setTaskId(task.getId());
            en.setTaskName(task.getName());
            en.setCreateTime(dateConverter(task.getCreateTime()));
            en.setTaskDefinitionKey(task.getTaskDefinitionKey());
            en.setParamJsonString(((ActivitiEntity) taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME)).getJsonString());
            en.setOpName(((ActivitiEntity) taskService.getVariable(task.getId(), ActivitiConstant.ACTIVITI_PARAM_BEAN_NAME)).getOpName());
            activitiList.add(en);
        }

        return Result.OK(activitiList);
    }


    //查看示例流程历史信息
    public Result<?> qryHistoryProcessInst(String processInstanceId) throws RuntimeException {
        List<ActivitiHisEntity> activitiList = new ArrayList<ActivitiHisEntity>();

        //该流程已经创建的任务节点
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();

        String assignee;
        ActivitiHisEntity activitiHisEntity;
        for (HistoricTaskInstance hai : list) {
            activitiHisEntity = new ActivitiHisEntity();
            assignee = hai.getAssignee();

            activitiHisEntity.setProcessInstanceId(processInstanceId);
            activitiHisEntity.setTaskId(hai.getId());
            activitiHisEntity.setTaskDefinitionKey(hai.getTaskDefinitionKey());
            activitiHisEntity.setTaskName(hai.getName());
            activitiHisEntity.setExecutor(assignee == null ? "" : activitiMapper.getAssigneeName(assignee));
            activitiHisEntity.setExeTime(dateConverter(hai.getEndTime()));

            String commentStr = "";
            for (Comment comment : taskService.getTaskComments(hai.getId())) {
                commentStr = commentStr + comment.getFullMessage();
            }
            activitiHisEntity.setExeComment(commentStr == null || commentStr.equals("") ? "待执行" : commentStr);
            activitiList.add(activitiHisEntity);
        }

        return Result.OK(activitiList);
    }

    //执行任务节点
    public Result<?> doTask(ActivitiEntity activitiEntity) throws RuntimeException {
        Result result = new Result();
        //流程参数
        String taskId = activitiEntity.getTaskId();
        String res = activitiEntity.getRes();
        String taskTodoTag = activitiEntity.getTaskTodoTag();
        try {
            //多个候选人的任务，先认领
            if (taskTodoTag.equals("TOCLAIM")) {
                taskService.claim(taskId, activitiEntity.getAssignee());
            }

            //如果有排他网关，用来决定下一步走向
            Map<String, Object> paramMap = new HashMap<String, Object>();
            if (res != null && !res.equals("")) {
                paramMap.put(ActivitiConstant.TASK_RES, res);
            }

            //审批说明增加结果描述
            String resDesc = "";
            if (res != null && res.equalsIgnoreCase("YES")) {
                resDesc = "审批通过：";
            } else if (res != null && res.equalsIgnoreCase("NO")) {
                resDesc = "审批不通过：";
            }

            //写入评论，并完成任务
            taskService.addComment(taskId, null, resDesc + activitiEntity.getComment());
            taskService.complete(taskId, paramMap);
            result.setMessage("任务节点执行完成！任务ID为：" + taskId);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            throw new RuntimeException("任务节点执行失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 执行业务逻辑操作
     *
     * @param activitiEntity
     * @return
     * @throws RuntimeException
     */
    public void executeBusiness(ActivitiEntity activitiEntity) throws RuntimeException {
        try {
            //存储过程里处理的任务
            if (activitiEntity.getTaskType().equals(ActivitiConst.TASK_TYPE_PROCEDURE)) {
                /*
                 * todo 这里改为统一的接口方便，直接复用原来的存储过程
                 *  简单改造一下，把任务taskDo放进参数，做其它用处
                 */
                String jsonString = activitiEntity.getJsonString();
                com.alibaba.fastjson.JSONObject jo = JSON.parseObject(jsonString);
                jo.put("taskDo", activitiEntity.getTaskDo());
                activitiEntity.setJsonString(jo.toJSONString());
                activitiMapper.executeBusiness(activitiEntity);
            } else if (activitiEntity.getTaskType().equals(ActivitiConst.TASK_TYPE_JAVA_REF)) {//反射java任务类执行的任务，如调用ws，http，socket接口等
                exeJAVATask(activitiEntity);
            }

            //执行失败，则回滚事务
            if (!activitiEntity.getResCode().equals(ActivitiConst.EXE_RES_SUCCESS)) {
                throw new RuntimeException("执行业务操作任务失败:" + activitiEntity.getResMsg());
            }
        } catch (Exception e) {
            throw new RuntimeException("执行业务操作任务失败：" + e.getMessage());
        }
    }

    //反射java任务类执行的任务，如调用ws，http，socket接口等
    private void exeJAVATask(ActivitiEntity activitiEntity) {
        try {
            //获取类的全路径
            Class c = Class.forName(activitiEntity.getTaskDo());
//            Class c = Class.forName("com.gxz.activiti.core.UploadServiceTesk1");
            //构造器构造类的实例
            Constructor constructor = c.getConstructor();
            Object o = constructor.newInstance();

            //调用执行类的execute方法
            Method method = c.getMethod("execute", new Class[]{ActivitiEntity.class});
            Object[] arguments = new Object[]{activitiEntity}; //ActivitiEntity 作为execute的输入参数
            method.invoke(o, arguments);

            //顺利执行到底就是ok
            activitiEntity.setRes(ActivitiConst.EXE_RES_SUCCESS);
            activitiEntity.setResMsg("任务执行成功。");
        } catch (Exception e) {
            e.printStackTrace();
            activitiEntity.setRes(ActivitiConst.EXE_RES_FAILED);
            activitiEntity.setResMsg(activitiEntity.getTaskName() + "任务执行失败：" + e.getMessage());
        }
    }


    //日期Date型转换成String型
    private String dateConverter(Date date) {
        if (date == null) {
            return "-";
        }
        return dateFormatter.format(date);
    }

    //查询工作流配置
    public List<ActivitiEntity> qryActivitiConfig(ActivitiEntity activitiEntity) throws RuntimeException {
        return activitiMapper.qryActivitiConfig(activitiEntity);
    }

    //生成流程图
    public InputStream generateImgInputStream(String processInstId) throws RuntimeException {
        BpmnModel bpmnModel = null;
        String procDefId = null;
        //执行中的流程
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstId).singleResult();

        //正在执行的没有查到，就要去执行完的历史里去查
        if (processInstance == null && historicProcessInstance == null) {
            logger.info("未找到该流程实例，无法生成流程图！");
            return null;
        } else if (processInstance != null && historicProcessInstance == null) {
            procDefId = processInstance.getProcessDefinitionId();
            bpmnModel = repositoryService.getBpmnModel(procDefId);
        } else if (processInstance == null && historicProcessInstance != null) {
            procDefId = historicProcessInstance.getProcessDefinitionId();
            bpmnModel = repositoryService.getBpmnModel(procDefId);
        } else if (processInstance != null && historicProcessInstance != null) {
            procDefId = processInstance.getProcessDefinitionId();
            bpmnModel = repositoryService.getBpmnModel(procDefId);
        }

        if (bpmnModel == null || procDefId == null || procDefId.equals("")) {
            logger.info("未找到该流程BPMN模版，无法生成流程图！");
            return null;
        }
//      TODO:高亮的逻辑前移到前台
//        //高亮显示历史任务结点
//        List<String> highLightedActivities = new ArrayList<String>();
        List<HistoricActivityInstance> hisActivityInstList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstId).orderByHistoricActivityInstanceId().asc().list();
//        for (HistoricActivityInstance h : hisActivityInstList) {
//            highLightedActivities.add(h.getActivityId());
//        }
//        //高亮正在执行任务结点
//        List<Task> taseList = taskService.createTaskQuery().processInstanceId(processInstId).list();
//        for (Task t : taseList) {
//            highLightedActivities.add(t.getTaskDefinitionKey());
//        }
//
//        //高亮显示的任务结点
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(procDefId);
        List<String> highLightedFlows = getHighLightedFlows(bpmnModel, definitionEntity, hisActivityInstList);

        // 高亮完成的流程


        //生成图片
        InputStream is = processEngineConfiguration.getProcessDiagramGenerator().generateDiagram(bpmnModel,
                "png", new ArrayList<String>(), highLightedFlows,
                "宋体",
                "宋体",
                null,
                1.0);

        return is;
    }


    /**
     * 获取需要高亮的线
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(BpmnModel bpmnModel, ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId

        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());


            List<FlowNode> sameStartTimeNodes = new ArrayList<FlowNode>();// 用以保存后续开始时间相同的节点
            FlowNode sameActivityImpl1 = null;

            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);// 第一个节点
            HistoricActivityInstance activityImp2_;

            for (int k = i + 1; k <= historicActivityInstances.size() - 1; k++) {
                activityImp2_ = historicActivityInstances.get(k);// 后续第1个节点

                if (activityImpl_.getActivityType().equals("userTask") && activityImp2_.getActivityType().equals("userTask") &&
                        df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))) //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                {

                } else {
                    sameActivityImpl1 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());//找到紧跟在后面的一个节点
                    break;
                }

            }
            sameStartTimeNodes.add(sameActivityImpl1); // 将后面第一个节点放在时间相同节点的集合里
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点

                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))) {// 如果第一个节点和第二个节点开始时间相同保存
                    FlowNode sameActivityImpl2 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows(); // 取出节点的所有出去的线

            for (SequenceFlow pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                FlowNode pvmActivityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(pvmTransition.getTargetRef());// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;
    }

    public Result<?> qryTasksHisList(ActivitiHisEntity activitiHisRpt) {
        //查询总记录数
        // Integer totalCnt = activitiMapper.qryTasksHisListCnt(activitiHisRpt);
        //查询本次页面的结果集
        List<ActivitiHisEntity> activitiHisList = activitiMapper.qryTasksHisList(activitiHisRpt);
        //设置返回结果
        return Result.OK(activitiHisList);
    }

    public Result<?> qryTasksHisChildList(ActivitiHisEntity activitiHisRpt) {
        //查询总记录数
        //查询本次页面的结果集
        List<ActivitiHisEntity> activitiHisChildList = activitiMapper.qryTasksHisChildList(activitiHisRpt);
        //设置返回结果
        return Result.OK(activitiHisChildList);
    }

    @Override
    public Result<?> completeEntrustTask(Map<String, Object> paramMap) {
        List<Task> toDodoTaskList = taskService.createTaskQuery().processInstanceId((String) paramMap.get("processInstId")).list();
        Task task = toDodoTaskList.get(toDodoTaskList.size() - 1);
        taskService.complete(task.getId(), paramMap);//TODO:增加完成详细信息
        return null;
    }


    @Override
    public void saveActBzTaskTrace(TaskInfoEntity task) {
        activitiMapper.saveActBzTask(task);
        activitiMapper.saveActBzTaskTrace(task);
    }

    @Override
    public Result<?> assignTask2Staff(Map<String, Object> paramMap) {
        String taskIds = paramMap.get("taskIds").toString();
        String userId = paramMap.get("userId").toString();
        String[] taskIdArray = taskIds.split(",");
        for (String taskId : taskIdArray) {
            taskService.setAssignee(taskId, userId);
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            // 记录跟踪信息
            Map<String, String> map = new HashMap<>();
            map.put("taskStatus", "D");
            map.put("remark", "任务已派发执行人，待处理。");
            TaskInfoEntity taskInfoEntity = new TaskInfoEntity(task, map);
            saveActBzTaskTrace(taskInfoEntity);
        }
        return new Result<>();
    }

    @Override
    public Result<?> signal(String processInstanceId) {
        List<Execution> list = runtimeService.createExecutionQuery().processInstanceId(processInstanceId).list();
        for (Execution exec : list) {
            if (null != exec && ((ExecutionEntity) exec).isActive()) {
                runtimeService.signal(exec.getId());
            }
        }
        return new Result<>();
    }

    @Override
    public List<ActStepConfigEntity> qryActStepMethodList(String processKey) {
        List<ActStepConfigEntity> actStepMethodList = activitiMapper.qryActStepMethodList(processKey);
        List<StepMethodDefEntity> stepMethodDefList = activitiMapper.qryStepMethodDefList(processKey);
        for (ActStepConfigEntity actStepConfigEntity : actStepMethodList) {
            String stepId = actStepConfigEntity.getStepId();
            // 初始化
            actStepConfigEntity.setStepMethodList(new ArrayList<StepMethodDefEntity>());
            List<StepMethodDefEntity> innerList = actStepConfigEntity.getStepMethodList();
            for (StepMethodDefEntity stepMethodDefEntity : stepMethodDefList) {
                if (stepId.equals(stepMethodDefEntity.getStepId())) {
                    innerList.add(stepMethodDefEntity);
                }
            }
        }
        return actStepMethodList;
    }

    /**
     * 获取各个节点的具体的信息
     *
     * @param processInstId 流程实例id
     * @return
     */
    public Map<String, Object> getProcessTrace(String processInstId) throws Exception {
        Map<String, Object> retObj = new HashMap<>();
        List<Map<String, Object>> activityInfos = new ArrayList<Map<String, Object>>();
        retObj.put("activityInfos", activityInfos);
        // 取流程实例的对应版本定义
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstId).finished().orderByHistoricActivityInstanceId().asc().list();
        //正在执行的没有查到，就要去执行完的历史里去查
        String processDefId = null;
        if (processInstance == null && historicProcessInstance == null) {
            logger.info("未找到该流程实例，无法获取流程节点信息！");
            return null;
        } else if (processInstance != null && historicProcessInstance == null) {
            processDefId = processInstance.getProcessDefinitionId();
        } else if (processInstance == null && historicProcessInstance != null) {
            processDefId = historicProcessInstance.getProcessDefinitionId();
        } else if (processInstance != null && historicProcessInstance != null) {
            processDefId = processInstance.getProcessDefinitionId();
        }
        // 取流程图节点定义列表activitiList
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefId).singleResult();
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(pd.getId());
        List<ActivityImpl> activitiList = processDefinition.getActivities();
        // 取执行步骤信息
        // 优先从缓存取
        Map<String, Object> stepInfoMap = new HashMap<>();
        List<HistoricVariableInstance> list = null;
        stepInfoMap = null == Constant.getCache(processInstId + "stepInfoMap") ? null : (Map<String, Object>) Constant.getCache(processInstId + "stepInfoMap");
        if (null == stepInfoMap || stepInfoMap.size() == 0) {
            list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstId).list();
            for (HistoricVariableInstance variable : list) {
                if (variable.getVariableName().equals("stepInfoMap")) {
                    stepInfoMap = (Map<String, Object>) variable.getValue();
                }
            }
        }
        // 构造返回前台的节点信息，包括：节点id、节点名称、节点描述、节点x、y、width、height
        Map<String, Object> lastActivity = new HashMap<>();
        for (ActivityImpl activity : activitiList) {
            Map<String, Object> activityInfo = new HashMap<String, Object>();
            activityInfo.put("width", activity.getWidth());
            activityInfo.put("height", activity.getHeight());
            activityInfo.put("x", activity.getX());
            activityInfo.put("y", activity.getY());
            activityInfo.put("actId", activity.getId());
            activityInfo.put("name", activity.getProperty("name")); // </strong>ActivityImpl 中没有getName方法，所以此处需要这样获取。
            activityInfo.put("finished", "N");
            activityInfo.put("stepList", (List<Map<String, Object>>) stepInfoMap.get(activity.getId()));
            activityInfo.put("lastStepFinished", (boolean) stepInfoMap.get("lastStepFinished"));
            // 设置哪些节点已完成
            for (HistoricActivityInstance hisActInst : historicActivityInstanceList) {
                String hisActId = hisActInst.getActivityId();
                if (activity.getId().equals(hisActId)) {
                    activityInfo.put("finished", "Y");
                }
            }
            // 设置当前节点
            if (activity.getId().equals(processInstance.getActivityId())) {
                activityInfo.put("current", "Y");
            }
            activityInfos.add(activityInfo);
            if (null != activityInfo.get("stepList")) {
                lastActivity = activityInfo;
            }
        }
        // 查询运行状态等信息
        Map<String, Object> runtimeInfo = new HashMap<>();
        runtimeInfo.put("status", qryProcStatus(processInstId));
        // 优先从缓存取
        String carId = null == Constant.getCache(processInstId + "carCardInfo[car_id]") ? null : Constant.getCache(processInstId + "carCardInfo[car_id]").toString();
        String viewCardId = null == Constant.getCache(processInstId + "mineCardInfo[view_card_id]") ? null : Constant.getCache(processInstId + "mineCardInfo[view_card_id]").toString();
        String workMode = null == Constant.getCache(processInstId + "work_mode") ? null : Constant.getCache(processInstId + "work_mode").toString();
        Boolean lastStepFinished = null == Constant.getCache(processInstId + "lastStepFinished") ? null : (Boolean) Constant.getCache(processInstId + "lastStepFinished");
        if (carId == null || viewCardId == null || workMode == null || null == list) {
            if (null == list || list.isEmpty()) {
                list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstId).list();
            }
            for (HistoricVariableInstance variable : list) {
                if (variable.getVariableName().equals("carCardInfo[car_id]")) {
                    carId = StringUtils.isEmpty(carId) ? variable.getValue().toString() : carId;
                }
                if (variable.getVariableName().equals("mineCardInfo[view_card_id]")) {
                    viewCardId = StringUtils.isEmpty(viewCardId) ? variable.getValue().toString() : viewCardId;
                }
                if (variable.getVariableName().equals("work_mode")) {
                    workMode = StringUtils.isEmpty(workMode) ? variable.getValue().toString() : workMode;
                }
            }
        }
        runtimeInfo.put("carId", carId);
        runtimeInfo.put("viewCardId", viewCardId);
        runtimeInfo.put("workMode", workMode);
        String stepName = "";
        String errInfo = "";
        List<Map<String, Object>> stepList = (List<Map<String, Object>>) lastActivity.get("stepList");
        if (null != stepList && !stepList.isEmpty()) {
            for (Map<String, Object> stepInfo : stepList) {
                stepName = stepInfo.get("stepName").toString();
                errInfo = stepInfo.get("errInfo").toString();
            }
        }
        String name = lastActivity.get("name").toString();
        if ("Start".equals(name)) {
            name = "开始";
        } else if ("End".equals(name)) {
            name = "结束";
        }
        runtimeInfo.put("procStep", name + "（" + stepName + "）");
        runtimeInfo.put("errInfo", errInfo);
        runtimeInfo.put("lastStepFinished", (null == lastStepFinished ? (Boolean) lastActivity.get("lastStepFinished") : lastStepFinished));
        retObj.put("runtimeInfo", runtimeInfo);
        return retObj;
    }

    /**
     * 激活被挂起的流程实例。
     *
     * @param processInstId
     */
    @Override
    public Result<?> activateProcess(String processInstId) throws Exception {
        // 激活流程
        try {
            Constant.setCache(processInstId + "status", "R");
            runtimeService.activateProcessInstanceById(processInstId);
        } catch (Exception e) {
            ;
        }
        // 执行未完成的步骤
        EndActListener endActListener = new EndActListener();
        Object taskObj = Constant.getCache(processInstId + "taskObj"); //优先从缓存取
        ExecutionEntity task = null;
        // 缓存里没有的时候，从数据库流程变量中取
        if (taskObj == null) {
            List<HistoricVariableInstance> list = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstId).list();
            for (HistoricVariableInstance variable : list) {
                if (taskObj == null && variable.getVariableName().equals(processInstId + "taskObj")) {
                    task = (ExecutionEntity) variable.getValue();
                }
            }
        } else {
            task = (ExecutionEntity) Constant.getCache(processInstId + "taskObj");
        }
        endActListener.execute(task);
        // 检查工作模式，决定是否自动流向下一个环节
        // 优先从缓存取
        String workMode = null == Constant.getCache(task.getProcessInstanceId() + "work_mode") ? null : Constant.getCache(task.getProcessInstanceId() + "work_mode").toString();
        // 缓存中没有时，从数据库取
        if (null == workMode) {
            JSONObject paramObj = new JSONObject();
            paramObj.put("processInstId", task.getProcessInstanceId());
            JSONObject workModeObj = new JSONObject();
            try {
                //todo 放放再说，奶奶的这玩意儿
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
            // 由异步的线程来触发signal信号。否则当前环节未执行完，环节未进入等待信号状态。发送signal也没有用。
            String freq = task.getVariable("freq").toString();
            AutoSignalThread thread = new AutoSignalThread(this, Long.parseLong(freq) * 1000, task.getProcessInstanceId());
            thread.start();
        }
        return new Result<>();
    }

    @Override
    public String qryProcStatus(String processInstId) {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
        if (pi == null) {
            return "E"; // 流程已结束
        } else {
            if (pi.isSuspended()) {
                String status = null == Constant.getCache(processInstId + "status") ? null : Constant.getCache(processInstId + "status").toString();
                if ("P".equals(status)) {
                    return "P";
                } else {
                    return "F";
                }
            } else {
                return "R"; // 运行中
            }
        }
    }
}
