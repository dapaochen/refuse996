package com.common.allutils.excel.import_service;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2020/8/24
 * 实体类验参数
 */
@Component
public class ImportVerifyHandler implements IExcelVerifyHandler<ImportEntity> {
    private final ThreadLocal<List<ImportEntity>> threadLocal = new ThreadLocal<>();


    private List<String> xxxService;

    @Override
    public ExcelVerifyHandlerResult verifyHandler(ImportEntity importEntity) {
        StringJoiner joiner = new StringJoiner(",");

        String phone = importEntity.getPhone();

//         校验 字段存在情况
        if (!xxxService.contains(phone)) {
            joiner.add("手机号重复："+ phone +"不存在，请联系管理员进行添加 ！");
        }

        // 校验excel是否重复
        List<ImportEntity> threadLocalVal = threadLocal.get();
        if (threadLocalVal == null) {
            threadLocalVal = new ArrayList<>();
        }

        threadLocalVal.forEach(e -> {
            int lineNumber = e.getRowNum() + 1;
            // 这里重写了 equals 方法 + hashCode 方法
            if (e.equals(importEntity)) {
                joiner.add("手机号与第" + lineNumber + "行重复");
            }
        });

        threadLocalVal.add(importEntity);
        threadLocal.set(threadLocalVal);


        if (joiner.length() != 0) {
            return new ExcelVerifyHandlerResult(false, joiner.toString());
        }

        return new ExcelVerifyHandlerResult(true);

    }

    public ThreadLocal<List<ImportEntity>> getThreadLocal() {
        return threadLocal;
    }
}
