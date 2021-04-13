package org.jeecg.modules.common.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.activiti.util.Constant;
import org.jeecg.modules.business.vo.CommonQueryEntity;
import org.jeecg.modules.business.vo.PkgEntity;
import org.jeecg.modules.business.vo.RegisterEntity;
import org.jeecg.modules.common.service.IBizToolService;
import org.jeecgframework.poi.cache.manager.POICacheManager;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.def.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecgframework.poi.excel.view.JeecgTemplateExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Auther: Hujw
 * @Date: 2021/3/16
 * @Description: 基本业务工具Api接口
 */

@RestController
@Api(tags = "业务工具")
@RequestMapping("/znrl/biztool")
@Slf4j
public class BizToolController {

    @Autowired
    public IBizToolService bizToolService;


    @ApiOperation(value="导出报表通用方法", notes="根据EXCEL模板和sql查询的数据组装报表")
    @GetMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
        TemplateExportParams params = new TemplateExportParams();
        params.setTemplateUrl("doc/trainDemo.xlsx");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "测试一下热部署");
        map.put("name", "hujw");
        map.put("age", 25);
        map.put("company", "NEPT");
        map.put("date", "2020-07-13");
        ModelAndView mv = new ModelAndView(new JeecgTemplateExcelView());
        mv.addObject(TemplateExcelConstants.FILE_NAME,"nept");
        mv.addObject(TemplateExcelConstants.PARAMS,params);
        mv.addObject(TemplateExcelConstants.MAP_DATA, map);
        return mv;
    }



    @AutoLog(value = "公共可分页查询语句")
    @ApiOperation(value="公共可分页查询语句", notes="公共可分页查询语句")
    @GetMapping(value = "/query")
    public Result<List<Map<String,Object>>> query(CommonQueryEntity commonQueryEntity) {
        Result result = new Result();
        if (commonQueryEntity.getSqlId() == null || commonQueryEntity.getSqlId() == "") {
            result.setSuccess(false);
            result.setMessage("未指定sql语句id");
        }

        if (commonQueryEntity.getIsPage() == null || commonQueryEntity.getIsPage() == "") {
            //直接传参查询，返回结果不分页
            Map maps = (Map) JSON.parse(commonQueryEntity.getJsonString());

            maps.put("sqlId",commonQueryEntity.getSqlId());
            try {
                List<Map<String,Object>> list = bizToolService.query(maps);

                result.setResult(list);
                result.setSuccess(true);

            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage("查询失败");
            }
        }
        else if ((commonQueryEntity.getIsPage() == "Y") || commonQueryEntity.getIsPage().equalsIgnoreCase("Y")) {

            //获取分页参数，根据参数进行分页查询
            Page<CommonQueryEntity> pageList = new Page<CommonQueryEntity>(commonQueryEntity.getPageNo(),commonQueryEntity.getPageSize());
            Map maps = (Map) JSON.parse(commonQueryEntity.getJsonString());

            try {
                maps.put("sqlId",commonQueryEntity.getSqlId());
                pageList = bizToolService.queryByPage(pageList,maps);
            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage("查询失败");
            }
            result.setSuccess(true);
            result.setResult(pageList);
        }else{
            //直接传参查询，返回结果不分页
            Map<String, Object> maps =  (Map) JSON.parse(commonQueryEntity.getJsonString());
            maps.put("sqlId",commonQueryEntity.getSqlId());
            try {
                List<Map<String,Object>> list = bizToolService.query(maps);

                result.setResult(list);
                result.setSuccess(true);

            } catch (Exception e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setMessage("查询失败");
            }

        }
        return result;
    }

    @AutoLog(value = "存储过程调用")
    @ApiOperation(value="存储过程调用", notes="存储过程调用专用Api,所有存储过程均使用本接口调用")
    @PostMapping(value = "/doCall")
    public Result doCall(@RequestBody PkgEntity pkgEntity) {
        //todo hujw:调试的时候切记json字符串jsonString中不要有空格和换行，存在会导致json字符串无法被controller正常识别，出现异常，例子如下
        // "{\"id\":\"30fe93bacb01bfba4d502b17cdce0041\",\"parentId\":\"60194678c39ed439d1d19265e3289706\",\"tug\":\"34\",\"type\":\"类型2\",\"release_time\":\"2002-06-21 14:12:39\",\"receive_time\":\"2083-03-07 15:15:43\",\"dd_num\":\"425411019641\",\"departure_time\":\"2178-03-13 20:39:40\",\"work_begin_time\":\"2016-07-07 09:01:39\"}"
        // 实在是不想自己一个个的转的话可以在线选择压缩转义json，这个网站就可以https://www.sojson.com/yasuo.html，然后去掉空格和换行
        // 当然正式环境下，前端生成的json字符串是已经转义的就没有这个问题的存在
        Result result = new Result();
        try {
            if (pkgEntity.getPkgName() == null) {
                result.setSuccess(false);
                result.setMessage("未指定过程名称");
            }else{
                //获取登录操作员的ID
//                LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//                pkgEntity.setOpCode(sysUser.getOrgCode());
                //pkgEntity.setOpCode("0");
                //获取业务参数
                String jsonString = pkgEntity.getJsonString().replace("&quot;","\"");
                pkgEntity.setJsonString(jsonString);
                bizToolService.doCall(pkgEntity);
                //设置正常的返回结果
                if ("1".equals(pkgEntity.getResCode())) {
                    // todo 如果状态时'1'的时候，说明是手动返回的异常，可以直接把错误信息存到系统日志表，方便运维调试
                    result.setSuccess(false);
                }else{
                    result.setSuccess(true);
                }
                //设置返回消息
                result.setMessage(pkgEntity.getResMsg());

                //设置有可用信息的返回内容
                result.setResult(pkgEntity.getResInfo());
            }
        } catch (Exception e) {
            result.setSuccess(false);
            String[] MessageStrings = e.getCause().getMessage().split("-->");
            result.setMessage(MessageStrings[MessageStrings.length-1]);
            e.printStackTrace();
        }
        return result;
    }
}
