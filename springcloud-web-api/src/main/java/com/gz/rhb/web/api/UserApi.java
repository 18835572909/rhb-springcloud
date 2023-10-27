package com.gz.rhb.web.api;

import com.gz.rhb.web.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: rhb
 * @date: 2023/10/25 20:48
 * @description: 用户服务
 */
@RequestMapping("/user")
public interface UserApi {

    // curl -s -H 'Accept: application/json' localhost:10001/user/get/007
    @GetMapping("/get/{id}")
    User getUser(@PathVariable("id") String id);


}
