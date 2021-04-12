package org.jeecg.modules.activiti.util;

import org.jeecg.modules.activiti.entity.ActivitiLogEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2018/9/14.
 */
public class ActLogger {
    protected static Logger logger = LoggerFactory.getLogger(ActLogger.class);

    /**
     * 记录流程执行日志到内存中-提示
     *
     * @param processInstId
     * @param msg
     */
    public static void info(String processInstId, String msg) {
        log(processInstId, msg, "info");
    }

    /**
     * 记录流程执行日志到内存中-错误
     *
     * @param processInstId
     * @param msg
     */
    public static void err(String processInstId, String msg) {
        log(processInstId, msg, "err");
    }

    /**
     * 记录流程执行日志到内存中-自定义日志类型
     *
     * @param processInstId
     * @param msg
     */
    public static void log(String processInstId, String msg, String logType) {
        ActivitiLogEntity activitiLogEntity = new ActivitiLogEntity();
        activitiLogEntity.setProcessInstId(processInstId);
        activitiLogEntity.setResMsg(msg);
        activitiLogEntity.setLogType(logType);
        activitiLogEntity.setGroupId("");//备用
        ActivitiLogUtil.recordLog(activitiLogEntity);
    }

}
