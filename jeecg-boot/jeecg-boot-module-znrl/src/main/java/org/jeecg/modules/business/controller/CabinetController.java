package org.jeecg.modules.business.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.business.service.ICabinetInfoService;
import org.jeecg.modules.business.utils.Constant;
import org.jeecg.modules.business.vo.CabinetEntity;
import org.jeecg.modules.business.vo.CabinetOpRecEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @program: jeecg-boot-parent
 * @description: 存样管理：存样
 * @author: Hujw
 * @create: 2021-03-16 11:45
 **/
@RestController
@Api(tags="存样业务处理")
@RequestMapping("/znrl/cabinet")
@Slf4j
public class CabinetController {

    @Autowired
    private ICabinetInfoService cabinetInfoService;

    @AutoLog(value = "/查询存查样柜历史操作记录详情分页查询")
    @ApiOperation(value="查询存查样柜历史操作记录详情分页查询", notes="查询存查样柜历史操作记录详情分页查询")
    @GetMapping(value = "/qryCabinetOpRec")
    public Result qryCabinetOpRecListByPage(CabinetOpRecEntity cabinetOpRecEntity) {


        Result result = new Result();
        Page<CabinetOpRecEntity> pageList = new Page<CabinetOpRecEntity>(cabinetOpRecEntity.getPageNo(), cabinetOpRecEntity.getPageSize());
        try {
            pageList = cabinetInfoService.qryCabinetOpRecList(pageList,cabinetOpRecEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setSuccess(true);
        result.setResult(pageList);

        return result;
    }

    @AutoLog(value = "/查询存查样柜详情")
    @ApiOperation(value="查询存查样柜详情", notes="查询存查样柜详情")
    @GetMapping(value = "/qryCabinetInfoList")
    public Result qryCabinetInfoList(CabinetEntity cabinetEntity) {


        Result result = new Result();
        List<CabinetEntity> list = null;
        try {
//            pageList = trainInfoService.qryTrainBasicListByPage(pageList,cabinetOpRecEntity);
            list = cabinetInfoService.qryCabinetInfoList(cabinetEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setSuccess(true);
        result.setResult(list);

        return result;
    }

    //查询柜子样包信息
    @AutoLog(value = "/查询存查样柜单格详情")
    @ApiOperation(value="查询存查样柜单格详情", notes="查询存查样柜单格详情")
    @RequestMapping(value = "/qryAutoSampleDetail/{cabinetInfo}")
    public Result qryAutoSampleDetail(@PathVariable String cabinetInfo) {

        Result result = new Result();
        List<CabinetEntity> list = null;
        try {
            String json;
            //ObjectMapper objectMapper = JacksonMapper.getInstance();
            String[] strArr = cabinetInfo.split("-");
            CabinetEntity cabinetEntity = new CabinetEntity();

            //有些地方存样柜名称有汉字前缀，通过路径传过来有乱码，故配置成后台常量
            if (Constant.getConstVal("GG_NAME_PREFIX") != null) {
                cabinetEntity.setGgName(Constant.getConstVal("GG_NAME_PREFIX") + Integer.valueOf(strArr[0]).toString());//柜子
            } else {
                cabinetEntity.setGgName(Integer.valueOf(strArr[0]).toString());//柜子
            }

            cabinetEntity.setCcName(strArr[1] + Integer.valueOf(strArr[2]).toString());//层
            cabinetEntity.setWwName(Integer.valueOf(strArr[3]).toString());//位
            //查询
            list = cabinetInfoService.qryCabinetInfoList(cabinetEntity);
            //获取对象转换成json字符串送到页面
//            if (list.size() != 0) {
//                json = objectMapper.writeValueAsString(list.get(0));
//            } else {
//                json = objectMapper.writeValueAsString(new CabinetEntity());
//            }


        } catch (Exception e) {
            e.printStackTrace();

        }

        result.setSuccess(true);
        result.setResult(list);

        return result;
    }
}
