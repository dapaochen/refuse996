package com.common.allutils.im.notice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.allutils.im.content.ImMessageConstant;
import com.common.allutils.im.enums.ImMessageTypeEnum;
import com.common.allutils.im.content.ImUrlConstant;
import com.common.allutils.im.util.IMUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 群组自定义消息请求包构建（自定义消息，比如：红包、轮盘）
 *
 */
@Slf4j
public class GroupMsgNotice {


    /**
     * 发送系统通知
     *
     * @param groupId
     * @param content
     * @return
     */
    public static String sendGroupNoticeMessage(String groupId, String content) {
        HashMap<String, String> map = new HashMap<String, String>(3);
        map.put("GroupId", groupId);
        map.put("Content", content);
        return JSON.toJSONString(map);
    }


    /**
     * 管理员发送自定义消息请求包
     *
     * @param groupId
     * @param content
     * @return 消息格式
     * {
     * "GroupId": "@TGS#2C5SZEAEF",
     * "Random": 8912345, // 随机数字，五分钟数字相同认为是重复消息
     * "MsgBody": [ // 消息体，由一个 element 数组组成，详见字段说明
     * {
     * "MsgType": "TIMCustomElem",
     * "MsgContent": {
     * "Data": "message",
     * "Desc": "notification",
     * "Ext": "url",
     * "Sound": "dingdong.aiff"
     * …………………………………………
     */
    public static String sendCustomizeMessage(String groupId, ImMessageTypeEnum type, Object content, String randomDieout) {

        if (ImUrlConstant.IM_NULL_PARAM.equals(randomDieout)) {
            randomDieout = IMUtil.doIMRandom();
        }

        String contentString = JSONObject.toJSONString(content);


        //第一层构建：GroupId、Random、MsgBody
        JSONObject magHead = new JSONObject();
        magHead.put("GroupId", groupId);
        magHead.put("Random", randomDieout);

        //第二层构建：MsgType、Random
        JSONObject msgBody = new JSONObject();
        msgBody.put("MsgType", ImMessageConstant.CustomizeMessage);

        //第三层构建：Data、Desc、Ext、Sound
        JSONObject msgCon = new JSONObject();
        msgCon.put("Data", type);
        msgCon.put("Desc", contentString);
        msgCon.put("Ext", "Ineffective");
        msgCon.put("Sound", "Ineffective");
        // 第三层构建结束

        msgBody.put("MsgContent", msgCon);
        // 第二层构建结束

        JSONArray msgBodyArr = new JSONArray();
        msgBodyArr.add(msgBody);
        magHead.put("MsgBody", msgBodyArr);

        return magHead.toString();


    }

    /**
     * 普通用户发送消息请求包
     *
     * @param groupId
     * @return 消息格式
     * {
     * "GroupId": "@TGS#2C5SZEAEF",
     * "Random": 8912345, // 随机数字，五分钟数字相同认为是重复消息
     * "MsgBody": [ // 消息体，由一个 element 数组组成，详见字段说明
     * {
     * "MsgType": "TIMCustomElem",
     * "MsgContent": {
     * "Data": "message",
     * "Desc": "notification",
     * "Ext": "url",
     * "Sound": "dingdong.aiff"
     * …………………………………………
     */
    public static String sendUserMessage(String groupId, String userCode,
                                         String message, String randomDieout) {
        if (ImUrlConstant.IM_NULL_PARAM.equals(randomDieout)) {
            randomDieout = IMUtil.doIMRandom();
        }
        //第一层构建：GroupId、Random、MsgBody
        JSONObject magHead = new JSONObject();
        magHead.put("GroupId", groupId);
        magHead.put("Random", randomDieout);
        magHead.put("From_Account", userCode);

        //第二层构建：MsgType、Random
        JSONObject msgBody = new JSONObject();
        msgBody.put("MsgType", ImMessageConstant.TextMessage);

        //第三层构建：Data、Desc、Ext、Sound
        JSONObject msgCon = new JSONObject();
        msgCon.put("Text", message);
        // 第三层构建结束

        msgBody.put("MsgContent", msgCon);
        // 第二层构建结束

        JSONArray msgBodyArr = new JSONArray();
        msgBodyArr.add(msgBody);
        magHead.put("MsgBody", msgBodyArr);

        return magHead.toString();

    }


}
