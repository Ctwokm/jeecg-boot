package org.jeecg.modules.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.mapper.PreparSamplingMapper;
import org.jeecg.modules.business.service.IPreparSamplingService;
import org.jeecg.modules.business.vo.SamplingRptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: ZJ
 * @create: 2021-02-22 14:03
 **/
@Service("preparSampingService")
@Transactional
public class PreparSamplingServiceImpl implements IPreparSamplingService {

    @Autowired
    public PreparSamplingMapper preparSamplingMapper;
    @Override
    public Page<SamplingRptEntity> qrySamplingList(Page<SamplingRptEntity> pageList, SamplingRptEntity samplingRptEntity) {
        return pageList.setRecords(preparSamplingMapper.qrySamplingList(pageList,samplingRptEntity));
    }

    @Override
    public List<SamplingRptEntity> qrySamplingList(SamplingRptEntity samplingRptEntity) {
        return preparSamplingMapper.qrySamplingList(samplingRptEntity);
    }

    @Override
    public void updateRecord(SamplingRptEntity samplingRptEntity) {
        preparSamplingMapper.updateRecord(samplingRptEntity);
    }
}
