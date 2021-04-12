package org.jeecg.modules.ureport.service;

import java.util.List;
import java.util.Map;

/**
 * @program: ZNRL-Boot
 * @description:  ureport标准化接口，导出报表专用，需要保证参数为String，String，Map，才可以被ureport识别
 * @author: Hujw
 * @create: 2021-01-06 23:48
 **/
public interface UreportService {

	/**
	 *
	 * @param uReportFileName 例如：demo.ureport.xml
	 * @param datasetName 文件所在路径
	 * @param qryParam  查询所需参数
	 * @return
	 */
	List<Map<String,String>> qryReportTestList(String uReportFileName, String datasetName, Map<String, Object> qryParam);

}
