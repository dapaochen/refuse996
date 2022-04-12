package com.common.allutils.im.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class IMVisitRespDTO {

    @JSONField(name = "Msg_info")
    private String info;
    @JSONField(name = "Msg_seq")
    private Integer seq;

}
