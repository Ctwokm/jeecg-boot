package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by zj on 2021/4/1.
 */
@Data
public class DeviceBroadEntity  {
    //动态信息部分
    public String broadRecId;//动态信息ID
    public String broadTime;//动态信息时间
    public String broadDec;//动态信息描述

    //设备故障报警信息部分
    public String errorRecId;//故障记录ID
    public String errorTime;//故障记录时间
    public String errorDec;//故障描述
    public String machineCode;//故障机器编码
    public String flowName;//流程名称
    public String errorCode;//错误代码
    public String errorPri;//故障优先级
    public String dataStatusCode;//数据状态编码
    public String dataStatus;//数据状态
    public String errorConfirmStatusCode;//确认状态编码
    public String errorConfirmStatus;//确认状态
    public String errorConfirmOp;//确认人
    public String errorConfirmTime;//确认时间
    public String createOp;//创建人
    public String opCode;//处理人ID
    public String confirmOpCode;//确认人ID
    public String bak;
    public String deviceIp;
    public String flowId;
    public String machinName;//故障机器名称

    private String deviceBroadPri;

    private String beginTime;
    private String endTime;

    private String resCode;
    private String resMsg;

}
