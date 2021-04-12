package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.CoalType;
import org.jeecg.modules.rapid.service.ICoalTypeService;

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
 * @Description: coal_type
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Api(tags="coal_type")
@RestController
@RequestMapping("/common/coalType")
@Slf4j
public class CoalTypeController extends JeecgController<CoalType, ICoalTypeService> {
	@Autowired
	private ICoalTypeService coalTypeService;

	/**
	 * 分页列表查询
	 *
	 * @param coalType
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "coal_type-分页列表查询")
	@ApiOperation(value="coal_type-分页列表查询", notes="coal_type-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CoalType coalType,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CoalType> queryWrapper = QueryGenerator.initQueryWrapper(coalType, req.getParameterMap());
		Page<CoalType> page = new Page<CoalType>(pageNo, pageSize);
		IPage<CoalType> pageList = coalTypeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param coalType
	 * @return
	 */
	@AutoLog(value = "coal_type-添加")
	@ApiOperation(value="coal_type-添加", notes="coal_type-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CoalType coalType) {
		coalTypeService.save(coalType);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param coalType
	 * @return
	 */
	@AutoLog(value = "coal_type-编辑")
	@ApiOperation(value="coal_type-编辑", notes="coal_type-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CoalType coalType) {
		coalTypeService.updateById(coalType);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "coal_type-通过id删除")
	@ApiOperation(value="coal_type-通过id删除", notes="coal_type-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		coalTypeService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "coal_type-批量删除")
	@ApiOperation(value="coal_type-批量删除", notes="coal_type-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.coalTypeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "coal_type-通过id查询")
	@ApiOperation(value="coal_type-通过id查询", notes="coal_type-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CoalType coalType = coalTypeService.getById(id);
		if(coalType==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(coalType);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param coalType
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CoalType coalType) {
        return super.exportXls(request, coalType, CoalType.class, "coal_type");
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
        return super.importExcel(request, response, CoalType.class);
    }

}
