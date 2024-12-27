package com.demo99.controller;

import com.demo99.Server.ShopServer;
import com.demo99.pojo.Goods;
import com.demo99.pojo.Order;
import com.demo99.pojo.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Controller
public class ShopController {
    @Autowired
    private ShopServer shopServer;


    //商家主菜单界面
    public void shopIndex(int Id,String shopName) {
        Scanner input = new Scanner(System.in);

        System.out.println("\n|============="+shopName+"===========|");
        System.out.println("1.店铺信息");
        System.out.println("2.管理店铺餐品");
        System.out.println("3.管理订单");
        System.out.println("-1.打烊");
        System.out.print("请选择当前要进行的操作：");
        String op = input.next();

        switch (op) {
            case "1":
                shopInfo(Id,shopName);
                break;
            case "2":
                shopGoods(shopName,Id);//店铺餐品方法-传Id
                break;
            case "3":
                getALLPo(shopName,Id);//该店铺全部订单方法-传Id
                break;
            case "-1":
                System.out.println("再见！");
                break;
            default:
                System.out.println("无效选择，请重新输入");
                shopIndex(Id,shopName);
                break;
        }
    }

    //显示店铺信息
    private void shopInfo(int Id,String shopName) {
        Scanner input = new Scanner(System.in);

        business u=shopServer.getShopInfo(Id);

        System.out.println("\n|============="+u.getShopName()+"===========|");
        System.out.println("店铺ID："+u.getId());
        System.out.println("店铺联系号码："+u.getPhoneNum());
        System.out.println("店铺地址："+u.getAddress());
        System.out.print("\n输入任意字符返回菜单:");
        String op=input.next();
        if (op!=null){
            shopIndex(Id,shopName);//规递返回菜单
        }else {
            System.out.println("错误！");
        }

    }

    //管理店铺餐品
    private void shopGoods(String shopName,int Id) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n|=============管理："+shopName+"餐品===========|");
        System.out.println("1.查看店铺全部餐品");
        System.out.println("2.新品上架");
        System.out.println("3.餐品下架");
        System.out.println("-1.返回");
        System.out.print("请选择当前要进行的操作：");
        String op = sc.next();

        switch (op) {
            case "1":
                getALLGoods(Id,shopName);
                break;
            case "2":
                newGoods(shopName,Id);//店铺餐品方法-传Id
                break;
            case "3":
                DelGoods(shopName,Id);//菜品del方法-传Id
                break;
            case "-1":
                shopIndex(Id,shopName);
                break;
            default:
                System.out.println("无效选择，请重新输入");
                shopGoods(shopName,Id);
                break;
        }
    }

    //查看全部商品
    private void getALLGoods(int Id,String shopName) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        Scanner sc = new Scanner(System.in);

        List<Goods> goods=shopServer.shopLookALLGoodsById(Id);

        //显示搜索结果
        if (!goods.isEmpty()) {
            for (Goods good : goods) {
                System.out.println("\n商品ID: " + good.getId() +
                        "\t\t商品名: " + GREEN + good.getGoodName() + RESET +
                        "\t价格: " + good.getPrice() +
                        "\t销量：" + good.getSales());
            }
            System.out.print("请输入任意字符返回菜单：");
            String shopId = sc.next();
            shopIndex(Id,shopName);
        } else {
            System.out.println(RED + "|===============店铺还没有上架商品！==============|" + RESET);
            System.out.print("请输入任意字符返回菜单：");
            String shopId = sc.next();
            shopIndex(Id,shopName);
        }
    }


    //商品上新
    private void newGoods(String shopName,int Id) {
        Scanner op = new Scanner(System.in);
        System.out.println("|=============添加新商品===========|");
        System.out.print("请输入商品名：");
        String goodName = op.next();
        System.out.print("请输入商品价格：");
        double price = op.nextDouble();
        int i=shopServer.NewShopByShopId(goodName,price,Id);
        if (i==1){
            System.out.println("\n添加成功！");
            shopIndex(Id,shopName);
        }else {
            System.out.println("添加失败！请稍后再试");
            newGoods(shopName,Id);
        }
    }


    //商品下架
    private void DelGoods(String shopName,int Id) {
            Scanner op = new Scanner(System.in);

            System.out.println("|=============配送商品===========|");
            System.out.print("请输入要删除的商品ID：");
            int goodId = op.nextInt();
            op.nextLine(); //清除换行符
            boolean i=shopServer.DelShopByShopIdAndGoodsId(goodId,Id);//商品id，商家id

            if (i){
                System.out.println("\n商品状态同步成功！");
                shopIndex(Id,shopName);
            }else{
                System.out.println("配送失败，状态修改不成功！请稍后再试");
                shopIndex(Id,shopName);
            }
    }



    //获取全部订单
    private void getALLPo(String shopName,int Id) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n|=============获取："+shopName+"全部订单===========|");
        System.out.println("1.查看店铺全部订单");
        System.out.println("2.配送餐品");
        System.out.println("-1.返回");
        System.out.print("请选择当前要进行的操作：");
        String op = sc.next();

        switch (op) {
            case "1":
                getALLOrder(Id,shopName);
                break;
            case "2":
                giveGoods(shopName,Id);//店铺餐品方法-传Id
                break;
            case "-1":
                shopIndex(Id,shopName);
                break;
            default:
                System.out.println("无效选择，请重新输入");
                getALLPo(shopName,Id);
                break;
        }
    }

    //查看全部订单
    private void getALLOrder(int id, String shopName) {
        Scanner sc = new Scanner(System.in);

        List<Order> orders=shopServer.getALLOrderByShopId(id);

        if (orders.isEmpty()){
            System.out.println("\n|==============店铺还没有订单出售哦！==============|");
            System.out.print("请输入任意字符返回菜单:");
            String op=sc.next();
            shopIndex(id,shopName);
        }else {
            System.out.println("\n|==============店铺订单==============|");
            System.out.println("查询到小店有："+orders.size()+"个订单");
            System.out.println("\n订单号\t订单金额\t\t收货人\t\t电话号\t\t\t配送地址\t\t\t订单状态");
            for (Order order : orders) {
                System.out.println(order.getId()+"\t\t"+order.getMoney()+"\t\t"+
                        order.getConsignee()+"\t\t\t"+order.getPhoneNum()+"\t\t"+
                        order.getDeliveryAddress()+"\t\t"+order.getState());
            }
            System.out.println("\n商家可以输入“1”进入配送页面");
            System.out.print("请输入任意字符返回菜单（输入1进入配送页）:");
            String op=sc.next();
            if (Objects.equals(op, "1")){
                giveGoods(shopName,id);
            }else {
                shopIndex(id, shopName);
            }
        }
    }

    //配送餐品
    private void giveGoods(String shopName, int id) {
        Scanner op = new Scanner(System.in);

        System.out.println("|=============配送商品===========|");
        System.out.print("请输入要送达的订单ID：");
        int orderId = op.nextInt();
        op.nextLine(); //清除换行符
        boolean i=shopServer.GiveShopByShopIdAndGoodsId(orderId,id);//订单id，商家id

        if (i){
            System.out.println("\n商品状态同步成功！");
            shopIndex(id,shopName);
        }else{
            System.out.println("配送失败，状态修改不成功！请稍后再试");
            shopIndex(id,shopName);
        }
    }
}
