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
 * @Description: coal_type
 * @Author: jeecg-boot
 * @Date:   2021-03-01
 * @Version: V1.0
 */
@Data
@TableName("coal_type")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="coal_type对象", description="coal_type")
public class CoalType implements Serializable {
    private static final long serialVersionUID = 1L;

	/**煤种编号*/
	@Excel(name = "煤种编号", width = 15)
    @ApiModelProperty(value = "煤种编号")
    private java.lang.String coalNo;
	/**煤种名称*/
	@Excel(name = "煤种名称", width = 15)
    @ApiModelProperty(value = "煤种名称")
    private java.lang.String coalName;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private java.lang.String remark;
	/**简称*/
	@Excel(name = "简称", width = 15)
    @ApiModelProperty(value = "简称")
    private java.lang.String forShort;
}
