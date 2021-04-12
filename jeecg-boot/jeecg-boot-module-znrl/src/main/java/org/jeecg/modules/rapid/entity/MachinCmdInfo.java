package org.jeecg.modules.rapid.entity;

import java.io.Serializable;
import java.util.Date;

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
 * @Description: machin_cmd_info
 * @Author: jeecg-boot
 * @Date:   2021-02-24
 * @Version: V1.0
 */
@Data
@TableName("machin_cmd_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="machin_cmd_info对象", description="machin_cmd_info")
public class MachinCmdInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**控制信息记录ID*/
	@Excel(name = "控制信息记录ID", width = 15)
    @ApiModelProperty(value = "控制信息记录ID")
    private Integer cmdRecId;
	/**machinCode*/
	@Excel(name = "machinCode", width = 15)
    @ApiModelProperty(value = "machinCode")
    private String machinCode;
	/**设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 */
	@Excel(name = "设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 ", width = 15)
    @ApiModelProperty(value = "设备类型 1:	汽车车厢采样机 2	:	制样机 3	:	翻车机 4:		存查样柜 5:		调度转发用 6	:	火车车厢采样机 7:		入厂皮带采样机 8	:	入炉皮带采样机 ")
    private String machinType;
	/**命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路*/
	@Excel(name = "命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路", width = 15)
    @ApiModelProperty(value = "命令代码 1：启动 2：停止 3：继续工作（采样、制样）4.卸样 5.打包 6.大水分旁路")
    private String commandCode;
	/**采样编码(采样机命令)；制样编码（制样机命令）*/
	@Excel(name = "采样编码(采样机命令)；制样编码（制样机命令）", width = 15)
    @ApiModelProperty(value = "采样编码(采样机命令)；制样编码（制样机命令）")
    private String sampleCode;
	/**命令发送时间*/
	@Excel(name = "命令发送时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "命令发送时间")
    private Date sendCmdTime;
	/**数据状态 0: 未读取 1: 已读取*/
	@Excel(name = "数据状态 0: 未读取 1: 已读取", width = 15)
    @ApiModelProperty(value = "数据状态 0: 未读取 1: 已读取")
    private String dataStatus;
	/**入厂流水*/
	@Excel(name = "入厂流水", width = 15)
    @ApiModelProperty(value = "入厂流水")
    private Integer recordNo;
	/**车号：汽车采样时需要填此字段给采样机*/
	@Excel(name = "车号：汽车采样时需要填此字段给采样机", width = 15)
    @ApiModelProperty(value = "车号：汽车采样时需要填此字段给采样机")
    private String carId;
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
}
