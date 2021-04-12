package org.jeecg.modules.rapid.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: ship_info
 * @Author: jeecg-boot
 * @Date:   2021-03-10
 * @Version: V1.0
 */
@Data
@TableName("ship_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ship_info对象", description="ship_info")
public class ShipInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**船ID*/
	@Excel(name = "船ID", width = 15)
    @ApiModelProperty(value = "船ID")
    private String shipId;

	/**船名称*/
	@Excel(name = "船名称", width = 15)
    @ApiModelProperty(value = "船名称")
    private java.lang.String shipName;
	/**船英文名称*/
	@Excel(name = "船英文名称", width = 15)
    @ApiModelProperty(value = "船英文名称")
    private java.lang.String shipEngName;
	/**船号*/
	@Excel(name = "船号", width = 15)
    @ApiModelProperty(value = "船号")
    private java.lang.String shipCode;
	/**如果无航次则为空*/
	@Excel(name = "如果无航次则为空", width = 15)
    @ApiModelProperty(value = "如果无航次则为空")
    private java.lang.String fixShipNo;
	/**舱位数量*/
	@Excel(name = "舱位数量", width = 15)
    @ApiModelProperty(value = "舱位数量")
    private java.lang.Integer cabinCnt;
	/**额定载重*/
	@Excel(name = "额定载重", width = 15)
    @ApiModelProperty(value = "额定载重")
    private java.math.BigDecimal loadTun;
	/**总排水量*/
	@Excel(name = "总排水量", width = 15)
    @ApiModelProperty(value = "总排水量")
    private java.math.BigDecimal totalTun;
	/**舷宽*/
	@Excel(name = "舷宽", width = 15)
    @ApiModelProperty(value = "舷宽")
    private java.math.BigDecimal width;
	/**船长度*/
	@Excel(name = "船长度", width = 15)
    @ApiModelProperty(value = "船长度")
    private java.math.BigDecimal length;
	/**国籍*/
	@Excel(name = "国籍", width = 15)
    @ApiModelProperty(value = "国籍")
    private java.lang.String nationCd;
	/**经营公司*/
	@Excel(name = "经营公司", width = 15)
    @ApiModelProperty(value = "经营公司")
    @Dict(dictTable = "carrier_info", dicCode = "carrier_no", dicText = "carrier_name")
    private java.lang.String companyNo;

	/**0未生效  1生效  2失效*/
	@Excel(name = "0未生效  1生效  2失效", width = 15)
    @ApiModelProperty(value = "0未生效  1生效  2失效")
    @Dict(dicCode = "gk_ship_info_status")
    private java.lang.String status;

    private String remark;

}
