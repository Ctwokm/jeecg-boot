package org.jeecg.modules.activiti.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.activiti.entity.ActStepConfigEntity;
import org.jeecg.modules.activiti.entity.ActivitiEntity;
import org.jeecg.modules.activiti.entity.ActivitiHisEntity;
import org.jeecg.modules.activiti.entity.TaskInfoEntity;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by xieyt on 2017-4-7.
 */

public interface IActivitiService {
    //查询可以发布流程的列表
    public List<ActivitiEntity> listToDeployBpmn() throws RuntimeException;

    //发布流程
    public Result<?> deployFlow(String processKey) throws RuntimeException;

    //启动业务流程
    public Result<?> startProcess(ActivitiEntity activitiEntity) throws RuntimeException;

    //启动分公司平台业务流程
    public Result<?> startProcess(Connection con, ActivitiEntity activitiEntity) throws RuntimeException;

    //查看待办任务（包括待办的和待领取的）
    public Result<?> qryTodoTasks(String assignee, String processKey) throws RuntimeException;

    //根据任务ID查看任务的详情
    public Result<?> qryTaskById(String taskId) throws RuntimeException;

    //查看示例流程历史信息
    public Result<?> qryHistoryProcessInst(String processInstanceId) throws RuntimeException;

    //执行任务节点
    public Result<?> doTask(ActivitiEntity activitiEntity) throws RuntimeException;

    //查询工作流配置
    public List<ActivitiEntity> qryActivitiConfig(ActivitiEntity activitiEntity) throws RuntimeException;

    //执行业务逻辑操作
    public void executeBusiness(ActivitiEntity activitiEntity) throws RuntimeException;

    //生成流程图
    public InputStream generateImgInputStream(String processInstId) throws RuntimeException;

    //查询审批历史查询
    public Result<?> qryTasksHisList(ActivitiHisEntity activitiHisRpt);

    //查询审批历史查询子表数据
    public Result<?> qryTasksHisChildList(ActivitiHisEntity activitiHisRpt);

    //提交完成委托登记任务
    public Result<?> completeEntrustTask(Map<String, Object> paramMap);

    // 保存任务跟踪信息ACT_BZ_TASK_TRACE
    public void saveActBzTaskTrace(TaskInfoEntity task);

    // 将任务分配给指定的执行人
    public Result<?> assignTask2Staff(Map<String, Object> paramMap);

    // 推动ReceiveTask流程向下流转
    public Result<?> signal(String processInstanceId);

    // 跟流程ID，查询流程配置的步骤+步骤执行方法列表
    List<ActStepConfigEntity> qryActStepMethodList(String processKey);

    /**
     * 获取各个节点的具体的信息
     *
     * @param wfKey 流程定义的key
     * @return
     */
    public Map<String, Object> getProcessTrace(String wfKey) throws Exception;

    /**
     * 激活被挂起的流程实例。
     * @param processInstId
     */
    public Result<?> activateProcess(String processInstId) throws Exception;

    /**
     * 查询流程实例运行状态
     * @param processInstId
     * @return
     */
    String qryProcStatus(String processInstId);
}
