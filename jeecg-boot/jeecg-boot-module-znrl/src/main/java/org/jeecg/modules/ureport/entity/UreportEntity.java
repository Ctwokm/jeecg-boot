package org.jeecg.modules.ureport.entity;

import java.util.Date;
import java.util.Map;

/**
 * @program: ZNRL-Boot
 * @description: 报表功能实体类，导出报表时用于接收封装前端参数
 * 注意：开始时间和结束时间非直接导出必要参数，该参数的作用是去数据库查询需要的数据并封装到map中
 * @author: Hujw
 * @create: 2021-01-05 10:37
 **/
public class UreportEntity {
	/**
	 * 导出报表开始时间节点
	 */
	private String startTime;

	/**
	 * 导出报表结束时间节点
	 */
	private String endTime;

	/**
	 * 文件前缀名，例如：demo.ureport.xml，前缀即为demo
	 */
	private String ureportFileName;

	/**
	 * 导出报表数据参数，通常是前端传输的报表需要的json数据
	 */
	private Map requestMap;

	public UreportEntity() {
	}

	public UreportEntity(String startTime, String endTime, String ureportFileName, Map requestMap) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.ureportFileName = ureportFileName;
		this.requestMap = requestMap;
	}

}
