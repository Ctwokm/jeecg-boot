package org.jeecg.modules.activiti.core;


import org.jeecg.modules.activiti.entity.ActivitiEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadTeskFailure {
    public static void execute(ActivitiEntity arguments){
//        arguments.setResCode("0");
//        System.out.println("22222222222222222");
        log.info("这里是审批驳回执行业务操作！");
    }
}
