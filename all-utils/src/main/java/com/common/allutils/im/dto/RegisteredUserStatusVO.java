package com.common.allutils.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class RegisteredUserStatusVO {
    @JSONField(name = "To_Account")
    private String userCode;

    @JSONField(name = "Status")
    private String status;
}
