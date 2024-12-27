package com.demo99.Server;

import com.demo99.pojo.Order;
import com.demo99.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServer {

    /**
     * 用户注册
     * 写入手机号密码地址用户名
     */
    int setUserInfo(String phoneNum, String userName,String password,String address);

    /**
     * 用户手机号登录
     * 根据手机号密码登录
     */
    User UserLoginByPhoneNumAndPassword(String phoneNum, String password);

    /**
     * 用户名登录
     * 根据用户名密码登录
     */
    User UserLoginByUserNameAndPassword(String userName, String password);

    //订单状态
    List<Order> OrderStatus(String userName, int userId);

    //查订单根据用户ID
    List<Order> getOrderList(int userId);

    //写入订单根据id
    int setTheOrderByUserId(double discountedPrice, int userId,String payType,int shopId);
}
