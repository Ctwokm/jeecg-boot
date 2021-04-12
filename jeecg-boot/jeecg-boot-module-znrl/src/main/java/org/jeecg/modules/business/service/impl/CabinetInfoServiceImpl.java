package org.jeecg.modules.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.mapper.CabinetInfoMapper;
import org.jeecg.modules.business.service.ICabinetInfoService;
import org.jeecg.modules.business.vo.CabinetEntity;
import org.jeecg.modules.business.vo.CabinetOpRecEntity;
import org.jeecg.modules.business.vo.RegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname CabinetInfoServiceImpl
 * @Description 存样柜业务处理
 * @Date 2021/3/16 11:22
 * @Created by jufq
 */

@Service("cabinetInfoService")
@Transactional
public class CabinetInfoServiceImpl implements ICabinetInfoService {

    @Autowired
    private CabinetInfoMapper cabinetInfoMapper;

    @Override
    public Page<CabinetOpRecEntity> qryCabinetOpRecList(Page<CabinetOpRecEntity> page, CabinetOpRecEntity cabinetOpRecEntity) {
        return cabinetInfoMapper.qryCabinetOpRecList(page, cabinetOpRecEntity);
    }

    @Override
    public List<CabinetEntity> qryCabinetInfoList(CabinetEntity cabinetEntity) {
        return cabinetInfoMapper.qryCabinetInfoList(cabinetEntity);
    }

}
