package org.jeecg.modules.temp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @Description: take_sample_rec
 * @Author: jeecg-boot
 * @Date:   2021-03-02
 * @Version: V1.0
 */
@Data
@TableName("take_sample_rec")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="take_sample_rec对象", description="take_sample_rec")
public class TakeSampleRec implements Serializable {
    private static final long serialVersionUID = 1L;

	/**采样记录ID*/
	@Excel(name = "采样记录ID", width = 15)
    @ApiModelProperty(value = "采样记录ID")
    @TableId(value = "take_sample_rec_id",type = IdType.AUTO)
    private Long takeSampleRecId;
	/**入厂流水:如果是车厢采样需要填此字段*/
	@Excel(name = "入厂流水:如果是车厢采样需要填此字段", width = 15)
    @ApiModelProperty(value = "入厂流水:如果是车厢采样需要填此字段")
    private Integer recordNo;
	/**卡号：汽车填RFID扫描编码*/
	@Excel(name = "卡号：汽车填RFID扫描编码", width = 15)
    @ApiModelProperty(value = "卡号：汽车填RFID扫描编码")
    private String cardId;
	/**车牌号/车号:如果是车厢采样需要填此字段*/
	@Excel(name = "车牌号/车号:如果是车厢采样需要填此字段", width = 15)
    @ApiModelProperty(value = "车牌号/车号:如果是车厢采样需要填此字段")
    private String carId;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private String sampleCode;
	/**封装码*/
	@Excel(name = "封装码", width = 15)
    @ApiModelProperty(value = "封装码")
    private String packCode;
	/**离线采样时由采样机生成并填入*/
	@Excel(name = "离线采样时由采样机生成并填入", width = 15)
    @ApiModelProperty(value = "离线采样时由采样机生成并填入")
    private String sampleCdMo;
	/**采样方式 0：皮带采样 1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'*/
	@Excel(name = "采样方式 0：皮带采样 1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'", width = 15)
    @ApiModelProperty(value = "采样方式 0：皮带采样 1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路'")
    private String sampleTyp;
	/**采样开始时间*/
	@Excel(name = "采样开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "采样开始时间")
    private Date startTime;
	/**采样完成时间*/
	@Excel(name = "采样完成时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "采样完成时间")
    private Date endTime;
	/**采样人员*/
	@Excel(name = "采样人员", width = 15)
    @ApiModelProperty(value = "采样人员")
    private String userName;
	/**存放汽车采样点数，火车皮采可以不填*/
	@Excel(name = "存放汽车采样点数，火车皮采可以不填", width = 15)
    @ApiModelProperty(value = "存放汽车采样点数，火车皮采可以不填")
    private Integer sampleCnt;
	/**采样片区 ：汽车采样填值 格式：“1；2；5”*/
	@Excel(name = "采样片区 ：汽车采样填值 格式：“1；2；5”", width = 15)
    @ApiModelProperty(value = "采样片区 ：汽车采样填值 格式：“1；2；5”")
    private String sampleAreas;
	/**采样坐标*/
	@Excel(name = "采样坐标", width = 15)
    @ApiModelProperty(value = "采样坐标")
    private String sampleCordna;
	/**是否空车 0空车 1非空*/
	@Excel(name = "是否空车 0空车 1非空", width = 15)
    @ApiModelProperty(value = "是否空车 0空车 1非空")
    private String emptyFlg;
	/**采样深度 :每节车采样深度*/
	@Excel(name = "采样深度 :每节车采样深度", width = 15)
    @ApiModelProperty(value = "采样深度 :每节车采样深度")
    private String sampleDepth;
	/**集样罐号：字样存储罐号*/
	@Excel(name = "集样罐号：字样存储罐号", width = 15)
    @ApiModelProperty(value = "集样罐号：字样存储罐号")
    private String barrelCode;
	/**采样机号*/
	@Excel(name = "采样机号", width = 15)
    @ApiModelProperty(value = "采样机号")
    private String machineCode;
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
	/**采样班次 01 白班、02中班、03前夜、04后夜(入炉皮带采样需要填此字段)*/
	@Excel(name = "采样班次 01 白班、02中班、03前夜、04后夜(入炉皮带采样需要填此字段)", width = 15)
    @ApiModelProperty(value = "采样班次 01 白班、02中班、03前夜、04后夜(入炉皮带采样需要填此字段)")
    private Integer sampleTeamNo;
	/**采样班采样顺序号 1 第一次采样，2 第二次采样（依次递增）(入炉皮带采样需要填此字段)*/
	@Excel(name = "采样班采样顺序号 1 第一次采样，2 第二次采样（依次递增）(入炉皮带采样需要填此字段)", width = 15)
    @ApiModelProperty(value = "采样班采样顺序号 1 第一次采样，2 第二次采样（依次递增）(入炉皮带采样需要填此字段)")
    private Integer sampleNum;
	/**采样重量*/
	@Excel(name = "采样重量", width = 15)
    @ApiModelProperty(value = "采样重量")
    private BigDecimal sampleQty;
	/**采样缩分比（缩分器动作次数）*/
	@Excel(name = "采样缩分比（缩分器动作次数）", width = 15)
    @ApiModelProperty(value = "采样缩分比（缩分器动作次数）")
    private Integer sampleRat;
	/**采样缩分次数*/
	@Excel(name = "采样缩分次数", width = 15)
    @ApiModelProperty(value = "采样缩分次数")
    private Integer sampleFreq;
	/**采样缩分间隔*/
	@Excel(name = "采样缩分间隔", width = 15)
    @ApiModelProperty(value = "采样缩分间隔")
    private Integer sampleGap;
}
