package org.jeecg.modules.business.vo;

import lombok.Data;
import org.jeecg.common.aspect.annotation.Dict;

@Data
public class ShipBatchEntity {
    private String shipRecId;
    private String batchNo;
    private String dayBatchNum;
    private String shipTransNo;//船运编号
    private String venNo;
    private String venName;
    private String mineNo;
    private String mineName;
    private String coalNo;
    private String coalName;
    private String thisBatchQty;
    private String sampleCode;
    private String samplingCode;
    private String laborCode;
    @Dict(dicCode = "gk_ship_batch_valid")
    private String isValid;//批次状态值
    private String batchTime;

    private String opCode;
}
