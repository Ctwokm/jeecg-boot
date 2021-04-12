package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by xieyt on 15-6-5.
 */
@Data
public class IndstAnalyEntity {
    /////////////各个化验都有的
    public String iaResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String temperature;//温度
    public String humidity;//湿度
    public String lossStyle;

    public String standard;
    public String samplingName;
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
    public String batchType;
    public String otherStatusName;
    ////////////////////////////各个化验不同的
    public String shKggWeight;//水灰空坩埚重
    public String shSamplingWeight;//水灰煤样重

    public String mad;//空干基水分
    public String aad;//空干基灰分
    public String ad;//干基灰分
    public String hkTotalWeight;//挥空坩埚加样重
    public String hkSamplingWeight;//挥空煤样重量
    public String vad;//空干基挥发分
    public String vdaf;//收到基挥发分
    public String ccVal;//超差
    public String ccStatus;
    public String status;
    public String statusName;
    ////////////////////////////////////////
    public String id;
    public String updateString;
    public String dataType;
    public String resCode;
    public String resMsg;
    public String apprEventTypeCd;

    private String reportTag;
    private String usage;

}
