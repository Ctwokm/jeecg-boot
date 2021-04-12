package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: Zouzp
 * @create: 2021/3/17 13:31
 **/
@Data
public class CarInfoRegisterEntity extends BaseEntity {
    /*车厢数据*/
    /*用于车卡注册 相关操作 于存储过程 用于交互*/
    /*'car1_length','car1_width','car1ntf_length','car1l10tl9_length',
              'car1l2tl1_length','car1l3tl2_length','car1l4tl3_length','car1l5tl4_length','car1NTFLength',
              'car1l6tl5_length','car1l7tl6_length',*/
    public String recId;
    public String cardTyp;//卡类型
    public String cardId;//卡号
    public String carId;
    public String backcarId;
    public String stdQty;
    public String floatQty;
    public String carTyp;
    public String transTyp;
    public String car1Length; //车厢长度
    public String car1Width;
    public String validSta;//车卡状态 0 正常  1 失效；
    public String endDtm;
    public String rmtNot;
    public String car1L1TWLength;
    public String car1L2TL1Length;
    public String car1L3TL2Length;
    public String car1L4TL3Length;
    public String car1L5TL4Length;
    public String car1L6TL5Length;
    public String car1L7TL6Length;
    public String beginTime;
    public String endTime;
    public String car1L10TL9Length;
    public String car1ntfLength;
    public String car1L11TL10Length;
    public String regDtm;

    public String car1G1TWLength;
    public String car1G2TG1Length;
    public String car1G3TG2Length;
    public String car1G4TG3Length;
    public String car1G5TG4Length;
    public String car1G6TG5Length;
    //车卡相关

}
