package org.jeecg.modules.activiti.core;

import cn.hutool.http.HttpRequest;
import org.jeecg.modules.activiti.entity.ActivitiEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UploadServiceTesk1 {
//PropertiesTool.getProperty("dataCenterUrl")

    public static void  execute(ActivitiEntity arguments) {
//        arguments.setResCode("0");
//        //        String result = HttpRequest.post("http://localhost:8090/business/znrltoerp/writeBackApprResult")
//        String result = HttpRequest.post("http://localhost:8090/business_war_exploded/business/znrltoerp/updateApprResult")
//                .body(arguments.jsonString)
//                .execute().body();
//        System.out.println(result);
        arguments.setResCode("0");
        log.info("这里是一级审批执行业务操作！");
    }
}
