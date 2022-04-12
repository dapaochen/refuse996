package com.common.allutils.excel_util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2020/10/16
 */
@Slf4j
public class ExcelUtil {


    /**
     * excel2003扩展名
     */
    public static final String EXCEL03_EXTENSION = ".xls";
    /**
     * excel2007扩展名
     */
    public static final String EXCEL07_EXTENSION = ".xlsx";

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response){

        //判断导出数据是否为空
        if (list == null) {
            list = new ArrayList<>();
        }

        //获取导出参数
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setHeight((short) 15);

        // 处理 excel 版本文件 , 默认 07 版本
        if (fileName.endsWith(EXCEL03_EXTENSION)) {
            exportParams.setType(ExcelType.HSSF);
        }
        else if (fileName.endsWith(EXCEL07_EXTENSION)) {
            exportParams.setType(ExcelType.XSSF);
        }

        // 设置导出样式
        exportParams.setStyle(ExcelStyleUtil.class);

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (ObjectUtil.isNotNull(workbook)) {
            doDownLoadExcel(fileName, response, workbook);
        }

    }

    private static void doDownLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try (OutputStream out = response.getOutputStream(); Workbook book = workbook) {

            //给文件名拼接上日期
            StringBuffer strBuf = new StringBuffer(fileName);
            int index = strBuf.indexOf(".");
            fileName = strBuf.insert(index, StrUtil.UNDERLINE + DateUtil.today()).toString();

            // 获取文件名并转码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            // 设置 编码 格式
            response.setCharacterEncoding("UTF-8");
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            // 输出表格
            book.write(out);

        } catch (IOException e) {
            log.error("[monitor][IO][表单功能],{}", e.getMessage());
        }
    }

}
