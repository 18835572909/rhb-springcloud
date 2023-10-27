package com.gz.rhb.web.service;

import com.gz.rhb.web.api.ItemApi;
import org.springframework.cloud.openfeign.FeignClient;
import com.gz.rhb.web.service.hystrix.ItemServiceFallbackFactory;

/**
 * @author: rhb
 * @date: 2023/10/25 22:07
 * @description:
 */
@FeignClient(value = "web-service-3",fallbackFactory = ItemServiceFallbackFactory.class)
public interface ItemService extends ItemApi {

}
