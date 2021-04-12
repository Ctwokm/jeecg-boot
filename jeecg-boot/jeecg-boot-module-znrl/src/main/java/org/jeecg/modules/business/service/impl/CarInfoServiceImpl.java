package org.jeecg.modules.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.mapper.CarInfoMapper;
import org.jeecg.modules.business.service.ICarInfoService;
import org.jeecg.modules.business.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("carInfoService")
@Transactional
public class CarInfoServiceImpl implements ICarInfoService {

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Override
    public Page<CarTransRecordEntity> qryCarTransRecordList(Page<CarTransRecordEntity> page,CarTransRecordEntity carTransRecordEntity) {
        return page.setRecords(carInfoMapper.qryCarTransRecordList(page,carTransRecordEntity));
    }

    @Override
    public Page<CarInCoalEntity> qryCarInCoalList(Page<CarInCoalEntity> page, CarInCoalEntity carInCoalEntity) {
        return page.setRecords(carInfoMapper.qryCarInCoalList(page,carInCoalEntity));
    }

    @Override
    public void deleteByCarInfo(CarTransRecordEntity carTransRecordEntity) {
        carInfoMapper.deleteByCarInfo(carTransRecordEntity);
    }

    @Override
    public CarInfoEntity focusCar(CarInfoEntity carInfoEntity) {
        return carInfoMapper.focusCar(carInfoEntity);
    }

    @Override
    public void addCarTransRecord(PkgEntity pkgEntity) {
        carInfoMapper.addCarTransRecord(pkgEntity);
    }

    @Override
    public Page<CarInfoRegisterEntity> qryCarRegisterList(Page<CarInfoRegisterEntity> page, CarInfoRegisterEntity carInfoRegisterEntity) {
        return page.setRecords(carInfoMapper.qryCarRegisterList(page,carInfoRegisterEntity));
    }

    @Override
    public Page<RegisterEntity> qryMineCardList(Page<RegisterEntity> page, RegisterEntity registerEntity) {
        return page.setRecords(carInfoMapper.qryMineCardList(page,registerEntity));
    }

    @Override
    public void modMineCardInfo(RegisterEntity registerEntity) {
        //直接操作修改方法；
        carInfoMapper.modMineCardInfo(registerEntity);
    }

    @Override
    public void addMineCardInfo(RegisterEntity registerEntity) {
        //直接操作新增方法
        carInfoMapper.addMineCardInfo(registerEntity);
    }

    @Override
    public Integer checkCard(RegisterEntity registerEntity) {
        return carInfoMapper.checkCard(registerEntity);
    }

    @Override
    public void deleteByMineCardId(RegisterEntity registerEntity) {
        carInfoMapper.deleteByMineCardId(registerEntity);
    }

    @Override
    public void moveMsgToMineHis(RegisterEntity registerEntity) {
        carInfoMapper.moveMsgToMineHis(registerEntity);
    }

    @Override
    public List queryPointList(RegisterEntity registerEntity) {
        return carInfoMapper.queryPointList(registerEntity);
    }

}
