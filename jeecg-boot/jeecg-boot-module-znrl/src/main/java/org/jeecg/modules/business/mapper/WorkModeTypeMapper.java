package org.jeecg.modules.business.mapper;

import org.jeecg.modules.business.vo.WorkModeTypeCommit;
import org.jeecg.modules.business.vo.WorkModeTypeEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkModeTypeMapper {

    public List<WorkModeTypeEntity> qryWorkModeTypeList(WorkModeTypeEntity workModeTypeEntity);

    public  void addSystemWorkModeLog(WorkModeTypeCommit WorkModeTypeCommit);

    public  void addSystemWorkMode(WorkModeTypeCommit WorkModeTypeCommit);

    public  void modiSystemWorkMode(WorkModeTypeCommit WorkModeTypeCommit);

    public WorkModeTypeCommit qrySystemWorkMode(WorkModeTypeCommit WorkModeTypeCommit);

}
