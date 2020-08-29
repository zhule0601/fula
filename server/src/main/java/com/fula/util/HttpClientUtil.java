package com.fula.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @param
 * @author zl
 * @description http 连接池
 * @date 2020/8/28 23:03
 * @return
 */
public class HttpClientUtil {

    public static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 全局连接池对象
     */
    private static final PoolingHttpClientConnectionManager CONN_MANAGER = new PoolingHttpClientConnectionManager();
    public static final int CONNECT_REQUEST_TIMEOUT_MS = 5000;
    public static final int CONNECT_TIMEOUT_MS = 10000;
    public static final int SOCKET_TIMEOUT_MS = 10000;

    static {
        // 设置最大连接数
        CONN_MANAGER.setMaxTotal(200);
        // 设置每个连接的路由数
        CONN_MANAGER.setDefaultMaxPerRoute(20);
    }

    /**
     * 获取Http客户端连接对象
     *
     * @return Http客户端连接对象
     */
    private static CloseableHttpClient getHttpClient(int connectRequestTimeoutMs, int connectTimeoutMs, int socketTimeoutMs) {
        // 创建Http请求配置参数
        RequestConfig requestConfig = RequestConfig.custom()
                // 获取连接超时时间
                .setConnectionRequestTimeout(connectRequestTimeoutMs)
                // 请求超时时间
                .setConnectTimeout(connectTimeoutMs)
                // 响应超时时间
                .setSocketTimeout(socketTimeoutMs)
                .build();
        // 创建httpClient
        return HttpClients.custom()
                // 把请求相关的超时信息设置到连接客户端
                .setDefaultRequestConfig(requestConfig)
                // 配置连接池管理对象
                .setConnectionManager(CONN_MANAGER)
                .build();
    }

    public static String executeString(HttpRequestBase request){
        CloseableHttpClient client = getHttpClient(CONNECT_REQUEST_TIMEOUT_MS, CONNECT_TIMEOUT_MS, SOCKET_TIMEOUT_MS);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = null;
        long startTime = System.currentTimeMillis();
        try {
            response = client.execute(request);
            entity = response.getEntity();
            result = EntityUtils.toString(entity);
            logger.info("http call finished. total cost: {}ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.info("http call failed. total cost: {}ms", System.currentTimeMillis() - startTime);
            logger.error("http call error.", e);
        }
        return result;
    }

    public static String executeString(HttpRequestBase request, int connectRequestTimeoutMs, int connectTimeoutMs, int socketTimeoutMs) {
        CloseableHttpClient client = getHttpClient(connectRequestTimeoutMs, connectTimeoutMs, socketTimeoutMs);
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String result = null;
        long startTime = System.currentTimeMillis();
        try {
            response = client.execute(request);
            entity = response.getEntity();
            result = EntityUtils.toString(entity);
            logger.info("http call finished. method: {}, URI: {}, total cost: {}ms",
                    request.getMethod(), request.getURI(), System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            logger.info("http call failed. method: {}, URI: {}, total cost: {}ms",
                    request.getMethod(), request.getURI(), System.currentTimeMillis() - startTime);
            logger.error("http call error.", e);
        }
        return result;
    }

}
