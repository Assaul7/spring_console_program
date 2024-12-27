package com.demo99.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String phoneNum;
    private String password;
    private String userName;//用户名
    private String address;//地址
}
