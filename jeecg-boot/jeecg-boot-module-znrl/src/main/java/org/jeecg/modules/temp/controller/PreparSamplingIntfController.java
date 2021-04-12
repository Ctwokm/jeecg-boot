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
import org.jeecg.modules.temp.entity.PreparSamplingIntf;
import org.jeecg.modules.temp.service.IPreparSamplingIntfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: prepar_sampling_intf
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Api(tags="prepar_sampling_intf")
@RestController
@RequestMapping("/modules/preparSamplingIntf")
@Slf4j
public class PreparSamplingIntfController extends JeecgController<PreparSamplingIntf, IPreparSamplingIntfService> {
	@Autowired
	private IPreparSamplingIntfService preparSamplingIntfService;

	/**
	 * 分页列表查询
	 *
	 * @param preparSamplingIntf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "prepar_sampling_intf-分页列表查询")
	@ApiOperation(value="prepar_sampling_intf-分页列表查询", notes="prepar_sampling_intf-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(PreparSamplingIntf preparSamplingIntf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<PreparSamplingIntf> queryWrapper = QueryGenerator.initQueryWrapper(preparSamplingIntf, req.getParameterMap());
		Page<PreparSamplingIntf> page = new Page<PreparSamplingIntf>(pageNo, pageSize);
		IPage<PreparSamplingIntf> pageList = preparSamplingIntfService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param preparSamplingIntf
	 * @return
	 */
	@AutoLog(value = "prepar_sampling_intf-添加")
	@ApiOperation(value="prepar_sampling_intf-添加", notes="prepar_sampling_intf-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody PreparSamplingIntf preparSamplingIntf) {
		preparSamplingIntfService.save(preparSamplingIntf);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param preparSamplingIntf
	 * @return
	 */
	@AutoLog(value = "prepar_sampling_intf-编辑")
	@ApiOperation(value="prepar_sampling_intf-编辑", notes="prepar_sampling_intf-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody PreparSamplingIntf preparSamplingIntf) {
		preparSamplingIntfService.updateById(preparSamplingIntf);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "prepar_sampling_intf-通过id删除")
	@ApiOperation(value="prepar_sampling_intf-通过id删除", notes="prepar_sampling_intf-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		preparSamplingIntfService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "prepar_sampling_intf-批量删除")
	@ApiOperation(value="prepar_sampling_intf-批量删除", notes="prepar_sampling_intf-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.preparSamplingIntfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "prepar_sampling_intf-通过id查询")
	@ApiOperation(value="prepar_sampling_intf-通过id查询", notes="prepar_sampling_intf-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		PreparSamplingIntf preparSamplingIntf = preparSamplingIntfService.getById(id);
		if(preparSamplingIntf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(preparSamplingIntf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param preparSamplingIntf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, PreparSamplingIntf preparSamplingIntf) {
        return super.exportXls(request, preparSamplingIntf, PreparSamplingIntf.class, "prepar_sampling_intf");
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
        return super.importExcel(request, response, PreparSamplingIntf.class);
    }

}
