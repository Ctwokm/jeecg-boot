package org.jeecg.modules.activiti.mapper;



import org.jeecg.modules.activiti.entity.*;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface CommonMapper {

	Timestamp getSysdate() throws RuntimeException;

    Long getNextval(Map paraMap) throws RuntimeException;

    String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException;

    //加载配置常量到内存
    public List<ConstantEntity> qryConstantCfgData();

    List<ConstantEntity> qryLogMenuButtonInfo();

    //插入接口日志关键数据
    public void insertIntfLog(BizLogEntity logEntity);

    //插入接口请求报文
    public void insertIntfLogExt(BizLogEntity logEntity);

    //插入Activiti日志关键数据
    public void insertActivitiLog(ActivitiLogEntity logEntity);

    //获取下一个序列ID
    public String getSeqNextval(CommonEntity commonEntity);

    public void getSendPointList(PointCommandEntity pointCommandEntity) throws RuntimeException;

    public void writebackSendPointList(PointCommandEntity pointCommandEntity) throws RuntimeException;
}
