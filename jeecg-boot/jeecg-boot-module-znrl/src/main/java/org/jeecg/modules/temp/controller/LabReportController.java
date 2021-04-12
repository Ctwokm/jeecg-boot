package org.jeecg.modules.temp.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.temp.entity.LabReport;
import org.jeecg.modules.temp.service.ILabReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: lab_report
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Api(tags="lab_report")
@RestController
@RequestMapping("/modules/labReport")
@Slf4j
public class LabReportController extends JeecgController<LabReport, ILabReportService> {
	@Autowired
	private ILabReportService labReportService;

	/**
	 * 分页列表查询
	 *
	 * @param labReport
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "lab_report-分页列表查询")
	@ApiOperation(value="lab_report-分页列表查询", notes="lab_report-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(LabReport labReport,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<LabReport> queryWrapper = QueryGenerator.initQueryWrapper(labReport, req.getParameterMap());
		Page<LabReport> page = new Page<LabReport>(pageNo, pageSize);
		IPage<LabReport> pageList = labReportService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param labReport
	 * @return
	 */
	@AutoLog(value = "lab_report-添加")
	@ApiOperation(value="lab_report-添加", notes="lab_report-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody LabReport labReport) {
		labReportService.save(labReport);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param labReport
	 * @return
	 */
	@AutoLog(value = "lab_report-编辑")
	@ApiOperation(value="lab_report-编辑", notes="lab_report-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody LabReport labReport) {
		labReportService.updateById(labReport);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "lab_report-通过id删除")
	@ApiOperation(value="lab_report-通过id删除", notes="lab_report-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		labReportService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "lab_report-批量删除")
	@ApiOperation(value="lab_report-批量删除", notes="lab_report-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.labReportService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "lab_report-通过id查询")
	@ApiOperation(value="lab_report-通过id查询", notes="lab_report-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		LabReport labReport = labReportService.getById(id);
		if(labReport==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(labReport);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param labReport
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, LabReport labReport) {
        return super.exportXls(request, labReport, LabReport.class, "lab_report");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, LabReport.class);
    }

}
