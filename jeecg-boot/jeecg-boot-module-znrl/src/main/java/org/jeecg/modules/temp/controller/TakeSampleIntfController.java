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
import org.jeecg.modules.temp.entity.TakeSampleIntf;
import org.jeecg.modules.temp.service.ITakeSampleIntfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: take_sample_intf
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Api(tags="take_sample_intf")
@RestController
@RequestMapping("/modules/takeSampleIntf")
@Slf4j
public class TakeSampleIntfController extends JeecgController<TakeSampleIntf, ITakeSampleIntfService> {
	@Autowired
	private ITakeSampleIntfService takeSampleIntfService;

	/**
	 * 分页列表查询
	 *
	 * @param takeSampleIntf
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "take_sample_intf-分页列表查询")
	@ApiOperation(value="take_sample_intf-分页列表查询", notes="take_sample_intf-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(TakeSampleIntf takeSampleIntf,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TakeSampleIntf> queryWrapper = QueryGenerator.initQueryWrapper(takeSampleIntf, req.getParameterMap());
		Page<TakeSampleIntf> page = new Page<TakeSampleIntf>(pageNo, pageSize);
		IPage<TakeSampleIntf> pageList = takeSampleIntfService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param takeSampleIntf
	 * @return
	 */
	@AutoLog(value = "take_sample_intf-添加")
	@ApiOperation(value="take_sample_intf-添加", notes="take_sample_intf-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody TakeSampleIntf takeSampleIntf) {
		takeSampleIntfService.save(takeSampleIntf);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param takeSampleIntf
	 * @return
	 */
	@AutoLog(value = "take_sample_intf-编辑")
	@ApiOperation(value="take_sample_intf-编辑", notes="take_sample_intf-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody TakeSampleIntf takeSampleIntf) {
		takeSampleIntfService.updateById(takeSampleIntf);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "take_sample_intf-通过id删除")
	@ApiOperation(value="take_sample_intf-通过id删除", notes="take_sample_intf-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		takeSampleIntfService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "take_sample_intf-批量删除")
	@ApiOperation(value="take_sample_intf-批量删除", notes="take_sample_intf-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.takeSampleIntfService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "take_sample_intf-通过id查询")
	@ApiOperation(value="take_sample_intf-通过id查询", notes="take_sample_intf-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		TakeSampleIntf takeSampleIntf = takeSampleIntfService.getById(id);
		if(takeSampleIntf==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(takeSampleIntf);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param takeSampleIntf
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TakeSampleIntf takeSampleIntf) {
        return super.exportXls(request, takeSampleIntf, TakeSampleIntf.class, "take_sample_intf");
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
        return super.importExcel(request, response, TakeSampleIntf.class);
    }

}
