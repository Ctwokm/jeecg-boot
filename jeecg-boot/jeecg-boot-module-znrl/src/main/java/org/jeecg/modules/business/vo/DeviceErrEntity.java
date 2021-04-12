package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by zj on 2021/4/1.
 */
@Data
public class DeviceErrEntity {
    //设备故障报警信息部分
    public String errorRecId;//故障记录ID
    public String machineCode;//故障机器编码
    public String flowId;
    public String errorCode;//错误代码
    public String errorTime;//故障记录时间
    public String errorDec;//故障描述
    public String dataStatus;//数据状态
    public String errorConfirm;//确认状态
    public String confirmOp;//确认人
    public String confirmTime;//确认时间
    public String errorPri;//故障优先级
    public String opCode;//处理人ID
    public String bak;
    public String deviceIp;
    public String flowName;//流程名称
    public String confirmOpCode;//确认人ID
    public String machineName;//故障机器编码
    public String beginTime;
    public String endTime;
    public String jsonStr;
    public String resCode;
    public String resMsg;

}
