package org.jeecg.modules.temp.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

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
 * @Description: take_sample_intf
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Data
@TableName("take_sample_intf")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="take_sample_intf对象", description="take_sample_intf")
public class TakeSampleIntf implements Serializable {
    private static final long serialVersionUID = 1L;

	/**记录ID*/
	@Excel(name = "记录ID", width = 15)
    @ApiModelProperty(value = "记录ID")
    private Integer sampleIntfRecId;
	/**入厂流水（车厢采样需要填）*/
	@Excel(name = "入厂流水（车厢采样需要填）", width = 15)
    @ApiModelProperty(value = "入厂流水（车厢采样需要填）")
    private Integer recordNo;
	/**车牌号/火车车号（车厢采样需要填）*/
	@Excel(name = "车牌号/火车车号（车厢采样需要填）", width = 15)
    @ApiModelProperty(value = "车牌号/火车车号（车厢采样需要填）")
    private String carId;
	/**卡号：汽车填RFID扫描编码*/
	@Excel(name = "卡号：汽车填RFID扫描编码", width = 15)
    @ApiModelProperty(value = "卡号：汽车填RFID扫描编码")
    private String cardId;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private String sampleCode;
	/**汽车来煤批次号*/
	@Excel(name = "汽车来煤批次号", width = 15)
    @ApiModelProperty(value = "汽车来煤批次号")
    private String carBatchNo;
	/**火车来煤批次号*/
	@Excel(name = "火车来煤批次号", width = 15)
    @ApiModelProperty(value = "火车来煤批次号")
    private String trainBatchNo;
	/**车辆数：本批次车辆/车厢节数*/
	@Excel(name = "车辆数：本批次车辆/车厢节数", width = 15)
    @ApiModelProperty(value = "车辆数：本批次车辆/车厢节数")
    private Integer carNum;
	/**本批次总净重、预计总重*/
	@Excel(name = "本批次总净重、预计总重", width = 15)
    @ApiModelProperty(value = "本批次总净重、预计总重")
    private BigDecimal allNetQty;
	/**煤种*/
	@Excel(name = "煤种", width = 15)
    @ApiModelProperty(value = "煤种")
    private String coalType;
	/**水分*/
	@Excel(name = "水分", width = 15)
    @ApiModelProperty(value = "水分")
    private BigDecimal coalWater;
	/**采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'*/
	@Excel(name = "采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'", width = 15)
    @ApiModelProperty(value = "采样方式 0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'")
    private String sampleTyp;
	/**采样点数（车厢采样）*/
	@Excel(name = "采样点数（车厢采样）", width = 15)
    @ApiModelProperty(value = "采样点数（车厢采样）")
    private Integer sampleCnt;
	/**采样片区 （车厢采样）：采样填值 格式：“1；2；5” */
	@Excel(name = "采样片区 （车厢采样）：采样填值 格式：“1；2；5” ", width = 15)
    @ApiModelProperty(value = "采样片区 （车厢采样）：采样填值 格式：“1；2；5” ")
    private String sampleAreas;
	/**采样坐标（车厢采样）*/
	@Excel(name = "采样坐标（车厢采样）", width = 15)
    @ApiModelProperty(value = "采样坐标（车厢采样）")
    private String sampleCordna;
	/**数据状态 0: 未读取 1: 已读取*/
	@Excel(name = "数据状态 0: 未读取 1: 已读取", width = 15)
    @ApiModelProperty(value = "数据状态 0: 未读取 1: 已读取")
    private String dataStatus;
	/**建立时间*/
	@Excel(name = "建立时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "建立时间")
    private Date insertTime;
	/**建立用户*/
	@Excel(name = "建立用户", width = 15)
    @ApiModelProperty(value = "建立用户")
    private String opCode;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**更新用户*/
	@Excel(name = "更新用户", width = 15)
    @ApiModelProperty(value = "更新用户")
    private String updateCode;
	/**本批次总票重*/
	@Excel(name = "本批次总票重", width = 15)
    @ApiModelProperty(value = "本批次总票重")
    private BigDecimal allTickQty;
}
