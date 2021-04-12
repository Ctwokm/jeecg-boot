package org.jeecg.modules.business.vo;

import lombok.Data;

/**
 * Created by zj on 2021/4/1.
 */
@Data
public class LogOpRecEntity{

    public String logId;

    public String logOpCode;

    public String logOpName;

    public String logType;

    public String logTypeDec;

    public String modDec;

    public String opStartTime;

    public String opEndTime;

    public String logTime;

    public String hostIp;
    public String operMenu;
    public String operButton;
    public String requestParams;
    public String sysLogType;

}
