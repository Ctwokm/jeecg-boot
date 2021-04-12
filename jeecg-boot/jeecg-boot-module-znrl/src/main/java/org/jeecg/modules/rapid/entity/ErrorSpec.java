package org.jeecg.modules.rapid.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: error_spec
 * @Author: jeecg-boot
 * @Date:   2021-03-15
 * @Version: V1.0
 */
@Data
@TableName("error_spec")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="error_spec对象", description="error_spec")
public class ErrorSpec implements Serializable {
    private static final long serialVersionUID = 1L;

	/**序列*/
	@Excel(name = "序列", width = 15)
    @ApiModelProperty(value = "序列")
    private java.lang.Integer errorSpecId;
	/**故障代码*/
	@Excel(name = "故障代码", width = 15)
    @ApiModelProperty(value = "故障代码")
    private java.lang.String errorCode;
	/**故障名称*/
	@Excel(name = "故障名称", width = 15)
    @ApiModelProperty(value = "故障名称")
    private java.lang.String errorTitle;
	/**故障定义*/
	@Excel(name = "故障定义", width = 15)
    @ApiModelProperty(value = "故障定义")
    private java.lang.String errorSpec;
	/**处理步骤方法*/
	@Excel(name = "处理步骤方法", width = 15)
    @ApiModelProperty(value = "处理步骤方法")
    private java.lang.String dealMethod;
	/**监控点IP*/
	@Excel(name = "监控点IP", width = 15)
    @ApiModelProperty(value = "监控点IP")
    private java.lang.String monitorIp;
	/**machinCode*/
	@Excel(name = "machinCode", width = 15)
    @ApiModelProperty(value = "machinCode")
    private java.lang.String machinCode;
	/**生效标志*/
	@Excel(name = "生效标志", width = 15)
    @ApiModelProperty(value = "生效标志")
    private java.lang.String state;
	/**建立时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "建立时间")
    private java.util.Date createTime;
	/**流程点*/
	@Excel(name = "流程点", width = 15)
    @ApiModelProperty(value = "流程点")
    private java.lang.String flowId;
	/**channelNo*/
	@Excel(name = "channelNo", width = 15)
    @ApiModelProperty(value = "channelNo")
    private java.lang.String channelNo;
}
