package com.common.allutils.im.util;

import cn.hutool.core.util.RandomUtil;
import com.common.allutils.im.content.ImUrlConstant;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class IMUtil {

    // 生成随机数的数字范围。。
    public static final String IM_BASE_NUMBER = "123456789";


    /**
     * 随机数生成方法（8位数）
     *
     * @IMRandom() ： 随机数，取值范围0 - 4294967295，剔除0开头的随机数，例如：01111111……
     */
    public static String doIMRandom() {
        return RandomUtil.randomString(IM_BASE_NUMBER, 8);
    }


    /**
     * 拼接 IM HTTP 请求的地址
     *
     * @param sdkAppId   创建应用时即时通信 IM 控制台分配的 SDKAppID
     * @param identifier 管理员帐号
     * @param userSig    管理员账号生成的签名
     * @param random     随机数，取值范围0 - 4294967295  ，使用IMRandom()生成
     * @return
     */
    public static String doImUrl(long sdkAppId, String api,
                                 String identifier, String userSig, String random) {

        StringBuilder imUrl = new StringBuilder();
        Map<String, String> params = new HashMap<>(5);
        params.put(ImUrlConstant.IM_PARAM_KEY_SDKAPPID, String.valueOf(sdkAppId));
        params.put(ImUrlConstant.IM_PARAM_KEY_IDENTIFIER, identifier);
        params.put(ImUrlConstant.IM_PARAM_KEY_USERSIG, userSig.trim());
        params.put(ImUrlConstant.IM_PARAM_KEY_RANDOM, random);
        params.put(ImUrlConstant.IM_PARAM_KEY_CONTENTTYPE, ImUrlConstant.IM_PARAM_VALUE_CONTENTTYPE);
        String queryString = Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(params);

        imUrl.append(ImUrlConstant.IM_BASE_URL)
                .append(api)
                .append("?")
                .append(queryString);
        String address = imUrl.toString();

        //log.info("IM 推送地址 ：{}", address);
        return address;
    }

}
