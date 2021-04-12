package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: Zouzp
 * @create: 2021/3/3 11:16
 **/
@Data
public class CarInfoEntity {
    //基本信息
    public String recId;
    public String orgNo;
    public String orgName;
    public String validSta;
    public String statusName;
    public String carId;
    public String backCarId;
    public String carTyp;
    public String carTypeName;
    public String transTyp;
    public String cardId;
    public String cardTyp;
    public String regDtm;
    public String endDtm;
    public String stdQty;
    public String floatQty;
    public String blkLstFlg;
    public String carToWLength;
    //
    public String cardTypeName;
    public String transTypeName;
    public String beginDate;
    public String endDate;

    //车厢1
    public String car1Length;
    public String car1Width;
    public String car1ToFLength;
    public String car1ToNLength;
    public String car1NTFLength;
    public String car1L1TWLenght;
    public String car1L2TL1Length;
    public String car1L3TL2Length;
    public String car1L4TL3Length;
    public String car1L5TL4Length;
    public String car1L6TL5Length;

    public String car1G1TWLength;
    public String car1G2TG1Length;
    public String car1G3TG2Length;
    public String car1G4TG3Length;
    public String car1G5TG4Length;
    public String car1G6TG5Length;

    //车厢1到2的距离
    public String car1To2Length;

    //车头离前车厢的间距
    public String carTo1Length;

    //车厢2
    public String car2Length;
    public String car2Width;
    public String car2ToFLength;
    public String car2ToNLength;
    public String car2NTFLength;
    public String car2L1TWLenght;
    public String car2L2TL1Length;
    public String car2L3TL2Length;
    public String car2L4TL3Length;
    public String car2L5TL4Length;
    public String car2L6TL5Length;
    public String car2G1TWLength;
    public String car2G2TG1Length;
    public String car2G3TG2Length;
    public String car2G4TG3Length;
    public String car2G5TG4Length;
    public String car2G6TG5Length;

    //操作人员信息
    public String fstUsrId;
    public String fstUsrDtm;
    public String lstUsrId;
    public String lstUsrDtm;
    public String rmtNot;

    public String carDriver;

    public String todayCnt;

    //返回操作结果
    public String resCode;
    public String resMsg;

    //织金单独要增加 【是否签订厂内卸煤管理协议】
    public String protocolSta;

    public String doActionTag;//动作标识 ADD, MOD
    public String jsonString;//json字符串
    public String opCode;
}
