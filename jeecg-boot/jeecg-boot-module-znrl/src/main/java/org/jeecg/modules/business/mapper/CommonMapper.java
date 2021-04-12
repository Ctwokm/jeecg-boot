package org.jeecg.modules.business.mapper;



import org.jeecg.modules.business.vo.ConstantEntity;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository("commonMapperUtil")
public interface CommonMapper {

	/*Timestamp getSysdate() throws RuntimeException;

    Long getNextval(Map paraMap) throws RuntimeException;

    String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException;*/

    //加载配置常量到内存
    public List<ConstantEntity> qryConstantCfgData();

    List<ConstantEntity> qryLogMenuButtonInfo();
}