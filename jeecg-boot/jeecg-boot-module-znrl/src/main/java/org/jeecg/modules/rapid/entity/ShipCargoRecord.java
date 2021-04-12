package org.jeecg.modules.rapid.entity;

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
 * @Description: ship_cargo_record
 * @Author: jeecg-boot
 * @Date:   2021-03-18
 * @Version: V1.0
 */
@Data
@TableName("ship_cargo_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ship_cargo_record对象", description="ship_cargo_record")
public class ShipCargoRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**入厂流水*/
	@Excel(name = "入厂流水", width = 15)
    @ApiModelProperty(value = "入厂流水")
    private String recordNo;
	/**船运输记录ID*/
	@Excel(name = "船运输记录ID", width = 15)
    @ApiModelProperty(value = "船运输记录ID")
    private String shipRecId;
	/**供应商(group1)*/
	@Excel(name = "供应商(group1)", width = 15)
    @ApiModelProperty(value = "供应商(group1)")
    private String venNo;
	/**矿点(group2)*/
	@Excel(name = "矿点(group2)", width = 15)
    @ApiModelProperty(value = "矿点(group2)")
    private String mineNo;
	/**煤种(group3)*/
	@Excel(name = "煤种(group3)", width = 15)
    @ApiModelProperty(value = "煤种(group3)")
    private String coalNo;
	/**一个供应商、煤种、矿点所分布的舱位， 逗号隔开*/
	@Excel(name = "一个供应商、煤种、矿点所分布的舱位， 逗号隔开", width = 15)
    @ApiModelProperty(value = "一个供应商、煤种、矿点所分布的舱位， 逗号隔开")
    private String cabinNos;
	/**运单吨位*/
	@Excel(name = "运单吨位", width = 15)
    @ApiModelProperty(value = "运单吨位")
    private BigDecimal tickQty;
	/**离港吨位*/
	@Excel(name = "离港吨位", width = 15)
    @ApiModelProperty(value = "离港吨位")
    private BigDecimal departQty;
	/**到厂吨位*/
	@Excel(name = "到厂吨位", width = 15)
    @ApiModelProperty(value = "到厂吨位")
    private BigDecimal receiveQty;
	/**0无损 1水分 2矸石 3自燃 4其它*/
	@Excel(name = "0无损 1水分 2矸石 3自燃 4其它", width = 15)
    @ApiModelProperty(value = "0无损 1水分 2矸石 3自燃 4其它")
    private String lossType;
	/**扣吨，单位：吨*/
	@Excel(name = "扣吨，单位：吨", width = 15)
    @ApiModelProperty(value = "扣吨，单位：吨")
    private BigDecimal kdQty;
	/**采样方式：0皮采 1皮带大水分旁路 2人工采样 3车厢采样  4车厢大水分旁路*/
	@Excel(name = "采样方式：0皮采 1皮带大水分旁路 2人工采样 3车厢采样  4车厢大水分旁路", width = 15)
    @ApiModelProperty(value = "采样方式：0皮采 1皮带大水分旁路 2人工采样 3车厢采样  4车厢大水分旁路")
    private String sampleType;
	/**记录数据状态  0未生效 1生效 2失效*/
	@Excel(name = "记录数据状态  0未生效 1生效 2失效", width = 15)
    @ApiModelProperty(value = "记录数据状态  0未生效 1生效 2失效")
    private String recStatus;
	/**建立时间*/
	@Excel(name = "建立时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "建立时间")
    private Date insertTime;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**卸港水尺吨位(到达港水尺吨位)*/
	@Excel(name = "卸港水尺吨位(到达港水尺吨位)", width = 15)
    @ApiModelProperty(value = "卸港水尺吨位(到达港水尺吨位)")
    private BigDecimal arriveQty;
	/**运输单位编码*/
	@Excel(name = "运输单位编码", width = 15)
    @ApiModelProperty(value = "运输单位编码")
    private String carrierNo;
}
