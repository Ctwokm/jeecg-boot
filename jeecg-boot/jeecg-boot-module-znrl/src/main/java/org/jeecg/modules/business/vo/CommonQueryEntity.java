package org.jeecg.modules.business.vo;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: Hujw
 * @Date: 2021/3/2
 * @Description: 调用存储过程专用封装
 */
@Data
public class CommonQueryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * sql语句的id
     */
    @ApiParam("sql语句的id")
    private String sqlId;
    /**
     * 是否分页,"0"为分页，空值或其他任意值为不分页
     */
    @ApiParam("是否分页,\"0\"为分页，空值或其他任意值为不分页")
    private String isPage;
    /**
     * 分页参数，页码
     */
    @ApiParam("分页参数，页码")
    private Long PageNo;
    /**
     * 分页参数，当页显示数量
     */
    @ApiParam("分页参数，当页显示数量")
    private Long PageSize;
    /**
     * 通用入参 json字符串
     */
    @ApiParam("通用入参 json字符串")
    private String jsonString;
    /**
     * 当前操作用户
     */
    @ApiParam("当前操作用户")
    private String opCode;
    /**
     * 出参 返回业务信息
     */
    @ApiParam("出参 返回业务信息")
    private String resInfo;
    /**
     * 出参 返回代码
     */
    @ApiParam("出参 返回代码")
    private String resCode;
    /**
     * 出参 返回消息
     */
    @ApiParam("出参 返回消息")
    private String resMsg;

}
