package org.jeecg.modules.rapid.service.impl;

import org.jeecg.modules.rapid.entity.ShipInfo;
import org.jeecg.modules.rapid.mapper.ShipInfoMapper;
import org.jeecg.modules.rapid.service.IShipInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: ship_info
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
@Service
public class ShipInfoServiceImpl extends ServiceImpl<ShipInfoMapper, ShipInfo> implements IShipInfoService {

    @Resource
    private ShipInfoMapper shipInfoMapper;
    @Override
    public void add(ShipInfo shipInfo) {
        shipInfoMapper.add(shipInfo);
    }

    @Override
    public void deleteById(String shipId) {
        shipInfoMapper.deteleByid(shipId);
    }

    @Override
    public void editById(ShipInfo shipInfo) {
        shipInfoMapper.editById(shipInfo);
    }
}
