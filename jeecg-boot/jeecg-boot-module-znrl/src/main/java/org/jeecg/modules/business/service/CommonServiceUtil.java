package org.jeecg.modules.business.service;

import org.jeecg.modules.business.vo.ConstantEntity;

import java.util.List;

public interface CommonServiceUtil {

	/*public Timestamp getSysdate() throws RuntimeException;

	public long getNextval(String seqName) throws RuntimeException;

	public String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException;*/

    //加载配置常量到内存
    public List<ConstantEntity> qryConstantCfgData() throws Exception;

    public List<ConstantEntity> qryLogMenuButtonInfo() throws Exception;
}