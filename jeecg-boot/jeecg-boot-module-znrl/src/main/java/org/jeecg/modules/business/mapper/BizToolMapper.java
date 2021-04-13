//package org.jeecg.modules.business.mapper;
//
//import java.util.List;
//import java.util.Map;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import org.apache.ibatis.annotations.Param;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.modules.business.vo.PkgEntity;
//import org.springframework.stereotype.Repository;
//
///**
// * @Description: 通用查询及存过调用方法
// * @Author: jeecg-boot
// * @Date:   2021-02-12
// * @Version: V1.0
// */
//
//@Repository
//public interface BizToolMapper {
//
//    /**
//     * @Author Hujw
//     * @Description //通用查询语句带分页，可以用来改造config_sql表中的sql语句，或者常用的查询语句用于直接移植到此处
//     * @Date 10:09 2021/3/2
//     * @Param
//     * @return
//     **/
//    public Page queryByPage(@Param("page")Page page, @Param("params") Map<String, Object> params);
//    /**
//     * @Author Hujw
//     * @Description //通用查询语句，可以用来改造config_sql表中的sql语句，用于直接移植到此处
//     * @Date 10:16 2021/3/2
//     * @Param
//     * @return
//     **/
//    public List<Map<String,Object>> query(Map<String, Object> params);
//    /**
//     * @Author Hujw
//     * @Description //通用存储过程调用语句，用来改造系统中存储过程，统一的封装调用接口，方便管理使用
//     * @Date 10:11 2021/3/2
//     * @Param
//     * @return
//     **/
//    public void doCall(PkgEntity pkgEntity);
//}
