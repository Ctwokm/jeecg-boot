package org.jeecg.modules.temp.controller;

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
import org.jeecg.modules.temp.entity.TakeSampleRec;
import org.jeecg.modules.temp.service.ITakeSampleRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
* @Description: take_sample_rec
* @Author: jeecg-boot
* @Date:   2021-03-02
* @Version: V1.0
*/
@Api(tags="take_sample_rec")
@RestController
@RequestMapping("/common/takeSampleRec")
@Slf4j
public class TakeSampleRecController extends JeecgController<TakeSampleRec, ITakeSampleRecService> {
   @Autowired
   private ITakeSampleRecService takeSampleRecService;

   /**
    * 分页列表查询
    *
    * @param takeSampleRec
    * @param pageNo
    * @param pageSize
    * @param req
    * @return
    */
   @AutoLog(value = "take_sample_rec-分页列表查询")
   @ApiOperation(value="take_sample_rec-分页列表查询", notes="take_sample_rec-分页列表查询")
   @GetMapping(value = "/list")
   public Result<?> queryPageList(TakeSampleRec takeSampleRec,
                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                  HttpServletRequest req) {
       QueryWrapper<TakeSampleRec> queryWrapper = QueryGenerator.initQueryWrapper(takeSampleRec, req.getParameterMap());
       Page<TakeSampleRec> page = new Page<TakeSampleRec>(pageNo, pageSize);
       IPage<TakeSampleRec> pageList = takeSampleRecService.page(page, queryWrapper);
       return Result.OK(pageList);
   }

   /**
    *   添加
    *
    * @param takeSampleRec
    * @return
    */
   @AutoLog(value = "take_sample_rec-添加")
   @ApiOperation(value="take_sample_rec-添加", notes="take_sample_rec-添加")
   @PostMapping(value = "/add")
   public Result<?> add(@RequestBody TakeSampleRec takeSampleRec) {
       takeSampleRecService.save(takeSampleRec);
       return Result.OK("添加成功！");
   }

   /**
    *  编辑
    *
    * @param takeSampleRec
    * @return
    */
   @AutoLog(value = "take_sample_rec-编辑")
   @ApiOperation(value="take_sample_rec-编辑", notes="take_sample_rec-编辑")
   @PutMapping(value = "/edit")
   public Result<?> edit(@RequestBody TakeSampleRec takeSampleRec) {
       takeSampleRecService.updateById(takeSampleRec);
       return Result.OK("编辑成功!");
   }

   /**
    *   通过id删除
    *
    * @param id
    * @return
    */
   @AutoLog(value = "take_sample_rec-通过id删除")
   @ApiOperation(value="take_sample_rec-通过id删除", notes="take_sample_rec-通过id删除")
   @DeleteMapping(value = "/delete")
   public Result<?> delete(@RequestParam(name="id",required=true) String id) {
       takeSampleRecService.removeById(id);
       return Result.OK("删除成功!");
   }

   /**
    *  批量删除
    *
    * @param ids
    * @return
    */
   @AutoLog(value = "take_sample_rec-批量删除")
   @ApiOperation(value="take_sample_rec-批量删除", notes="take_sample_rec-批量删除")
   @DeleteMapping(value = "/deleteBatch")
   public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
       this.takeSampleRecService.removeByIds(Arrays.asList(ids.split(",")));
       return Result.OK("批量删除成功!");
   }

   /**
    * 通过id查询
    *
    * @param id
    * @return
    */
   @AutoLog(value = "take_sample_rec-通过id查询")
   @ApiOperation(value="take_sample_rec-通过id查询", notes="take_sample_rec-通过id查询")
   @GetMapping(value = "/queryById")
   public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
       TakeSampleRec takeSampleRec = takeSampleRecService.getById(id);
       if(takeSampleRec==null) {
           return Result.error("未找到对应数据");
       }
       return Result.OK(takeSampleRec);
   }

   /**
   * 导出excel
   *
   * @param request
   * @param takeSampleRec
   */
   @RequestMapping(value = "/exportXls")
   public ModelAndView exportXls(HttpServletRequest request, TakeSampleRec takeSampleRec) {
       return super.exportXls(request, takeSampleRec, TakeSampleRec.class, "take_sample_rec");
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
       return super.importExcel(request, response, TakeSampleRec.class);
   }

}
