package org.jeecg.modules.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.mapper.MonitorMapper;
import org.jeecg.modules.business.service.IMonitorService;
//import org.jeecg.modules.business.vo.GridModel;
import org.jeecg.modules.business.vo.DeviceBroadEntity;
import org.jeecg.modules.business.vo.DeviceErrEntity;
import org.jeecg.modules.business.vo.LogOpRecEntity;
import org.jeecg.modules.business.vo.ManualSampleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ZJ
 */
@Service("monitorService")
@Transactional
@SuppressWarnings("unchecked")
public class MonitorServiceImpl implements IMonitorService {
    //private static final Logger LOG = LoggerFactory.getLogger(MonitorServiceImpl.class);
    @Autowired
    public MonitorMapper monitorMapper;

    @Override
    public Page<LogOpRecEntity> qryLogModList(Page<LogOpRecEntity> pageList, LogOpRecEntity logOpRecEntity) {
        return pageList.setRecords(monitorMapper.qryLogModList(pageList,logOpRecEntity));
    }

    @Override
    public Page<DeviceErrEntity> qryDeviceErrList(Page<DeviceErrEntity> pageList, DeviceErrEntity deviceErrEntity) {
        return pageList.setRecords(monitorMapper.qryDeviceErrList(pageList,deviceErrEntity));
    }

    @Override
    public Page<DeviceBroadEntity> qryDeviceBroadList(Page<DeviceBroadEntity> pageList, DeviceBroadEntity deviceBroadEntity) {
        return pageList.setRecords(monitorMapper.qryDeviceBroadList(pageList,deviceBroadEntity));
    }

    @Override
    public void deviceErrDeal(DeviceErrEntity deviceErrEntity) {
        monitorMapper.deviceErrDeal(deviceErrEntity);
    }

}
