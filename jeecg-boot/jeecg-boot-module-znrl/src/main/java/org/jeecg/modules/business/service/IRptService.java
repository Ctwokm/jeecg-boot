package org.jeecg.modules.business.service;

import org.jeecg.modules.business.vo.WeightRptEntity;

public interface IRptService {

    //查询称重汇总报表
    public WeightRptEntity qryWeightSummaryRpt(WeightRptEntity weightRptEntity);
   //查询火车的汇总报表
    WeightRptEntity qryTrainWeightSummaryRpt(WeightRptEntity weightRptEntity);

}
