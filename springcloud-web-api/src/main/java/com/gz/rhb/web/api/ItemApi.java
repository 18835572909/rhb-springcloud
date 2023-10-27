package com.gz.rhb.web.api;

import com.gz.rhb.web.pojo.Item;
import com.gz.rhb.web.pojo.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 21:31
 * @description: 订单服务
 */
@RequestMapping("/item")
public interface ItemApi {

    // curl -s -H 'Accept: application/json' localhost:10003/item/id/SNS000003
    @GetMapping("/id/{id}")
    Item getItem(@PathVariable("id") String id);

    // curl -s -H 'Accept: application/json' localhost:10003/item/orderItems/20231025000001
    @GetMapping("/orderItems/{orderNo}")
    List<Item> orderItemList(@PathVariable("orderNo") String orderNo);

    @GetMapping("/param")
    String getParam(@RequestParam("param") String param);

}
