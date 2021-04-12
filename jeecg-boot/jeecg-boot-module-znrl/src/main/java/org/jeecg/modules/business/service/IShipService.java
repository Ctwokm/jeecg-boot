package org.jeecg.modules.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.vo.ShipBatchEntity;
import org.jeecg.modules.business.vo.ShipEntity;

import java.util.List;

public interface IShipService extends IService<ShipEntity> {
    Page<ShipEntity> recordByPage(Page<ShipEntity> page, ShipEntity shipEntity);

    Page<ShipEntity> cargoRecordByPage(Page<ShipEntity> page, ShipEntity shipEntity);

    Result editByCall(ShipEntity shipEntity, String pkgName);

    List<ShipBatchEntity> shipBatchInfo(ShipBatchEntity shipBatchEntity);

    Result forceCompleteBatch(ShipBatchEntity shipBatchEntity);
}
