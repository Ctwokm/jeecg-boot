package org.jeecg.modules.rapid.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @Description: dictionary_table
 * @Author: jeecg-boot
 * @Date:   2021-02-25
 * @Version: V1.0
 */
@Data
@TableName("dictionary_table")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="dictionary_table对象", description="dictionary_table")
public class DictionaryTable implements Serializable {
    private static final long serialVersionUID = 1L;

	/**表主键*/
	@Excel(name = "表主键", width = 15)
    @ApiModelProperty(value = "表主键")
    private java.lang.Long dictionaryTableId;
	/**ID*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "ID")
    private java.lang.String id;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**类型区别*/
	@Excel(name = "类型区别", width = 15)
    @ApiModelProperty(value = "类型区别")
    private java.lang.String type;
	/**父级ID*/
	@Excel(name = "父级ID", width = 15)
    @ApiModelProperty(value = "父级ID")
    private java.lang.String parentId;
	/**备注信息*/
	@Excel(name = "备注信息", width = 15)
    @ApiModelProperty(value = "备注信息")
    private java.lang.String remark;
	/**创建日期*/
	@Excel(name = "创建日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createDate;
	/**是否允许页面配置 Y允许 N不允许*/
	@Excel(name = "是否允许页面配置 Y允许 N不允许", width = 15)
    @ApiModelProperty(value = "是否允许页面配置 Y允许 N不允许")
    private java.lang.String canConf;
	/**状态 0正常 1失效*/
	@Excel(name = "状态 0正常 1失效", width = 15)
    @ApiModelProperty(value = "状态 0正常 1失效")
    private java.lang.String status;
}
