package org.jeecg.modules.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.business.mapper.BizToolMapper;
import org.jeecg.modules.business.service.IBizToolService;
import org.jeecg.modules.business.vo.PkgEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
@Service("bizToolService")
public class BizToolServiceImpl implements IBizToolService {

    @Autowired
    private BizToolMapper bizToolMapper;

    /**
     * @Author Hujw
     * @Description //通用查询语句，可以用来改造config_sql表中的sql语句，用于直接移植到此处
     * @Date 10:16 2021/3/2
     * @Param
     * @return
     **/
    public Page queryByPage(Page page, Map<String, Object> params) {

        return bizToolMapper.queryByPage(page,params);
    }
    /**
     * @Author Hujw
     * @Description //通用查询语句，可以用来改造config_sql表中的sql语句，用于直接移植到此处
     * @Date 10:16 2021/3/2
     * @Param
     * @return
     **/
    public List<Map<String,Object>> query(Map<String, Object> params) {

        return bizToolMapper.query(params);
    }

    /**
     * @Author Hujw
     * @Description //通用存储过程调用语句，用来改造系统中存储过程，统一的封装调用接口，方便管理使用
     * @Date 10:16 2021/3/2
     * @Param
     * @return
     **/
    @Override
    public void doCall(PkgEntity pkgEntity) {
        bizToolMapper.doCall(pkgEntity);
    }

}
