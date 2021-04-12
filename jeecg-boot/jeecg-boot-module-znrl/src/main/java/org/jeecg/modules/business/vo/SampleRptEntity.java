package org.jeecg.modules.business.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * Created by zj on 2021/3/3
 * 采样报表查询
 */
@Data
public class SampleRptEntity  {

    @Excel(name = "采样编码",width = 15)
    private String sampleCode;
    private String samplingCode;
    @Excel(name = "采样开始时间",width = 15)
    private String startTime;
    @Excel(name = "采样结束时间",width = 15)
    private String endTime;
    private String coalNo;
    @Excel(name = "煤种",width = 15)
    private String coalName;
    @Excel(name = "采样类型",width = 15)
    private String sampleType;//采样方式名称
    private String carNum;
    @Excel(name = "车(船)号",width = 15)
    private String carId;
    @Excel(name = "批次号",width = 15)
    private String batchNo;
    @Excel(name = "批次类型",width = 15)
    private String batchNoType;
    private String batchTypeName;
    private String samplePoints;
    @Excel(name = "采样点数",width = 15)
    private String sampleCnt;
    @Excel(name = "总吨数",width = 15)
    private String totalNetQty;
    @Excel(name = "采样重量",width = 15)
    private String allNetQty;
    private String venNo;
    private String venName;
    private String mineNo;
    private String mineName;
    private String colryNo;
    @Excel(name = "采样车数",width = 15)
    private String carCnt;
    private String opCode;
    private String sampleTyp;//采样方式编码
    private String userCode;

    private String jsonString;
    private String resCode;
    private String resMsg;

    private String sampleQty;
    @Excel(name = "采样设备",width = 15)
    private String machineCode;
    private String suofenCnt;
    private String sampleCntNew;
    private String usageRate;//北仑采样机投运率
    private String blNeed;//北仑需要按批次统计的采样机投运率
    private String sampleFrequence;//从江南需求增加而来(采样间隔)
    private String sampleGape;//从江南需求增加而来(缩分间隔)
    private String remark;
    private String barrelCode;
    private String coordinate;
	private String userName;
    private String barrelCnt;
    private String batchCnt;
    private String autoSampleBatchCnt;
    private String manualSampleBatchCnt;
    private String autoSampleRate;
    private String autoSamplingBatchCnt;
    private String manualSamplingBatchCnt;
    private String autoSamplingRate;
    private String operType;
    private String batchTime;
    private String coalType;
    private String recordNo;
    private String boilerNo;
    private String goodsReceiver;
    private String bc;
    private String sampleSize;
    private String balanceNo;
    private String weightTime;
    private String weight;
    private String barrelId;
    private String teamNo;//班次
    private String classNo;//值次
}
