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
import org.jeecg.modules.temp.entity.BatchNoInfo;
import org.jeecg.modules.temp.service.IBatchNoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: batch_no_info
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Api(tags="batch_no_info")
@RestController
@RequestMapping("/modules/batchNoInfo")
@Slf4j
public class BatchNoInfoController extends JeecgController<BatchNoInfo, IBatchNoInfoService> {
	@Autowired
	private IBatchNoInfoService batchNoInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param batchNoInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "batch_no_info-分页列表查询")
	@ApiOperation(value="batch_no_info-分页列表查询", notes="batch_no_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(BatchNoInfo batchNoInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<BatchNoInfo> queryWrapper = QueryGenerator.initQueryWrapper(batchNoInfo, req.getParameterMap());
		Page<BatchNoInfo> page = new Page<BatchNoInfo>(pageNo, pageSize);
		IPage<BatchNoInfo> pageList = batchNoInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param batchNoInfo
	 * @return
	 */
	@AutoLog(value = "batch_no_info-添加")
	@ApiOperation(value="batch_no_info-添加", notes="batch_no_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody BatchNoInfo batchNoInfo) {
		batchNoInfoService.save(batchNoInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param batchNoInfo
	 * @return
	 */
	@AutoLog(value = "batch_no_info-编辑")
	@ApiOperation(value="batch_no_info-编辑", notes="batch_no_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody BatchNoInfo batchNoInfo) {
		batchNoInfoService.updateById(batchNoInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "batch_no_info-通过id删除")
	@ApiOperation(value="batch_no_info-通过id删除", notes="batch_no_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		batchNoInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "batch_no_info-批量删除")
	@ApiOperation(value="batch_no_info-批量删除", notes="batch_no_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.batchNoInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "batch_no_info-通过id查询")
	@ApiOperation(value="batch_no_info-通过id查询", notes="batch_no_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		BatchNoInfo batchNoInfo = batchNoInfoService.getById(id);
		if(batchNoInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(batchNoInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param batchNoInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, BatchNoInfo batchNoInfo) {
        return super.exportXls(request, batchNoInfo, BatchNoInfo.class, "batch_no_info");
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
        return super.importExcel(request, response, BatchNoInfo.class);
    }

}
