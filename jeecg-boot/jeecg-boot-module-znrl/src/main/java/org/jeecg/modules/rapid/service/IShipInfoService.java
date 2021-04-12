package org.jeecg.modules.rapid.service;

import org.jeecg.modules.rapid.entity.ShipInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: ship_info
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
public interface IShipInfoService extends IService<ShipInfo> {

    void add(ShipInfo shipInfo);

    void deleteById(String shipId);

    void editById(ShipInfo shipInfo);
}
