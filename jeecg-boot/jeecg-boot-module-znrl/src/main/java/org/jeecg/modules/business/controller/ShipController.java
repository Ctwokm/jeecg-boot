package org.jeecg.modules.business.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.business.service.IShipService;
import org.jeecg.modules.business.vo.ShipBatchEntity;
import org.jeecg.modules.business.vo.ShipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "船运来煤业务管理")
@RequestMapping("/znrl/ship")
@Slf4j
public class ShipController {

    @Autowired
    IShipService shipService;

    /**
     * 分页列表查询
     *
     * @param shipEntity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "ship_record-分页列表查询")
    @ApiOperation(value="ship_record-分页列表查询", notes="ship_record-分页列表查询")
    @GetMapping(value = "/recordByPage")
    public Result<?> recordByPage(ShipEntity shipEntity,
                                   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                   HttpServletRequest req) {
        Page<ShipEntity> pageList = new Page<ShipEntity>(pageNo, pageSize);
        pageList = shipService.recordByPage(pageList, shipEntity);
        return Result.OK(pageList);
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_record-add")
    @ApiOperation(value="ship_record-add", notes="ship_record-add")
    @PostMapping(value = "/addRecord")
    public Result addRecord(@RequestBody ShipEntity shipEntity) {
        shipEntity.setActionTag("ADD");
        return shipService.editByCall(shipEntity, "p_ship_editRecord");
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_record-edit")
    @ApiOperation(value="ship_record-edit", notes="ship_record-edit")
    @PutMapping(value = "/editRecord")
    public Result editRecord(@RequestBody ShipEntity shipEntity) {
        shipEntity.setActionTag("MOD");
        return shipService.editByCall(shipEntity, "p_ship_editRecord");
    }

    /**
     * @param shipRecId
     * @return
     */
    @AutoLog(value = "ship_record-delete")
    @ApiOperation(value="ship_record-delete", notes="ship_record-delete")
    @DeleteMapping(value = "/deleteRecord")
    public Result deleteRecord(@RequestParam(name="shipRecId",required=true) String shipRecId) {
        ShipEntity shipEntity = new ShipEntity();
        shipEntity.setShipRecId(shipRecId);
        shipEntity.setActionTag("DEL");
        return shipService.editByCall(shipEntity, "p_ship_editRecord");
    }

    /**
     * 分页列表查询
     *
     * @param shipEntity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "ship_cargo_record-分页列表查询")
    @ApiOperation(value="ship_cargo_record-分页列表查询", notes="ship_cargo_record-分页列表查询")
    @GetMapping(value = "/cargoRecordByPage")
    public Result<?> cargoRecordByPage(ShipEntity shipEntity,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
        Page<ShipEntity> pageList = new Page<ShipEntity>(pageNo, pageSize);
        pageList = shipService.cargoRecordByPage(pageList, shipEntity);
        return Result.OK(pageList);
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_cargo_record-add")
    @ApiOperation(value="ship_cargo_record-add", notes="ship_cargo_record-add")
    @PostMapping(value = "/addCargoRecord")
    public Result addCargoRecord(@RequestBody ShipEntity shipEntity) {
        shipEntity.setActionTag("ADD");
        return shipService.editByCall(shipEntity, "p_ship_editCargoRecord");
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_cargo_record-edit")
    @ApiOperation(value="ship_cargo_record-edit", notes="ship_cargo_record-edit")
    @PutMapping(value = "/editCargoRecord")
    public Result editCargoRecord(@RequestBody ShipEntity shipEntity) {
        shipEntity.setActionTag("MOD");
        return shipService.editByCall(shipEntity, "p_ship_editCargoRecord");
    }

    /**
     * @param recordNo
     * @return
     */
    @AutoLog(value = "ship_cargo_record-delete")
    @ApiOperation(value="ship_cargo_record-delete", notes="ship_cargo_record-delete")
    @DeleteMapping(value = "/deleteCargoRecord")
    public Result deleteCargoRecord(@RequestParam(name="shipRecId",required=true) String shipRecId, @RequestParam(name="recordNo",required=true) String recordNo) {
        ShipEntity shipEntity = new ShipEntity();
        shipEntity.setShipRecId(shipRecId);
        shipEntity.setRecordNo(recordNo);
        shipEntity.setActionTag("DEL");
        return shipService.editByCall(shipEntity, "p_ship_editCargoRecord");
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_process_batch")
    @ApiOperation(value="ship_process_batch", notes="ship_process_batch")
    @PutMapping(value = "/processBatch")
    public Result processBatch(@RequestBody ShipEntity shipEntity) {
        shipEntity.setActionTag("BAT");
        return shipService.editByCall(shipEntity, "p_ship_processBatch");
    }

    /**
     * 船运煤分批详情查询
     *
     * @param shipBatchEntity
     * @param req
     * @return
     */
    @AutoLog(value = "船运分批详情查询")
    @ApiOperation(value="船运分批详情查询", notes="船运分批详情查询")
    @GetMapping(value = "/shipBatchInfo")
    public Result<?> shipBatchInfo(ShipBatchEntity shipBatchEntity,
                                   HttpServletRequest req) {
        //虽然无需分页查询，但仍用Page对象进行包装后返回，方便DictAspect类顺利解析和翻译返回对象中用@Dict注解的字段。
        Page<ShipBatchEntity> pageList = new Page<ShipBatchEntity>();
        pageList.setRecords(shipService.shipBatchInfo(shipBatchEntity));
        return Result.OK(pageList);
    }

    /**
     * @param shipBatchEntity
     * @return
     */
    @AutoLog(value = "ship_force_complete_batch")
    @ApiOperation(value="ship_force_complete_batch", notes="ship_force_complete_batch")
    @PutMapping(value = "/forceCompleteBatch")
    public Result forceCompleteBatch(@RequestBody ShipBatchEntity shipBatchEntity) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        shipBatchEntity.setOpCode(sysUser.getId());
        return shipService.forceCompleteBatch(shipBatchEntity);
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_set_current_ship")
    @ApiOperation(value="ship_set_current_ship", notes="ship_set_current_ship")
    @PutMapping(value = "/setCurrentShip")
    public Result setCurrentShip(@RequestBody ShipEntity shipEntity) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        shipEntity.setOpCode(sysUser.getId());
        shipEntity.setActionTag("SET");
        return shipService.editByCall(shipEntity, "p_ship_setCurrentShip");
    }

    /**
     * @param shipEntity
     * @return
     */
    @AutoLog(value = "ship_finish_unload")
    @ApiOperation(value="ship_finish_unload", notes="ship_finish_unload")
    @PutMapping(value = "/finishUnload")
    public Result finishUnload(@RequestBody ShipEntity shipEntity) {
        try{
            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            shipEntity.setOpCode(sysUser.getId());
            shipEntity.setActionTag("FIN");
            return shipService.editByCall(shipEntity, "p_ship_finishUnload");
        }catch(Exception e){
            return Result.error(e.getCause().getMessage());
        }
    }
}
