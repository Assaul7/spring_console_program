package com.demo99.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;

//订单类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private double money;
    private String consignee;//收货人
    private String deliveryAddress;//收货地址
    private String phoneNum;//收货手机
    private LocalDateTime first_distributionTime;
    private LocalDateTime Latest_distributionTime;
    private String state;//订单当前状态
    private int shopId;//商家id
    private String payType;
}
