package org.jeecg.modules.rapid.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rapid.entity.CarrierInfo;
import org.jeecg.modules.rapid.entity.DictionaryTable;
import org.jeecg.modules.rapid.entity.ShipInfo;
import org.jeecg.modules.rapid.entity.ShipRecord;
import org.jeecg.modules.rapid.service.ICarrierInfoService;
import org.jeecg.modules.rapid.service.IDictionaryTableService;
import org.jeecg.modules.rapid.service.IShipInfoService;
import org.jeecg.modules.rapid.service.IShipRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: ship_record
 * @Author: jeecg-boot
 * @Date:   2021-03-17
 * @Version: V1.0
 */
@Api(tags="ship_record")
@RestController
@RequestMapping("/rapid/shipRecord")
@Slf4j
public class ShipRecordController extends JeecgController<ShipRecord, IShipRecordService> {
	 @Autowired
	 private IShipRecordService shipRecordService;

	 @Autowired
	 private IShipInfoService shipInfoService;

	 @Autowired
	 private ICarrierInfoService carrierInfoService;

	 @Autowired
	 private IDictionaryTableService dictionaryTableService;

	/**
	 * 分页列表查询
	 *
	 * @param shipRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "ship_record-分页列表查询")
	@ApiOperation(value="ship_record-分页列表查询", notes="ship_record-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(ShipRecord shipRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ShipRecord> queryWrapper = QueryGenerator.initQueryWrapper(shipRecord, req.getParameterMap());
		Page<ShipRecord> page = new Page<>(pageNo, pageSize);
		IPage<ShipRecord> pageList = shipRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param shipRecord
	 * @return
	 */
	@AutoLog(value = "ship_record-添加")
	@ApiOperation(value="ship_record-添加", notes="ship_record-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody ShipRecord shipRecord) {
		int total = shipRecordService.count();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String transNo = sdf.format(new Date()) + total;
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

		shipRecord.setShipTransNo(transNo);
		shipRecord.setRecStatus("1");
		shipRecord.setInsertTime(new Date());
		shipRecord.setOpCode(sysUser.getId());
		shipRecordService.save(shipRecord);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param shipRecord
	 * @return
	 */
	@AutoLog(value = "ship_record-编辑")
	@ApiOperation(value="ship_record-编辑", notes="ship_record-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody ShipRecord shipRecord) {
		QueryWrapper<ShipRecord> qw = new QueryWrapper<>();
		qw.in("ship_rec_id", shipRecord.getShipRecId());
		shipRecordService.update(shipRecord, qw);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param shipRecId
	 * @return
	 */
	@AutoLog(value = "ship_record-通过shipRecId删除")
	@ApiOperation(value="ship_record-通过shipRecId删除", notes="ship_record-通过shipRecId删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="shipRecId",required=true) String shipRecId) {
		Map<String, Object> map = new HashMap<>();
		map.put("ship_rec_id", shipRecId);
		shipRecordService.removeByMap(map);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "ship_record-批量删除")
	@ApiOperation(value="ship_record-批量删除", notes="ship_record-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		QueryWrapper<ShipRecord> qw = new QueryWrapper<>();
		qw.in("ship_rec_id", Arrays.asList(ids.split(",")));
		this.shipRecordService.remove(qw);
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "ship_record-通过id查询")
	@ApiOperation(value="ship_record-通过id查询", notes="ship_record-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		ShipRecord shipRecord = null;
		//shipRecord = shipRecordService.getById(id);
		if(shipRecord == null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(shipRecord);
	}

}
