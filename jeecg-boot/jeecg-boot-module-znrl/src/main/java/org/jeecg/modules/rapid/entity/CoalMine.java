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
 * @Description: coal_mine
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Data
@TableName("coal_mine")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coal_mine对象", description="coal_mine")
public class CoalMine implements Serializable {
    private static final long serialVersionUID = 1L;

	/**mineNo*/
	@Excel(name = "mineNo", width = 15)
    @ApiModelProperty(value = "mineNo")
    private java.lang.String mineNo;
	/**矿名*/
	@Excel(name = "矿名", width = 15)
    @ApiModelProperty(value = "矿名")
    private java.lang.String mineName;
	/**area*/
	@Excel(name = "area", width = 15)
    @ApiModelProperty(value = "area")
    private java.lang.String area;
	/**transDist*/
	@Excel(name = "transDist", width = 15)
    @ApiModelProperty(value = "transDist")
    private java.math.BigDecimal transDist;
	/**transTime*/
	@Excel(name = "transTime", width = 15)
    @ApiModelProperty(value = "transTime")
    private java.math.BigDecimal transTime;
	/**yearOutp*/
	@Excel(name = "yearOutp", width = 15)
    @ApiModelProperty(value = "yearOutp")
    private java.math.BigDecimal yearOutp;
	/**coalNo*/
	@Excel(name = "coalNo", width = 15)
    @ApiModelProperty(value = "coalNo")
    private java.lang.String coalNo;
	/**lealPerson*/
	@Excel(name = "lealPerson", width = 15)
    @ApiModelProperty(value = "lealPerson")
    private java.lang.String lealPerson;
	/**transMode*/
	@Excel(name = "transMode", width = 15)
    @ApiModelProperty(value = "transMode")
    private java.lang.String transMode;
	/**mineProp*/
	@Excel(name = "mineProp", width = 15)
    @ApiModelProperty(value = "mineProp")
    private java.lang.String mineProp;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**简称*/
	@Excel(name = "简称", width = 15)
    @ApiModelProperty(value = "简称")
    private java.lang.String forShort;
}
