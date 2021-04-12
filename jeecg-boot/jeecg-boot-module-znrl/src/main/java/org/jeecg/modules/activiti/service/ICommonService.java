package org.jeecg.modules.activiti.service;



import org.jeecg.modules.activiti.entity.*;

import java.sql.Timestamp;
import java.util.List;

public interface ICommonService {

	public Timestamp getSysdate() throws RuntimeException;

	public long getNextval(String seqName) throws RuntimeException;

	public String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException;

    //加载配置常量到内存
    public List<ConstantEntity> qryConstantCfgData() throws Exception;

    public List<ConstantEntity> qryLogMenuButtonInfo() throws Exception;

    //插入接口日志关键数据
    public void insertIntfLog(BizLogEntity logEntity);

    //插入activiti日志关键数据
    public void insertActivitiLog(ActivitiLogEntity logEntity);

    public String getSendPointList(PointCommandEntity pointCommandEntity) throws RuntimeException;

    public void writebackSendPointList(PointCommandEntity pointCommandEntity) throws RuntimeException;

}
