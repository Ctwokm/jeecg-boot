package org.jeecg.modules.business.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.activiti.util.TcpClient;
import org.jeecg.modules.business.service.ICarInfoService;
import org.jeecg.modules.business.utils.JacksonMapper;
import org.jeecg.modules.business.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @program: jeecg-boot-parent
 * @description: 汽车来煤管理主类，相关方法controller在此处
 * @author: zoudp
 * @create: 2021年3月2日
 **/

@RestController
@Api(tags="汽车业务处理")
@RequestMapping("/znrl/carInfo")
@Slf4j
public class CarInfoController {
    @Autowired
    private ICarInfoService carInfoService;

    @AutoLog(value = "业务方法demo")
    @ApiOperation(value="业务方法demo", notes="业务方法demo")
    @GetMapping(value = "/demo")
    public Result<String> demo() {
        Result<String> result = new Result<String>();
        result.setResult("Hello demo!");
        result.setSuccess(true);
        return result;
    }

    @AutoLog("汽车来煤方法_jj模板")
    @ApiOperation(value ="汽车来煤的查询方法", notes = "汽车来煤的查询方法")
    @GetMapping("/carInfoList")
    public Result<Page<CarTransRecordEntity>> carInList(CarTransRecordEntity carTransRecordEntity){
           Result<Page<CarTransRecordEntity>> result = new Result<>();
           Page<CarTransRecordEntity> pageList = new Page<>(carTransRecordEntity.pageNo,carTransRecordEntity.pageSize);
           pageList = carInfoService.qryCarTransRecordList(pageList,carTransRecordEntity);
           log.info("查询当前页："+pageList.getCurrent());
           log.info("查询当前页数量："+pageList.getSize());
           log.info("查询结果数量："+pageList.getRecords().size());
           log.info("数据总数："+pageList.getTotal());
           result.setSuccess(true);
           result.setResult(pageList);
           return result;
    }

    @AutoLog("汽车来煤方法_cg模板")
    @ApiOperation(value ="汽车来煤的查询方法", notes = "汽车来煤的查询方法")
    @GetMapping("/carInCoalList")
    public Result<Page<CarInCoalEntity>> qryCarInCoalList(CarInCoalEntity carInCoalEntity){
        Result<Page<CarInCoalEntity>> result = new Result<>();
        Page<CarInCoalEntity> pageList = new Page<>(carInCoalEntity.pageNo,carInCoalEntity.pageSize);
        pageList = carInfoService.qryCarInCoalList(pageList,carInCoalEntity);
        log.info("查询当前页："+pageList.getCurrent());
        log.info("查询当前页数量："+pageList.getSize());
        log.info("查询结果数量："+pageList.getRecords().size());
        log.info("数据总数："+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog("汽车来煤页面删除操作_jj模板")
    @ApiOperation(value ="汽车来煤页面删除操作", notes = "汽车来煤页面删除操作方法")
    @DeleteMapping("/deleteByCarInfo")
    public Result deleteByCarInfo(String id){
        Result result = new Result();
        CarTransRecordEntity carTransRecordEntity = new CarTransRecordEntity();
        try {
            carTransRecordEntity.setOpCode("1");
            carTransRecordEntity.setDoActionTag("DEL");
            carTransRecordEntity.setRecordNo(id);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            carInfoService.deleteByCarInfo(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccess(true);
                result.setMessage("删除汽车来煤信息成功！");
            } else {
                result.setSuccess(false);
                result.setMessage("删除汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setMessage("数据删除异常:" + e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    @AutoLog("汽车来煤页面删除操作_cg模板")
    @ApiOperation(value ="汽车来煤页面删除操作", notes = "汽车来煤页面删除操作方法")
    @DeleteMapping("/deleteByCar")
    public Result deleteByCar(String id){
        Result result = new Result();
        RegisterEntity registerEntity = new RegisterEntity();
        try {
            /*registerEntity.setOpCode("1");
            registerEntity.setDoActionTag("DEL");
            registerEntity.setRecordNo(id);

            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);
            carTransRecordEntity.setJsonString(json);

            carInfoService.deleteByCarInfo(carTransRecordEntity);

            //设置返回结果
            if (carTransRecordEntity.getResCode() != null && carTransRecordEntity.getResCode().equals("0")) {
                result.setSuccess(true);
                result.setMessage("删除汽车来煤信息成功！");
            } else {
                result.setSuccess(false);
                result.setMessage("删除汽车来煤信息失败！" + carTransRecordEntity.getResMsg());
            }*/
        } catch (Exception e) {
            result.setMessage("数据删除异常:" + e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    @AutoLog("定位汽车信息的方法")
    @ApiOperation(value ="定位汽车信息的方法", notes = "定位汽车信息的方法")
    @PostMapping("/focusCar")
    public Result focusCar(@RequestBody CarInfoEntity carInfoEntity){
        Result result = new Result();
        //查询
        CarInfoEntity c = null;
        try {
            c = carInfoService.focusCar(carInfoEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setResult(c);
        return result;
    }

    @AutoLog("汽车来煤新增的方法")
    @ApiOperation(value = "汽车来煤新增的方法",notes = "汽车来煤新增的方法")
    @PostMapping("/carTransRec/add")
    public Result addCarTransRecord(@RequestBody CarTransRecordEntity carTransRecordEntity){
        Result result = new Result();
        try {
            PkgEntity pkgEntity = new PkgEntity();
            carTransRecordEntity.setDoActionTag("ADD");//新增标识，存储过程用以区分
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(carTransRecordEntity);

            carTransRecordEntity.setJsonString(json);
            pkgEntity.setJsonString(json);
            pkgEntity.setOpCode("1");

            carInfoService.addCarTransRecord(pkgEntity);

            //设置返回结果
            if (pkgEntity.getResCode() != null && pkgEntity.getResCode().equals("0")) {
                result.setSuccess(true);
                result.setMessage("新增汽车来煤信息成功！");
            } else {
                result.setSuccess(false);
                result.setMessage("新增汽车来煤信息失败！" + pkgEntity.getResMsg());
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("新增汽车来煤信息异常！");
            e.printStackTrace();
        }
        return result;

    }

    @AutoLog("汽车来煤修改的方法")
    @ApiOperation(value = "汽车来煤修改的方法",notes = "汽车来煤修改的方法")
    @PostMapping("/carTransRec/mod")
    public Result modCarTransRecord(){
        return null;
    }

    @AutoLog("汽车注册发卡查询方法")
    @ApiOperation(value ="汽车注册发卡查询方法", notes = "汽车注册发卡查询方法")
    @GetMapping("/carRegisterList")
    public Result<Page<CarInfoRegisterEntity>> qryCarRegisterList(CarInfoRegisterEntity carInfoRegisterEntity){
        Result<Page<CarInfoRegisterEntity>> result = new Result<>();
        Page<CarInfoRegisterEntity> pageList  = new Page<>(carInfoRegisterEntity.pageNo,carInfoRegisterEntity.pageSize);
        pageList = carInfoService.qryCarRegisterList(pageList,carInfoRegisterEntity);
        log.info("查询当前页："+pageList.getCurrent());
        log.info("查询当前页数量："+pageList.getSize());
        log.info("查询结果数量："+pageList.getRecords().size());
        log.info("数据总数："+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog("汽车矿卡查询方法")
    @ApiOperation(value ="汽车矿卡查询方法", notes = "汽车矿卡查询方法")
    @GetMapping("/mineRegisterList")
    public Result<Page<RegisterEntity>> qryMineCardList(RegisterEntity registerEntity){
        Result<Page<RegisterEntity>> result = new Result<>();
        Page<RegisterEntity> pageList  = new Page<>(registerEntity.pageNo,registerEntity.pageSize);
        pageList = carInfoService.qryMineCardList(pageList,registerEntity);
        log.info("查询当前页："+pageList.getCurrent());
        log.info("查询当前页数量："+pageList.getSize());
        log.info("查询结果数量："+pageList.getRecords().size());
        log.info("数据总数："+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    @AutoLog("汽车矿卡修改的方法")
    @ApiOperation(value = "矿卡修改的方法",notes = "矿卡修改的方法")
    @PostMapping("/modMineCardInfo")
    public Result modMineCardInfo(@RequestBody RegisterEntity registerEntity){
        Result result = new Result();
        try {
            carInfoService.modMineCardInfo(registerEntity);
            result.setSuccess(true);
            result.setMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改出错");
        }
        return result;
    }

    @AutoLog("汽车矿卡发卡的方法")
    @ApiOperation(value = "汽车矿卡发卡的方法",notes = "汽车矿卡发卡的方法")
    @PostMapping("/addMineCardInfo")
    public Result addMineCardInfo(@RequestBody RegisterEntity registerEntity){
        Result result = new Result();
        try {
            carInfoService.addMineCardInfo(registerEntity);
            result.setSuccess(true);
            result.setMessage("ok");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改出错");
        }
        return result;
    }


    //读取车卡中的卡号
    @AutoLog("读取车卡中的卡号")
    @ApiOperation(value = "读取车卡中的卡号",notes = "读取车卡中的卡号")
    @PostMapping(value="/touchCarInfo")
    public Result getCard() {
        Result result = new Result();
        try {
            TcpClient tcpClient = new TcpClient();
            String req = "0A:FF";
            String rsp = tcpClient.send(req);
            if(rsp!=null){
                String [] str = rsp.split(":");
                if(str.length==3){
                    result.setMessage(str[1]);
                    result.setSuccess(true);


                }else{
                    result.setSuccess(false);
                    result.setMessage("请重试！");
                }
            }else{
                result.setSuccess(false);
                result.setMessage("请重试！");
            }
        } catch (Exception e) {
            result.setMessage("获取卡号异常:" + e.getMessage());
            result.setSuccess(false);
            e.printStackTrace();
        }
        return result;
    }

    @AutoLog("作废矿卡记录")
    @ApiOperation(value = "作废矿卡记录",notes = "作废矿卡记录")
    @DeleteMapping(value="/moveMsgToMineHis")
    @Transactional
    public Result moveMsgToMineHis(@RequestBody RegisterEntity registerEntity){
        Result result = new Result();
        try {
            //此处做move和delete两个操作
            carInfoService.moveMsgToMineHis(registerEntity);
            carInfoService.deleteByMineCardId(registerEntity);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("操作失败！ 数据移除出现问题！");
            //如果数据出现异常则直接返回；此类做事务处理，如果出现异常则回滚；
            e.printStackTrace();
            return result;
        }
        result.setSuccess(true);
        result.setMessage("操作成功！");
        return result;
    }


    @AutoLog("校验卡号")
    @ApiOperation(value = "校验卡号",notes = "校验卡号")
    @PostMapping(value="/checkCard")
    public Result checkCard(@RequestBody RegisterEntity registerEntity){
        Result result = new Result();
        try{
            Integer integer = carInfoService.checkCard(registerEntity);
            if (integer >= 1){
                result.setMessage("该卡号存在有效使用记录！");
                result.setCode(0);
                result.setSuccess(false);
            }else if (integer == 0 ){
                result.setMessage("该卡号可以正常使用！");
                result.setCode(1);
                result.setSuccess(true);
            }else {
                result.setMessage("检查卡号的方法出错！");
                result.setCode(0);
                result.setSuccess(false);
            }

        }catch (Exception e){
            result.setMessage("检查卡号的方法出错！");
            result.setSuccess(false);
            e.printStackTrace();
        }

        return result;
    }

    @AutoLog("采样点分布图")
    @ApiOperation(value = "采样点分布图查询",notes = "采样点分布图")
    @PostMapping(value = "/queryPointList")
    public Result queryPointList(RegisterEntity registerEntity){
        Result result = new Result();
        try{
            //获取点位集合  "800,2700,1800;1300,6000,1900",
            //              "1400,2100,1700;1000,3200,1700",
            List<String> list = carInfoService.queryPointList(registerEntity);
            Iterator<String> iterator = list.iterator();
            List<String[]> tempList = new ArrayList<>();
            while (iterator.hasNext()){
                //获取当前行信息 "800,2700,1800;1300,6000,1900",
                String str = iterator.next();
                //分割str split =["800,2700,1800","1300,6000,1900"],
                String[] split = str.split(";");
                //遍历数组
                for (int i = 0 ;i<split.length;i++){
                    //splitStr = [800,2700,1800]
                    String[] splitStr = split[i].split(",");
                    //获取分割后的str 的前两个数据，作为坐标的 x y
                    String [] temp = new String[2];
                    //分割完成后进行赋值处理，如果当前的点分割后少于2个值，则认为无效；
                    if(splitStr.length >= 2){
                        temp[0] = splitStr[1];
                        temp[1] = splitStr[0];
                    }
                    tempList.add(temp);
                }

            }
            result.setResult(tempList);
            result.setSuccess(true);
        }catch (Exception e){
            result.setMessage("查询出错！");
            result.setCode(0);
            result.setSuccess(false);
            e.printStackTrace();
        }


        return result;
    }

}
