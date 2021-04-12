package org.jeecg.modules.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.vo.CabinetEntity;
import org.jeecg.modules.business.vo.CabinetOpRecEntity;
import org.jeecg.modules.business.vo.RegisterEntity;

import java.util.List;

/**
 * @Classname CabinetInfoService
 * @Description 存样柜业务处理
 * @Date 2021/3/16 10:56
 * @Created by jufq
 */
public interface ICabinetInfoService {

    //查询气动存查样柜历史信息
   // public List<CabinetOpRecEntity> qryCabinetOpRecList(CabinetOpRecEntity cabinetOpRecEntity);

    public Page<CabinetOpRecEntity> qryCabinetOpRecList(Page<CabinetOpRecEntity> page, CabinetOpRecEntity cabinetOpRecEntity);


    //查询气动存查样柜信息
    public List<CabinetEntity> qryCabinetInfoList(CabinetEntity cabinetEntity);
}
