package org.jeecg.modules.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.vo.SamplingRptEntity;

import java.util.List;

public interface IPreparSamplingService {
    Page<SamplingRptEntity> qrySamplingList(Page<SamplingRptEntity> pageList, SamplingRptEntity sampleRptEntity);

    List<SamplingRptEntity> qrySamplingList(SamplingRptEntity sampleRptEntity);

    void updateRecord(SamplingRptEntity sampleRptEntity);
}
