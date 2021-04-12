package org.jeecg.modules.rapid.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.ShipCargoRecord;
import org.jeecg.modules.rapid.entity.ShipRecord;
import org.jeecg.modules.rapid.service.IShipCargoRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: ship_cargo_record
 * @Author: jeecg-boot
 * @Date:   2021-03-18
 * @Version: V1.0
 */
@Api(tags="ship_cargo_record")
@RestController
@RequestMapping("/rapid/shipCargoRecord")
@Slf4j
public class ShipCargoRecordController extends JeecgController<ShipCargoRecord, IShipCargoRecordService> {
	@Autowired
	private IShipCargoRecordService shipCargoRecordService;

	/**
	 * 分页列表查询
	 *
	 * @param shipCargoRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ship_cargo_record-分页列表查询")
	@ApiOperation(value="ship_cargo_record-分页列表查询", notes="ship_cargo_record-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ShipCargoRecord shipCargoRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ShipCargoRecord> queryWrapper = QueryGenerator.initQueryWrapper(shipCargoRecord, req.getParameterMap());
		Page<ShipCargoRecord> page = new Page<>(pageNo, pageSize);
		IPage<ShipCargoRecord> pageList = shipCargoRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param shipCargoRecord
	 * @return
	 */
	@AutoLog(value = "ship_cargo_record-添加")
	@ApiOperation(value="ship_cargo_record-添加", notes="ship_cargo_record-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ShipCargoRecord shipCargoRecord) {
		shipCargoRecordService.save(shipCargoRecord);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param shipCargoRecord
	 * @return
	 */
	@AutoLog(value = "ship_cargo_record-编辑")
	@ApiOperation(value="ship_cargo_record-编辑", notes="ship_cargo_record-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ShipCargoRecord shipCargoRecord) {
		QueryWrapper<ShipCargoRecord> qw = new QueryWrapper<>();
		qw.in("record_no", shipCargoRecord.getRecordNo());
		shipCargoRecordService.update(shipCargoRecord, qw);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ship_cargo_record-通过id删除")
	@ApiOperation(value="ship_cargo_record-通过id删除", notes="ship_cargo_record-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("record_no", id);
		shipCargoRecordService.removeByMap(map);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ship_cargo_record-批量删除")
	@ApiOperation(value="ship_cargo_record-批量删除", notes="ship_cargo_record-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		QueryWrapper<ShipCargoRecord> qw = new QueryWrapper<>();
		qw.in("record_no", Arrays.asList(ids.split(",")));
		this.shipCargoRecordService.remove(qw);
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ship_cargo_record-通过id查询")
	@ApiOperation(value="ship_cargo_record-通过id查询", notes="ship_cargo_record-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ShipCargoRecord shipCargoRecord = null;
		//shipCargoRecord = shipCargoRecordService.getById(id);
		if(shipCargoRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(shipCargoRecord);
	}

}
