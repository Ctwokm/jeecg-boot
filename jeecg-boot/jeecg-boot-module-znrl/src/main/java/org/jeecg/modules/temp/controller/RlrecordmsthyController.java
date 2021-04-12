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
import org.jeecg.modules.temp.entity.Rlrecordmsthy;
import org.jeecg.modules.temp.service.IRlrecordmsthyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: rlrecordmsthy
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Api(tags="rlrecordmsthy")
@RestController
@RequestMapping("/modules/rlrecordmsthy")
@Slf4j
public class RlrecordmsthyController extends JeecgController<Rlrecordmsthy, IRlrecordmsthyService> {
	@Autowired
	private IRlrecordmsthyService rlrecordmsthyService;

	/**
	 * 分页列表查询
	 *
	 * @param rlrecordmsthy
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "rlrecordmsthy-分页列表查询")
	@ApiOperation(value="rlrecordmsthy-分页列表查询", notes="rlrecordmsthy-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Rlrecordmsthy rlrecordmsthy,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Rlrecordmsthy> queryWrapper = QueryGenerator.initQueryWrapper(rlrecordmsthy, req.getParameterMap());
		Page<Rlrecordmsthy> page = new Page<Rlrecordmsthy>(pageNo, pageSize);
		IPage<Rlrecordmsthy> pageList = rlrecordmsthyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param rlrecordmsthy
	 * @return
	 */
	@AutoLog(value = "rlrecordmsthy-添加")
	@ApiOperation(value="rlrecordmsthy-添加", notes="rlrecordmsthy-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Rlrecordmsthy rlrecordmsthy) {
		rlrecordmsthyService.save(rlrecordmsthy);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param rlrecordmsthy
	 * @return
	 */
	@AutoLog(value = "rlrecordmsthy-编辑")
	@ApiOperation(value="rlrecordmsthy-编辑", notes="rlrecordmsthy-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Rlrecordmsthy rlrecordmsthy) {
		rlrecordmsthyService.updateById(rlrecordmsthy);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "rlrecordmsthy-通过id删除")
	@ApiOperation(value="rlrecordmsthy-通过id删除", notes="rlrecordmsthy-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		rlrecordmsthyService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "rlrecordmsthy-批量删除")
	@ApiOperation(value="rlrecordmsthy-批量删除", notes="rlrecordmsthy-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.rlrecordmsthyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "rlrecordmsthy-通过id查询")
	@ApiOperation(value="rlrecordmsthy-通过id查询", notes="rlrecordmsthy-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Rlrecordmsthy rlrecordmsthy = rlrecordmsthyService.getById(id);
		if(rlrecordmsthy==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(rlrecordmsthy);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param rlrecordmsthy
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Rlrecordmsthy rlrecordmsthy) {
        return super.exportXls(request, rlrecordmsthy, Rlrecordmsthy.class, "rlrecordmsthy");
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
        return super.importExcel(request, response, Rlrecordmsthy.class);
    }

}
