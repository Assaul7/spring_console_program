package com.demo99.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class business {
    private int id;//店铺id
    private String phoneNum;
    private String password;
    private String shopName;//商家
    private String address;//商家地址
}
