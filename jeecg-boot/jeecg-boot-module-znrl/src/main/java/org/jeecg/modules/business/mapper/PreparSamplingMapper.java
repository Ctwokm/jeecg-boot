package org.jeecg.modules.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.SamplingRptEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreparSamplingMapper extends BaseMapper<SamplingRptEntity> {
    List<SamplingRptEntity> qrySamplingList(Page<SamplingRptEntity> pageList, @Param("samplingRptEntity") SamplingRptEntity samplingRptEntity);

    List<SamplingRptEntity> qrySamplingList(@Param("samplingRptEntity") SamplingRptEntity samplingRptEntity);

    void updateRecord(SamplingRptEntity samplingRptEntity);
}
