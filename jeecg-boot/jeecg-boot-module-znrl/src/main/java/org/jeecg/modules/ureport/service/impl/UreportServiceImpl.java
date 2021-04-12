package org.jeecg.modules.ureport.service.impl;

import org.apache.commons.lang.StringUtils;
import org.jeecg.modules.ureport.mapper.UreportMapper;
import org.jeecg.modules.ureport.service.UreportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Hujw
 * @create: 2021-01-06 23:48
 **/
@Service("UreportServiceImpl")
public class UreportServiceImpl implements UreportService {

	@Autowired
	UreportMapper ureportMapper;
	/**
	 * @param uReportFileName 例如：demo.ureport.xml
	 * @param datasetName     文件所在路径
	 * @param qryParam        查询所需参数
	 * @return
	 */
	@Override
	public List<Map<String, String>> qryReportTestList(String uReportFileName, String datasetName, Map<String, Object> qryParam) {
		String beginDt = (null==qryParam.get("begin_time")||"".equals(qryParam.get("begin_time"))) ? null : qryParam.get("begin_time").toString();
		String endDt = ( null == qryParam.get("end_time")||"".equals( qryParam.get("end_time")) ) ? null : qryParam.get("end_time").toString();
		Map<String, String> Param = new HashMap<>();
		Param.put("begin_time",beginDt);
		Param.put("end_time",endDt);
		List<Map<String, String>> list = ureportMapper.qryReportTestList(Param);
		return ureportMapper.qryReportTestList(Param);
	}
}
