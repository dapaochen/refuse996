package com.common.allutils.excel.import_service;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2020/10/13
 *
 * 实体类
 *
 * */
@Data
public class ImportEntity implements IExcelDataModel, IExcelModel, Serializable {

    private static final long serialVersionUID = 8784558846716472888L;

    /**
     * 行号
     */
    private Integer rowNum;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 联系方式
     */
    @Excel(name = "联系方式")
    private String phone;


    @Override
    public void setRowNum(Integer integer) {
        this.rowNum=integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ImportEntity that = (ImportEntity) o;
        return Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone);
    }

}
