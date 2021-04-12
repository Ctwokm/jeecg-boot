package org.jeecg.modules.rapid.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.rapid.entity.DictionaryTable;
import org.jeecg.modules.rapid.service.IDictionaryTableService;

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
 * @Description: dictionary_table
 * @Author: jeecg-boot
 * @Date:   2021-02-25
 * @Version: V1.0
 */
@Api(tags="dictionary_table")
@RestController
@RequestMapping("/common/dictionaryTable")
@Slf4j
public class DictionaryTableController extends JeecgController<DictionaryTable, IDictionaryTableService> {
	@Autowired
	private IDictionaryTableService dictionaryTableService;

	/**
	 * 分页列表查询
	 *
	 * @param dictionaryTable
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "dictionary_table-分页列表查询")
	@ApiOperation(value="dictionary_table-分页列表查询", notes="dictionary_table-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(DictionaryTable dictionaryTable,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DictionaryTable> queryWrapper = QueryGenerator.initQueryWrapper(dictionaryTable, req.getParameterMap());
		Page<DictionaryTable> page = new Page<DictionaryTable>(pageNo, pageSize);
		IPage<DictionaryTable> pageList = dictionaryTableService.page(page, queryWrapper);
		return Result.OK(pageList);
	}

	/**
	 *   添加
	 *
	 * @param dictionaryTable
	 * @return
	 */
	@AutoLog(value = "dictionary_table-添加")
	@ApiOperation(value="dictionary_table-添加", notes="dictionary_table-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody DictionaryTable dictionaryTable) {
		dictionaryTableService.save(dictionaryTable);
		return Result.OK("添加成功！");
	}

	/**
	 *  编辑
	 *
	 * @param dictionaryTable
	 * @return
	 */
	@AutoLog(value = "dictionary_table-编辑")
	@ApiOperation(value="dictionary_table-编辑", notes="dictionary_table-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody DictionaryTable dictionaryTable) {
		dictionaryTableService.updateById(dictionaryTable);
		return Result.OK("编辑成功!");
	}

	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dictionary_table-通过id删除")
	@ApiOperation(value="dictionary_table-通过id删除", notes="dictionary_table-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		dictionaryTableService.removeById(id);
		return Result.OK("删除成功!");
	}

	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "dictionary_table-批量删除")
	@ApiOperation(value="dictionary_table-批量删除", notes="dictionary_table-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.dictionaryTableService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}

	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "dictionary_table-通过id查询")
	@ApiOperation(value="dictionary_table-通过id查询", notes="dictionary_table-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		DictionaryTable dictionaryTable = dictionaryTableService.getById(id);
		if(dictionaryTable==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(dictionaryTable);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param dictionaryTable
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DictionaryTable dictionaryTable) {
        return super.exportXls(request, dictionaryTable, DictionaryTable.class, "dictionary_table");
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
        return super.importExcel(request, response, DictionaryTable.class);
    }

}
