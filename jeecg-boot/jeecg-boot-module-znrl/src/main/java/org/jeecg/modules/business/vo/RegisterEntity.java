package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * @Auther: Hujw
 * @Date: 2021/2/22
 * @Description: 登记信息类，负责封装返回前端视图需要的数据，火车、汽车、船尽可能通用一个类
 */

@Data
public class RegisterEntity extends BaseEntity {

    //具体信息

    public String recordNo;//入厂流水
    public String recId;
    public String trainNo;//车次号
    public String trainNoAlias;//车次号
    public String carId;//车号
    public String oldCarId;//原车号
    public String colryNo;//矿点
    public String coalType;//煤矿id
    public String coalNo;//煤质类型
    public String leaveDtm;//发货日期
    public String leaveName;//离开状态
    public String recordDtm;//入厂时间
    public String czDtm;//称重时间
    public String tickQty;//票重
    public String mzQty;//毛重
    public String totalMzQty;//总毛重
    public String pzQty;//皮重
    public String netQty;//净重
    public String bzQty;
    public String totalNetQty;//总毛重
    public String totalTickQty;//总票重
    public String ydQty;//盈吨
    public String kudQty;//亏吨
    public String lossQty;//运损
    public String fcTime;//翻车时间
    public String batchNo;//批次号
    public String balanceNo;//轨道号
    public String carTyp;//车型
    public String remark;//备注
    public String trackNo;//火车入厂的轨道号 3#， 5# 。。。
    public String sampleCode;
    public String manCheckSample;//抽检样采样编码
    public String attachBatchNo;
    public String sampleTyp;//采样方式 0在线采样  1离线采样
    public String emptyFlg;//是否为空 0是空  1非空
    public String xhNum;//车厢序号
    public String sampleTypName;//采样方式 0在线采样  1离线采样
    public String emptyFlgName;//是否为空 0是空  1非空

    public String carCnt;//车数
    public String venNo;//供应商ID
    public String beginTime;//入厂开始时间
    public String endTime;//入厂结束时间
    public String mineName;//煤矿名称
    public String coalName;//煤种名称
    public String venName;//供应商

    public String startPlace;//始发站
    public String startPlaceName;//始发站
    public String finalPlace;//到站
    public String finalPlaceName;//到站

    public String speed;//过衡速度
    public String aimTime;//对位时间

    public String goodsReceiver;//收货单位
    public String deliverTime;//发站时间

    public String contractNo;//合同编号
    public String contractName;//合同名称

    //以json字符串的方式传入存储过程
    public String insertString;
    public String updateString;
    public String deleteString;
    public String publicString;
    public String opCode;

    public String updateCode;
    public String updateName;

    //返回操作结果
    public String resCode;
    public String resMsg;

    //返回操作结果
    public String purchaseType;//采购类型
    public String contractType;//合同类型
    public String provinceNo;//身份编号


    private String uploadStatus ;
    private String cfmFlg ;  //借用给修改标志
    public String updateTime;
    public String totalYdQty;//总盈吨
    public String totalKudQty;//总亏吨
    public String totalLossQty;//总亏吨
    public String ykd;



    /*汽车类相关*/
    public String kdQty;
    public String recordId;//主键ID
    public String cardId;//卡号
    public String cardTyp;//卡类型
    public String cardTypeName;//卡类型
    public String backCarId;//后车牌号
    public String mineNo;//煤矿
    public String colryName;//煤矿MINE_NAME
    public String carTypeName;//车型名称
    public String carrierNo;//运输单位id
    public String orgNo;
    public String carrierName;//运输单位
    public String coalWater;//湿度  1湿煤 2一般湿煤 3干煤 4一般干煤
    public String coalWaterName;//湿度  1湿煤 2一般湿煤 3干煤 4一般干煤
    public String tickNo;//票号
    public String beginDate;
    public String endDate;
    public String insertTime;//插入数据时间
    private String tickPzQty;//出矿皮重
    private String tickMzQty;//出矿毛重
    private String cyjNo;//采样机编号或通道号
    private String channel;
    private String actionTag;
    private String readTag;
    private String ghIndex;
    private String startName;
    private String finalName;
    private String sampleType;
    public String sampleDtm;//采样厂时间
    public String jqDtm;//称轻时间
    public String carStatus;//车辆状态
    private String batchNoType;
    public String totalEntryCnt;//入厂车数
    public String totalPzQty;//总皮重
    public String totalKdQty;//总扣吨
    public String apprEventTypeCd;
    public String mineCardType;//矿卡类型
    public String transTyp;//运输类型(0煤车、1灰车、2搬倒车、3渣车、4石粉车、5石膏车)
    public String leaveFlg;//出厂标识  0入厂未采样   1回皮出厂  2采样未称重   3称重未回皮
    public String validSta;//车卡状态 0 正常  1 失效；
    public String regDtm;
    public String endDtm;
    public String stdQty;
    public String rmtNot;
    public String floatQty;

    /*车厢数据*/

    /*'car1_length','car1_width','car1ntf_length','car1l10tl9_length',
              'car1l2tl1_length','car1l3tl2_length','car1l4tl3_length','car1l5tl4_length','car1NTFLength',
              'car1l6tl5_length','car1l7tl6_length',*/
    public String car1Length; //车厢长度
    public String car1Width;
    public String car1ntfLength;
    public String car1L11TL10Length;
    public String car1L1TWLength;
    public String car1L2TL1Length;
    public String car1L3TL2Length;
    public String car1L4TL3Length;
    public String car1L5TL4Length;
    public String car1L6TL5Length;
    public String car1L7TL6Length;
    public String car1G1TWLength;
    public String car1G2TG1Length;
    public String car1G3TG2Length;
    public String car1G4TG3Length;
    public String car1G5TG4Length;
    public String car1G6TG5Length;
    public String car1L10TL9Length;


    //车卡相关
    public String cardRecId;
    public String mineCardId;
    public String viewCardId;
    public String effTime;
    public String expTime;
    public String mineCardTypeName;
    public String opName;
    public String shipId;
    public String carNumber;


}
