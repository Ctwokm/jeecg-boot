package org.jeecg.modules.business.service;


import org.jeecg.modules.business.vo.WorkModeTypeEntity;

import java.util.List;

/**
 * @Auther: Hujw
 * @Date: 2021/4/14
 * @Description:
 */
public interface IConfigService {
    public List<WorkModeTypeEntity> qryWorkModeTypeList(WorkModeTypeEntity workModeTypeEntity);
}
