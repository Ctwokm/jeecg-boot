package org.jeecg.modules.temp.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.temp.entity.LabReport;
import org.springframework.stereotype.Repository;

/**
 * @Description: lab_report
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Repository
public interface LabReportMapper extends BaseMapper<LabReport> {
}
