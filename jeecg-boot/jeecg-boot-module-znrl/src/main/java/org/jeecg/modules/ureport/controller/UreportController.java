package org.jeecg.modules.ureport.controller;/*
package org.jeecg.modules.ureport.controller;

import com.bstek.ureport.export.ExportManager;
import com.bstek.ureport.export.html.HtmlReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.njgdhb.common.annotation.AutoLog;
import com.njgdhb.modules.ureport.entity.UreportEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * @program: ZNRL-Boot
 * @description: 报表导出服务，按照目前的情况基本可以分为两种模式，以参数中的Map是否为空来区分，如果是空，则需要去调用service对应报表的sql去查询数据库
 * 1、前端数据json直接过来组装map导出，适用于数据已知且数据量不大的情况
 * 2、前端调用service查询报表数据组装导出，数据量大或未知
 * 2.1、如果查询的话基本上是基于起止日期查询，因此日期参数必须要有，startTime和endTime
 * @author: Hujw
 * @create: 2021-01-04 17:47
 **//*


@RestController
@RequestMapping("/customizeUreport")
@Api(tags="报表导出")
@Slf4j
public class UreportController {

	@Autowired
	ExportManager exportManager;

//	@AutoLog(value = "报表导出功能-导出Excel")
//	@ApiOperation(value = "报表导出功能-导出Excel", notes = "报表导出功能-导出Excel")
//	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
//	@ResponseBody
//	public Object demoReportExcel(HttpServletRequest request, UreportEntity ureportEntity){
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		Map<String, Object> map = new HashMap<String, Object>();
////		Date st = null;
////		Date et = null;
////		if (StringUtils.isNotEmpty(starttime)) {
////			st = sdf.parse(starttime);
////			map.put("starttime", st);
////		}
////		if (StringUtils.isNotEmpty(endtime)) {
////			et = sdf.parse(endtime);
////			map.put("endtime", et);
////		}
//		HtmlReport htmlReport = exportManager.exportHtml("file:upaybus.ureport.xml", request.getContextPath(), map);
////		return htmlReport;
//		return null;
//	}

	@AutoLog(value = "报表导出功能-展示HTML")
	@ApiOperation(value = "报表导出功能-展示HTML", notes = "报表导出功能-展示HTML")
	@RequestMapping(value = "/exportHtml", method = RequestMethod.GET)
	@ResponseBody
	public Object demoReportHtml(HttpServletRequest request,
	                             @RequestParam(value="ureportFileName", required=true) String ureportFileName) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> map = new HashMap<String, Object>();
//		Date st = null;
//		Date et = null;
//		if (StringUtils.isNotEmpty(ureportEntity.getStartTime())) {
//			st = sdf.parse(ureportEntity.getEndTime());
//			map.put("start_time", st);
//		}
//		if (StringUtils.isNotEmpty(ureportEntity.getEndTime())) {
//			et = sdf.parse(ureportEntity.getEndTime());
//			map.put("end_time", et);
//		}
			HtmlReport htmlReport = exportManager.exportHtml("file:"+ureportFileName+".ureport.xml", request.getContextPath(), map);
			return htmlReport;
		}



}
*/
