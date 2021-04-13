package org.jeecg.modules.common.vo;

import lombok.Data;

/**
 * @program: jeecg-boot-parent
 * @description:
 * @author: Zouzp
 * @create: 2021/3/2 14:49
 **/
@Data
public class BaseEntity {
    public int beginRowIndex;
    public int endRowIndex;
    public String  sort;
    public String order;
    public Integer pageNo;
    public Integer pageSize;

    //pageIndex;第xx页 3
    //pageSize;每页的记录数 3
    public void setPageRowIndex(int pageNo, int pageSize) {
        this.beginRowIndex = (pageNo - 1) * pageSize + 1;
        this.endRowIndex = pageNo * pageSize;
    }

}
