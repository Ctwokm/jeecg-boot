package org.jeecg.modules.rapid.service;

import org.jeecg.modules.rapid.entity.ErrorSpec;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: error_spec
 * @Author: fz
 * @Date:   2021-03-15
 * @Version: V1.0
 */
public interface IErrorSpecService extends IService<ErrorSpec> {

    void add(ErrorSpec errorSpec);

    void editById(ErrorSpec errorSpec);

    void deleteErrorById(String errorSpecId);
}
