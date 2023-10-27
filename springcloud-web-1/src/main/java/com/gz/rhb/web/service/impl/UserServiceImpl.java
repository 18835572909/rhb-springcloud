package com.gz.rhb.web.service.impl;

import cn.hutool.core.util.IdUtil;
import com.gz.rhb.web.pojo.User;
import com.gz.rhb.web.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
public class UserServiceImpl implements UserService {

    private List<User> users = new ArrayList<>(4);

    private Map<String,User> userMap = new HashMap<>(4);

    @PostConstruct
    private void init(){
        users.add(User.builder().age(21).name("小四").sex(1).id("007").build());
        users.add(User.builder().age(22).name("可乐").sex(0).id("008").build());
        users.add(User.builder().age(25).name("阿虎").sex(1).id("009").build());

        userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity(), (k1, k2) -> k1));
    }

    @Override
    public User getUser(String id) {
        return userMap.get(id);
    }

}
