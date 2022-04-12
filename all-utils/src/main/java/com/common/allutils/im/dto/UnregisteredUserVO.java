package com.common.allutils.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class UnregisteredUserVO {

    @JSONField(name = "To_Account")
    private String userCode;

    @JSONField(name = "ErrorCode")
    private String errorCode;
}
