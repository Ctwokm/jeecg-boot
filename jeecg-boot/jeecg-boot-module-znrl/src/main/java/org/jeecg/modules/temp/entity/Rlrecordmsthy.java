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
 * @Description: rlrecordmsthy
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Data
@TableName("rlrecordmsthy")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rlrecordmsthy对象", description="rlrecordmsthy")
public class Rlrecordmsthy implements Serializable {
    private static final long serialVersionUID = 1L;

	/**供应商*/
	@Excel(name = "供应商", width = 15)
    @ApiModelProperty(value = "供应商")
    private String venNo;
	/**承运商*/
	@Excel(name = "承运商", width = 15)
    @ApiModelProperty(value = "承运商")
    private String carrierNo;
	/**煤品种*/
	@Excel(name = "煤品种", width = 15)
    @ApiModelProperty(value = "煤品种")
    private String coalNo;
	/**煤矿*/
	@Excel(name = "煤矿", width = 15)
    @ApiModelProperty(value = "煤矿")
    private String colryNo;
	/**粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm*/
	@Excel(name = "粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm", width = 15)
    @ApiModelProperty(value = "粒度 1：13mm≤粒度＜50mm 2：50mm≤粒度＜100mm")
    private String coalSize;
	/**水分 1：湿煤2：一般湿煤3：干煤4：一般干煤*/
	@Excel(name = "水分 1：湿煤2：一般湿煤3：干煤4：一般干煤", width = 15)
    @ApiModelProperty(value = "水分 1：湿煤2：一般湿煤3：干煤4：一般干煤")
    private String coalWater;
	/**票重*/
	@Excel(name = "票重", width = 15)
    @ApiModelProperty(value = "票重")
    private BigDecimal tickQty;
	/**毛重*/
	@Excel(name = "毛重", width = 15)
    @ApiModelProperty(value = "毛重")
    private BigDecimal mzQty;
	/**皮重*/
	@Excel(name = "皮重", width = 15)
    @ApiModelProperty(value = "皮重")
    private BigDecimal pzQty;
	/**净重*/
	@Excel(name = "净重", width = 15)
    @ApiModelProperty(value = "净重")
    private BigDecimal netQty;
	/**运损*/
	@Excel(name = "运损", width = 15)
    @ApiModelProperty(value = "运损")
    private BigDecimal lossQty;
	/**扣吨*/
	@Excel(name = "扣吨", width = 15)
    @ApiModelProperty(value = "扣吨")
    private BigDecimal kdQty;
	/**亏吨*/
	@Excel(name = "亏吨", width = 15)
    @ApiModelProperty(value = "亏吨")
    private BigDecimal kudQty;
	/**盈吨*/
	@Excel(name = "盈吨", width = 15)
    @ApiModelProperty(value = "盈吨")
    private BigDecimal ydQty;
	/**称重时间*/
	@Excel(name = "称重时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "称重时间")
    private Date czDtm;
	/**检轻时间*/
	@Excel(name = "检轻时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "检轻时间")
    private Date jqDtm;
	/**卸煤时间*/
	@Excel(name = "卸煤时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "卸煤时间")
    private Date xmDtm;
	/**速度*/
	@Excel(name = "速度", width = 15)
    @ApiModelProperty(value = "速度")
    private BigDecimal carSpd;
	/**火车来煤批次号*/
	@Excel(name = "火车来煤批次号", width = 15)
    @ApiModelProperty(value = "火车来煤批次号")
    private String trainBatchNo;
	/**采样编码*/
	@Excel(name = "采样编码", width = 15)
    @ApiModelProperty(value = "采样编码")
    private String sampleCode;
	/**采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路*/
	@Excel(name = "采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路", width = 15)
    @ApiModelProperty(value = "采样方式 ：0：皮带采样  1：皮带大水分旁路 2：人工采样 3：车厢采样 4：车厢大水分旁路")
    private String sampleTyp;
	/**是否空车，1：为非空车，0或者null为空车*/
	@Excel(name = "是否空车，1：为非空车，0或者null为空车", width = 15)
    @ApiModelProperty(value = "是否空车，1：为非空车，0或者null为空车")
    private String emptyFlg;
	/**已出厂(0:否、1:是)*/
	@Excel(name = "已出厂(0:否、1:是)", width = 15)
    @ApiModelProperty(value = "已出厂(0:否、1:是)")
    private String leaveFlg;
	/**入厂时间*/
	@Excel(name = "入厂时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "入厂时间")
    private Date recordDtm;
	/**出厂时间*/
	@Excel(name = "出厂时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "出厂时间")
    private Date leaveDtm;
	/**翻车机编号*/
	@Excel(name = "翻车机编号", width = 15)
    @ApiModelProperty(value = "翻车机编号")
    private String fcjCode;
	/**上车单是否确认 0 ：未确认 1：已确认*/
	@Excel(name = "上车单是否确认 0 ：未确认 1：已确认", width = 15)
    @ApiModelProperty(value = "上车单是否确认 0 ：未确认 1：已确认")
    private String cfmFlg;
	/**发站*/
	@Excel(name = "发站", width = 15)
    @ApiModelProperty(value = "发站")
    private String startPlace;
	/**到站*/
	@Excel(name = "到站", width = 15)
    @ApiModelProperty(value = "到站")
    private String finalPlace;
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
	/**备注*/
	@Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
	/**入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ...*/
	@Excel(name = "入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ...", width = 15)
    @ApiModelProperty(value = "入厂轨道号  1号轨道 1#；2号轨道 2#；3号轨道 3# ...")
    private String trackNo;
	/**过衡号，1号衡：1;   2号衡：2;*/
	@Excel(name = "过衡号，1号衡：1;   2号衡：2;", width = 15)
    @ApiModelProperty(value = "过衡号，1号衡：1;   2号衡：2;")
    private String balanceNo;
	/**对位时间*/
	@Excel(name = "对位时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "对位时间")
    private Date aimTime;
	/**附加批次号，如底样面样的*/
	@Excel(name = "附加批次号，如底样面样的", width = 15)
    @ApiModelProperty(value = "附加批次号，如底样面样的")
    private String attachBatchNo;
	/**收货人（配置ID）*/
	@Excel(name = "收货人（配置ID）", width = 15)
    @ApiModelProperty(value = "收货人（配置ID）")
    private String goodsReceiver;
	/**发站时间*/
	@Excel(name = "发站时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "发站时间")
    private Date deliverTime;
	/**合同编码*/
	@Excel(name = "合同编码", width = 15)
    @ApiModelProperty(value = "合同编码")
    private String contractNo;
	/**入厂流水*/
	@Excel(name = "入厂流水", width = 15)
    @ApiModelProperty(value = "入厂流水")
    private Integer recordNo;
	/**大票编号*/
	@Excel(name = "大票编号", width = 15)
    @ApiModelProperty(value = "大票编号")
    private String tickNo;
	/**车次*/
	@Excel(name = "车次", width = 15)
    @ApiModelProperty(value = "车次")
    private String trainNo;
	/**卡号*/
	@Excel(name = "卡号", width = 15)
    @ApiModelProperty(value = "卡号")
    private String cardId;
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
    @ApiModelProperty(value = "车牌号")
    private String carId;
	/**火车车型*/
	@Excel(name = "火车车型", width = 15)
    @ApiModelProperty(value = "火车车型")
    private String carTyp;
	/**车厢序号*/
	@Excel(name = "车厢序号", width = 15)
    @ApiModelProperty(value = "车厢序号")
    private BigDecimal xhNum;
}
