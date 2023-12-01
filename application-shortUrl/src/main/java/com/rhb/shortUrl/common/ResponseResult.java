package com.rhb.shortUrl.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: rhb
 * @date: 2023/11/29 18:53
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult<T> {
    private String code;
    private String msg;
    private T data;
}
