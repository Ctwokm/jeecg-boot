package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.ErrorSpec;
import org.jeecg.modules.rapid.service.IErrorSpecService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: error_spec
 * @Author: fz
 * @Date:   2021-03-15
 * @Version: V1.0
 */
@Api(tags="error_spec")
@RestController
@RequestMapping("/errorspec/errorSpec")
@Slf4j
public class ErrorSpecController extends JeecgController<ErrorSpec, IErrorSpecService> {
	@Autowired
	private IErrorSpecService errorSpecService;

	/**
	 * 分页列表查询
	 *
	 * @param errorSpec
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "error_spec-分页列表查询")
	@ApiOperation(value="error_spec-分页列表查询", notes="error_spec-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ErrorSpec errorSpec,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ErrorSpec> queryWrapper = QueryGenerator.initQueryWrapper(errorSpec, req.getParameterMap());
		Page<ErrorSpec> page = new Page<ErrorSpec>(pageNo, pageSize);
		IPage<ErrorSpec> pageList = errorSpecService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param errorSpec
	 * @return
	 */
	@AutoLog(value = "error_spec-添加")
	@ApiOperation(value="error_spec-添加", notes="error_spec-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ErrorSpec errorSpec) {
		errorSpecService.add(errorSpec);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param errorSpec
	 * @return
	 */
	@AutoLog(value = "error_spec-编辑")
	@ApiOperation(value="error_spec-编辑", notes="error_spec-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ErrorSpec errorSpec) {
		errorSpecService.editById(errorSpec);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param errorSpecId
	 * @return
	 */
	@AutoLog(value = "error_spec-通过id删除")
	@ApiOperation(value="error_spec-通过id删除", notes="error_spec-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="errorSpecId",required=true) String errorSpecId) {
		System.out.println(errorSpecId);
		errorSpecService.deleteErrorById(errorSpecId);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "error_spec-批量删除")
	@ApiOperation(value="error_spec-批量删除", notes="error_spec-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.errorSpecService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "error_spec-通过id查询")
	@ApiOperation(value="error_spec-通过id查询", notes="error_spec-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ErrorSpec errorSpec = errorSpecService.getById(id);
		if(errorSpec==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(errorSpec);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param errorSpec
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ErrorSpec errorSpec) {
        return super.exportXls(request, errorSpec, ErrorSpec.class, "error_spec");
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
        return super.importExcel(request, response, ErrorSpec.class);
    }

}
