package com.common.allutils.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.allutils.im.content.ImUrlConstant;
import com.common.allutils.im.dto.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class IMReplyInfo {

    /**
     * 构建 发送消息的 返回信息
     *
     * @param imUrl
     * @param json
     * @return
     */
    public static IMVisitRespDTO getSendMessageInfo(String imUrl, String json) {
        String resultString = IMHttpVisit.doPostString(imUrl, json);
        JSONObject resultJson = JSON.parseObject(resultString);
        IMVisitRespDTO imVisitRespDTO = new IMVisitRespDTO();

        if (ImUrlConstant.IM_FAIL_PARAM.equals(resultJson.get(ImUrlConstant.IM_ACTION_STATUS))) {
            log.error("发送消息IM发送异常 resultJson：{}", resultString);
            //这里的返回code，调整为IM的返回 code和message
            throw new IllegalArgumentException(resultJson.get("ErrorCode") + "  " + resultJson.get("ErrorInfo"));
        }

        imVisitRespDTO.setInfo((String) resultJson.get("ErrorInfo"));
        imVisitRespDTO.setSeq((Integer) resultJson.get("MsgSeq"));

        return imVisitRespDTO;
    }

    /**
     * 构建 创建群组的 返回信息
     *
     * @param imUrl
     * @param json
     * @return
     */
    public static CreateGroupRespDTO getCreateGroup(String imUrl, String json) {
        String resultString = IMHttpVisit.doPostString(imUrl, json);

        JSONObject resultJson = JSON.parseObject(resultString);


        if (ImUrlConstant.IM_FAIL_PARAM.equals(resultJson.get(ImUrlConstant.IM_ACTION_STATUS))) {
            log.error("创建群组IM发送异常 resultJson：{}", resultString);
            //这里的返回code，调整为IM的返回 code和message
            throw new IllegalArgumentException(resultJson.get("ErrorCode") + "  " + resultJson.get("ErrorInfo"));
        }
        CreateGroupRespDTO createGroupRespDTO = new CreateGroupRespDTO();
        createGroupRespDTO.setGroupID((String) resultJson.get("GroupId"));

        return createGroupRespDTO;
    }

    /**
     * 构建 删除群组的 返回信息
     *
     * @param imUrl
     * @param json
     * @return
     */
    public static Boolean getDestroyGroup(String imUrl, String json) {
        String resultString = IMHttpVisit.doPostString(imUrl, json);

        JSONObject resultJson = JSON.parseObject(resultString);


        if (ImUrlConstant.IM_FAIL_PARAM.equals(resultJson.get(ImUrlConstant.IM_ACTION_STATUS))) {
            log.error("删除群组IM发送异常 resultJson：{}", resultString);
            //这里的返回code，调整为IM的返回 code和message
            throw new IllegalArgumentException(resultJson.get("ErrorCode") + "  " + resultJson.get("ErrorInfo"));
        }

        return true;
    }


    /**
     * 构建 删除群成员 返回信息
     *
     * @param imUrl
     * @param json
     * @return
     */
    public static Boolean getDeleteGroupMember(String imUrl, String json) {
        String resultString = IMHttpVisit.doPostString(imUrl, json);

        JSONObject resultJson = JSON.parseObject(resultString);


        if (ImUrlConstant.IM_FAIL_PARAM.equals(resultJson.get(ImUrlConstant.IM_ACTION_STATUS))) {
            log.error("删除群成员IM发送异常 resultJson：{}", resultString);
            //这里的返回code，调整为IM的返回 code和message
            throw new IllegalArgumentException(resultJson.get("ErrorCode") + "  " + resultJson.get("ErrorInfo"));
        }

        return true;
    }


    /**
     * 构建 请求用户状态 的返回信息
     *
     * @param imUrl
     * @param json
     * @return
     */
    public static UsersStateMsgRespDTO getUsersStateMsgResp(String imUrl, String json) {
        String resultString = IMHttpVisit.doPostString(imUrl, json);

        JSONObject resultJson = JSON.parseObject(resultString);

        if (ImUrlConstant.IM_FAIL_PARAM.equals(resultJson.get(ImUrlConstant.IM_ACTION_STATUS))) {
            log.error("直播用户查询在线状态IM发送异常 resultJson：{}", resultString);
            //这里的返回code，调整为IM的返回 code和message
            throw new IllegalArgumentException(resultJson.get("ErrorCode") + "  " + resultJson.get("ErrorInfo"));
        }

        UsersStateMsgRespDTO usersStateMsgRespDTO = new UsersStateMsgRespDTO();
        List<RegisteredUserStatusVO> res = new ArrayList<>();
        JSONArray queryResult = resultJson.getJSONArray("QueryResult");
        if (queryResult.size() > 0) {
            for (int j = 0; j < queryResult.size(); j++) {
                RegisteredUserStatusVO registeredUserStatus = new RegisteredUserStatusVO();
                String status = queryResult.getJSONObject(j).getString("Status");
                String toAccount = queryResult.getJSONObject(j).getString("To_Account");
                registeredUserStatus.setStatus(status);
                registeredUserStatus.setUserCode(toAccount);
                res.add(registeredUserStatus);
            }
            usersStateMsgRespDTO.setRegisteredUserStatuses(res);
        }

        JSONArray errorList = resultJson.getJSONArray("ErrorList");
        if (errorList == null) {
            return usersStateMsgRespDTO;
        }
        List<UnregisteredUserVO> unRes = new ArrayList<>();
        for (int j = 0; j < errorList.size(); j++) {
            UnregisteredUserVO unregisteredUserVO = new UnregisteredUserVO();
            String toAccount = errorList.getJSONObject(j).getString("To_Account");
            String errorCode = errorList.getJSONObject(j).getString("ErrorCode");
            unregisteredUserVO.setErrorCode(errorCode);
            unregisteredUserVO.setUserCode(toAccount);
            unRes.add(unregisteredUserVO);
        }

        usersStateMsgRespDTO.setUnregisteredUsers(unRes);
        return usersStateMsgRespDTO;
    }


}
