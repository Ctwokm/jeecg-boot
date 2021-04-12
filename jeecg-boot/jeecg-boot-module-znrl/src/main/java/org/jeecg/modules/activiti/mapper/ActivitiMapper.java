package org.jeecg.modules.activiti.mapper;

import org.jeecg.modules.activiti.entity.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xieyt on 2017-4-12.
 */

@Repository
public interface ActivitiMapper {

    //查询工作流配置
    public List<ActivitiEntity> qryActivitiConfig(ActivitiEntity activitiEntity);

    //执行业务操作任务
    public void executeBusiness(ActivitiEntity activitiEntity);

    public String getAssigneeName(String assignee);

    //执行业务操作任务
    public void callbackToWriteProcessInstId(ActivitiEntity activitiEntity);

    public Integer qryTasksHisListCnt(ActivitiHisEntity activitiHisRpt);

    public List<ActivitiHisEntity> qryTasksHisList(ActivitiHisEntity activitiHisRpt);

    public List<ActivitiHisEntity> qryTasksHisChildList(ActivitiHisEntity activitiHisRpt);

    // 保存任务跟踪信息ACT_BZ_TASK_TRACE
    void saveActBzTaskTrace(TaskInfoEntity task);

    // 保存任务主表信息
    void saveActBzTask(TaskInfoEntity task);

    // 跟流程ID，查询流程配置的步骤+步骤执行方法列表
    List<ActStepConfigEntity> qryActStepMethodList(@Param("processKey") String processKey);

    // 查询方法执行步骤列表
    List<StepMethodDefEntity> qryStepMethodDefList(@Param("processKey") String processKey);

    // 写测点或写入业务数据
    public void calPkgTruck(CallTruckEntity param);
}
