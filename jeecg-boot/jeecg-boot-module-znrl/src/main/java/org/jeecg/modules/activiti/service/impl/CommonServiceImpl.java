package org.jeecg.modules.activiti.service.impl;

import org.jeecg.modules.activiti.entity.*;
import org.jeecg.modules.activiti.mapper.CommonMapper;
import org.jeecg.modules.activiti.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("commonService")
@Transactional
@SuppressWarnings("unchecked")
public class CommonServiceImpl implements ICommonService {

    @Autowired
	private CommonMapper commonMapper;

    public Timestamp getSysdate() throws RuntimeException {
		return commonMapper.getSysdate();
    }

    public long getNextval(String seqName) throws RuntimeException {
        Map paraMap = new HashMap();
        paraMap.put("seqName", seqName);
		return commonMapper.getNextval(paraMap);
    }

    public String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException {
        return commonMapper.getTableColumnByColumn(tableColumnByColumn);
    }

    //加载配置常量到内存
    @Override
    public List<ConstantEntity> qryConstantCfgData() throws Exception{
        return commonMapper.qryConstantCfgData();
    }

    @Override
    public List<ConstantEntity> qryLogMenuButtonInfo() throws Exception {
        return commonMapper.qryLogMenuButtonInfo();
    }
    //插入接口日志关键数据
    public void insertIntfLog(BizLogEntity logEntity){
        CommonEntity commonEntity = new CommonEntity();
        commonEntity.setSeqName("SEQ_INTF_LOG");
        String logId = commonMapper.getSeqNextval(commonEntity);
        logEntity.setLogId(logId);
        commonMapper.insertIntfLog(logEntity);
        //commonMapper.insertIntfLogExt(logEntity);
    }

    //插入activiti日志关键数据
    public void insertActivitiLog(ActivitiLogEntity logEntity) {
        commonMapper.insertActivitiLog(logEntity);
    }


    public String getSendPointList(PointCommandEntity pointCommandEntity) throws RuntimeException {
        commonMapper.getSendPointList(pointCommandEntity);

        return pointCommandEntity.getCommandJason();
    }

    public void writebackSendPointList(PointCommandEntity pointCommandEntity) throws RuntimeException {
        commonMapper.writebackSendPointList(pointCommandEntity);
    }
}
