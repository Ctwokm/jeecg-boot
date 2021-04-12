package org.jeecg.modules.business.vo;

import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 船运煤
 * @author: liuzh
 * @date: 2021/3/17 13:05
 */

@Data
public class ShipEntity {
    //船
    public String contractNo;  // 合同编号
    public String contractName;//合同名称
    public String shipId;
    public String shipName;
    public String shipEngName;
    public String shipCode;
    public String fixShipNo;
    public String cabinCnt;
    public String loadTun;
    public String totalTun;
    public String width;
    public String length;
    public String nationCd;
    public String companyNo;
    public String companyName;
    public String remark;

    //运输记录
    public String shipRecId;
    public String shipTransNo;
    public String carrierNo;
    public String carrierName;
    public String waybillNo;
    public String waterTun;
    public String receiverNo;
    public String receiverName;
    @Dict(dicCode = "gk_ship_record_status")
    public String status;
    public String statusName;
    public String loadHours;
    public String unloadHours;
    public String startPortNo;
    public String finalPortNo;
    public String startPortName;
    public String finalPortName;
    public String startTime;
    public String estimateTime;
    public String factTime;
    public String norTime;
    public String arrangeTime;
    public String unloadTime;

    public String departTime;
    public String berthNo;
    public String opCode;
    public String updateCode;

    //货运记录
    public String recordNo;
    public String venNo;
    public String venName;
    public String mineNo;
    public String mineName;
    public String coalNo;
    public String coalName;
    public String cabinNos;
    public String tickQty;
    public String allTickQty;
    @Dict(dicCode = "gk_ship_loss_type")
    public String lossType;
    public String lossTypeName;
    public String kdQty;
    public String departQty;
    public String arriveQty;
    public String receiveQty;//到厂吨位
    @Dict(dicCode = "gk_ship_sample_type")
    public String sampleType;//采样方式
    public String sampleTypeName;//采样方式
    @Dict(dicCode = "gk_ship_is_batch")
    public String isBatch;
    public String isBatchName;
    public String isCurrent;
    public String currentBatchNo;
    public String currentSampleCode;
    public String currentSamplingCode;
    public String batchNo;
    public String sampleCode;
    public String samplingCode;
    public String trainNo;
    public String batchTime;
    public String jsonStr;

    //查询条件用的
    public String actionTag;
    public String beginDate;
    public String endDate;
    public String jsonString;
    public String resCode;
    public String resMsg;

    private String multShipId;
    private String oldStatus;
    private String beltNo;
    private String shipRecIDWayTag;

    private String allNetQty ;
    private String uploadStatus ;

    private String lossQty;
    private String lossQtyPercent;
}
