package org.jeecg.modules.rapid.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: ship_record
 * @Author: jeecg-boot
 * @Date:   2021-03-17
 * @Version: V1.0
 */
@Data
@TableName("ship_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ship_record对象", description="ship_record")
public class ShipRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**船运输记录ID*/
	@Excel(name = "船运输记录ID", width = 15)
    @ApiModelProperty(value = "船运输记录ID")
    private String shipRecId;
	/**YYYYMMDD+序号（待定）*/
	@Excel(name = "YYYYMMDD+序号（待定）", width = 15)
    @ApiModelProperty(value = "YYYYMMDD+序号（待定）")
    private String shipTransNo;
	/**船ID*/
	@Excel(name = "船ID", width = 15)
    @ApiModelProperty(value = "船ID")
    @Dict(dictTable = "ship_info", dicCode = "ship_id", dicText = "ship_name")
    private String shipId;
	/**运输单位编号*/
	@Excel(name = "运输单位编号", width = 15)
    @ApiModelProperty(value = "运输单位编号")
    @Dict(dictTable = "carrier_info", dicCode = "carrier_no", dicText = "carrier_name")
    private String carrierNo;
	/**货票号(运单号)*/
	@Excel(name = "货票号(运单号)", width = 15)
    @ApiModelProperty(value = "货票号(运单号)")
    private String waybillNo;
	/**水尺吨位*/
	@Excel(name = "水尺吨位", width = 15)
    @ApiModelProperty(value = "水尺吨位")
    private BigDecimal waterTun;
	/**收货单位*/
	@Excel(name = "收货单位", width = 15)
    @ApiModelProperty(value = "收货单位")
    private String receiverNo;
	/**0待装 1在装 2待出发 3重载在途 4到达待卸 5在卸 6卸空待回 7离港*/
	@Excel(name = "0待装 1在装 2待出发 3重载在途 4到达待卸 5在卸 6卸空待回 7离港", width = 15)
    @ApiModelProperty(value = "0待装 1在装 2待出发 3重载在途 4到达待卸 5在卸 6卸空待回 7离港")
    @Dict(dicCode = "gk_ship_record_status")
    private String status;
	/**装港用时*/
	@Excel(name = "装港用时", width = 15)
    @ApiModelProperty(value = "装港用时")
    private BigDecimal loadHours;
	/**卸港用时*/
	@Excel(name = "卸港用时", width = 15)
    @ApiModelProperty(value = "卸港用时")
    private BigDecimal unloadHours;
	/**始发港口*/
	@Excel(name = "始发港口", width = 15)
    @ApiModelProperty(value = "始发港口")
    private String startPortNo;
	/**到达港口*/
	@Excel(name = "到达港口", width = 15)
    @ApiModelProperty(value = "到达港口")
    private String finalPortNo;
	/**发港时间*/
	@Excel(name = "发港时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发港时间")
    private Date startTime;
	/**预计到港时间*/
	@Excel(name = "预计到港时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预计到港时间")
    private Date estimateTime;
	/**实际到港时间*/
	@Excel(name = "实际到港时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "实际到港时间")
    private Date factTime;
	/**接卸时间*/
	@Excel(name = "接卸时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "接卸时间")
    private Date arrangeTime;
	/**卸港完成时间*/
	@Excel(name = "卸港完成时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "卸港完成时间")
    private Date unloadTime;
	/**离港时间*/
	@Excel(name = "离港时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "离港时间")
    private Date departTime;
	/**泊位号*/
	@Excel(name = "泊位号", width = 15)
    @ApiModelProperty(value = "泊位号")
    private String berthNo;
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
	/**记录数据状态  0未生效 1生效 2失效*/
	@Excel(name = "记录数据状态  0未生效 1生效 2失效", width = 15)
    @ApiModelProperty(value = "记录数据状态  0未生效 1生效 2失效")
    private String recStatus;
	/**锚地时间*/
	@Excel(name = "锚地时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "锚地时间")
    private Date norTime;
	/**是否中转 Y/N*/
	@Excel(name = "是否中转 Y/N", width = 15)
    @ApiModelProperty(value = "是否中转 Y/N")
    private String isTransfer;
	/**上一段船运输id*/
	@Excel(name = "上一段船运输id", width = 15)
    @ApiModelProperty(value = "上一段船运输id")
    private BigDecimal previousShipId;
	/**运输分段， 1，2*/
	@Excel(name = "运输分段， 1，2", width = 15)
    @ApiModelProperty(value = "运输分段， 1，2")
    private String transSegment;
	/**中转港口*/
	@Excel(name = "中转港口", width = 15)
    @ApiModelProperty(value = "中转港口")
    private String transferPortNo;

}
