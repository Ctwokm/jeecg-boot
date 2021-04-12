package org.jeecg.modules.business.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.modules.business.service.TrainInfoService;
import org.jeecg.modules.business.utils.JacksonMapper;
import org.jeecg.modules.business.vo.RegisterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @program: jeecg-boot-parent
 * @description: 火车来煤业务管理处理控制器，包含火车相关的所有的业务操作
 * @author: Hujw
 * @create: 2021-02-22 11:13
 **/

@RestController
@Api(tags = "火车业务处理")
@RequestMapping("/znrl/train")
@Slf4j
public class TrainInfoController {

	@Autowired
	public TrainInfoService trainInfoService;

	@AutoLog(value = "业务方法demo")
	@ApiOperation(value="业务方法demo", notes="业务方法demo")
	@GetMapping(value = "/demo")
	public Result<String> demo() {
		Result<String> result = new Result<String>();
		result.setResult("Hello demo!");
		result.setSuccess(true);
		return result;
	}

//	@AutoLog(value = "/查询火车入厂登记基本信息详情")
//	@ApiOperation(value="查询火车入厂登记基本信息详情", notes="查询火车入厂登记基本信息详情")
//	@GetMapping(value = "/trainBasicList")
//	public Result qryTrainBasicList(RegisterEntity registerEntity) {
//
//		Result result = new Result();
//		Page<RegisterEntity> pageList = new Page<RegisterEntity>(registerEntity.getPageNo(), registerEntity.getPageSize());
//		try {
//			pageList = trainInfoService.qryTrainBasicListByPage(pageList,registerEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		result.setSuccess(true);
//		result.setResult(pageList);
//
//		return result;
//	}
	@AutoLog(value = "/查询火车入厂登记基本信息详情分页查询")
	@ApiOperation(value="查询火车入厂登记基本信息详情分页查询", notes="查询火车入厂登记基本信息详情分页查询")
	@GetMapping(value = "/trainBasicListByPage")
	public Result qryTrainBasicListByPage(RegisterEntity registerEntity) {

		Result result = new Result();
		Page<RegisterEntity> pageList = new Page<RegisterEntity>(registerEntity.getPageNo(), registerEntity.getPageSize());
		try {
			pageList = trainInfoService.qryTrainBasicListByPage(pageList,registerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setSuccess(true);
		result.setResult(pageList);

		return result;
	}

//	@AutoLog(value = "/查询火车入厂登记信息详情")
//	@ApiOperation(value="查询火车入厂登记信息详情", notes="查询火车入厂登记信息详情")
//	@GetMapping(value = "/trainDetailList")
//	@ResponseBody
//	public Result qryTrainDetailList(RegisterEntity registerEntity) {
//
//		//查询
//		Result result = new Result();
//		try {
//			result = trainInfoService.qryTrainDetailList(registerEntity);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//	}

	@AutoLog(value = "/分页查询火车入厂登记信息详情")
	@ApiOperation(value="分页查询火车入厂登记信息详情", notes="分页查询火车入厂登记信息详情")
	@GetMapping(value = "/trainDetailListByPage")
	@ResponseBody
	public Result qryTrainDetailListByPage(RegisterEntity registerEntity) {

		Page<RegisterEntity> pageList = new Page<RegisterEntity>(registerEntity.getPageNo(), registerEntity.getPageSize());
		//查询
		Result result = new Result();
		try {
			pageList = trainInfoService.qryTrainDetailListByPage(pageList , registerEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.setSuccess(true);
		result.setResult(pageList);

		return result;
	}

	//根据车次号删除火车记录（通过存储过程删除）
	@AutoLog(value = "/根据车次号删除火车记录")
	@ApiOperation(value="根据车次号删除火车记录", notes="根据车次号删除火车记录（通过存储过程删除）")
	@DeleteMapping(value = "/deleteTrainInfo")
	@ResponseBody
	@Transactional
	public Result deleteTrainInfo(RegisterEntity registerEntity) {
		Result result = new Result();
		try {

			//获取登录操作员的ID
//			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//            String opCode = sysUser.getId();
			registerEntity.setOpCode("hujw");
			//删除整个车次数据
			trainInfoService.deleteWholeTrain(registerEntity);
			//设置返回结果
			if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
				result.setMessage("删除车次操作成功！");
				result.setSuccess(true);
			} else {
				result.setMessage("车次信息删除失败:" + registerEntity.getResMsg());
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setMessage("车次信息删除异常:" + e.getMessage());
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}

	//删除单个车厢，并进行重新排序
	@AutoLog(value = "/删除单个车厢")
	@ApiOperation(value="删除单个车厢", notes="删除单个车厢（通过存储过程删除，并进行重新排序）")
	@DeleteMapping(value = "/deleteOneTrain")
	@ResponseBody
	@Transactional
	public Result deleteOneTrain(RegisterEntity registerEntity) {
		Result result = new Result();
		try {
			//获取界面输入参数

			//获取登录操作员的ID
//			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//            String opCode = sysUser.getId();
			registerEntity.setOpCode("hujw");
			//新增数据
			trainInfoService.deleteOneTrain(registerEntity);
			//设置返回结果
			if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
				result.setMessage("删除操作成功！");
				result.setSuccess(true);
			} else {
				result.setMessage("火车车厢信息删除失败:" + registerEntity.getResMsg());
				result.setSuccess(false);
			}
		} catch (Exception e) {
			result.setMessage("火车车厢信息删除异常:" + e.getMessage());
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}
	//编辑单个车厢信息（新增）
	@AutoLog(value = "/编辑单个车厢信息")
	@ApiOperation(value="编辑单个车厢信息", notes="编辑单个车厢信息（通过存储过程删除，并进行重新排序）")
	@PostMapping(value = "/modifyOneTrainInfo")
	@ResponseBody
	@Transactional
	public Result modifyOneTrainInfo(@RequestBody RegisterEntity registerEntity) {
		Result result = new Result();
		try {
			//获取登录操作员的ID
//			ShiroUser shiroUser = org.gxz.znrl.shiro.SecurityUtils.getShiroUser();
//			String opCode = shiroUser.getUser().getId().toString();
			//todo 后期需要补上权限
			registerEntity.setOpCode("hujw");

			ObjectMapper objectMapper = JacksonMapper.getInstance();
			String json = objectMapper.writeValueAsString(registerEntity);
			registerEntity.setUpdateString(json);

			trainInfoService.modifyOneTrainInfo(registerEntity);

			//设置返回结果
			if (registerEntity.getResCode() != null && registerEntity.getResCode().equals("0")) {
				result.setSuccess(true);
				result.setMessage("修改火车来煤信息成功！");
			} else {
				result.setSuccess(false);
				result.setMessage("修改火车来煤信息失败！" + registerEntity.getResMsg());
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改火车来煤信息异常！" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
