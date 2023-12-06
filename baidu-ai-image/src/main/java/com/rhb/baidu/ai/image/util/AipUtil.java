package com.rhb.baidu.ai.image.util;

import com.baidu.aip.ocr.AipOcr;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author: rhb
 * @date: 2023/12/6 9:18
 * @description:
 */
@Slf4j
public class AipUtil {

    private static final String APP_ID = "44410705";
    private static final String API_KEY = "0VDrf7oNoR5npPYrPsrd1isQ";
    private static final String SECRET_KEY = "HEGufwy9FKT9fRXQEbDbQI3QCULTGLaH";

    public static JSONObject imgToText(String path) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        log.info(res.toString());
        return res;
    }

}
