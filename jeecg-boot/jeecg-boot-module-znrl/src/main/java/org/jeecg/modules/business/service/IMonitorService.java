package org.jeecg.modules.business.service;


//import org.jeecg.modules.business.vo.GridModel;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.vo.DeviceBroadEntity;
import org.jeecg.modules.business.vo.DeviceErrEntity;
import org.jeecg.modules.business.vo.LogOpRecEntity;
import org.jeecg.modules.business.vo.ManualSampleEntity;

import java.util.List;

/**
 * Created by Suyb
 */
public interface IMonitorService {


    Page<LogOpRecEntity> qryLogModList(Page<LogOpRecEntity> pageList, LogOpRecEntity logOpRecEntity);

    Page<DeviceErrEntity> qryDeviceErrList(Page<DeviceErrEntity> pageList, DeviceErrEntity deviceErrEntity);

    Page<DeviceBroadEntity> qryDeviceBroadList(Page<DeviceBroadEntity> pageList, DeviceBroadEntity deviceBroadEntity);

    void deviceErrDeal(DeviceErrEntity deviceErrEntity);
}
