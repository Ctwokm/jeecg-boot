package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * @Auther: Hujw
 * @Date: 2021/3/16
 * @Description:
 */
@Data
public class HEntity {
    /////////////各个化验都有的
    public String hResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String samplingName;//试样名称
    public String temperature;//温度
    public String humidity;//湿度
    public String standard;
    public String samplingWeight;//试样重量

    public String ccVal;//超差
    public String status;
    public String statusName;
    public String opName;
    public String opTime;
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
    public String method;//方法
    public String mad;//水分
    public String cad;//空干基碳
    public String cd; //干基碳
    public String had;//空干基氢
    public String hd; //干基氢
    ////////////////////////////////////////
    public String updateString;
    public String dataType;
    public String resCode;
    public String resMsg;
    public String apprEventTypeCd;
    private String ccStatus;
    private String reportTag;
    public String opDate;
    private String usage;

}
