package com.demo99.Server.Impl;

import com.demo99.Mapper.UserMapper;
import com.demo99.Server.UserServer;
import com.demo99.pojo.Goods;
import com.demo99.pojo.Order;
import com.demo99.pojo.User;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.JobOriginatingUserName;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
public class userServerImpl implements UserServer {
    @Autowired
    private UserMapper userMapper;

    //用户注册方法
    @Override
    public int setUserInfo(String phoneNum, String userName,String password, String address) {
        //调用mapper注册方法写入数据库
        int i =userMapper.setUserInfo(phoneNum, userName,password,address);
        if (i!=0){
            return i;
        }else {
            return 0;
        }
    }

    //用户手机号密码登录方法
    @Override
    public User UserLoginByPhoneNumAndPassword(String phoneNum, String password) {
        return userMapper.UserLoginByPhoneNumAndPassword(phoneNum, password);
    }

    //用户名密码登录方法
    @Override
    public User UserLoginByUserNameAndPassword(String userName, String password) {
        return userMapper.UserLoginByUserNameAndPassword(userName, password);
    }

    //订单状态
    @Override
    public List<Order> OrderStatus(String userName,int userId){
        return List.of();
    }

    //根据用户id查订单
    @Override
    public List<Order> getOrderList(int userId) {
        List<Order> orders = userMapper.SearchOrderByuserId(userId);
        return orders;
    }

    //写入订单根据id
    @Override
    public int setTheOrderByUserId(double discountedPrice, int userId,String payType,int shopId) {

        User user = userMapper.getUserInfoByUserId(userId);
        // 获取当前时间并转换为LocalTime
        LocalTime now = LocalTime.now().withSecond(0).withNano(0);
        System.out.println("当前时间: " + now);

        if (user!=null){
            String state="待配送";

            //当前时间+10分钟确保不会越过午夜
            LocalTime firstDistributionTime = now.plus(Duration.ofMinutes(10)).withSecond(0).withNano(0);
            //如果超过了午夜，就调整到当天最早的合法时间
            if (firstDistributionTime.isBefore(now)) {
                firstDistributionTime = LocalTime.MIDNIGHT.plus(Duration.ofMinutes(10));
            }

            //firstDistributionTime再加15分钟, 同样确保不会越过午夜
            LocalTime latestDistributionTime = firstDistributionTime.plus(Duration.ofMinutes(15)).withSecond(0).withNano(0);
            //如果超过了午夜，就调整到当天最早的合法时间
            if (latestDistributionTime.isBefore(firstDistributionTime)) {
                latestDistributionTime = LocalTime.MIDNIGHT.plus(Duration.ofMinutes(25));
            }
            System.out.println("商家已接单，配送区间在 : "+firstDistributionTime+" -- "+latestDistributionTime);

            //转换为java.sql.Time类型以便于与数据库交互
            Time sqlFirstDistributionTime = Time.valueOf(firstDistributionTime);
            Time sqlLatestDistributionTime = Time.valueOf(latestDistributionTime);

            String userName=user.getUserName();
            String phoneNum=user.getPhoneNum();
            String address=user.getAddress();
            double money=discountedPrice;
//            System.out.println("店家id"+shopId);
//            System.out.println("用户id"+userId);

            // 将传入的 double 类型的 money 转换为 BigDecimal
            BigDecimal moneyDecimal = new BigDecimal(Double.toString(money));

            // 设置 scale 为 2，并使用 RoundingMode.HALF_UP 进行四舍五入
            BigDecimal moneyScaled = moneyDecimal.setScale(2, RoundingMode.HALF_UP);

            return userMapper.setTheOrderByUserId(moneyScaled,userName,phoneNum,address,sqlFirstDistributionTime,sqlLatestDistributionTime,state,shopId,userId,payType);
        }else {
            System.out.println("网络错误！");
        }
        return 0;
    }
}
