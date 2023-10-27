package com.gz.rhb.web.service.hystrix;

import com.gz.rhb.web.pojo.Item;
import com.gz.rhb.web.service.ItemService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 22:08
 * @description:
 */
@Slf4j
@Component
public class ItemServiceFallbackFactory implements FallbackFactory<ItemService> {

    @Override
    public ItemService create(Throwable throwable) {
        log.info("Item服务异常[getItem].{}",1);
        return new ItemService() {
            @Override
            public Item getItem(String id) {
                return new Item();
            }

            @Override
            public List<Item> orderItemList(String orderNo) {
                return new ArrayList<>();
            }

            @Override
            public String getParam(String param) {
                return "";
            }
        };
    }
}
