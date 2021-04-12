package org.jeecg.modules.business.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zoudp
 * @create 20210316
 */
@Repository
public interface CarInfoMapper  {

    /**
     * 查询汽车来煤信息_jj模板
     * @param page
     * @param carTransRecordEntity
     * @return
     */
    public List<CarTransRecordEntity> qryCarTransRecordList(@Param("page")Page<CarTransRecordEntity> page, @Param("carTransRecordEntity") CarTransRecordEntity carTransRecordEntity);

    /**
     * 查询汽车来煤信息_cg模板
     * @param page
     * @param carInCoalEntity
     * @return
     */
    public List<CarInCoalEntity> qryCarInCoalList(@Param("page")Page<CarInCoalEntity> page,@Param("carInCoalEntity") CarInCoalEntity carInCoalEntity);

    /**
     * 汽车来煤管理的删除操作
     * @param carTransRecordEntity
     */
    public void  deleteByCarInfo(CarTransRecordEntity carTransRecordEntity);

    /**
     * 查询定位某个车辆的方法；
     * @param carInfoEntity
     * @return
     */
    public CarInfoEntity focusCar(CarInfoEntity carInfoEntity);

    /**
     * 新增汽车来煤信息的方法 ,走数据库存储过程
     * @param pkgEntity
     */
    public void addCarTransRecord(PkgEntity pkgEntity);

    /**
     * 查询汽车注册页面的方法
     * @param page
     * @param carInfoRegisterEntity
     * @return
     */
    public List<CarInfoRegisterEntity> qryCarRegisterList(@Param("page")Page<CarInfoRegisterEntity> page, @Param("carInfoRegisterEntity") CarInfoRegisterEntity carInfoRegisterEntity);

    /**
     * 查询汽车矿卡发卡的方法
     * @param page
     * @param registerEntity
     * @return
     */
    public List<RegisterEntity> qryMineCardList(@Param("page")Page<RegisterEntity> page,@Param("registerEntity") RegisterEntity registerEntity);

    /**
     * 修改汽车矿卡的方法
     * @param registerEntity
     */
    public void modMineCardInfo(RegisterEntity registerEntity) ;

    /**
     * 矿卡发卡的方法（新增矿卡）
     * @param registerEntity
     */
    public void addMineCardInfo(RegisterEntity registerEntity);

    /**
     * 校验卡的方法（有效的只有一个）
     * @param registerEntity
     * @return
     */
    public Integer checkCard(RegisterEntity registerEntity);

    /**
     * 矿卡作废的方法
     * @param registerEntity
     */
    public void deleteByMineCardId(RegisterEntity registerEntity);

    /**
     * 矿卡作废移除的方法；
     * @param registerEntity
     */
    public void moveMsgToMineHis(RegisterEntity registerEntity);

    /**
     * 汽车采样点分布图查询方法；
     * @param registerEntity
     * @return
     */
    public List queryPointList(RegisterEntity registerEntity);

}
