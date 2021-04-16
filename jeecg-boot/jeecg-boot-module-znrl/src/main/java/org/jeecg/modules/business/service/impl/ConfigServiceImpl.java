package org.jeecg.modules.business.service.impl;


import org.jeecg.modules.business.mapper.WorkModeTypeMapper;
import org.jeecg.modules.business.service.IConfigService;
import org.jeecg.modules.business.vo.WorkModeTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Hujw
 * @Date: 2021/4/14
 * @Description: 
 */
@Service("configService")
@Transactional
public class ConfigServiceImpl implements IConfigService {

    @Autowired
    public WorkModeTypeMapper workModeTypeMapper;
    
    @Override
    public List<WorkModeTypeEntity> qryWorkModeTypeList(WorkModeTypeEntity workModeTypeEntity) {
        return workModeTypeMapper.qryWorkModeTypeList(workModeTypeEntity);
    }

}
