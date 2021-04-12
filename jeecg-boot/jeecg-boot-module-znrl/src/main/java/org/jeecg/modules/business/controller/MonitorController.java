package org.jeecg.modules.business.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.business.service.IMonitorService;

import org.jeecg.modules.business.utils.JacksonMapper;
import org.jeecg.modules.business.vo.DeviceBroadEntity;
import org.jeecg.modules.business.vo.DeviceErrEntity;
import org.jeecg.modules.business.vo.LogOpRecEntity;


import org.jeecg.modules.business.vo.SamplingRptEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: zj
 * @Date: 2021/4/1
 * @Description: 关于页面监控的的后台controller入口
 * 如:操作日志\设备实时和故障
 **/
@RestController("MonitorController")
@Api(tags = "监控业务管理")
@RequestMapping("/znrl/monitor")
@Slf4j
public class MonitorController {

    @Autowired
    private IMonitorService monitorService;

    @AutoLog(value = "/查询操作日志基本信息详情")
    @ApiOperation(value="查询操作日志基本信息详情", notes="查询操作日志基本信息详情")
    @GetMapping(value = "logMod/list")
    public Result<Page<LogOpRecEntity>> qryLogModList(LogOpRecEntity logOpRecEntity,
                                                      @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name="pageSize",defaultValue = "10")Integer pageSize,
                                                      HttpServletRequest req) {

        Result<Page<LogOpRecEntity>> result= new Result<Page<LogOpRecEntity>>();
        Page<LogOpRecEntity> pageList = new Page<LogOpRecEntity>(pageNo,pageSize);
        pageList = monitorService.qryLogModList(pageList,logOpRecEntity);
        log.info("查询当前页:"+pageList.getCurrent());
        log.info("查询当前页数量:"+pageList.getSize());
        log.info("查询结果数量:"+pageList.getRecords().size());
        log.info("数量总数:"+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "/查询设备故障基本信息详情")
    @ApiOperation(value="查询设备故障基本信息详情", notes="查询设备故障基本信息详情")
    @GetMapping(value = "deviceErr/list")
    public Result<Page<DeviceErrEntity>> qryDeviceErrList(DeviceErrEntity deviceErrEntity,
                                                       @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name="pageSize",defaultValue = "10")Integer pageSize,
                                                       HttpServletRequest req) {

        Result<Page<DeviceErrEntity>> result= new Result<Page<DeviceErrEntity>>();
        Page<DeviceErrEntity> pageList = new Page<DeviceErrEntity>(pageNo,pageSize);
        pageList = monitorService.qryDeviceErrList(pageList,deviceErrEntity);
        log.info("查询当前页:"+pageList.getCurrent());
        log.info("查询当前页数量:"+pageList.getSize());
        log.info("查询结果数量:"+pageList.getRecords().size());
        log.info("数量总数:"+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog(value = "/处理设备故障信息")
    @ApiOperation(value="处理设备故障信息", notes="处理设备故障信息")
    @PutMapping(value = "deviceErr/edit")
    public Result<?> editDeviceErr(@RequestBody DeviceErrEntity deviceErrEntity) {
        try {

            deviceErrEntity.setOpCode("3d464b4ea0d2491aab8a7bde74c57e95");

            monitorService.deviceErrDeal(deviceErrEntity);

            //设置返回结果

            return Result.OK("故障报警信息处理成功!");

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("故障报警信息处理异常!");
        }
    }

    @AutoLog(value = "/查询设备实时状态基本信息详情")
    @ApiOperation(value="查询设备实时状态基本信息详情", notes="查询设备实时状态基本信息详情")
    @GetMapping(value = "deviceBroad/list")
    public Result<Page<DeviceBroadEntity>> qryDeviceBroadList(DeviceBroadEntity deviceBroadEntity,
                                                         @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name="pageSize",defaultValue = "10")Integer pageSize,
                                                         HttpServletRequest req) {

        Result<Page<DeviceBroadEntity>> result= new Result<Page<DeviceBroadEntity>>();
        Page<DeviceBroadEntity> pageList = new Page<DeviceBroadEntity>(pageNo,pageSize);
        pageList = monitorService.qryDeviceBroadList(pageList,deviceBroadEntity);
        log.info("查询当前页:"+pageList.getCurrent());
        log.info("查询当前页数量:"+pageList.getSize());
        log.info("查询结果数量:"+pageList.getRecords().size());
        log.info("数量总数:"+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }



}
