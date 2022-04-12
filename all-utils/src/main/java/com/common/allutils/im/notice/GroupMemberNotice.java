package com.common.allutils.im.notice;


import com.alibaba.fastjson.JSONObject;
import com.common.allutils.im.content.ImUrlConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.List;



@Slf4j
public class GroupMemberNotice {

    /**
     * 删除群成员
     *
     * @param groupId
     * @param users
     * @return
     */
    public static String delMemberNotice(String groupId, List<String> users) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("GroupId", groupId);
        jsonObject.put("Silence", ImUrlConstant.IM_SILENCE);
        jsonObject.put("MemberToDel_Account", users);
        return jsonObject.toString();
    }

    /**
     * 用户登录状态消息体
     *
     * @return String
     */
    public static String userStateNotice(List<String> users) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("To_Account", users);
        return jsonObject.toString();
    }
}
