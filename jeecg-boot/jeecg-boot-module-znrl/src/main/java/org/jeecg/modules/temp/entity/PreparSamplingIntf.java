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
 * @Description: prepar_sampling_intf
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Data
@TableName("prepar_sampling_intf")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="prepar_sampling_intf对象", description="prepar_sampling_intf")
public class PreparSamplingIntf implements Serializable {
    private static final long serialVersionUID = 1L;

	/**信息记录ID*/
	@Excel(name = "信息记录ID", width = 15)
    @ApiModelProperty(value = "信息记录ID")
    private Integer samplingIntfRecId;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private String sampleCode;
	/**制样编码*/
	@Excel(name = "制样编码", width = 15)
    @ApiModelProperty(value = "制样编码")
    private String samplingCode;
	/**煤种*/
	@Excel(name = "煤种", width = 15)
    @ApiModelProperty(value = "煤种")
    private String coalType;
	/**粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm*/
	@Excel(name = "粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm", width = 15)
    @ApiModelProperty(value = "粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm")
    private String coalSize;
	/**水分 1：湿煤2：一般湿煤3：干煤4：一般干煤*/
	@Excel(name = "水分 1：湿煤2：一般湿煤3：干煤4：一般干煤", width = 15)
    @ApiModelProperty(value = "水分 1：湿煤2：一般湿煤3：干煤4：一般干煤")
    private String coalWater;
	/**样品重量*/
	@Excel(name = "样品重量", width = 15)
    @ApiModelProperty(value = "样品重量")
    private BigDecimal coalQty;
	/**样品包数*/
	@Excel(name = "样品包数", width = 15)
    @ApiModelProperty(value = "样品包数")
    private Integer packCnt;
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
}
