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
 * @Description: lab_report
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Data
@TableName("lab_report")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="lab_report对象", description="lab_report")
public class LabReport implements Serializable {
    private static final long serialVersionUID = 1L;

	/**记录号*/
	@Excel(name = "记录号", width = 15)
    @ApiModelProperty(value = "记录号")
    private Integer labReportId;
	/**制样编码*/
	@Excel(name = "制样编码", width = 15)
    @ApiModelProperty(value = "制样编码")
    private String laborCode;
	/**1初次化验 2第2次 3第3次 4第4次*/
	@Excel(name = "1初次化验 2第2次 3第3次 4第4次", width = 15)
    @ApiModelProperty(value = "1初次化验 2第2次 3第3次 4第4次")
    private String batchType;
	/**全水*/
	@Excel(name = "全水", width = 15)
    @ApiModelProperty(value = "全水")
    private BigDecimal mt;
	/**水分*/
	@Excel(name = "水分", width = 15)
    @ApiModelProperty(value = "水分")
    private BigDecimal mad;
	/**空干基灰分*/
	@Excel(name = "空干基灰分", width = 15)
    @ApiModelProperty(value = "空干基灰分")
    private BigDecimal aad;
	/**干燥基灰分*/
	@Excel(name = "干燥基灰分", width = 15)
    @ApiModelProperty(value = "干燥基灰分")
    private BigDecimal ad;
	/**收到基灰分*/
	@Excel(name = "收到基灰分", width = 15)
    @ApiModelProperty(value = "收到基灰分")
    private BigDecimal aar;
	/**空干基挥发分*/
	@Excel(name = "空干基挥发分", width = 15)
    @ApiModelProperty(value = "空干基挥发分")
    private BigDecimal vad;
	/**干燥基挥发分*/
	@Excel(name = "干燥基挥发分", width = 15)
    @ApiModelProperty(value = "干燥基挥发分")
    private BigDecimal vd;
	/**收到基挥发分*/
	@Excel(name = "收到基挥发分", width = 15)
    @ApiModelProperty(value = "收到基挥发分")
    private BigDecimal var;
	/**干燥无灰基挥发分*/
	@Excel(name = "干燥无灰基挥发分", width = 15)
    @ApiModelProperty(value = "干燥无灰基挥发分")
    private BigDecimal vdaf;
	/**空干基全硫*/
	@Excel(name = "空干基全硫", width = 15)
    @ApiModelProperty(value = "空干基全硫")
    private BigDecimal stAd;
	/**干燥基全硫*/
	@Excel(name = "干燥基全硫", width = 15)
    @ApiModelProperty(value = "干燥基全硫")
    private BigDecimal stD;
	/**收到基全硫*/
	@Excel(name = "收到基全硫", width = 15)
    @ApiModelProperty(value = "收到基全硫")
    private BigDecimal stAr;
	/**干燥基氢*/
	@Excel(name = "干燥基氢", width = 15)
    @ApiModelProperty(value = "干燥基氢")
    private BigDecimal hd;
	/**空气干燥基氢*/
	@Excel(name = "空气干燥基氢", width = 15)
    @ApiModelProperty(value = "空气干燥基氢")
    private BigDecimal had;
	/**收到基氢*/
	@Excel(name = "收到基氢", width = 15)
    @ApiModelProperty(value = "收到基氢")
    private BigDecimal har;
	/**干燥无灰基氢*/
	@Excel(name = "干燥无灰基氢", width = 15)
    @ApiModelProperty(value = "干燥无灰基氢")
    private BigDecimal hdaf;
	/**空气干燥基碳*/
	@Excel(name = "空气干燥基碳", width = 15)
    @ApiModelProperty(value = "空气干燥基碳")
    private BigDecimal cad;
	/**空气干燥基氮*/
	@Excel(name = "空气干燥基氮", width = 15)
    @ApiModelProperty(value = "空气干燥基氮")
    private BigDecimal nad;
	/**空干基弹筒发热量*/
	@Excel(name = "空干基弹筒发热量", width = 15)
    @ApiModelProperty(value = "空干基弹筒发热量")
    private BigDecimal qbAd;
	/**收到基弹筒发热量*/
	@Excel(name = "收到基弹筒发热量", width = 15)
    @ApiModelProperty(value = "收到基弹筒发热量")
    private BigDecimal qbAr;
	/**干燥基弹筒发热量*/
	@Excel(name = "干燥基弹筒发热量", width = 15)
    @ApiModelProperty(value = "干燥基弹筒发热量")
    private BigDecimal qbD;
	/**干燥无灰基弹筒发热量*/
	@Excel(name = "干燥无灰基弹筒发热量", width = 15)
    @ApiModelProperty(value = "干燥无灰基弹筒发热量")
    private BigDecimal qbDaf;
	/**空干基高位发热量*/
	@Excel(name = "空干基高位发热量", width = 15)
    @ApiModelProperty(value = "空干基高位发热量")
    private BigDecimal qgrAd;
	/**收到基高位发热量*/
	@Excel(name = "收到基高位发热量", width = 15)
    @ApiModelProperty(value = "收到基高位发热量")
    private BigDecimal qgrAr;
	/**干燥基高位发热量*/
	@Excel(name = "干燥基高位发热量", width = 15)
    @ApiModelProperty(value = "干燥基高位发热量")
    private BigDecimal qgrD;
	/**干燥无灰基高位发热量*/
	@Excel(name = "干燥无灰基高位发热量", width = 15)
    @ApiModelProperty(value = "干燥无灰基高位发热量")
    private BigDecimal qgrDaf;
	/**空干基低位发热量*/
	@Excel(name = "空干基低位发热量", width = 15)
    @ApiModelProperty(value = "空干基低位发热量")
    private BigDecimal qnetAd;
	/**收到基低位发热量J*/
	@Excel(name = "收到基低位发热量J", width = 15)
    @ApiModelProperty(value = "收到基低位发热量J")
    private BigDecimal qnetArj;
	/**收到基低位发热量M*/
	@Excel(name = "收到基低位发热量M", width = 15)
    @ApiModelProperty(value = "收到基低位发热量M")
    private BigDecimal qnetArm;
	/**干燥基低位发热量*/
	@Excel(name = "干燥基低位发热量", width = 15)
    @ApiModelProperty(value = "干燥基低位发热量")
    private BigDecimal qnetD;
	/**空干基固定碳*/
	@Excel(name = "空干基固定碳", width = 15)
    @ApiModelProperty(value = "空干基固定碳")
    private BigDecimal fcad;
	/**收到基固定碳*/
	@Excel(name = "收到基固定碳", width = 15)
    @ApiModelProperty(value = "收到基固定碳")
    private BigDecimal fcar;
	/**干燥基固定碳*/
	@Excel(name = "干燥基固定碳", width = 15)
    @ApiModelProperty(value = "干燥基固定碳")
    private BigDecimal fcd;
	/**干燥无灰基固定碳*/
	@Excel(name = "干燥无灰基固定碳", width = 15)
    @ApiModelProperty(value = "干燥无灰基固定碳")
    private BigDecimal fcdaf;
	/**焦渣特性*/
	@Excel(name = "焦渣特性", width = 15)
    @ApiModelProperty(value = "焦渣特性")
    private Integer crc;
	/**变形温度*/
	@Excel(name = "变形温度", width = 15)
    @ApiModelProperty(value = "变形温度")
    private BigDecimal dt;
	/**软化温度*/
	@Excel(name = "软化温度", width = 15)
    @ApiModelProperty(value = "软化温度")
    private BigDecimal st;
	/**半球温度*/
	@Excel(name = "半球温度", width = 15)
    @ApiModelProperty(value = "半球温度")
    private BigDecimal ht;
	/**流动温度*/
	@Excel(name = "流动温度", width = 15)
    @ApiModelProperty(value = "流动温度")
    private BigDecimal ft;
	/**加权类型  1按比例加权  2按吨位加权*/
	@Excel(name = "加权类型  1按比例加权  2按吨位加权", width = 15)
    @ApiModelProperty(value = "加权类型  1按比例加权  2按吨位加权")
    private String proportionType;
	/**加权值*/
	@Excel(name = "加权值", width = 15)
    @ApiModelProperty(value = "加权值")
    private BigDecimal proportion;
	/**加权标识 0未加权 1已加权 9加权后报告*/
	@Excel(name = "加权标识 0未加权 1已加权 9加权后报告", width = 15)
    @ApiModelProperty(value = "加权标识 0未加权 1已加权 9加权后报告")
    private String proportionTag;
	/**0：未审核；1：审核中；2：审核通过；3：审核未通过*/
	@Excel(name = "0：未审核；1：审核中；2：审核通过；3：审核未通过", width = 15)
    @ApiModelProperty(value = "0：未审核；1：审核中；2：审核通过；3：审核未通过")
    private String apprStatus;
	/**建立时间*/
	@Excel(name = "建立时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "建立时间")
    private Date insertTime;
	/**化验日期*/
	@Excel(name = "化验日期", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "化验日期")
    private Date hyDate;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**更新用户*/
	@Excel(name = "更新用户", width = 15)
    @ApiModelProperty(value = "更新用户")
    private String updateCode;
	/**更新时间*/
	@Excel(name = "更新时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private Date jhTime;
	/**更新用户*/
	@Excel(name = "更新用户", width = 15)
    @ApiModelProperty(value = "更新用户")
    private String jhCode;
	/**建立用户*/
	@Excel(name = "建立用户", width = 15)
    @ApiModelProperty(value = "建立用户")
    private String opCode;
	/**opDesc*/
	@Excel(name = "opDesc", width = 15)
    @ApiModelProperty(value = "opDesc")
    private String opDesc;
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
}
