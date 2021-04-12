package org.jeecg.modules.activiti.service.impl;


import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.activiti.entity.CallTruckEntity;
import org.jeecg.modules.activiti.mapper.ActivitiMapper;
import org.jeecg.modules.activiti.service.IStepMethodService;
import org.jeecg.modules.activiti.util.ActLogger;
import org.jeecg.modules.activiti.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 流程环节中，各步骤执行的方法实现
 * Created by Administrator on 2018/9/18.
 */
@Service("stepMethodService")
@Transactional
public class StepMethodServiceImpl implements IStepMethodService {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ActivitiMapper activitiMapper;


    /**
     * 车卡识别。管控流程中是通过天线识别来车的车卡信息。仿真时只需取出流程启动时传入的车卡信息即可。
     *
     * @param map
     * @throws Exception
     */
    public void rfidTruckCard(Map<String, Object> map) throws Exception {
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "车卡识别完成！车卡ID：" + map.get("carCardInfo[card_id]") + "，车牌号：" + map.get("carCardInfo[car_id]"));
    }

    /**
     * 矿卡识别。
     *
     * @param map
     * @throws Exception
     */
    public void rfidMineCard(Map<String, Object> map) throws Exception {
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "矿卡识别完成！矿卡ID：" + map.get("mineCardInfo[mine_card_id]") + ",矿卡号：" + map.get("mineCardInfo[view_card_id]"));
    }

    /**
     * 写测点。亮绿灯+抬倒闸
     *
     * @param map
     * @throws Exception
     */
    public void writeLightGreen(Map<String, Object> map) throws Exception {
        String param = "[{\"device\": \"CY01\",\"value\": \"1\"}]";
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        CallTruckEntity calParam = new CallTruckEntity(genJsonIn(map, param), opId);
        try {
            activitiMapper.calPkgTruck(calParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!calParam.getResCode().equals("0")) {
            throw new Exception(calParam.getResMsg().toString());
        }
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "红绿灯亮绿灯完成！写测点：" + calParam.getResMsg());
    }

    /**
     * 写测点。亮红灯+落倒闸
     *
     * @param map
     * @throws Exception
     */
    public void writeBrake(Map<String, Object> map) throws Exception {
        String param = "[{\"device\": \"CY01\",\"value\": \"1\"}]";
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        CallTruckEntity calParam = new CallTruckEntity(genJsonIn(map, param), opId);
        try {
            activitiMapper.calPkgTruck(calParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!calParam.getResCode().equals("0")) {
            throw new Exception(calParam.getResMsg().toString());
        }
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "道闸控制完成！写测点：" + calParam.getResMsg());
    }

    /**
     * 入厂前置检查
     *
     * @param map
     * @throws Exception
     */
    public void rcBefore(Map<String, Object> map) throws Exception {
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        // 构造调用存过程所需的json入参
        com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
        dataJson.put("businessTag", "RC_BEFORE");
        dataJson.put("traineeGroupId", map.get("traineeGroupId"));
        com.alibaba.fastjson.JSONObject jsonIn = new com.alibaba.fastjson.JSONObject();
        jsonIn.put("dataJson", dataJson);
        CallTruckEntity calParam = new CallTruckEntity(jsonIn.toJSONString(), opId);
        activitiMapper.calPkgTruck(calParam);
        if (!calParam.getResCode().equals("0")) {
            throw new Exception(calParam.getResMsg());
        }
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "调用入厂前置检查存储过程完成！返回结果：" + calParam.getResMsg());
    }

    /**
     * 调用存过生成入厂记录
     *
     * @param map
     * @throws Exception
     */
    public void rcAfter(Map<String, Object> map) throws Exception {
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        // 构造调用存过程所需的json入参
        com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
        dataJson.put("businessTag", "RC_AFTER");
        dataJson.put("doActionTag", "ADD");
        dataJson.put("record_no", "");
        dataJson.put("cardId", map.get("carCardInfo[card_id]"));
        dataJson.put("cardTyp", map.get("carCardInfo[card_typ]"));
        dataJson.put("car_id", map.get("carCardInfo[car_id]"));
        dataJson.put("colry_no", map.get("mineCardInfo[mine_no]"));
        dataJson.put("mineCardId", map.get("mineCardInfo[mine_card_id]"));
        dataJson.put("coal_no", map.get("mineCardInfo[coal_no]"));
        dataJson.put("carrier_no", map.get("carCardInfo[org_no]"));
        dataJson.put("ven_no", map.get("mineCardInfo[ven_no]"));
        dataJson.put("coal_water", "0");
        dataJson.put("tick_no", "");
        dataJson.put("tick_qty", "0");
        dataJson.put("sample_typ", "0");
        dataJson.put("opCode", opId);
        dataJson.put("remark", "仿真流程调用");
        dataJson.put("record_dtm", DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        dataJson.put("mz_qty", "0");
        dataJson.put("pz_qty", "0");
        dataJson.put("kd_qty", "0");
        dataJson.put("traineeGroupId", map.get("traineeGroupId"));
        com.alibaba.fastjson.JSONObject jsonIn = new com.alibaba.fastjson.JSONObject();
        jsonIn.put("dataJson", dataJson);
        CallTruckEntity calParam = new CallTruckEntity(jsonIn.toJSONString(), opId);
        activitiMapper.calPkgTruck(calParam);
        if (!calParam.getResCode().equals("0")) {
            throw new Exception(calParam.getResMsg());
        }
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "生成入厂记录完成！返回结果：" + calParam.getResMsg());
    }

    /**
     * 称重。生成指定范围内的随机重量
     *
     * @param map
     * @throws Exception
     */
    public void weighing(Map<String, Object> map) throws Exception {
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        int minWeight = Integer.parseInt(map.get("minWeight").toString());
        int maxWeight = Integer.parseInt(map.get("maxWeight").toString());
        int random = new Random().nextInt(maxWeight) % (maxWeight - minWeight + 1) + minWeight;
        String processInstId = map.get("processInstId").toString();
        Constant.setCache(processInstId + "weight", random);
        ActLogger.info(processInstId, "生成随机重量完成！返回结果：" + random);
    }

    /**
     * 调用存储过程，判断可否进入称重环节。
     *
     * @param map
     * @throws Exception
     */
    public void czBefore(Map<String, Object> map) throws Exception {
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        // 构造调用存过程所需的json入参
        com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
        dataJson.put("businessTag", "RC_BEFORE");
        dataJson.put("traineeGroupId", map.get("traineeGroupId"));
        com.alibaba.fastjson.JSONObject jsonIn = new com.alibaba.fastjson.JSONObject();
        jsonIn.put("dataJson", dataJson);
        CallTruckEntity calParam = new CallTruckEntity(jsonIn.toJSONString(), opId);
        activitiMapper.calPkgTruck(calParam);
        if (!calParam.getResCode().equals("0")) {
            throw new Exception(calParam.getResMsg());
        }
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "调用称重前置存储过程完成！返回结果：" + calParam.getResMsg());
    }

    /**
     * 空方法。未确认的步骤执行可先配置此方法。
     * @param map
     * @throws Exception
     */
    public void empty(Map<String, Object> map)throws Exception {
        String processInstId = map.get("processInstId").toString();
        ActLogger.info(processInstId, "这是一个空方法。具体要干什么待业务确定！");
    }
    /**
     * 调用存储过程，保存称重信息。
     *
     * @param map
     * @throws Exception
     */
    public void czAfter(Map<String, Object> map) throws Exception {
        String processInstId = map.get("processInstId").toString();
        String opId = null == map.get("userId") ? "" : map.get("userId").toString();
        // 构造调用存过程所需的json入参
        com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
        dataJson.put("businessTag", "RC_AFTER");
        dataJson.put("weight", Constant.getCache(processInstId + "weight").toString());
        dataJson.put("traineeGroupId", map.get("traineeGroupId"));
        com.alibaba.fastjson.JSONObject jsonIn = new com.alibaba.fastjson.JSONObject();
        jsonIn.put("dataJson", dataJson);
        CallTruckEntity calParam = new CallTruckEntity(jsonIn.toJSONString(), opId);
        activitiMapper.calPkgTruck(calParam);
        if (!calParam.getResCode().equals("0")) {
            throw new Exception(calParam.getResMsg());
        }

        ActLogger.info(processInstId, "调用称重存储过程完成！返回结果：" + calParam.getResMsg());
    }

    /**
     * 构造jsonIn，用于调用生成写测点协议的存储过程
     *
     * @param map
     * @param paramList
     * @return
     */
    private String genJsonIn(Map<String, Object> map, String paramList) throws Exception {
        // 构造调用pkg_truck_shf.main存过程所需的json入参
        com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
        dataJson.put("businessTag", "WRITE_CD"); //操作类型：生成写测点协议
        dataJson.put("opCode", map.get("userId"));
        dataJson.put("traineeGroupId", map.get("traineeGroupId"));
        com.alibaba.fastjson.JSONArray param = com.alibaba.fastjson.JSONArray.parseArray(paramList);

        com.alibaba.fastjson.JSONObject jsonIn = new com.alibaba.fastjson.JSONObject();
        jsonIn.put("dataJson", dataJson);
        jsonIn.put("param", param);

        return jsonIn.toJSONString();
    }
}
