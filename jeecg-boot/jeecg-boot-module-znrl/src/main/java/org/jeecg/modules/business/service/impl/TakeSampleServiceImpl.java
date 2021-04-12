package org.jeecg.modules.business.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.mapper.TakeSampleMapper;
import org.jeecg.modules.business.service.ITakeSampleService;
import org.jeecg.modules.business.vo.SampleRptEntity;
import org.jeecg.modules.temp.entity.TakeSampleRec;
import org.jeecg.modules.temp.mapper.BatchNoInfoMapper;
import org.jeecg.modules.temp.mapper.TakeSampleRecMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: ZJ
 * @create: 2021-02-22 14:03
 **/
@Service("takeSampleService")
@Transactional
public class TakeSampleServiceImpl implements ITakeSampleService {

    @Autowired
    public TakeSampleMapper takeSampleMapper;
    @Autowired
    public TakeSampleRecMapper takeSampleRecMapper;
    @Autowired
    public BatchNoInfoMapper batchNoInfoMapper;
    @Override
    public Page<SampleRptEntity> qryTakeSampleBasicList(Page<SampleRptEntity> pageList, SampleRptEntity sampleRptEntity) {
        return pageList.setRecords(takeSampleMapper.qryTakeSampleBasicList(pageList,sampleRptEntity));
    }

    @Override
    public List<SampleRptEntity> qryTakeSampleBasicList(SampleRptEntity sampleRptEntity) {
        return takeSampleMapper.qryTakeSampleBasicList(sampleRptEntity);
    }

    @Override
    public void updateRecord(SampleRptEntity sampleRptEntity) {
        //逻辑由存储过程搬到后台
        //实体类获取
        String sampleCode = sampleRptEntity.getSampleCode();
        String startTime = sampleRptEntity.getStartTime();
        String sampleTyp = sampleRptEntity.getSampleTyp();
        String endTime = sampleRptEntity.getEndTime();
        String userCode = sampleRptEntity.getUserCode();
        String sampleGape = sampleRptEntity.getSampleGape();
        String sampleQty = sampleRptEntity.getSampleQty();
        String sampleCnt = sampleRptEntity.getSampleCnt();
        String totalNetQty = sampleRptEntity.getTotalNetQty();
        //响应
        sampleRptEntity.setResCode("0");
        sampleRptEntity.setResMsg("ok");
        //自定义变量
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("sample_code",sampleCode);
        List<TakeSampleRec> takeSampleRecs = takeSampleRecMapper.selectByMap(columnMap);
        //1.判断更新或插入
        if(takeSampleRecs.size() > 0){
            //2.更新
            TakeSampleRec takeSampleRec = new TakeSampleRec();
            for(int i = 0;i<takeSampleRecs.size();i++){
                takeSampleRec.setTakeSampleRecId(takeSampleRecs.get(i).getTakeSampleRecId());
                takeSampleRec.setSampleTyp(sampleTyp);
                takeSampleRec.setStartTime(DateUtil.parse(startTime));
                takeSampleRec.setEndTime(DateUtil.parse(endTime));
                takeSampleRec.setSampleGap(Integer.valueOf(sampleGape));
                takeSampleRec.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
                takeSampleRec.setSampleCnt(Integer.valueOf(sampleCnt));
                takeSampleRec.setSampleQty(Convert.toBigDecimal(sampleQty));
                takeSampleRec.setUpdateCode(userCode);
                takeSampleRec.setOpCode(userCode);
                takeSampleRecMapper.updateById(takeSampleRec);
            }

        }else {
            //3,插入
            TakeSampleRec takeSampleRec = new TakeSampleRec();
            takeSampleRec.setSampleCode(sampleCode);
            takeSampleRec.setSampleTyp(sampleTyp);
            takeSampleRec.setStartTime(DateUtil.parse(startTime));
            takeSampleRec.setEndTime(DateUtil.parse(endTime));
            takeSampleRec.setSampleGap(Integer.valueOf(sampleGape));
            takeSampleRec.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
            takeSampleRec.setSampleCnt(Integer.valueOf(sampleCnt));
            takeSampleRec.setSampleQty(Convert.toBigDecimal(sampleQty));
            takeSampleRec.setUpdateCode(userCode);
            takeSampleRec.setOpCode(userCode);
            takeSampleRecMapper.insert(takeSampleRec);
        }
        //更新批次表
//        QueryWrapper<BatchNoInfo> batchNoInfoQueryWrapper =new QueryWrapper<>();
//        batchNoInfoQueryWrapper.eq("sample_code",sampleCode);
//        BatchNoInfo batchNoInfo = new BatchNoInfo();
//        batchNoInfo.setAllNetQty(Convert.toBigDecimal(totalNetQty));
//        batchNoInfoMapper.update(batchNoInfo,batchNoInfoQueryWrapper);

    }
}
