package org.jeecg.modules.business.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.business.vo.RegisterEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainInfoMapper{
//
//	//火车注册
//	public void trainRegister(RegisterEntity registerEntity);
//
//	//火车大票批量录入
//	public void batchTrainDPRecord(RegisterEntity registerEntity);
//
//	//火车大票批量录入并同时分批
//	public void batchTrainDPRecordAndBatch(RegisterEntity registerEntity);
//
//	//新增火车，可以在原来车次上新增，也可以新增测试
//	public void addNewTrain(RegisterEntity registerEntity);
//
//	//合并火车车次
//	public void mergeTrain(RegisterEntity registerEntity);
//
//	//拆分车次
//	public void splitTrain(RegisterEntity registerEntity);
//
//	//火车大票批量录入并同时分批
//	public void adjustTrainOrder(RegisterEntity registerEntity);
//
//	//火车来煤分批
//	public void dealTrainBatchInfo(RegisterEntity registerEntity);

	//删除单个车厢
	public void deleteOneTrain(RegisterEntity registerEntity);

//	//确认翻车
//	public void confirmTrainDump(RegisterEntity registerEntity);

	//查询火车入厂登记基本信息详情
	public List<RegisterEntity> qryTrainBasicList(RegisterEntity registerEntity);
	//查询火车入厂登记基本信息详情，分页
	public List<RegisterEntity> qryTrainBasicListByPage(@Param("page") Page<RegisterEntity> page, @Param("registerEntity") RegisterEntity registerEntity);
	//查询火车入厂登记信息详情
	public List<RegisterEntity> qryTrainDetailList(RegisterEntity registerEntity);
	public List<RegisterEntity> qryTrainDetailListByPage(@Param("page") Page<RegisterEntity> page, @Param("registerEntity") RegisterEntity registerEntity);
//
//	//查询火车入厂登记信息合计
//	public List<RegisterEntity> qryTrainDetailListSummary(RegisterEntity registerEntity);
//
//	//根据车次号删除火车记录
//	public void deleteTrainInfo(String trainNo);
//
//	//批量删除车节信息
//	public void deleteMassTrainBody(RegisterEntity registerEntity);
//
//	//查询单个车厢详情
//	public List<RegisterEntity> qryOneTrainDetail(String recordNo);

	//修改单个车厢信息
	public void modifyOneTrainInfo(RegisterEntity registerEntity);

	//删除整个车次(存储过程实现)
	public void deleteWholeTrain(RegisterEntity registerEntity);


//	//查询火车重量修改记录
//	public List<RegisterEntity> qryOneTrainHisList(RegisterEntity registerEntity);

}
