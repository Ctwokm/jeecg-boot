package org.jeecg.modules.activiti.util;

import org.jeecg.modules.activiti.entity.ActivitiLogEntity;
import org.jeecg.modules.activiti.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by xieyt on 15-5-15.
 * activiti异步入库日志管理工具
 */
@Component
public class ActivitiLogUtil extends SpringBeanAutowiringSupport {

    @Autowired
    private ICommonService commonService;

    //日志存放队列-线程同步安全
    public static ConcurrentLinkedQueue<ActivitiLogEntity> logQueue = new ConcurrentLinkedQueue<>();

    //单例模式获取该类的实例
    private static ActivitiLogUtil logUtilSingleton;

    public static ActivitiLogUtil getInstance(){
        if(logUtilSingleton == null){
            logUtilSingleton = new ActivitiLogUtil();
        }
        return logUtilSingleton;
    }

    private ActivitiLogUtil(){}

    //写日志实例到队列
    public static void recordLog(ActivitiLogEntity logEntity) {
        logQueue.add(logEntity);
    }


    private void insertActivitiLog(ActivitiLogEntity logEntity) throws Exception{
        try {
            commonService.insertActivitiLog(logEntity);
        } catch (Exception e) {
            throw e;
        }
    }


    //处理日志入库
    private void recordToDb() {
        try {
            while(!logQueue.isEmpty()){
                insertActivitiLog(logQueue.poll());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    //异步入库线程
    class LogWorkerThread extends Thread {
        public void run() {
            while (true) {
                try {
                    recordToDb();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        this.sleep(3*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
