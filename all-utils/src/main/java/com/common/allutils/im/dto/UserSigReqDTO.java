package com.common.allutils.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: 陈仁鑫
 * @Date: Create in  2020/7/7
 */
@Data
public class UserSigReqDTO {

    @JSONField(name = "user_code")
    private String userCode;
}
