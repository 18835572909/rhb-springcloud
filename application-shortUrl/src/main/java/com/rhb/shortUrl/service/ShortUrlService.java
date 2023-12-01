package com.rhb.shortUrl.service;

import cn.hutool.json.JSONUtil;
import com.google.common.cache.Cache;
import com.rhb.shortUrl.common.LocalCache;
import com.rhb.shortUrl.common.SysType;
import com.rhb.shortUrl.pojo.ShortUrl;
import com.rhb.shortUrl.util.Base62Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: rhb
 * @date: 2023/11/29 19:03
 * @description:
 */
@Slf4j
@Service
public class ShortUrlService {

    public String encode(String longUrl) {
        long currentTimeMillis = System.currentTimeMillis();
        ShortUrl shortUrl = ShortUrl.builder()
                .id(currentTimeMillis)
                .longUrl(longUrl)
                .shortUrl(Base62Utils.idToShortKey(currentTimeMillis))
                .type(SysType.ORDER.name())
                .build();
        Cache cache = LocalCache.NO_LOADING.getCache();
        cache.put(currentTimeMillis,JSONUtil.toJsonStr(shortUrl));
        return shortUrl.getShortUrl();
    }

    public String decode(String shortKey) {
        long id = Base62Utils.shortKeyToId(shortKey);
        Cache cache = LocalCache.NO_LOADING.getCache();
        ShortUrl shortUrl = JSONUtil.toBean((String) cache.getIfPresent(id), ShortUrl.class);
        log.info("shortUrl:{}",shortUrl);
        return shortUrl.getLongUrl();
    }

}
