package com.gz.rhb.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: rhb
 * @date: 2023/10/25 21:20
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Item implements Serializable {

    private static final long serialVersionUID = 7372764962515579476L;

    private String id;

    private String spu;

    private String sku;

    private BigDecimal price;

}
