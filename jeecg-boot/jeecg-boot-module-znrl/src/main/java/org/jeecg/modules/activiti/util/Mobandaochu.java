package org.jeecg.modules.activiti.util;

import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 模板导出
 */
public class Mobandaochu {

    public static TemplateExportParams getTemplateParams(String name){
        return new TemplateExportParams("doc/"+name+".xlsx");
    }

    public static Workbook putong() {
        TemplateExportParams params = getTemplateParams("trainDemo");
//        params.setSheetNum(1);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "员工个人信息");
        map.put("name", "大熊");
        map.put("date", "2020-07-13");
        map.put("age", 22);
        map.put("company", "北京机器猫科技有限公司");
        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        return workbook;

    }

    public static void main(String[] args) throws IOException {
        Workbook workbook = putong();
        File savefile = new File("D:\\poi");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream("D:\\poi\\result.xlsx");
        workbook.write(fos);
        fos.close();
    }

}
