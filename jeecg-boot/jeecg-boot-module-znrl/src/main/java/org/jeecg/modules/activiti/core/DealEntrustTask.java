package org.jeecg.modules.activiti.core;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by Administrator on 2018/7/5.
 */
public class DealEntrustTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("这里是处理委托登记信息："+delegateExecution);
    }
}
