package org.jeecg.modules.business.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
