package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by xieyt on 15-6-5.
 */
@Data
public class ScalesEntity {
    //共性属性
    public String scalesResId;
    public String laborCode;
    public String deviceNo;
    public String deviceName;
    public String ggNo;
    public String temperature;
    public String humidity;
    public String standard;
    public String opName;
    public String opTime;
    public String opDate;
    public String status;
    public String updateTime;
    public String updateCode;
    public String insertTime;
    public String opCode;
    public String beginDate;
    public String endDate;
    public String batchType;
    public String statusName;
    public String otherStatusName;
    public String indexType;//指标类型
    public String indexVal;
    //////////////////////////////////个性属性
    public String mypQty;
    public String samplingQty;
    public String totalQty;
    public String afterDryQty;
    public String testQty;
    public String qtyLoss;
    public String lossStyle;
    public String mt;
    public String mad;
    public String adStyle;
    public String aad;
    public String ad;
    public String vad;
    public String id;
    public String ccVal;
    public String ccStatus;
    //以json字符串的方式传入存储过程
    public String insertString;
    public String updateString;
    public String deleteString;
    public String publicString;
    public String scalesDataString;
    public String deviceDataString;
    public String dataType;
    public String confirmCd;
    public String confirmTime;
    public String confirmName;
    public String remark;
    public String orgKeyId;
    public String indexTypeName;
    //返回操作结果
    public String resCode;
    public String resMsg;
    public String usage;

}
