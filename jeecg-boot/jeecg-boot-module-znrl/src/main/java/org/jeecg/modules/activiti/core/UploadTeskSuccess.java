package org.jeecg.modules.activiti.core;


import org.jeecg.modules.activiti.entity.ActivitiEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadTeskSuccess {
    public static void  execute(ActivitiEntity arguments) {
//        arguments.setResCode("0");
//        System.out.println("1111111111111111111111");
        log.info("这里是审批成功执行业务操作！");
    }
}
