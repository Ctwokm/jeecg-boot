package org.jeecg.modules.business.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.mapper.TrainInfoMapper;
import org.jeecg.modules.business.service.TrainInfoService;
import org.jeecg.modules.business.vo.RegisterEntity;
import org.jeecg.modules.common.mapper.BizToolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: Hujw
 * @create: 2021-02-22 14:03
 **/
@Service("trainInfoService")
@Transactional
public class TrainInfoServiceImpl implements TrainInfoService{

	@Autowired
	private TrainInfoMapper trainInfoMapper;

	@Autowired
	private BizToolMapper bizToolMapper;
//
//	//火车注册
//	public void trainRegister(RegisterEntity registerEntity){
//		trainInfoMapper.trainRegister(registerEntity);
//	}
//
//	//火车大票批量录入
//	public void batchTrainDPRecord(RegisterEntity registerEntity){
//		trainInfoMapper.batchTrainDPRecord(registerEntity);
//	}
//
//	//火车大票批量录入并同时分批
//	public void batchTrainDPRecordAndBatch(RegisterEntity registerEntity){
//		trainInfoMapper.batchTrainDPRecordAndBatch(registerEntity);
//	}
//
//	//新增火车，可以在原来车次上新增，也可以新增测试
//	public void addNewTrain(RegisterEntity registerEntity){
//		trainInfoMapper.addNewTrain(registerEntity);
//	}
//
//	//合并火车车次
//	public void mergeTrain(RegisterEntity registerEntity){
//		trainInfoMapper.mergeTrain(registerEntity);
//	}
//
//	//拆分车次
//	public void splitTrain(RegisterEntity registerEntity){
//		trainInfoMapper.splitTrain(registerEntity);
//	}
//
//	//调整编辑顺序
//	public void adjustTrainOrder(RegisterEntity registerEntity){
//		trainInfoMapper.adjustTrainOrder(registerEntity);
//	}
//
//	//火车来煤分批
//	public void dealTrainBatchInfo(RegisterEntity registerEntity){
//		trainInfoMapper.dealTrainBatchInfo(registerEntity);
//	}

	//删除单个车厢
	public void deleteOneTrain(RegisterEntity registerEntity){
		trainInfoMapper.deleteOneTrain(registerEntity);
	}

//	//确认翻车
//	public void confirmTrainDump(RegisterEntity registerEntity){
//		trainInfoMapper.confirmTrainDump(registerEntity);
//	}

	//查询火车入厂登记信息详情
	public Result qryTrainDetailList(RegisterEntity registerEntity){
		//查询本次页面的结果集
		List<RegisterEntity> list =  trainInfoMapper.qryTrainDetailList(registerEntity);
		//设置返回结果
		Result m = new Result();
		m.setResult(list);

		return m;
	}
	//查询火车入厂登记详细信息，使用
	public Page<RegisterEntity> qryTrainDetailListByPage(Page<RegisterEntity> page, RegisterEntity registerEntity){
		return page.setRecords(trainInfoMapper.qryTrainDetailListByPage(page,registerEntity));
	}


//	//查询火车入厂登记信息合计
//	public Result qryTrainDetailListSummary(RegisterEntity registerEntity){
//		//查询本次页面的结果集
//		List<RegisterEntity> list =  trainInfoMapper.qryTrainDetailListSummary(registerEntity);
//		//设置返回结果
//		Result m = new Result();
//		m.setResult(list);
//
//		return m;
//	}
//

	//查询火车入厂登记基本信息详情
	public Result qryTrainBasicList(RegisterEntity registerEntity){

		List<RegisterEntity> list = new ArrayList<>();
		//查询本次页面的结果集
		try {
			list = trainInfoMapper.qryTrainBasicList(registerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}


		//设置返回结果
		Result m = new Result();
		m.setResult(list);

		return m;
	}
	//查询火车入厂登记基本信息详情分页查询
	public Page<RegisterEntity> qryTrainBasicListByPage(Page<RegisterEntity> page, RegisterEntity registerEntity){
		return page.setRecords(trainInfoMapper.qryTrainBasicListByPage(page,registerEntity));
	}

//
//	//根据车次号删除火车记录
//	public void deleteTrainInfo(String trainNo){
//		trainInfoMapper.deleteTrainInfo(trainNo);
//	}
//
//	public void deleteMassTrainBody(RegisterEntity registerEntity){
//		trainInfoMapper.deleteMassTrainBody(registerEntity);
//	}
//
//	//查询单个车厢详情
//	public RegisterEntity qryOneTrainDetail(String recordNo) {
//		List<RegisterEntity> list = trainInfoMapper.qryOneTrainDetail(recordNo);
//
//		//设置返回结果
//		RegisterEntity d = list.get(0);
//
//		return d;
//	}

	//修改单个车厢信息
	public void modifyOneTrainInfo(RegisterEntity registerEntity){
		trainInfoMapper.modifyOneTrainInfo(registerEntity);
	}

	//删除整个车次(用存储过程)
	public void deleteWholeTrain(RegisterEntity registerEntity){
		trainInfoMapper.deleteWholeTrain(registerEntity);
	}


//	//查询火车重量修改记录  聊城
//	public Result qryOneTrainHisList(RegisterEntity registerEntity){
//		//查询本次页面的结果集
//		List<RegisterEntity> list = trainInfoMapper.qryOneTrainHisList(registerEntity);
//
//		//设置返回结果
//		Result m = new Result();
//		m.setResult(list);
//
//		return m;
//	}
}
