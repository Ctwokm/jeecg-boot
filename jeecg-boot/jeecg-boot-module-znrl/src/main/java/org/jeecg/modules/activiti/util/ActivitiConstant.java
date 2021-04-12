package org.jeecg.modules.activiti.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by xieyt on 14-12-10.
 */
@Component
public class ActivitiConstant extends SpringBeanAutowiringSupport {
    //websocket空闲会话保持时长（毫秒）
    public static long KEEP_IDLE_WS_SESSION_TIMEOUT = 36000*1000;

    //socket 监听端口
    public static int SOCKET_LISTEN_PORT = 2012;

    public static String ACTIVITI_PARAM_BEAN_NAME = "ACTIVITI_PARAM_BEAN";

    public static String TASK_RES = "RES";

    //附件上传目录
    public static String ATTACHMENT_DIRECORY = "c:\\attachment\\";


    public final static String EXE_RES_SUCCESS = "0";
    public final static String EXE_RES_FAILED = "1";

    public final static String TASK_TYPE_PROCEDURE = "0";
    public final static String TASK_TYPE_JAVA_REF = "1";
}

