package com.demo99.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//菜品类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int shop_id;
    private int id;
    private String shopName;//店铺名
    private String goodName;//菜品名
    private double price;//价格
    private int sales;//销量
}
