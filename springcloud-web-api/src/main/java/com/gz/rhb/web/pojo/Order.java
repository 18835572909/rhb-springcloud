package com.gz.rhb.web.pojo;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author: rhb
 * @date: 2023/10/25 21:18
 * @description:
 */
@Data
@SuperBuilder
public class Order implements Serializable {

    private static final long serialVersionUID = 3702047759382529579L;

    private String id;

    private String orderNo;

    private Date addTime;

    private BigDecimal orderPrice;

    private List<Item> items;

}
