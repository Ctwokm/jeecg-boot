package org.jeecg.modules.rapid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.ShipInfo;
import org.jeecg.modules.rapid.service.ICarrierInfoService;
import org.jeecg.modules.rapid.service.IShipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@RestController
@Api(tags = "船基础信息管理")
@RequestMapping("/rapid/shipInfo")
@Slf4j
public class ShipInfoController {

	@Autowired
	public IShipInfoService shipInfoService;

	@Autowired
	public ICarrierInfoService carrierInfoService;

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
		shipInfoService.save(shipInfo);
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
		QueryWrapper<ShipInfo> qw = new QueryWrapper<>();
		qw.in("ship_id", shipInfo.getShipId());
		shipInfoService.update(shipInfo, qw);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ship_info-通过ship_id删除")
	@ApiOperation(value="ship_info-通过ship_id删除", notes="ship_info-通过ship_id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("ship_id", id);
		shipInfoService.removeByMap(map);
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
		QueryWrapper<ShipInfo> qw = new QueryWrapper<>();
		qw.in("ship_id", Arrays.asList(ids.split(",")));
		this.shipInfoService.remove(qw);
		return Result.OK("批量删除成功!");
	}
}
