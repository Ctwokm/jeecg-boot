package org.jeecg.modules.rapid.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: carrier_info
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Data
@TableName("carrier_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="carrier_info对象", description="carrier_info")
public class CarrierInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**carrierNo*/
	@Excel(name = "carrierNo", width = 15)
    @ApiModelProperty(value = "carrierNo")
    private java.lang.String carrierNo;
	/**运输单位名称*/
	@Excel(name = "运输单位名称", width = 15)
    @ApiModelProperty(value = "运输单位名称")
    private java.lang.String carrierName;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private java.lang.String carrierAddr;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String contNum;
	/**联系人*/
	@Excel(name = "联系人", width = 15)
    @ApiModelProperty(value = "联系人")
    private java.lang.String contPerson;
	/**自卸车数量*/
	@Excel(name = "自卸车数量", width = 15)
    @ApiModelProperty(value = "自卸车数量")
    private java.lang.Integer zycNum;
	/**平板车数量*/
	@Excel(name = "平板车数量", width = 15)
    @ApiModelProperty(value = "平板车数量")
    private java.lang.Integer pbcNum;
	/**无费车数量*/
	@Excel(name = "无费车数量", width = 15)
    @ApiModelProperty(value = "无费车数量")
    private java.lang.Integer wfcNum;
	/**特殊车数量*/
	@Excel(name = "特殊车数量", width = 15)
    @ApiModelProperty(value = "特殊车数量")
    private java.lang.Integer tscNum;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**简称*/
	@Excel(name = "简称", width = 15)
    @ApiModelProperty(value = "简称")
    private java.lang.String forShort;
}
