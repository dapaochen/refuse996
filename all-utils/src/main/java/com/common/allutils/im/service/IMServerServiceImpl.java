package com.common.allutils.im.service;


import com.common.allutils.im.IMReplyInfo;
import com.common.allutils.im.content.*;
import com.common.allutils.im.dto.CreateGroupRespDTO;
import com.common.allutils.im.dto.IMVisitRespDTO;
import com.common.allutils.im.dto.UserSigReqDTO;
import com.common.allutils.im.dto.UsersStateMsgRespDTO;
import com.common.allutils.im.enums.ImMessageTypeEnum;
import com.common.allutils.im.notice.GroupMemberNotice;
import com.common.allutils.im.notice.GroupMsgNotice;
import com.common.allutils.im.notice.GroupStateNotice;
import com.common.allutils.im.util.IMUtil;
import com.tencentyun.TLSSigAPIv2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class IMServerServiceImpl {

    /**
     * 即时通信IM 控制台的appidID
     */
    @Value("${im.sdk-app-id}")
    private long sdkAppId;

    /**
     * 即时通信IM 管理员账号
     */
    @Value("${im.identifier}")
    private String identifier;

    /**
     * 即时通信IM UserSig 的有效时长 （86400 为一天）
     */
    @Value("${im.key}")
    private String key;

    /**
     * 即时通信IM 控制台的秘钥
     */
    @Value("${im.expire}")
    private Integer expire;


    private String getAdminUserSig(String userCode) {
        TLSSigAPIv2 sigApi = new TLSSigAPIv2(sdkAppId, key);
        return sigApi.genSig(userCode, expire);

    }

    public String getUserSig(UserSigReqDTO userCode) {
        TLSSigAPIv2 api = new TLSSigAPIv2(sdkAppId, key);
        return api.genSig(userCode.getUserCode(), expire);

    }

    public long getAppConfig() {
        return sdkAppId;
    }

    public String getIdentifier() {
        return identifier;
    }


    public IMVisitRespDTO sendGroupNotice(String groupId, String message) {

        String userSig = getAdminUserSig(identifier).trim();

        if (groupId == null) {
            log.info("该直播间查找不到，系统消息是：{}",message);
            throw new IllegalArgumentException("该直播间查找不到，系统消息是：" + message);
        }

        // 获取 contentType  消息体 的json 格式
        String json = GroupMsgNotice.sendGroupNoticeMessage(groupId, message);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.IM_GROUP_SYSTEM_API_PATHPARAM, identifier, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getSendMessageInfo(imUrl, json);

    }


    public IMVisitRespDTO sendCustomizeMessage(String groupId, String userCode,
                                               ImMessageTypeEnum type, Object o, String randomDieout) {


        //HTTP 请求的连接userSig必须为管理员
        String userSig = getAdminUserSig(identifier).trim();

        // 获取 contentType  消息体 的json 格式
        String json = GroupMsgNotice.sendCustomizeMessage(groupId, type, o, randomDieout);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.IM_GROUP_MESSAGE_PATHPARAM, userCode, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getSendMessageInfo(imUrl, json);

    }

    public IMVisitRespDTO sendUserMessage(String groupId, String userCode, String message, String randomDieout) {

        String userSig = getAdminUserSig(identifier).trim();

        // 获取 contentType  消息体 的json 格式
        String json = GroupMsgNotice.sendUserMessage(groupId, userCode, message, randomDieout);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.IM_GROUP_MESSAGE_PATHPARAM, identifier, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getSendMessageInfo(imUrl, json);

    }

    public UsersStateMsgRespDTO queryUsersState(List<String> users) {
        String userSig = getAdminUserSig(identifier).trim();

        // 获取 contentType  消息体 的json 格式
        String json = GroupMemberNotice.userStateNotice(users);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.QUERYSTATE, identifier, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getUsersStateMsgResp(imUrl, json);
    }

    public CreateGroupRespDTO createGroup(String groupOwner, String groupName) {

        String userSig = getAdminUserSig(identifier).trim();

        // 获取 contentType  消息体 的json 格式
        String json = GroupStateNotice.sendGroupNoticeMessage(groupOwner, groupName);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.CREATEGROUP, identifier, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getCreateGroup(imUrl, json);
    }

    public boolean destroyGroup(String groupId) {

        String userSig = getAdminUserSig(identifier).trim();

        // 获取 contentType  消息体 的json 格式
        String json = GroupStateNotice.sendGroupNoticeMessage(groupId);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.DESTROYGROUP, identifier, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getDestroyGroup(imUrl, json);

    }

    public boolean deleteGroupMember(String groupId, List<String> users) {

        String userSig = getAdminUserSig(identifier).trim();

        // 获取 contentType  消息体 的json 格式
        String json = GroupMemberNotice.delMemberNotice(groupId, users);

        // 获取随机数（8位数）:取值范围0 - 4294967295
        String random = IMUtil.doIMRandom();

        // 拼接 IM HTTP 请求的地址
        String imUrl = IMUtil.doImUrl(sdkAppId,
                ImUrlConstant.DELETEGROUPMEMBER, identifier, userSig, random);

        // 进行 IM HTTP 调用
        return IMReplyInfo.getDeleteGroupMember(imUrl, json);

    }

}

