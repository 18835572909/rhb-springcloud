package com.rhb.shortUrl.common;

import com.google.common.cache.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: rhb
 * @date: 2023/11/29 19:04
 * @description:
 */
@Slf4j
@Getter
@AllArgsConstructor
public enum LocalCache {

    NO_LOADING(noLoadingCache()),
    LOADING(loadingCache());

    private Cache cache;

    private static Map<Integer,String> initCache = new HashMap<>(4);

    static {
        initCache.put(1,"v1");
        initCache.put(2,"v2");
        initCache.put(3,"v3");
        initCache.put(4,"v4");
    }

    private static CacheBuilder<Object, Object> getCacheBuilder(){
        return CacheBuilder.newBuilder()
                .initialCapacity(16)
                .concurrencyLevel(3)
                .maximumSize(32)                                    // 当Cache中的记录数量达到最大值后再调用put方法向其中添加对象，Guava会先从当前缓存的对象记录中选择一条删除掉，腾出空间后再将新的对象存储到Cache中。
//                .maximumWeight(1024*1024*1024)                      // 当缓存的最大数量/容量逼近或超过我们所设置的最大值时，Guava就会使用LRU算法对之前的缓存进行回收
//                .weigher((o, o2) -> o.toString().getBytes().length+o2.toString().getBytes().length)
//                .refreshAfterWrite(Duration.ofMinutes(5))           // 超过指定时间，缓存会调用reload方法(如果没有reload则调用load方法)刷新；在refresh过程中，如果这是有查询过来，查询会返回旧值；而expireAfterAccess和expireAfterWriter超过超时时间后查询会去数据源查询数据，对比发现refreshAfterWriter比前两个性能好一些，因为不需要去数据源load数据，但不能严格保证查询数据都是新值 删除监听器：
                .expireAfterAccess(Duration.ofMinutes(5))           // 超过指定时间没有读/写，缓存就会被回收；
//                .expireAfterWrite(Duration.ofMillis(3))             // 超过指定时间没有写，缓存就被回收；
                .removalListener(removalNotification -> {
                    log.info("cache remove:{}-{}", removalNotification.getKey(), removalNotification.getValue());
                })
                /**
                 * 打开统计信息开关
                 * 可以对Cache的命中率、加载数据时间等信息进行统计。
                 * 在构建Cache对象时，可以通过CacheBuilder的recordStats方法开启统计信息的开关。开关开启后Cache会自动对缓存的各种操作进行统计，调用Cache的stats方法可以查看统计后的信息。
                 */
                .recordStats();
    }

    private static Cache noLoadingCache(){
        return getCacheBuilder().build();
    }

    private static Cache loadingCache(){
        return getCacheBuilder().build(new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return initCache.get(key);
            }
        });
    }

}
