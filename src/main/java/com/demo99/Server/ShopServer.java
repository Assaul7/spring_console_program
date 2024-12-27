package com.demo99.Server;

import com.demo99.pojo.Goods;
import com.demo99.pojo.Order;
import com.demo99.pojo.business;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopServer {

    /**
     * 获取店铺信息
     * @param id
     * @return
     */
    business getShopInfo(int id);


    /**
     * 写入店铺信息
     *  注册
     *
     */
    int setShopInfo(String phoneNum,  String shopName,String password, String address);


    /**
     * 商家登录
     * 手机号密码
     *
     */
    business shopLoginByPhoneNumAndPassword(String phoneNum, String password);

    /**
     * 搜索商家
     */
    List<business> findMerchantsByName(String shopName,String userName,int userId);

    /**
     * 根据店铺id查店名
     * @param shopId 店铺ID
     * @return
     */
    business getShopInfoByShopID(int shopId);

    /**
     * 查全部店铺
     * @return
     */
    List<business> findALLBusiness();


    /**
     * 推荐店铺
     * @return
     */
    List<business> findElseBusiness();

    /**
     * 查商品根据商品名
     * @return
     */
    List<Goods> SearchGoods(String goodsName, String userName, int userId);

    /**
     * 查全部商品根据店铺id
     * @return
     */
    List<Goods> SearchShopAllGoodsByShopId(String shopId);

    //下单操作，传递所有选中的商品ID
    Order confirmOrder(List<Integer> itemId, int userId,int shopId);

    //选择支付方式
    String pickPay(double discountedPrice, int Id);

    //模拟卡顿加载方法
    void load(String info) ;

    //查询店铺全部商品
    List<Goods> shopLookALLGoodsById(int Id);

    //添加商品
    int NewShopByShopId(String goodName, double price,int id);

    //删除商品
    boolean DelShopByShopIdAndGoodsId(int goodId, int id);

    //查全部订单根据商家ID
    List<Order> getALLOrderByShopId(int id);

    //配送商品根据商家与订单id
    boolean GiveShopByShopIdAndGoodsId(int orderId, int id);
}
