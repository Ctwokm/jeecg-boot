package org.jeecg.modules.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.ShipBatchEntity;
import org.jeecg.modules.business.vo.ShipEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipMapper extends BaseMapper<ShipEntity> {
    List<ShipEntity> recordByPage(@Param("page")Page<ShipEntity> page, @Param("shipEntity") ShipEntity shipEntity);

    List<ShipEntity> cargoRecordByPage(@Param("page")Page<ShipEntity> page, @Param("shipEntity") ShipEntity shipEntity);

    List<ShipBatchEntity> shipBatchInfo(@Param("shipBatchEntity") ShipBatchEntity shipBatchEntity);

    void forceCompleteBatch(@Param("shipBatchEntity") ShipBatchEntity shipBatchEntity);
}

