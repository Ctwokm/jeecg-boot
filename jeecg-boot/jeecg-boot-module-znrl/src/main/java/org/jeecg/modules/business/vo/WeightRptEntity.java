package org.jeecg.modules.business.vo;

/**
 * Created by xieyt on 14-12-5.
 */

import lombok.Data;

@Data
public class WeightRptEntity {
    public String recordNo;//入厂序号
    public String beginTime; //开始时间
    public String endTime; //结束时间

    public String leaveBeginTime; //出厂开始时间
    public String leaveEndTime; //出厂结束时间
    public String czBeginTime; //称重开始时间
    public String czEndTime; //称重结束时间

    public String venNo;//供煤单位ID
    public String venNam;//供煤单位
    public String carrierNo;//运输单位ID
    public String carrierNam;//运输单位
    public String carId;//车牌号
    public String carTyp;//车型编码   0半挂  1外挂
    public String carTypeName;//车型
    public String transTyp;//车辆类型 0煤车 1灰车 2其他 5厂内倒运煤
    public String transTypeName;//车辆类型
    public String planMktNo;//计划口径ID ？
    public String planMktName;//计划口径 ？
    public String positionNo;//位置
    public String positionNam;//位置
    public String coalNo;//煤种
    public String coalNam;//煤种名称
    public String colryNam;//矿点
    public String startPlace;//始发站
    public String startPlaceName;//始发站

    public String bottomHeight;//车底高度
    public String maxLoad;//最大载重
    public String entryOrder;//入厂顺序
    public String doorNo;//入厂位置
    public String recordDt; //日期，格式化
    public String recordDtm;//入厂时间
    public String curPos;//车的当前位置
    public String nextPos;//车的下一个位置

    public String smplStartTime;//采样开始时间
    public String smplEndTime;//采样结束时间
    public String machineCode;//采样机
    public String sampleUserName;//采样人

    public String czDtm;//称重时间
    public String jqDtm;//称轻时间
    public String mzQty;//毛重
    public String kdQty;//扣吨
    public String kgQty;//扣吨
    public String pzQty;//皮重
    public String netQty;//净重
    public String tickQty;//票重
    public String ykQty;//盈亏
    public String ysQty;//验收重量
    public String jqBalaNo;//回皮位置
    public String czBalaNo;//称重位置
    public String status;//1采样未称重  2称重未称轻  3已经称轻
    public String batchNo;//批次号
    public String xhNum;//车厢序号
    public String balanceNo;//火车过衡序号


    /////////////////////汇总报表数据
    public String totalEntryCnt;//入厂车数
    public String totalCZCnt;//称重车数
    public String totalJQCnt;//称轻车数
    public String totalSampleCnt;//采样车数
    public String totalDenyCnt;//拒收车数
    public String totalCheckInCnt;//验收车数
    public String totalCheckInTunCnt;//回皮位置

    public String totalMzQty;//总毛重
    public String totalKdQty;//总扣吨
    public String totalPzQty;//总皮重
    public String totalNetQty;//总净重
    public String totalTickQty;//总票重(矿发数量)
    public String totalHpQty;//回皮总重量

    public String carCount;//当天发货商车辆总数

    public String coalMine;

    public String tickNo;

    public String kdOpName;//扣吨人
    public String kdStatus;//扣吨状态 0-未扣吨  1-已扣吨
    public String mineCardType;//矿卡类型
    public String noKdNetQty;
    public String diffQty;
    public String totalDiffQty;
    public String totalNoKdNetQty;
    public String diffRatio;
    public String batchNoType;//批次类型}
    public String powerFactory;//电厂    ZJ织金

    public String shipNo;//船名ID
    public String shipName;//船名名称
    public String trainNo;

}
