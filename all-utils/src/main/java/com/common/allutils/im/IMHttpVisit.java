package com.common.allutils.im;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


@Slf4j
public class IMHttpVisit {

    /**
     * IM 的 HTTP 调用方法
     *
     * @param address
     * @param json
     * @return
     */
    public static String doPostString(String address, String json) {
        return doPost(address, json);
    }

    /**
     * 调用 IM 业务逻辑
     *
     * @param address URL
     * @param json    请求消息包
     * @return
     */
    private static String doPost(String address, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(address);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(5000).build();
            httpPost.setConfig(requestConfig);

            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            log.error("HTTP关闭失败: {}",e.getMessage());
            throw new IllegalArgumentException("HTTP执行失败，请重试！");
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error("HTTP关闭失败: {}",e.getMessage());
                throw new IllegalArgumentException("HTTP关闭失败，请重试！");
            }
        }
        return resultString;
    }

}
