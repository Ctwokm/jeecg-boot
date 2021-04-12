package org.jeecg.modules.business.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.business.service.IPreparSamplingService;
import org.jeecg.modules.business.utils.JacksonMapper;
import org.jeecg.modules.business.vo.SamplingRptEntity;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: jeecg-boot-parent
 * @description: 制样结果业务管理处理控制器，包含制样相关的所有的业务操作
 * @author: ZJ
 * @create: 2021-02-22 11:13
 **/

@RestController
@Api(tags = "制样业务处理")
@RequestMapping("/znrl/sampling")
@Slf4j
public class PreparSamplingController {

    @Autowired
    public IPreparSamplingService preparSamplingService;

    @Value("${jeecg.path.upload}")
    private String upLoadPath;

    @AutoLog(value = "/查询制样结果基本信息详情")
    @ApiOperation(value="查询制样结果基本信息详情", notes="查询火车入厂登记基本信息详情")
    @GetMapping(value = "/list")
    public Result<Page<SamplingRptEntity>> qrySamplingList(SamplingRptEntity samplingRptEntity,
                                                           @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name="pageSize",defaultValue = "10")Integer pageSize,
                                                           HttpServletRequest req) {

        Result<Page<SamplingRptEntity>> result= new Result<Page<SamplingRptEntity>>();
        Page<SamplingRptEntity> pageList = new Page<SamplingRptEntity>(pageNo,pageSize);
        pageList = preparSamplingService.qrySamplingList(pageList,samplingRptEntity);
        log.info("查询当前页:"+pageList.getCurrent());
        log.info("查询当前页数量:"+pageList.getSize());
        log.info("查询结果数量:"+pageList.getRecords().size());
        log.info("数量总数:"+pageList.getTotal());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     *   添加
     *
     * @param samplingRptEntity
     * @return
     */
    @AutoLog(value = "制样添加")
    @ApiOperation(value="制样添加", notes="制样添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody SamplingRptEntity samplingRptEntity) {
        //takeSampleRecService.save(takeSampleRec);
        return Result.OK("添加成功！");
    }

    /**
     *  编辑
     *
     * @param samplingRptEntity
     * @return
     */
    @AutoLog(value = "制样编辑")
    @ApiOperation(value="制样编辑", notes="制样编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody SamplingRptEntity samplingRptEntity) {
        try {
            //获取登录操作员的ID
            //LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
            //samplingRptEntity.setOpCode(sysUser.getOrgCode());
            samplingRptEntity.setOpCode("zhoujian");

            //
            ObjectMapper objectMapper = JacksonMapper.getInstance();
            String json = objectMapper.writeValueAsString(samplingRptEntity);
            samplingRptEntity.setJsonString(json);
            preparSamplingService.updateRecord(samplingRptEntity);

            //设置返回结果
            if(samplingRptEntity.getResCode() != null && samplingRptEntity.getResCode().equals("0")){
                return Result.OK("编辑成功!");
            }else {
                return Result.error("操作失败!"+samplingRptEntity.getResMsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("操作发生异常!");
        }

    }

    /**
     *   通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "制样通过id删除")
    @ApiOperation(value="制样通过id删除", notes="制样通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        // takeSampleRecService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     *  批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "制样批量删除")
    @ApiOperation(value="制样批量删除", notes="制样批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        //this.takeSampleRecService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "制样-通过id查询")
    @ApiOperation(value="制样通过id查询", notes="制样通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        //TakeSampleRec takeSampleRec = takeSampleRecService.getById(id);
//        if(takeSampleRec==null) {
//            return Result.error("未找到对应数据");
//        }
        // return Result.OK(takeSampleRec);
        return null;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param samplingRptEntity
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, SamplingRptEntity samplingRptEntity) {


        // Step.1 组装查询条件   修改实体类
        //QueryWrapper<SamplingRptEntity> queryWrapper = QueryGenerator.initQueryWrapper(samplingRptEntity, request.getParameterMap());
        // LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        // Step.2 获取导出数据   修改实体类   修改获取数据方法
        List<SamplingRptEntity> pageList = preparSamplingService.qrySamplingList(samplingRptEntity);
        List<SamplingRptEntity> exportList = null;

        exportList = pageList;

        // 过滤选中数据
        // TODO: 2021/3/8  选中的数据先不处理
        String selections = request.getParameter("selections");
//        if (oConvertUtils.isNotEmpty(selections)) {
//            List<String> selectionList = Arrays.asList(selections.split(","));
//            exportList = pageList.stream().filter(item -> selectionList.contains(getId(item))).collect(Collectors.toList());
//        } else {
//            exportList = pageList;
//        }

        // Step.3 AutoPoi 导出Excel
        //设置标题
        String title = "制样结果表";
        //实体类对象
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, title); //此处设置的filename无效 ,前端会重更新设置一下
        //注解对象Class
        mv.addObject(NormalExcelConstants.CLASS, SamplingRptEntity.class);
        //update-begin--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置--------------------

        ExportParams exportParams=new ExportParams(title + "报表", "导出人:" + "zhoujian", title);
        exportParams.setImageBasePath(upLoadPath);
        //update-end--Author:liusq  Date:20210126 for：图片导出报错，ImageBasePath未设置----------------------
        //自定义表格参数
        mv.addObject(NormalExcelConstants.PARAMS,exportParams);
        //导出数据列表
        mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        //ApplicationContext
        return mv;

    }


//
//    /**
//     * 通过excel导入数据
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//        return super.importExcel(request, response, SamplingRptEntity.class);
    //   }
}
