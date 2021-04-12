package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by xieyt on 15-6-5.
 */
@Data
public class HotEntity {
    /////////////各个化验都有的
    public String hotResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String samplingName;//试样名称
    public String temperature;//温度
    public String humidity;//湿度
    public String samplingWeight;//试样重量
    public String standard;
    public String status;
    public String statusName;
    public String ccStatus;
    public String opName;
    public String opTime;
    public String opDate;
    public String opCode;
    public String updateTime;
    public String updateCode;
    public String confirmTime;
    public String confirmCode;
    public String insertTime;
    public String beginDate;
    public String endDate;
    public String id;
    public String batchType;
    public String otherStatusName;
    ////////////////////////////各个化验不同的
    public String tempIncrease;//主期升温
    public String jzValue;//校正值
    public String jqTemp;//机器热熔温
    public String qbAd;//弹筒热
    public String ccVal;//超差

    ////////////////////////////////////////
    public String updateString;
    public String dataType;
    public String resCode;
    public String resMsg;
    public String apprEventTypeCd;

    private String reportTag;
    private String usage;

}
