package org.jeecg.modules.business.service.impl;

import org.jeecg.modules.business.mapper.RptMapper;
import org.jeecg.modules.business.service.IRptService;
import org.jeecg.modules.business.vo.WeightRptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("rptService")
@Transactional
@SuppressWarnings("unchecked")
public class RptServiceImpl implements IRptService {

    @Autowired
    public RptMapper rptMapper;
    @Override
    public WeightRptEntity qryWeightSummaryRpt(WeightRptEntity weightRptEntity) {
        Integer intEntryTotalCnt = rptMapper.qryEntryTotalCnt(weightRptEntity);
        Integer intWeightTotalCnt = rptMapper.qryWeightTotalCnt(weightRptEntity);
        Integer intLightTotalCnt = rptMapper.qryLightTotalCnt(weightRptEntity);

        String intTotalMz = rptMapper.qryTotalMz(weightRptEntity);
        String intTotalPz = rptMapper.qryTotalPz(weightRptEntity);
        String intTotalNet = rptMapper.qryTotalNet(weightRptEntity);
        String intTotalTick = rptMapper.qryTotalTick(weightRptEntity);

        String intTotalKd = "0";
        if (weightRptEntity.getPowerFactory() != null && "ZJ".equals(weightRptEntity.getPowerFactory())){
            intTotalKd = rptMapper.qryTotalKd4ZJ(weightRptEntity);
        } else {
            intTotalKd = rptMapper.qryTotalKd(weightRptEntity);
        }


        String intTotalHpQty = rptMapper.qryTotalHpQty(weightRptEntity);
        String intTotalNoKdNetQty = rptMapper.qryTotalNoKdNetQty(weightRptEntity);
        String intTotalDiffQty = rptMapper.qryTotalDiffQty(weightRptEntity);
        //设置返回结果
        WeightRptEntity w = new WeightRptEntity();
        w.setTotalEntryCnt(intEntryTotalCnt.toString());
        w.setTotalCZCnt(intWeightTotalCnt.toString());
        w.setTotalJQCnt(intLightTotalCnt.toString());

        w.setTotalMzQty(intTotalMz);
        w.setTotalPzQty(intTotalPz);
        w.setTotalNetQty(intTotalNet);
        w.setTotalTickQty(intTotalTick);
        w.setTotalKdQty(intTotalKd);
        w.setTotalHpQty(intTotalHpQty);
        w.setTotalNoKdNetQty(intTotalNoKdNetQty);
        w.setTotalDiffQty(intTotalDiffQty);
        return w;
    }

    @Override
    public WeightRptEntity qryTrainWeightSummaryRpt(WeightRptEntity weightRptEntity) {
        Integer intEntryTotalCnt = rptMapper.qryTrainEntryTotalCnt(weightRptEntity);
        Integer intWeightTotalCnt = rptMapper.qryTrainWeightTotalCnt(weightRptEntity);

        String intTotalMz = rptMapper.qryTrainTotalMz(weightRptEntity);
        String intTotalPz = rptMapper.qryTrainTotalPz(weightRptEntity);
        String intTotalNet = rptMapper.qryTrainTotalNet(weightRptEntity);
        String intTotalTick = rptMapper.qryTrainTotalTick(weightRptEntity);

        //设置返回结果
        WeightRptEntity w = new WeightRptEntity();
        w.setTotalEntryCnt(intEntryTotalCnt.toString());
        w.setTotalCZCnt(intWeightTotalCnt.toString());
        w.setTotalMzQty(intTotalMz);
        w.setTotalPzQty(intTotalPz);
        w.setTotalNetQty(intTotalNet);
        w.setTotalTickQty(intTotalTick);
        return w;
    }
}
