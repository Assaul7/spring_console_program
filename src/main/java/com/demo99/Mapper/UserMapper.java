package com.demo99.Mapper;

import com.demo99.pojo.Order;
import com.demo99.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;

@Mapper
public interface UserMapper {
    // 注册
    @Insert("INSERT INTO user(phoneNum,userName,password,address) VALUES(#{phoneNum},#{userName},#{password},#{address})")
    int setUserInfo(@Param("phoneNum") String phoneNum, @Param("userName")String userName,@Param("password")String password , @Param("address")String address);


    //根据电话号与密码登录
    @Select("SELECT * FROM user WHERE phoneNum = #{phoneNum} AND password = #{password}")
    User UserLoginByPhoneNumAndPassword(@Param("phoneNum")String phoneNum,@Param("password")String password);

    //根据用户名与密码登录
    @Select("SELECT * FROM user WHERE userName = #{userName} AND password = #{password}")
    User UserLoginByUserNameAndPassword(@Param("userName")String userName,@Param("password")String password);

    //根据用户id查订单
    @Select("SELECT * FROM db_orders WHERE user_id = #{userId}")
    List<Order> SearchOrderByuserId(int userId);

    //根据id查用户信息
    @Select("SELECT * FROM user WHERE id = #{userId}")
    User getUserInfoByUserId(int userId);

    // 写入订单
    @Insert("INSERT INTO db_orders(money, consignee, phoneNum, deliveryAddress, " +
            "first_distributionTime, latest_distributionTime, state, shop_id, user_id, payType) " +
            "VALUES(#{moneyScaled}, #{consignee}, #{phoneNum},#{deliveryAddress}, " +
            "#{firstDistributionTime}, #{latestDistributionTime}, #{state}, #{shopId}, #{userId}, #{payType})")
    int setTheOrderByUserId(
            @Param("moneyScaled") BigDecimal moneyScaled,
            @Param("consignee") String consignee,  // 使用更符合字段名的参数名
            @Param("phoneNum") String phoneNum,
            @Param("deliveryAddress") String deliveryAddress,
            @Param("firstDistributionTime") Time firstDistributionTime,
            @Param("latestDistributionTime") Time latestDistributionTime,
            @Param("state") String state,
            @Param("shopId") int shopId,
            @Param("userId") int userId,
            @Param("payType") String payType);

}
