package org.jeecg.modules.activiti.service;

import java.util.Map;

/**
 * 流程环节中，各步骤执行的方法
 * Created by Administrator on 2018/9/18.
 */
public interface IStepMethodService {
    /**
     * 车卡识别。管控流程中是通过天线识别来车的车卡信息。仿真时只需取出流程启动时传入的车卡信息即可。
     *
     * @param map
     * @throws Exception
     */
    public void rfidTruckCard(Map<String, Object> map) throws Exception;

    /**
     * 矿卡识别。
     *
     * @param map
     * @throws Exception
     */
    public void rfidMineCard(Map<String, Object> map) throws Exception;

    /**
     * 写测点。亮绿灯+抬倒闸
     *
     * @param map
     * @throws Exception
     */
    public void writeLightGreen(Map<String, Object> map) throws Exception;

    /**
     * 写测点。亮红灯+落倒闸
     *
     * @param map
     * @throws Exception
     */
    public void writeBrake(Map<String, Object> map) throws Exception;

    /**
     * 入厂前置检查
     *
     * @param map
     * @throws Exception
     */
    public void rcBefore(Map<String, Object> map) throws Exception;

    /**
     * 调用存过生成入厂记录
     *
     * @param map
     * @throws Exception
     */
    public void rcAfter(Map<String, Object> map) throws Exception;

    /**
     * 称重。生成指定范围内的随机重量
     *
     * @param map
     * @throws Exception
     */
    public void weighing(Map<String, Object> map) throws Exception;

    /**
     * 调用存储过程，判断可否进入称重环节。
     * @param map
     * @throws Exception
     */
    public void czBefore(Map<String, Object> map) throws Exception;

    /**
     * 调用存储过程，保存称重信息。
     *
     * @param map
     * @throws Exception
     */
    public void czAfter(Map<String, Object> map) throws Exception;

    /**
     * 空方法。未确认的步骤执行可先配置此方法。
     * @param map
     * @throws Exception
     */
    public void empty(Map<String, Object> map)throws Exception;
}
