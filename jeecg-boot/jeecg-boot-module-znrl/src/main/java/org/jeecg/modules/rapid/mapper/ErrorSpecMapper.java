package org.jeecg.modules.rapid.mapper;

import org.jeecg.modules.rapid.entity.ErrorSpec;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: error_spec
 * @Author: fz
 * @Date:   2021-03-15
 * @Version: V1.0
 */
public interface ErrorSpecMapper extends BaseMapper<ErrorSpec> {

    void add(ErrorSpec errorSpec);

    void editById(ErrorSpec errorSpec);

    void deleteErrorById(String errorSpecId);
}
