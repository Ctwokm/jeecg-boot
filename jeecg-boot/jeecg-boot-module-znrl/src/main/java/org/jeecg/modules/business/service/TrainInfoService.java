package org.jeecg.modules.business.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.vo.RegisterEntity;

public interface TrainInfoService {

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
//	//调整编辑顺序
//	public void adjustTrainOrder(RegisterEntity registerEntity);
//
//	//火车来煤分批
//	public void dealTrainBatchInfo(RegisterEntity registerEntity);

	//删除单个车厢
	public void deleteOneTrain(RegisterEntity registerEntity);

//	//确认翻车
//	public void confirmTrainDump(RegisterEntity registerEntity);
//
	//查询火车入厂登记信息详情
	@Deprecated
	public Result qryTrainDetailList(RegisterEntity registerEntity);
	
	public Page<RegisterEntity> qryTrainDetailListByPage(Page<RegisterEntity> page, RegisterEntity registerEntity);
//
//	//查询火车入厂登记信息合计
//	public Result qryTrainDetailListSummary(RegisterEntity registerEntity);

	//查询火车入厂登记信息详情
	@Deprecated
	public Result qryTrainBasicList(RegisterEntity registerEntity);
	//查询火车入厂登记信息详情分页查询
	public Page<RegisterEntity> qryTrainBasicListByPage(Page<RegisterEntity> page, RegisterEntity registerEntity);

//	//根据车次号删除火车记录
//	public void deleteTrainInfo(String trainNo);
//
//	//批量删除车节信息
//	public void deleteMassTrainBody(RegisterEntity registerEntity);
//
//	//查询火车重量修改记录
//	public Result qryOneTrainHisList(RegisterEntity registerEntity);

	//删除整个车次(用存储过程)
	public void deleteWholeTrain(RegisterEntity registerEntity);

//	//查询单个车厢详情
//	public RegisterEntity qryOneTrainDetail(String recordNo);

	//修改单个车厢信息
	public void modifyOneTrainInfo(RegisterEntity registerEntity);

}
