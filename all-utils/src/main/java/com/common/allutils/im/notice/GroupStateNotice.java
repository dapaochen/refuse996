package com.common.allutils.im.notice;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GroupStateNotice {
    /**
     * 创建群组请求包构建
     *
     * @param groupId 群主名称
     */
    public static String sendGroupNoticeMessage(String groupId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GroupId", groupId);
        return jsonObject.toString();
    }


    /**
     * 创建群组请求包构建
     *
     * @param groupOwner 群主名称
     * @param groupName  群名称
     */
    public static String sendGroupNoticeMessage(String groupOwner, String groupName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Owner_Account", groupOwner);
        jsonObject.put("Type", "ChatRoom");
        jsonObject.put("Name", groupName);
        return jsonObject.toString();
    }


}
