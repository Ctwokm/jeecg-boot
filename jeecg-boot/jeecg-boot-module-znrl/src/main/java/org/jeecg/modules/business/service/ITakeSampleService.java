package org.jeecg.modules.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.vo.SampleRptEntity;

import java.util.List;

public interface ITakeSampleService  {
    Page<SampleRptEntity> qryTakeSampleBasicList(Page<SampleRptEntity> pageList, SampleRptEntity sampleRptEntity);

    List<SampleRptEntity> qryTakeSampleBasicList(SampleRptEntity sampleRptEntity);

    void updateRecord(SampleRptEntity sampleRptEntity);
}
