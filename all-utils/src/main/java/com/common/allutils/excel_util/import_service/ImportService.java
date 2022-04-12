package com.common.allutils.excel_util.import_service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.alibaba.fastjson.JSONObject;
import com.common.allutils.excel_util.ExcelConfig;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2022/4/12
 */
public class ImportService {

    @Resource
    ImportVerifyHandler verifyHandler;

    public void importExcelPublic(MultipartFile multipartFile) {
        ExcelImportResult<ImportEntity> result;


        try {

            ImportParams params = new ImportParams();
            //设置行头，默认 第一行 是行头
            params.setHeadRows(ExcelConfig.HEAD_PARAM);
            // 开启 EXCEL 校验功能
            params.setNeedVerify(true);
            params.setVerifyHandler(verifyHandler);

            result = ExcelImportUtil.importExcelMore(multipartFile.getInputStream(), ImportEntity.class, params);

        } catch (Exception e) {
            throw new IllegalArgumentException("Excel模板出错");
        } finally {
            // 清除threadLocal 防止内存泄漏
            ThreadLocal<List<ImportEntity>> threadLocal = verifyHandler.getThreadLocal();
            if (threadLocal != null) {
                threadLocal.remove();
            }
        }
        // 如果 excel 存在错误，则进行返回
        importExcelError(result);

        // 数据入库
        importExcelToDB(result);
    }

    private void importExcelToDB(ExcelImportResult<ImportEntity> result) {

    }

    private void importExcelError(ExcelImportResult<ImportEntity> result) {
        if (result.isVerifyFail()) {

            List<ImportError> list = new ArrayList<>();
            for (ImportEntity entity : result.getFailList()) {
                int line = entity.getRowNum() + 1;
                ImportError errorMessage = ImportError.builder()
                        .message("第" + line + "行的错误是：" + entity.getErrorMsg())
                        .build();

                list.add(errorMessage);
            }
            String message = "导入失败，行数为：" + JSONObject.toJSONString(list) ;
            throw new IllegalArgumentException(message);
        }
    }
}
