package com.gz.rhb.gateway.config.bean;

import lombok.Data;

/**
 * @author: rhb
 * @date: 2023/10/26 15:41
 * @description:
 */
@Data
public class RouteObject {

    private String id;

    private String uri;

    private String predicate;

    private String predicateValue;

    private String filter;

    private String filterValue;

}
