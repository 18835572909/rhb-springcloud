package com.gz.rhb.web.service;

import com.gz.rhb.web.pojo.Item;

import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 20:35
 * @description:
 */
public interface ItemService {

    Item getItem(String id);

    List<Item> orderItemList(String orderNo);

}
