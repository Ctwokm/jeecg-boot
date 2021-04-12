package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.MachinCmdInfo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.rapid.service.IMachinCmdInfoService;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: machin_cmd_info
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Api(tags="machin_cmd_info")
@RestController
@RequestMapping("/modules/machinCmdInfo")
@Slf4j
public class MachinCmdInfoController extends JeecgController<MachinCmdInfo, IMachinCmdInfoService> {
	@Autowired
	private IMachinCmdInfoService machinCmdInfoService;

	/**
	 * 分页列表查询
	 *
	 * @param machinCmdInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "machin_cmd_info-分页列表查询")
	@ApiOperation(value="machin_cmd_info-分页列表查询", notes="machin_cmd_info-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(MachinCmdInfo machinCmdInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<MachinCmdInfo> queryWrapper = QueryGenerator.initQueryWrapper(machinCmdInfo, req.getParameterMap());
		Page<MachinCmdInfo> page = new Page<MachinCmdInfo>(pageNo, pageSize);
		IPage<MachinCmdInfo> pageList = machinCmdInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param machinCmdInfo
	 * @return
	 */
	@AutoLog(value = "machin_cmd_info-添加")
	@ApiOperation(value="machin_cmd_info-添加", notes="machin_cmd_info-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody MachinCmdInfo machinCmdInfo) {
		machinCmdInfoService.save(machinCmdInfo);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param machinCmdInfo
	 * @return
	 */
	@AutoLog(value = "machin_cmd_info-编辑")
	@ApiOperation(value="machin_cmd_info-编辑", notes="machin_cmd_info-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody MachinCmdInfo machinCmdInfo) {
		machinCmdInfoService.updateById(machinCmdInfo);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "machin_cmd_info-通过id删除")
	@ApiOperation(value="machin_cmd_info-通过id删除", notes="machin_cmd_info-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		machinCmdInfoService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "machin_cmd_info-批量删除")
	@ApiOperation(value="machin_cmd_info-批量删除", notes="machin_cmd_info-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.machinCmdInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "machin_cmd_info-通过id查询")
	@ApiOperation(value="machin_cmd_info-通过id查询", notes="machin_cmd_info-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		MachinCmdInfo machinCmdInfo = machinCmdInfoService.getById(id);
		if(machinCmdInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(machinCmdInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param machinCmdInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, MachinCmdInfo machinCmdInfo) {
        return super.exportXls(request, machinCmdInfo, MachinCmdInfo.class, "machin_cmd_info");
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
        return super.importExcel(request, response, MachinCmdInfo.class);
    }

}
