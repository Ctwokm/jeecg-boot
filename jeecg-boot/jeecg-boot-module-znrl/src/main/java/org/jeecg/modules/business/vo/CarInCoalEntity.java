package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: Zouzp
 * @create: 2021/3/19 9:08
 **/
@Data
public class CarInCoalEntity extends BaseEntity{
    //用于汽车来煤的相关操作于存储过程调用使用，registerEntity 相关属性过多会导致：Data too Long Exception；
    public String recordNo;//入厂流水
    public String tickNo;//票号
    public String batchNo;//批次号
    public String carId;
    public String cardId;
    public String venNo;
    public String venName;
    public String coalNo;
    public String coalName;
    public String colryNo;
    public String mineName;
    public String carrierNo;
    public String carrierName;
    public String mzQty;
    public String pzQty;
    public String netQty;
    public String tickQty;
    public String kdQty;
    public String coalWater;
    public String sampleTyp;
    public String recordDtm;
    public String czDtm;
    public String jqDtm;
    public String remark;
    public String status;
    public String firstNum;
    public String contractCode;
    public String sampleCode;
    public String cyjNo;
    public String transTyp;
    public String leaveFlg;
    public String xmArea;
    public String beginTime;//入厂开始时间
    public String endTime;//入厂结束时间


}
