package com.common.allutils.im.dto;

import lombok.Data;

import java.util.List;


@Data
public class UsersStateMsgRespDTO {
    /**
     * 注册用户的登录状态
     */
    private List<RegisteredUserStatusVO> registeredUserStatuses;

    /**
     * 未注册用户的登录状态
     */
    private List<UnregisteredUserVO> unregisteredUsers;


}
