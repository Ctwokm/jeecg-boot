package org.jeecg.modules.rapid.mapper;

import org.jeecg.modules.rapid.entity.ShipInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: ship_info
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
public interface ShipInfoMapper extends BaseMapper<ShipInfo> {

    void add(ShipInfo shipInfo);

    void deteleByid(String shipId);

    void editById(ShipInfo shipInfo);
}
