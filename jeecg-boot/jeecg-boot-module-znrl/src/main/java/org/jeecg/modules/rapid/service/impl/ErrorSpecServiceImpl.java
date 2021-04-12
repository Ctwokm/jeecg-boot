package org.jeecg.modules.rapid.service.impl;

import org.jeecg.modules.rapid.entity.ErrorSpec;
import org.jeecg.modules.rapid.mapper.ErrorSpecMapper;
import org.jeecg.modules.rapid.service.IErrorSpecService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Description: error_spec
 * @Author: fz
 * @Date:   2021-03-15
 * @Version: V1.0
 */
@Service
public class ErrorSpecServiceImpl extends ServiceImpl<ErrorSpecMapper, ErrorSpec> implements IErrorSpecService {
    @Resource
    private ErrorSpecMapper errorSpecMapper;
    @Override
    public void add(ErrorSpec errorSpec) {
            errorSpecMapper.add(errorSpec);
    }

    @Override
    public void editById(ErrorSpec errorSpec) {
        errorSpecMapper.editById(errorSpec);
    }

    @Override
    public void deleteErrorById(String errorSpecId) {
        errorSpecMapper.deleteErrorById(errorSpecId);
    }
}
