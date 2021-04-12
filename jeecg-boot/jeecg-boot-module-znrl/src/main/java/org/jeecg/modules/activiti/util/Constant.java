package org.jeecg.modules.activiti.util;


import org.jeecg.modules.activiti.entity.ConstantEntity;
import org.jeecg.modules.activiti.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by xieyt on 14-12-10.
 * wz 10-27：开发客户签名功能需要在系统管理中读取常量，故从business移动到admin工程中，原类删除掉，同时修改InitServlet.java代码
 */
@Component
public class Constant {

    @Autowired
    private  ICommonService ICommonService;

    public static final String LOG_NAME = "Main";

    public static final String HANDSHAKE_REQ = "handshake";

    public static final String HANDSHAKE_RESP = "handshake_ok";

    public static final String PDC_VAL_FLASH = "FLASH";

    public static final String PDC_VAL_SUM = "SUM";

    public static Properties props = new Properties();

    public static String getTimeStr(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    //websocket空闲会话保持时长（毫秒）
    public static long KEEP_IDLE_WS_SESSION_TIMEOUT = 36000 * 1000;

    //socket 监听端口
    public static int SOCKET_LISTEN_PORT = 2012;

    //获取配置常量
    public static String getConstVal(String constKey) {
        return constantMap.get(constKey);
    }

    //系统静态参数内存存放
    public static HashMap<String, String> constantMap = new HashMap<String, String>();

    public void loadConst() {

        try {
            List<ConstantEntity> list = ICommonService.qryConstantCfgData();
            for (ConstantEntity c : list) {
                Constant.constantMap.put(c.getKey(), c.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 内存缓存
    public static LRUCache<String,Object> memCache = new LRUCache<>(10000);

    // get缓存值
    public static Object getCache(String key) {
        return memCache.get(key);
    }

    // set缓存值
    public static void setCache(String key, Object value) {
        memCache.put(key, value);
    }

}

