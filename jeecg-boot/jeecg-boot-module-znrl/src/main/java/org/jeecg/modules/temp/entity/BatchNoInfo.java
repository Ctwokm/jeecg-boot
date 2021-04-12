package org.jeecg.modules.temp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: batch_no_info
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Data
@TableName("batch_no_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="batch_no_info对象", description="batch_no_info")
public class BatchNoInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**来煤批次号*/
	@Excel(name = "来煤批次号", width = 15)
    @ApiModelProperty(value = "来煤批次号")
    @TableId(value = "batch_no")
    private String batchNo;
	/**0:汽车来煤批次 1:火车来煤批次 2:船运煤批次 6:船煤抽检批次 7:火车煤抽检批次 8:汽车煤抽检批次 9:入炉煤采样批次 3:人工采样批次*/
	@Excel(name = "0:汽车来煤批次 1:火车来煤批次 2:船运煤批次 6:船煤抽检批次 7:火车煤抽检批次 8:汽车煤抽检批次 9:入炉煤采样批次 3:人工采样批次", width = 15)
    @ApiModelProperty(value = "0:汽车来煤批次 1:火车来煤批次 2:船运煤批次 6:船煤抽检批次 7:火车煤抽检批次 8:汽车煤抽检批次 9:入炉煤采样批次 3:人工采样批次")
    private String batchNoType;
	/**trainNo*/
	@Excel(name = "trainNo", width = 15)
    @ApiModelProperty(value = "trainNo")
    private String trainNo;
	/**供应商*/
	@Excel(name = "供应商", width = 15)
    @ApiModelProperty(value = "供应商")
    private String venNo;
	/**煤品种*/
	@Excel(name = "煤品种", width = 15)
    @ApiModelProperty(value = "煤品种")
    private String coalNo;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private String sampleCode;
	/**制样编码*/
	@Excel(name = "制样编码", width = 15)
    @ApiModelProperty(value = "制样编码")
    private String samplingCode;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private String laborCode;
	/**批次序号：当日生成批次序号*/
	@Excel(name = "批次序号：当日生成批次序号", width = 15)
    @ApiModelProperty(value = "批次序号：当日生成批次序号")
    private Integer dayBatchNum;
	/**本批次包含车号*/
	@Excel(name = "本批次包含车号", width = 15)
    @ApiModelProperty(value = "本批次包含车号")
    private String carIds;
	/**总车数*/
	@Excel(name = "总车数", width = 15)
    @ApiModelProperty(value = "总车数")
    private Integer carNum;
	/**本批次总净重、预计总重*/
	@Excel(name = "本批次总净重、预计总重", width = 15)
    @ApiModelProperty(value = "本批次总净重、预计总重")
    private BigDecimal allNetQty;
	/**是否已生效，是否形成正式数据： 0 ：未完成 1：已完成  2：完成处理中*/
	@Excel(name = "是否已生效，是否形成正式数据： 0 ：未完成 1：已完成  2：完成处理中", width = 15)
    @ApiModelProperty(value = "是否已生效，是否形成正式数据： 0 ：未完成 1：已完成  2：完成处理中")
    private String isValid;
	/**建立时间*/
	@Excel(name = "建立时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "建立时间")
    private Date insertTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**建立用户*/
	@Excel(name = "建立用户", width = 15)
    @ApiModelProperty(value = "建立用户")
    private String opCode;
	/**更新用户*/
	@Excel(name = "更新用户", width = 15)
    @ApiModelProperty(value = "更新用户")
    private String updateCode;
	/**矿点*/
	@Excel(name = "矿点", width = 15)
    @ApiModelProperty(value = "矿点")
    private String mineNo;
	/**本批次总票重*/
	@Excel(name = "本批次总票重", width = 15)
    @ApiModelProperty(value = "本批次总票重")
    private BigDecimal allTickQty;
	/**运输单位编号*/
	@Excel(name = "运输单位编号", width = 15)
    @ApiModelProperty(value = "运输单位编号")
    private String carrierNo;
	/**来煤计划ID*/
	@Excel(name = "来煤计划ID", width = 15)
    @ApiModelProperty(value = "来煤计划ID")
    private Integer carPlanId;
	/**人工采用编码*/
	@Excel(name = "人工采用编码", width = 15)
    @ApiModelProperty(value = "人工采用编码")
    private String manSampleCode;
	/**批次关联ID，如底样面样和主批次关联*/
	@Excel(name = "批次关联ID，如底样面样和主批次关联", width = 15)
    @ApiModelProperty(value = "批次关联ID，如底样面样和主批次关联")
    private String batchRelaId;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**合同编码*/
	@Excel(name = "合同编码", width = 15)
    @ApiModelProperty(value = "合同编码")
    private String contractNo;
	/**receiveTime*/
	@Excel(name = "receiveTime", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "receiveTime")
    private Date receiveTime;
	/**receiveOp*/
	@Excel(name = "receiveOp", width = 15)
    @ApiModelProperty(value = "receiveOp")
    private String receiveOp;
}
