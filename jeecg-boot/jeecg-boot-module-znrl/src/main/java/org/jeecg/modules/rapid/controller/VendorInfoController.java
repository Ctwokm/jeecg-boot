package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.VendorInfo;
import org.jeecg.modules.rapid.service.IVendorInfoService;

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
 * @Description: vendor_info
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Api(tags="vendor_info")
@RestController
@RequestMapping("/common/vendorInfo")
@Slf4j
public class VendorInfoController extends JeecgController<VendorInfo, IVendorInfoService> {
	@Autowired
	private IVendorInfoService vendorInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param vendorInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "vendor_info-分页列表查询")
	@ApiOperation(value="vendor_info-分页列表查询", notes="vendor_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(VendorInfo vendorInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<VendorInfo> queryWrapper = QueryGenerator.initQueryWrapper(vendorInfo, req.getParameterMap());
		Page<VendorInfo> page = new Page<VendorInfo>(pageNo, pageSize);
		IPage<VendorInfo> pageList = vendorInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param vendorInfo
	 * @return
	 */
	@AutoLog(value = "vendor_info-添加")
	@ApiOperation(value="vendor_info-添加", notes="vendor_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody VendorInfo vendorInfo) {
		vendorInfoService.save(vendorInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param vendorInfo
	 * @return
	 */
	@AutoLog(value = "vendor_info-编辑")
	@ApiOperation(value="vendor_info-编辑", notes="vendor_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody VendorInfo vendorInfo) {
		vendorInfoService.updateById(vendorInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "vendor_info-通过id删除")
	@ApiOperation(value="vendor_info-通过id删除", notes="vendor_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		vendorInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "vendor_info-批量删除")
	@ApiOperation(value="vendor_info-批量删除", notes="vendor_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.vendorInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "vendor_info-通过id查询")
	@ApiOperation(value="vendor_info-通过id查询", notes="vendor_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		VendorInfo vendorInfo = vendorInfoService.getById(id);
		if(vendorInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(vendorInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param vendorInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, VendorInfo vendorInfo) {
        return super.exportXls(request, vendorInfo, VendorInfo.class, "vendor_info");
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
        return super.importExcel(request, response, VendorInfo.class);
    }

}
