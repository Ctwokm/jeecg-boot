package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.CarrierInfo;
import org.jeecg.modules.rapid.service.ICarrierInfoService;

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
 * @Description: carrier_info
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Api(tags="carrier_info")
@RestController
@RequestMapping("/common/carrierInfo")
@Slf4j
public class CarrierInfoController extends JeecgController<CarrierInfo, ICarrierInfoService> {
	@Autowired
	private ICarrierInfoService carrierInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param carrierInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "carrier_info-分页列表查询")
	@ApiOperation(value="carrier_info-分页列表查询", notes="carrier_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CarrierInfo carrierInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CarrierInfo> queryWrapper = QueryGenerator.initQueryWrapper(carrierInfo, req.getParameterMap());
		Page<CarrierInfo> page = new Page<CarrierInfo>(pageNo, pageSize);
		IPage<CarrierInfo> pageList = carrierInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param carrierInfo
	 * @return
	 */
	@AutoLog(value = "carrier_info-添加")
	@ApiOperation(value="carrier_info-添加", notes="carrier_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CarrierInfo carrierInfo) {
		carrierInfoService.save(carrierInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param carrierInfo
	 * @return
	 */
	@AutoLog(value = "carrier_info-编辑")
	@ApiOperation(value="carrier_info-编辑", notes="carrier_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CarrierInfo carrierInfo) {
		carrierInfoService.updateById(carrierInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "carrier_info-通过id删除")
	@ApiOperation(value="carrier_info-通过id删除", notes="carrier_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		carrierInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "carrier_info-批量删除")
	@ApiOperation(value="carrier_info-批量删除", notes="carrier_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.carrierInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "carrier_info-通过id查询")
	@ApiOperation(value="carrier_info-通过id查询", notes="carrier_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CarrierInfo carrierInfo = carrierInfoService.getById(id);
		if(carrierInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(carrierInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param carrierInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CarrierInfo carrierInfo) {
        return super.exportXls(request, carrierInfo, CarrierInfo.class, "carrier_info");
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
        return super.importExcel(request, response, CarrierInfo.class);
    }

}
