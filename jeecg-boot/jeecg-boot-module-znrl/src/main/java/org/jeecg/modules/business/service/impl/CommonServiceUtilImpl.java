package org.jeecg.modules.business.service.impl;



import org.jeecg.modules.business.mapper.CommonMapper;

import org.jeecg.modules.business.service.CommonServiceUtil;
import org.jeecg.modules.business.vo.ConstantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("commonServiceUtil")
@Transactional
@SuppressWarnings("unchecked")
public class CommonServiceUtilImpl implements CommonServiceUtil {

   @Autowired
	private CommonMapper commonMapper;

   /* public Timestamp getSysdate() throws RuntimeException {
//		return commonMapper.getSysdate();
        return baseDao.getMapper(CommonMapper.class).getSysdate();
    }

    public long getNextval(String seqName) throws RuntimeException {
        Map paraMap = new HashMap();
        paraMap.put("seqName", seqName);
        return baseDao.getMapper(CommonMapper.class).getNextval(paraMap);
//		return commonMapper.getNextval(paraMap);
    }

    public String getTableColumnByColumn(TableColumnByColumn tableColumnByColumn) throws RuntimeException {
        return baseDao.getMapper(CommonMapper.class).getTableColumnByColumn(tableColumnByColumn);
    }*/

    //加载配置常量到内存
    @Override
    public List<ConstantEntity> qryConstantCfgData(){
        return commonMapper.qryConstantCfgData();
    }

    @Override
    public List<ConstantEntity> qryLogMenuButtonInfo()  {
        return commonMapper.qryLogMenuButtonInfo();
    }
}
