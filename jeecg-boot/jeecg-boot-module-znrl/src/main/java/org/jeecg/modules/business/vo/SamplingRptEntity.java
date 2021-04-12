package org.jeecg.modules.business.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * Created by zj on 2021/3/9.
 * 制样报表查询
 */
@Data
public class SamplingRptEntity {
//    汇总、明细用
    @Excel(name = "制样编码" ,width = 15)
    private String sampleCode;
    @Excel(name = "批次类型" ,width = 15)
    private String batchTypeName;//批次类型名称
    @Excel(name = "制样方式" ,width = 15)
    private String zyTypeName;
    @Excel(name = "来样重量" ,width = 15)
    private String zyWeight;
    @Excel(name = "3mm存查样封装码" ,width = 15)
    private String packCode3;// --3mm封装码
    @Excel(name = "0.2mm化验封装码" ,width = 15)
    private String packCode21; //--0.2mm化验封装码 3
    @Excel(name = "0.2mm存查样封装码" ,width = 15)
    private String packCode22; //--0.2mm存查样封装码 4
    @Excel(name = "6mm全水样封装码" ,width = 15)
    private String packCode6; //--6mm全水样封装码
    @Excel(name = "制样时间" ,width = 15)
    private String startTime;
    @Excel(name = "制样人" ,width = 15)
    private String userName;

    private String samplingCode;

    private String laborCode;

    private String batchNo;

    private String zyType;





    private String endTime;

    private String userCode;





    private String batchNoType;

    private String doActionTag;

    private String mineNo;

    private String coalNo;

    private String carrierNo;

    private String sampleTeamNo;

    private String allNetQty;

//    明细用

    private String sampleType;

    private String coalWater;

    private String coalSize;
    private String coalName;
    private String mineName;
    private String venName;
    private String sampleWeight;
    private String sampleRemark;

    private String sampleWeight61;
    private String sampleWeight62;
    private String sampleWeight31;
    private String sampleWeight32;
    private String sampleWeight21;
    private String sampleWeight22;

    private String sampleWeight33;
    private String sampleWeight34;
    private String sampleWeight35;

    private String sampleWeight63;
    private String packCode63;
    private String sampleWeight71;
    private String packCode71;

    private String zyWeight6;

    private String zyWeight3;

    private String zyWeight21;

    private String zyWeight22;

    private String zyWeight33;
    private String zyWeight34;
    private String zyWeight35;

    private String packCode; //封装码


    private String packCode62;
    private String packCode31;

    private String packCode33;// --3mm一次抽查封装码
    private String packCode34;// --3mm二次抽查封装码
    private String packCode35;// --3mm三次抽查封装码

    private String carIds;
    private String venNo;
    private String actionTag;
    private String jsonString;
    private String opCode;

    private String resCode;
    private String resMsg;

    private String rlPlace;
    private String rlDate;

    private String startTime61;
    private String endTime61;
    private String startTime62;
    private String endTime62;
    private String startTime32;
    private String endTime32;
    private String startTime21;
    private String endTime21;
    private String startTime22;
    private String endTime22;
    private String startTime33;
    private String endTime33;
    private String startTime34;
    private String endTime34;
    private String startTime35;
    private String endTime35;
    private String machineCode;

    public String startPlaceName;//始发站


    public String carCnt;//总车节数
    public String carJqCnt;//总车节数

    public String remark;

    private String teamNo;

    private String sampleDate;

    private String insertTime;

    private String shipName;

    private String arguementFlag;

    private String batchRelaId;
    private String samplingStatus;
    private String laborStatus;
    private String laborCode4Check;
    private String sampleCode4Check;
    private String samplingCode4Check;

    private String goodsReceiver;
    private String barrelCnt;
    private String sampleBelongDt;
    private String laborCodeOrg;


}
