package com.gz.rhb.web.controller;

import com.gz.rhb.web.api.UserApi;
import com.gz.rhb.web.pojo.User;
import com.gz.rhb.web.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author: rhb
 * @date: 2023/10/25 20:32
 * @description:
 */
@RestController
public class UserController implements UserApi {

    @Resource
    UserService userService;

    public User getUser(@PathVariable("id") String id){
        return userService.getUser(id);
    }

}
