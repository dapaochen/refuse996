package com.common.allutils.im.enums;


public enum ImMessageTypeEnum {
    /**
     * IM自定义消息类型
     */
    TEXT(1, "text", "文本"),
    IMAGE(2, "image", "图片"),

    ;
    Integer id;
    String code;
    String name;

    ImMessageTypeEnum(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }



}
