package org.jeecg.modules.business.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.business.service.IRptService;
import org.jeecg.modules.business.vo.SamplingRptEntity;
import org.jeecg.modules.business.vo.WeightRptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: jeecg-boot-parent
 * @description: 报表业务管理
 * @author: Hujw
 * @create: 2021-03-16 16:56
 **/
@RestController
@Api(tags = "报表业务管理")
@RequestMapping("/znrl/report")
@Slf4j
public class ReportController {

    @Autowired
    public IRptService rptService;


    @AutoLog(value = "/查询汽车汇总信息详情")
    @ApiOperation(value="查询汽车汇总信息详情", notes="查询汽车汇总信息详情")
    @GetMapping(value = "/weightSummaryRpt")
    @ResponseBody
    public Result<WeightRptEntity> qryWeightSummaryRpt(WeightRptEntity weightRptEntity) {

        Result<WeightRptEntity> result= new Result<WeightRptEntity>();
        //查询累计值
        WeightRptEntity weightSummaryEntity = null;
        try {
            weightSummaryEntity = rptService.qryWeightSummaryRpt(weightRptEntity);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取汽车汇总信息错误");
            e.printStackTrace();
        }
        result.setSuccess(true);
        result.setResult(weightSummaryEntity);
        return result;
    }


    @AutoLog(value = "/查询汽车汇总信息详情")
    @ApiOperation(value="查询汽车汇总信息详情", notes="查询汽车汇总信息详情")
    @GetMapping(value = "/trainWeightSummaryRpt")
    @ResponseBody
    public Result<WeightRptEntity> qryTrainWeightSummaryRpt(WeightRptEntity weightRptEntity) {

        Result<WeightRptEntity> result= new Result<WeightRptEntity>();

        //查询
        WeightRptEntity weightSummaryEntity = null;
        try {
            weightSummaryEntity = rptService.qryTrainWeightSummaryRpt(weightRptEntity);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("获取火车汇总信息错误");
            e.printStackTrace();
        }

        result.setSuccess(true);
        result.setResult(weightSummaryEntity);
        return result;
    }
}
