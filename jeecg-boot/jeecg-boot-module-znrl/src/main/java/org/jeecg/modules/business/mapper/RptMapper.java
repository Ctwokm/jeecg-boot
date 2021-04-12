package org.jeecg.modules.business.mapper;

import org.jeecg.modules.business.vo.WeightRptEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RptMapper {

//    ---------------------------------------汽车
    Integer qryEntryTotalCnt(WeightRptEntity weightRptEntity);

    Integer qryWeightTotalCnt(WeightRptEntity weightRptEntity);

    Integer qryLightTotalCnt(WeightRptEntity weightRptEntity);

    String qryTotalMz(WeightRptEntity weightRptEntity);

    String qryTotalPz(WeightRptEntity weightRptEntity);

    String qryTotalNet(WeightRptEntity weightRptEntity);

    String qryTotalTick(WeightRptEntity weightRptEntity);

    String qryTotalKd4ZJ(WeightRptEntity weightRptEntity);

    String qryTotalKd(WeightRptEntity weightRptEntity);

    String qryTotalHpQty(WeightRptEntity weightRptEntity);

    String qryTotalNoKdNetQty(WeightRptEntity weightRptEntity);

    String qryTotalDiffQty(WeightRptEntity weightRptEntity);
//    ---------------------------------------汽车

//    ---------------------------------------火车
    Integer qryTrainEntryTotalCnt(WeightRptEntity weightRptEntity);

    Integer qryTrainWeightTotalCnt(WeightRptEntity weightRptEntity);

    String qryTrainTotalMz(WeightRptEntity weightRptEntity);

    String qryTrainTotalPz(WeightRptEntity weightRptEntity);

    String qryTrainTotalNet(WeightRptEntity weightRptEntity);

    String qryTrainTotalTick(WeightRptEntity weightRptEntity);

//    ---------------------------------------火车
}
