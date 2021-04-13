package org.jeecg.modules.common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.vo.PkgEntity;
import org.jeecg.modules.business.vo.RegisterEntity;

import java.util.List;
import java.util.Map;

public interface IBizToolService {
    /**
     * @Author Hujw
     * @Description //通用查询语句，可以用来改造config_sql表中的sql语句，用于直接移植到此处
     * @Date 10:16 2021/3/2
     * @Param
     * @return
     **/
    Page queryByPage(Page page, Map<String, Object> params);
    /**
     * @Author Hujw
     * @Description //通用查询语句，可以用来改造config_sql表中的sql语句，用于直接移植到此处
     * @Date 10:16 2021/3/2
     * @Param
     * @return
     **/
    List<Map<String,Object>> query(Map<String, Object> params);
    /**
     * @Author Hujw
     * @Description //通用存储过程调用语句，用来改造系统中存储过程，统一的封装调用接口，方便管理使用
     * @Date 10:16 2021/3/2
     * @Param
     * @return
     **/
    public void doCall(PkgEntity pkgEntity);
}
