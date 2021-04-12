package org.jeecg.modules.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.business.mapper.ShipMapper;
import org.jeecg.modules.business.service.IBizToolService;
import org.jeecg.modules.business.service.IShipService;
import org.jeecg.modules.business.vo.PkgEntity;
import org.jeecg.modules.business.vo.ShipBatchEntity;
import org.jeecg.modules.business.vo.ShipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShipServiceImpl extends ServiceImpl<ShipMapper, ShipEntity> implements IShipService {

    @Autowired
    ShipMapper shipMapper;

    @Autowired
    IBizToolService bizToolService;

    @Override
    public Page<ShipEntity> recordByPage(Page<ShipEntity> page, ShipEntity shipEntity) {
        return page.setRecords(shipMapper.recordByPage(page, shipEntity));
    }

    @Override
    public Page<ShipEntity> cargoRecordByPage(Page<ShipEntity> page, ShipEntity shipEntity) {
        return page.setRecords(shipMapper.cargoRecordByPage(page, shipEntity));
    }

    @Override
    public Result editByCall(ShipEntity shipEntity, String pkgName) {
        String jsonString = JSON.toJSONString(shipEntity);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String opCode = sysUser.getId();

        PkgEntity pkgEntity = new PkgEntity();
        pkgEntity.setPkgName(pkgName);
        pkgEntity.setJsonString(jsonString);
        pkgEntity.setOpCode(opCode);
        bizToolService.doCall(pkgEntity);

        //返回结果
        if ("1".equals(pkgEntity.getResCode())) {
            return Result.error(pkgEntity.getResMsg());
        }else{
            return Result.OK();
        }
    }

    @Override
    public List<ShipBatchEntity> shipBatchInfo(ShipBatchEntity shipBatchEntity) {
        return shipMapper.shipBatchInfo(shipBatchEntity);
    }

    @Override
    public Result forceCompleteBatch(ShipBatchEntity shipBatchEntity) {
        try{
            shipMapper.forceCompleteBatch(shipBatchEntity);
            return Result.OK();
        }catch(Exception e){
            return Result.error("处理异常：" + e.getMessage());
        }
    }
}
