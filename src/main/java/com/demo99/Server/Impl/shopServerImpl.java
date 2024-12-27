package com.demo99.Server.Impl;

import com.demo99.Mapper.ShopMapper;
import com.demo99.Server.ShopServer;
import com.demo99.pojo.Goods;
import com.demo99.pojo.Order;
import com.demo99.pojo.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class shopServerImpl implements ShopServer {

    @Autowired
    private ShopMapper shopMapper;

    @Override
    public business getShopInfo(int id) {
        business b = shopMapper.getShopInfo(id);
        return b;
    }

    //商家注册
    @Override
    public int setShopInfo(String phoneNum,  String shopName,String password, String address) {
        return shopMapper.setShopInfo(phoneNum,  shopName,password, address);
    }

    //商家登录
    @Override
    public business shopLoginByPhoneNumAndPassword(String phoneNum, String password) {
        return shopMapper.shopLoginByPhoneNumAndPassword(phoneNum, password);
    }

    //搜索商家
    public List<business> findMerchantsByName(String shopName,String userName,int userId) {
        if (shopName == null || shopName.trim().isEmpty()) {
            throw new IllegalArgumentException("店铺名称不能为空");
        }

        try {
            List<business> merchants = shopMapper.findMerchantsByName(shopName.trim());
            if (merchants.isEmpty()) {
                System.out.println("|==============店铺未有商品上架！==============|");
                System.out.println("为你推荐");
                findElseBusiness();
            } else {
                System.out.println("\n");
                System.out.println("|===============为您找到"+merchants.size()+"个，关于“"+shopName+"”的商家==============|");
            }
            return merchants;
        } catch (Exception e) {
            System.out.println("搜索商户名称时发生错误:: " + shopName);
            e.printStackTrace();
            throw new RuntimeException("未能搜索商户", e);
        }
    }

    //根据shopID查店铺名
    @Override
    public business getShopInfoByShopID(int shopId) {
        return shopMapper.getShopInfoByID(shopId);
    }

    //查全部店铺
    @Override
    public List<business> findALLBusiness() {
        try {
            List<business> merchants = shopMapper.findALLBussiness();
            if (merchants.isEmpty()) {
                System.out.println("\n");
                System.out.println("|===============对不起饿了妈还未有商家==============|");
            } else {
                System.out.println("\n");
                System.out.println("|===============为您找到"+merchants.size()+"个商家==============|");
            }
            return merchants;
        } catch (Exception e) {
            System.out.println("网络异常搜索商户时发生错误404");
            e.printStackTrace();
            throw new RuntimeException("未能搜索商户！", e);
        }
    }

    //推荐5个店铺
    @Override
    public List<business> findElseBusiness() {
        try {
            List<business> merchants = shopMapper.findElseBussiness();
            return merchants;
        } catch (Exception e) {
            System.out.println("网络异常搜索商户时发生错误404");
            e.printStackTrace();
            throw new RuntimeException("未能搜索商户！", e);
        }
    }

    //查商品根据商品名字
    @Override
    public List<Goods> SearchGoods(String goodsName, String userName, int userId) {
        if (goodsName == null || goodsName.trim().isEmpty()) {
            throw new IllegalArgumentException("店铺名称不能为空");
        }

        try {
            List<Goods> merchants = shopMapper.SearchGoodsByName(goodsName.trim());
            if (merchants.isEmpty()) {
                System.out.print("\n");
            } else {
                System.out.println("\n");
                System.out.println("|===============为您找到"+merchants.size()+"个，关于“"+goodsName+"”商品的商家==============|");
            }
            return merchants;
        } catch (Exception e) {
            System.out.println("搜索商品名称时发生错误:: " + goodsName);
            e.printStackTrace();
            throw new RuntimeException("发生错误未能搜索商品", e);
        }
    }

    /**
     * 查(db_goods表)
     * 根据shop_id(外键店铺id"Shop_Id"字段)
     * 查全部商品，并查店铺信息
     */
    @Override
    public List<Goods> SearchShopAllGoodsByShopId(String shopId) {
        if (shopId == null || shopId.trim().isEmpty()) {
            throw new IllegalArgumentException("发生错误：店铺id为空");
        }

        try {
            return shopMapper.SearchALLGoodsByShopId(shopId.trim());
        } catch (Exception e) {
            System.out.println("搜索商品时发生错误: " + shopId);
            e.printStackTrace();
            throw new RuntimeException("发生错误未能搜索商品", e);
        }
    }

    //确认下单，传递所有选中的商品
    @Override
    public Order confirmOrder(List<Integer> itemIds, int userId,int shopID) {
        double amount=0.0;//商品总金额初始化
        double DeliveryFees = 0.0;//配送费初始化
        double PackagingFee = 0.0;//打包初始化

        System.out.println("|=======================预览=======================|");
        System.out.println(String.format("%-20s\t%8s", "商品名称", "价格"));//商品名的宽度设为20个字符，价格栏宽度为8个字符

        // 根据商品id查商品名并打印
        for (Integer itemId : itemIds) {
            Goods goods = shopMapper.getGoodsById(itemId);
            System.out.println(String.format("%-20s\t%8.2f", goods.getGoodName(), goods.getPrice()));
        }
        // 总价格计算
        for (Integer itemId : itemIds) {
            Goods goodsMoney = shopMapper.getGoodsMoneyById(itemId);
            PackagingFee+=1;
            DeliveryFees+=0.6;
            amount += goodsMoney.getPrice();
        }
        double totalPrice =amount+PackagingFee+DeliveryFees;
        double discountRate = generateRandomDiscount(); // 生成随机折扣率
        double discountedPrice = totalPrice * discountRate; // 应用折扣
        System.out.println("商品金额："+amount);
        System.out.println("打包费："+ PackagingFee );
        System.out.println(String.format("配送费：%.2f ", DeliveryFees));
        System.out.println("应付："+ totalPrice);
        System.out.println(String.format("优惠：%.0f 折", discountRate * 10));
        System.out.println(String.format("实付：%1.2f 元", discountedPrice));//折后价

        // 创建Order对象并设置money属性
        Order order = new Order();
        order.setMoney(discountedPrice);
        order.setShopId(shopID);
        return order;
    }


    //生成80%到99%随机折扣率
    private double generateRandomDiscount() {
        Random random = new Random();
        return 0.7 + random.nextDouble() * (0.9 - 0.7);
    }

    //选择支付方式
    public String pickPay(double discountedPrice, int Id){
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("\n|======================"+df.format(discountedPrice)+"元=========================|");
        System.out.println("1.微信支付");
        System.out.println("2.支付宝支付");
        System.out.println("-1.我再想想~（退出）");
        System.out.print("请选择你的支付方式：");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void load(String info){
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";

        // 创建并启动加载指示器线程
        Thread loadingThread = new Thread(() -> {
            AtomicInteger dots = new AtomicInteger(0);
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    String message = (info != null) ? info : "请稍后";
                    System.out.print("\r"+GREEN+message+RESET+ ".".repeat(dots.getAndIncrement() % 4));
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                //线程被中断时退出
            }
        });

        loadingThread.start();
        //模拟卡顿，等待5秒
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //处理或记录异常
            Thread.currentThread().interrupt(); //恢复中断状态
        }
        //停止加载指示器线程
        loadingThread.interrupt();
        //使用空格覆盖剩余的点号，清除最后一行的加载信息
        System.out.println("\r加载完成！             ");
    }

    //查店铺信息(db_goods表)
    @Override
    public List<Goods> shopLookALLGoodsById(int id) {
        try {
            return shopMapper.shopLookALLGoodsById(id);
        } catch (Exception e) {
            System.out.println("搜索商品时发生错误: " + id);
            e.printStackTrace();
            throw new RuntimeException("发生错误未能搜索商品", e);
        }
    }

    //新增商品
    @Override
    public int NewShopByShopId(String goodName, double price,int id) {
        return shopMapper.NewShopByShopId(goodName,price,id);
    }

    //删除商品
    @Override
    public boolean DelShopByShopIdAndGoodsId(int goodId, int id) {
        //先查商品goods表商品id和商家id是否存在
        int shopId=id;
        int Id=goodId;
        boolean i = shopMapper.getShopAndGoodsId(Id,shopId);
        String state="已送达";

        if (i){
            return shopMapper.DelShopByShopIdAndGoodsId(goodId);

        }else {
            return false;
        }
    }

    //查询店铺所有订单根据商家id
    @Override
    public List<Order> getALLOrderByShopId(int id) {
        return shopMapper.getALLOrderByShopId(id);
    }

    //配送商品
    @Override
    public boolean GiveShopByShopIdAndGoodsId(int orderId, int id) {
        //先查商品order表订单id和商家id是否存在
        int shopId=id;
        int Id=orderId;
        boolean i = shopMapper.getShopAndOrderId(Id,shopId);
        String state="已送达";

        if (i){
            return shopMapper.reviseOrderAndState(state,orderId);

        }else {
            return false;
        }

    }
}
