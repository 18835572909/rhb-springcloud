package com.gz.rhb.web.controller;

import com.gz.rhb.web.api.ItemApi;
import com.gz.rhb.web.pojo.Item;
import com.gz.rhb.web.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 20:32
 * @description:
 */
@RestController
public class ItemController implements ItemApi {

    @Resource
    ItemService itemService;

    @Override
    public Item getItem(String id) {
        return itemService.getItem(id);
    }

    @Override
    public List<Item> orderItemList(String orderNo) {
        return itemService.orderItemList(orderNo);
    }

    @Override
    public String getParam(String param) {
        return param;
    }
}
