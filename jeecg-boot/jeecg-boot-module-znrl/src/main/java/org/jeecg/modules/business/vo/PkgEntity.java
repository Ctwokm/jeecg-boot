package org.jeecg.modules.business.vo;

import lombok.Data;

import java.io.Serializable;
/**
 * @Auther: Hujw
 * @Date: 2021/3/2
 * @Description: 调用存储过程专用封装
 */
@Data
public class PkgEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 包和方法名称
     */
    private String pkgName;
    /**
     * 通用入参 json字符串
     */
    private String jsonString;
    /**
     * 当前操作用户
     */
    private String opCode;
    /**
     * 出参 返回业务信息
     */
    private String resInfo;
    /**
     * 出参 返回代码
     */
    private String resCode;
    /**
     * 出参 返回消息
     */
    private String resMsg;

}
