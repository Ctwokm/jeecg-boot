package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by dage on 15-6-19.
 */
@Data
public class ReportEntity extends BaseEntity{
    private String labReportId;
    private String laborCode;
    private String shipRecID;
    private String mt;
    private String mad;
    private String aad;
    private String ad;
    private String aar;
    private String vad;
    private String vd;
    private String fcad;
    private String fcar;
    private String fcd;
    private String fcdaf;

    private String var;
    private String vdaf;
    private String stad;
    private String std;
    private String star;
    private String hd;
    private String had;
    private String har;
    private String hdaf;
    private String cad;
    private String apprStatusCode;

    private String nad;
    private String st;
    private String dt;
    private String ht;
    private String ft;
    private String crc;

    private String qbad;
    private String qbar;
    private String qbd;
    private String qbdaf;
    private String qgrad;
    private String qgrar;
    private String qgrd;
    private String qgrdaf;
    private String qnetad;

    private String qnetarj;
    private String qnetarm;
    private String qnetd;
    private String batchType;
    private String apprStatus;
    private String updateTime;
    private String updateCode;
    private String jhTime;
    private String jhCode;
    private String hyDate;
    private String sampleDate;

    private String insertTime;
    private String opCode;
    private String opDesc;
    public String beginDate;
    public String endDate;
    public String coalName;
    public String venName;
    public String mineName;
    public String standingBookId;
    public String shipId;
    public String shipName;
    public String batchNo;
    public String batchNoType;
    public String transNo;
    public String rlDate;
    public String updateString;
    public String resCode;
    public String resMsg;
    //加权使用的
    public String proportionRequired;
    public String proportionType;
    public String proportion;
    public String proportionTag;
    public String proportionTypeName;
    public String proportionTagName;
    private String carCnt;
    private String batchTime;

    private String startPlace;
    private String laborCodeArray;
    private String receiveOp; //接样人
    private String receiveTime; //接样时间

    private String term;//期号
}
