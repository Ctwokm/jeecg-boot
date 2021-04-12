package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * create by zzp
 * 汽车来煤相关实体类
 * 20210319
 */

@Data
public class CarTransRecordEntity extends BaseEntity {
    public String recordId;//主键ID
    public String recordNo;//入厂序号，也做流水号
    public String cardId;//卡号
    public String cardTyp;//卡类型
    public String cardTypeName;//卡类型
    public String carId;//前车牌号
    public String backCarId;//后车牌号
    public String mineNo;//煤矿
    public String colryName;//煤矿MINE_NAME
    public String carTyp;//车型
    public String carTypeName;//车型名称
    public String coalNo;//煤种
    public String coalName;//煤种名称
    public String carrierNo;//运输单位id
    public String orgNo;
    public String carrierName;//运输单位
    public String venNo;//供应商
    public String venName;//供应商名称
    public String coalWater;//湿度  1湿煤 2一般湿煤 3干煤 4一般干煤
    public String coalWaterName;//湿度  1湿煤 2一般湿煤 3干煤 4一般干煤
    public String tickNo;//票号
    public String tickQty;//票重(出矿净重)
    public String sampleTyp;//采样方式
    public String recordDtm;//记录时间
    public String czDtm;//入厂时间
    public String remark;//备注
    public String batchNo;//批次号

    public String beginDate;
    public String endDate;

    public String insertTime;//插入数据时间
    public String opCode;//操作人员

    public String doActionTag;//动作标识 ADD, MOD
    public String jsonString;//json字符串

    //返回操作结果
    public String resCode;
    public String resMsg;

    private String mzQty;//毛重
    private String pzQty;//皮重
    private String netQty;//净重
    private String kdQty;//扣吨
    private String manCheckSample;
    private String attachBatchNo;
    private String tickPzQty;//出矿皮重
    private String tickMzQty;//出矿毛重
    private String cyjNo;//采样机编号或通道号
    private String channel;
    private String actionTag;
    private String readTag;

    private String ghIndex;

    private String startPlace;
    private String finalPlace;
    private String startName;
    private String finalName;

    private String sampleType;
    public String sampleDtm;//采样厂时间
    public String jqDtm;//称轻时间
    public String carStatus;//车辆状态
    public String sampleCode;
    public String updateString;
    public String publicString;
    private String batchNoType;
    public String totalEntryCnt;//入厂车数
    public String totalMzQty;//总毛重
    public String totalPzQty;//总皮重
    public String totalKdQty;//总扣吨
    public String totalNetQty;//总净重
    public String totalTickQty;//总票重(矿发数量)
    public String apprEventTypeCd;

    public String mineCardType;//矿卡类型
    public String trainNo;
    public String beginTime;//入厂开始时间
    public String endTime;//入厂结束时间
    public Integer pageNo;
    public Integer pageSize;
}
