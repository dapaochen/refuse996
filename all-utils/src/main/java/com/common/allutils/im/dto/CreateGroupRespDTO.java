package com.common.allutils.im.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class CreateGroupRespDTO {
    @JSONField(name = "GroupId")
    private String groupID;
}
