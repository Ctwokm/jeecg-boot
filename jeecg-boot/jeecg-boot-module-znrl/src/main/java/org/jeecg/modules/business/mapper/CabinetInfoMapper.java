package org.jeecg.modules.business.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.CabinetEntity;
import org.jeecg.modules.business.vo.CabinetOpRecEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Classname CabinetInfoMapper
 * @Description 存样柜业务处理
 * @Date 2021/3/16 10:47
 * @Created by jufq
 */

@Repository
public interface CabinetInfoMapper {


    //查询气动存查样柜历史信息
    //public List<CabinetOpRecEntity> qryCabinetOpRecList(CabinetOpRecEntity cabinetOpRecEntity);
    public Page<CabinetOpRecEntity> qryCabinetOpRecList(@Param("page")Page<CabinetOpRecEntity> page, @Param("cabinetOpRecEntity")CabinetOpRecEntity cabinetOpRecEntity);

    //查询气动存查样柜信息
    public List<CabinetEntity> qryCabinetInfoList(CabinetEntity cabinetEntity);
}
