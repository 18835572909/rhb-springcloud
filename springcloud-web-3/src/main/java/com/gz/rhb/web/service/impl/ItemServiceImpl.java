package com.gz.rhb.web.service.impl;

import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import com.gz.rhb.web.pojo.Item;
import org.springframework.stereotype.Service;
import com.gz.rhb.web.service.ItemService;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author: rhb
 * @date: 2023/10/25 20:35
 * @description:
 */
@Service
public class ItemServiceImpl implements ItemService {

    private List<Item> items = new ArrayList<>(4);

    private Map<String,List<Item>> orderItemMap = new HashMap<>(4);

    private Map<String,Item> itemMap = new HashMap<>(4);

    @PostConstruct
    private void init(){
        items.add(Item.builder().id("SNS000001").sku("SDR123456").spu("SDW123456").price(new BigDecimal(2.5)).build());
        items.add(Item.builder().id("SNS000002").sku("SDR123457").spu("SDW123457").price(new BigDecimal(2.5)).build());
        items.add(Item.builder().id("SNS000003").sku("SDR123458").spu("SDW123458").price(new BigDecimal(2.5)).build());

        itemMap = items.stream().collect(Collectors.toMap(Item::getId, Function.identity(), (k1, k2) -> k2));

        orderItemMap.put("20231025000001",Lists.newArrayList(items.get(0),items.get(1)));
        orderItemMap.put("20231025000002",Lists.newArrayList(items.get(2),items.get(1)));
        orderItemMap.put("20231025000003",Lists.newArrayList(items.get(0),items.get(2)));
    }

    @Override
    public Item getItem(String id) {
        return itemMap.get(id);
    }

    @Override
    public List<Item> orderItemList(String orderNo) {
        return orderItemMap.get(orderNo);
    }
}
