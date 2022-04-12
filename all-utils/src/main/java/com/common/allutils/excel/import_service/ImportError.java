package com.common.allutils.excel.import_service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2020/8/18
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ImportError implements Serializable {

    private static final long serialVersionUID = 1957679046880021844L;

    private String message;


}
