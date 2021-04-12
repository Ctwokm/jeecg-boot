package org.jeecg.modules.business.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.DeviceBroadEntity;
import org.jeecg.modules.business.vo.DeviceErrEntity;
import org.jeecg.modules.business.vo.LogOpRecEntity;
import org.jeecg.modules.business.vo.ManualSampleEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Suyb
 */
@Repository
public interface MonitorMapper {

    List<LogOpRecEntity> qryLogModList(Page<LogOpRecEntity> pageList,@Param("logOpRecEntity") LogOpRecEntity logOpRecEntity);

    List<DeviceErrEntity> qryDeviceErrList(Page<DeviceErrEntity> pageList,@Param("deviceErrEntity") DeviceErrEntity deviceErrEntity);

    List<DeviceBroadEntity> qryDeviceBroadList(Page<DeviceBroadEntity> pageList,@Param("deviceBroadEntity") DeviceBroadEntity deviceBroadEntity);

    void deviceErrDeal(DeviceErrEntity deviceErrEntity);
}
