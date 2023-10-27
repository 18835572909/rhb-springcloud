package com.gz.rhb.web.pojo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @author: rhb
 * @date: 2023/10/25 20:34
 * @description:
 */
@Data
@SuperBuilder
public class User implements Serializable {

    private static final long serialVersionUID = 1455956348623672385L;

    private String id;

    private String name;

    private Integer sex;

    private Integer age;

}
