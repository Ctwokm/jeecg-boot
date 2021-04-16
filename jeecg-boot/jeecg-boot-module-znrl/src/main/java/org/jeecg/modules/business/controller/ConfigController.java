package org.jeecg.modules.business.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.business.service.IConfigService;
import org.jeecg.modules.business.vo.WorkModeTypeEntity;
import org.jeecg.modules.business.vo.WorkModeTypeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: jeecg-boot-parent
 * @description: 基本配置，例如：供应商、矿点等配置，统一
 * @author: Hujw
 * @create: 2021-03-16 11:43
 **/
@RestController
@Api(tags = "基本配置管理")
@RequestMapping("/znrl/config")
@Slf4j
public class ConfigController {

	@Autowired
	IConfigService configService;

	@GetMapping(value="/workModeType/list/{typeId}")
	@ResponseBody
	public Result qryWorkModeTypeList(@PathVariable int typeId){
		Result result = new Result();
		WorkModeTypeResult workModeTypeResult = new WorkModeTypeResult();
		try {
			workModeTypeResult = getWorkModeTypeList(typeId);
			result.setResult(workModeTypeResult);
		} catch (Exception e) {
			result.setSuccess(false);
			e.printStackTrace();
		}
		return result;
	}


	private WorkModeTypeResult getWorkModeTypeList(int typeId) {
		WorkModeTypeEntity workModeTypeEntity = new WorkModeTypeEntity();
		WorkModeTypeResult returnResult = new WorkModeTypeResult();
		List<WorkModeTypeResult> temp = new ArrayList<WorkModeTypeResult>();;
		workModeTypeEntity.setParentTypeId(typeId);
		try {
			List<WorkModeTypeEntity> list = configService.qryWorkModeTypeList(workModeTypeEntity);
			returnResult.setParentTypeId(workModeTypeEntity.getParentTypeId());
			if (list != null) {
				returnResult.setWorkModeTypeList(list);
				for (int i = 0;i<list.size();i++) {
					if ("0".equals(list.get(i).getShowType())) {//父级需要取下级配置，子级不需要
						temp.add(getWorkModeTypeList(list.get(i).getTypeId()));
					}
				}
				returnResult.setWorkModeTypeResult(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnResult;
	}

}
