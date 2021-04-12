package org.jeecg.modules.business.vo;


import lombok.Data;

@Data
public class SEntity {
    /////////////各个化验都有的
    public String sResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String temperature;//温度
    public String humidity;//湿度
    public String opName;
    public String opTime;
    public String opDate;
    public String opCode;
    public String updateTime;
    public String updateCode;
    public String confirmTime;
    public String confirmCode;
    public String insertTime;
    public String hyDate;
    public String beginDate;
    public String endDate;
    public String batchType;
    public String lossStyle;
    public String otherStatusName;
    ////////////////////////////各个化验不同的
    public String samplingName;//试样名称
    public String standard;
    public String samplingWeight;//试样重量
    public String mad;//空干基水分
    public String stAd;//空干基全硫
    public String stD;//干基全硫
    public String ccVal;//超差
    public String jzStyle;//校正方式
    public String totalTime;//总时间
    public String status;
    public String ccStatus;
    public String statusName;
    public String proportion;//加权值
    public String proportionType;//加权类型

    ////////////////////////////////////////
    public String id;
    public String updateString;
    public String dataType;
    public String resCode;
    public String resMsg;
    public String apprEventTypeCd;
    public String labReportId;
    public String shipRecID;
    public String st;
    public String crc;
    public String had;
    private String reportTag;
    private String jsonStr;
    private String idsArray;
    private String indexType;
    private String indexValue;
    private String remark;

    private String sampleStatus;
    private String mtSampleSize;
    private String labSampleSize;
    private String size;
    private String usage;
}
