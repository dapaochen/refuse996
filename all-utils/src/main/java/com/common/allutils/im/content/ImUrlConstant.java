package com.common.allutils.im.content;

public interface ImUrlConstant {

    /**
     * 基本URL
     **/
    String IM_BASE_URL = "https://console.tim.qq.com/";

    /**
     * 在 IM 中的 api
     **/
    String IM_GROUP_SYSTEM_API_PATHPARAM = "v4/group_open_http_svc/send_group_system_notification";
    String IM_GROUP_MESSAGE_PATHPARAM = "v4/group_open_http_svc/send_group_msg";
    String QUERYSTATE = "v4/openim/querystate";
    String CREATEGROUP = "v4/group_open_http_svc/create_group";
    String DESTROYGROUP = "v4/group_open_http_svc/destroy_group";
    String DELETEGROUPMEMBER = "v4/group_open_http_svc/delete_group_member";

    /**
     * 请求参数key
     **/
    String IM_PARAM_KEY_SDKAPPID = "sdkappid";
    String IM_PARAM_KEY_IDENTIFIER = "identifier";
    String IM_PARAM_KEY_USERSIG = "usersig";
    String IM_PARAM_KEY_RANDOM = "random";
    String IM_PARAM_KEY_CONTENTTYPE = "contenttype";

    /**
     * 请求参数值
     **/
    String IM_PARAM_VALUE_CONTENTTYPE = "json";


    /**
     * IM 返回参数
     **/
    String IM_FAIL_PARAM = "FAIL";
    String IM_NULL_PARAM = "null";
    String IM_ACTION_STATUS = "ActionStatus";
    // 删除群成员时，当增加该参数，不会给任何人下发通知
    String IM_SILENCE = "1";
}
