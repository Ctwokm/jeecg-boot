package org.jeecg.modules.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.vo.*;

import java.util.List;
/**
 * @author zoudp
 * @create 20210316
 */

public interface ICarInfoService {
    /**
     * 汽车来煤查询方法_jj模板
     * @param page
     * @return
     */
    public Page<CarTransRecordEntity> qryCarTransRecordList(Page<CarTransRecordEntity> page,CarTransRecordEntity carTransRecordEntity);

    /**
     * 汽车来煤查询方法_cg模板
     * @param page
     * @param carInCoalEntity
     * @return
     */
    public Page<CarInCoalEntity> qryCarInCoalList(Page<CarInCoalEntity> page,CarInCoalEntity carInCoalEntity);

    /**
     * 汽车来煤删除记录方法；
     * @param carTransRecordEntity
     */
    public void deleteByCarInfo(CarTransRecordEntity carTransRecordEntity);

    /**
     * 查询定位某个车辆的方法；
     * @param carInfoEntity
     * @return
     */
    public CarInfoEntity focusCar(CarInfoEntity carInfoEntity);

    /**
     * 新增汽车来煤的方法
     * @param pkgEntity
     */
    public void addCarTransRecord(PkgEntity pkgEntity);

    /**
     * 查询汽车发卡的页面
     * @param page
     * @param carInfoRegisterEntity
     * @return
     */
    public Page<CarInfoRegisterEntity> qryCarRegisterList(Page<CarInfoRegisterEntity> page, CarInfoRegisterEntity carInfoRegisterEntity);

    /**
     * 查询汽车矿卡发卡的页面
     * @param page
     * @param registerEntity
     * @return
     */
    public Page<RegisterEntity> qryMineCardList(Page<RegisterEntity> page,RegisterEntity registerEntity);

    /**
     * 修改汽车矿卡的方法；
     * @param registerEntity
     */
    public void modMineCardInfo(RegisterEntity registerEntity);

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
