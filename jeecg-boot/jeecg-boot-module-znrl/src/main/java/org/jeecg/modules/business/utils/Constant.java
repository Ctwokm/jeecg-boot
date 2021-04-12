package org.jeecg.modules.business.utils;


import org.jeecg.modules.business.service.CommonServiceUtil;
import org.jeecg.modules.business.vo.ConstantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component("ConstantUtil")
public class Constant extends SpringBeanAutowiringSupport {
    @Autowired
    private CommonServiceUtil commonServiceUtil;

    //websocket空闲会话保持时长（毫秒）
    public static long KEEP_IDLE_WS_SESSION_TIMEOUT = 36000*1000;

    //socket 监听端口
    public static int SOCKET_LISTEN_PORT = 2012;

    /*
    //系统常量配置存放容器
    public static HashMap<String, Object> GLOBAL_CONSTANT_MAP = new HashMap<>();

    //获取配置常量
    public static String getConstVal(String constKey) {
        Object o = GLOBAL_CONSTANT_MAP.get(constKey);
        return o == null ? "" : (String)o;
    }
    */
    //获取配置常量
    public static String getConstVal(String constKey) {
        return constantMap.get(constKey);
    }

    //系统静态参数内存存放
    public static HashMap<String, String> constantMap = new HashMap<String, String>();

    public void loadConst(){
        try {
            List<ConstantEntity> list = commonServiceUtil.qryConstantCfgData();
            for (ConstantEntity c : list) {
                Constant.constantMap.put(c.getKey(),c.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<ConstantEntity> menuList = commonServiceUtil.qryLogMenuButtonInfo();
            for (ConstantEntity c : menuList) {
                menuMap.put(c.getKey(),c.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String,String> menuMap = new HashMap<String,String>();

}

