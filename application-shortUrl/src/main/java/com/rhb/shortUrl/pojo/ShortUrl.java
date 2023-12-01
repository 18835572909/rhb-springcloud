package com.rhb.shortUrl.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author: rhb
 * @date: 2023/12/1 10:25
 * @description:
 */
@Data
@Builder
public class ShortUrl {
    private Long id;
    // 业务类型
    private String type;
    // 原始url
    private String longUrl;
    // 短连接url
    private String shortUrl;
}
