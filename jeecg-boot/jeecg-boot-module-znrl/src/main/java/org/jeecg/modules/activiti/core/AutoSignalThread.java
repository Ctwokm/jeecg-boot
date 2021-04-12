package org.jeecg.modules.activiti.core;


import org.jeecg.modules.activiti.service.IActivitiService;
import org.jeecg.modules.activiti.util.Constant;

/**
 * Created by Administrator on 2018/9/12.
 */
public class AutoSignalThread extends Thread {

    private IActivitiService activitiService;
    private long sleepMillis;
    private String processInstanceId;

    public AutoSignalThread(IActivitiService activitiService, long sleepMillis, String processInstanceId) {
        this.activitiService = activitiService;
        this.sleepMillis = sleepMillis;
        this.processInstanceId = processInstanceId;
    }

    public IActivitiService getActivitiService() {
        return activitiService;
    }

    public void setActivitiService(IActivitiService activitiService) {
        this.activitiService = activitiService;
    }

    public long getSleepMillis() {
        return sleepMillis;
    }

    public void setSleepMillis(long sleepMillis) {
        this.sleepMillis = sleepMillis;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(sleepMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean hasFailure = null == Constant.getCache(processInstanceId + "hasFailure")?false: (Boolean) Constant.getCache(processInstanceId + "hasFailure");
        if (!hasFailure) {
            activitiService.signal(processInstanceId);
        }
    }
}
