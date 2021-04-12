package org.jeecg.modules.ureport.mapper;

import java.util.List;
import java.util.Map;

public interface UreportMapper {
	List<Map<String,String>> qryReportTestList(Map<String, String> qryParam);
}
