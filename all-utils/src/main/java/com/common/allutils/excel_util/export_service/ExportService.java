package com.common.allutils.excel_util.export_service;

import com.common.allutils.excel_util.ExcelUtil;
import com.common.allutils.excel_util.import_service.ImportEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2022/4/12
 */
public class ExportService {

    public String exportOrderProcessExcel(HttpServletResponse response) {

        // 导出的数据
        List<String> arrList = new ArrayList<>();

        ExcelUtil.exportExcel(arrList, "业务流程表", "业务流程表",
                ImportEntity.class, "业务流程表.xlsx", response);
        return "导出成功";
    }
}
