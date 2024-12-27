package com.demo99.Mapper;

import com.demo99.pojo.Goods;
import com.demo99.pojo.Order;
import com.demo99.pojo.business;
import net.minidev.json.JSONUtil;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopMapper {
    //查询店铺信息byID
    @Select("SELECT * FROM business WHERE id = #{id}")
    business getShopInfo(int id);


    //商家注册
    @Insert("INSERT INTO business(phoneNum,shopName,password,address) VALUES(#{phoneNum},#{shopName},#{password},#{address})")
    int setShopInfo(@Param("phoneNum") String phoneNum,@Param("shopName") String shopName,@Param("password") String password,@Param("address") String address);

    //商家登录
    @Select("SELECT * FROM business WHERE phoneNum = #{phoneNum} AND password = #{password}")
    business shopLoginByPhoneNumAndPassword(@Param("phoneNum")String phoneNum,@Param("password") String password);

    //搜索商家
    @Select("SELECT * FROM business WHERE shopName LIKE CONCAT('%', #{shopName}, '%')")
    List<business> findMerchantsByName(@Param("shopName") String shopName);

    //根据店铺id查店信息
    @Select("SELECT * FROM business WHERE id =#{shopId}")
    business getShopInfoByID(@Param("shopId") int shopId);

    //全部商家
    @Select("SELECT * FROM business")
    List<business> findALLBussiness();

    //推荐商家
    @Select("SELECT * FROM business LIMIT 5")
    List<business> findElseBussiness();

    /**
     * 一对多查询
     * 根据商品名模糊查询商品
     * 根据商品外键查询商家名
     */
    @Select("SELECT g.*, b.shopName " +
            "FROM db_goods g " +
            "LEFT JOIN business b ON g.shop_id = b.id " +
            "WHERE g.goodName LIKE CONCAT('%', #{goodsName}, '%')")
    List<Goods> SearchGoodsByName(@Param("goodsName") String goodsName);

    /**
     * 根据外键店铺id（shop_id）
     * 查询店铺db_goods表关于shop_id全部商品
     */
    @Select("SELECT * FROM db_goods WHERE shop_id=#{shopId}")
    List<Goods> SearchALLGoodsByShopId(@Param("shopId") String shopId);

    //id查商品名
    @Select("SELECT * FROM db_goods WHERE id=#{itemId}")
    Goods getGoodsById(@Param("itemId") Integer itemId);

    //id查商品价格
    @Select("SELECT price FROM db_goods WHERE id=#{itemId}")
    Goods getGoodsMoneyById(@Param("itemId") Integer itemId);

    /**
     * 根据外键店铺id（shop_id）
     * 查询店铺db_goods表关于shop_id全部商品
     */
    @Select("SELECT * FROM db_goods WHERE shop_id=#{id}")
    List<Goods> shopLookALLGoodsById(@Param("id") int id);


    // 新增商品
    @Insert("INSERT INTO db_goods(goodName, price, shop_id) VALUES (#{goodName},#{price},#{id})")
    int NewShopByShopId(@Param("goodName") String goodName,@Param("price") double price,@Param("id") int id);

    //查询商家是否有这个商品
    @Select(" SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS result FROM db_goods WHERE id =#{goodId}  AND shop_id =#{id}")
    boolean getShopAndGoodsId(@Param("goodId") int goodId,@Param("id") int id);

    //有商品执行删除
    @Insert("DELETE FROM db_goods WHERE id=#{goodId}")
    boolean DelShopByShopIdAndGoodsId(@Param("goodId") int goodId);

    //查全部订单根据商家id
    @Select("SELECT * FROM db_orders WHERE shop_id=#{id}")
    List<Order> getALLOrderByShopId(int id);

    //查询商家是否有这个订单
    @Select(" SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END AS result FROM db_orders WHERE id =#{Id}  AND shop_id =#{shopId}")
    boolean getShopAndOrderId(@Param("Id")int Id,@Param("shopId") int shopId);

    //修改订单状态
    @Insert("UPDATE db_orders SET state=#{state} WHERE id=#{orderId}")
    boolean reviseOrderAndState(@Param("state") String state,@Param("orderId") int orderId);
}
