package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.ShipInfo;
import org.jeecg.modules.rapid.service.IShipInfoService;

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
 * @Description: ship_info
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
@Api(tags="ship_info")
@RestController
@RequestMapping("/ShipInfo/shipInfo")
@Slf4j
public class ShipBasicInfoController extends JeecgController<ShipInfo, IShipInfoService> {
	@Autowired
	private IShipInfoService shipInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param shipInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ship_info-分页列表查询")
	@ApiOperation(value="ship_info-分页列表查询", notes="ship_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ShipInfo shipInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ShipInfo> queryWrapper = QueryGenerator.initQueryWrapper(shipInfo, req.getParameterMap());
		Page<ShipInfo> page = new Page<ShipInfo>(pageNo, pageSize);
		IPage<ShipInfo> pageList = shipInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param shipInfo
	 * @return
	 */
	@AutoLog(value = "ship_info-添加")
	@ApiOperation(value="ship_info-添加", notes="ship_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ShipInfo shipInfo) {
		shipInfoService.add(shipInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param shipInfo
	 * @return
	 */
	@AutoLog(value = "ship_info-编辑")
	@ApiOperation(value="ship_info-编辑", notes="ship_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ShipInfo shipInfo) {
		System.out.println("===============================================");
		System.out.println(shipInfo);
		shipInfoService.editById(shipInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param shipId
	 * @return
	 */
	@AutoLog(value = "ship_info-通过id删除")
	@ApiOperation(value="ship_info-通过id删除", notes="ship_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="shipId",required=true) String shipId) {
		shipInfoService.deleteById(shipId);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ship_info-批量删除")
	@ApiOperation(value="ship_info-批量删除", notes="ship_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		System.out.println(ids);
		this.shipInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ship_info-通过id查询")
	@ApiOperation(value="ship_info-通过id查询", notes="ship_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ShipInfo shipInfo = shipInfoService.getById(id);
		if(shipInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(shipInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param shipInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ShipInfo shipInfo) {
        return super.exportXls(request, shipInfo, ShipInfo.class, "ship_info");
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
        return super.importExcel(request, response, ShipInfo.class);
    }

}
