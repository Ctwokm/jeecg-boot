package org.jeecg.modules.business.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.SampleRptEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TakeSampleMapper extends BaseMapper<SampleRptEntity> {
    List<SampleRptEntity> qryTakeSampleBasicList(Page<SampleRptEntity> pageList, @Param("sampleRptEntity") SampleRptEntity sampleRptEntity);

    List<SampleRptEntity> qryTakeSampleBasicList(@Param("sampleRptEntity") SampleRptEntity sampleRptEntity);

    void updateRecord(SampleRptEntity sampleRptEntity);
}
