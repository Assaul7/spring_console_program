package com.demo99.controller;

import com.demo99.Server.ShopServer;
import com.demo99.Server.UserServer;
import com.demo99.pojo.Goods;
import com.demo99.pojo.Order;
import com.demo99.pojo.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class UserController {
    @Autowired
    private ShopServer shopServer;
    @Autowired
    private UserServer userServer;


    public void UserIndex(String userName, int Id) {

        Scanner sc = new Scanner(System.in);

        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";


        System.out.println("|===============" + userName + "用户你好！欢迎使用饿了妈点餐==============|");
        System.out.println("1.搜索商家");
        System.out.println("2.全部商家");
        System.out.println("3.搜索商品");
        System.out.println("4.个人中心");
        System.out.println(RED + "-1.不吃了" + RESET);
        System.out.print("请输入你要进行的操作号码：");
        String op = sc.next();
        switch (op) {
            case "1":
                FindAMerchant(userName, Id);
                break;
            case "2":
                ALLBussiness(userName, Id);
                break;
            case "3":
                SearchGoods(userName, Id);
                break;
            case "4":
                UserMy(userName, Id);
                break;
            case "-1":
                System.out.println("感谢使用本系统，再见！");
                return;
            default:
                System.out.println(RED + "输入有误!" + RESET);
                UserIndex(userName, Id);
        }
    }

    //搜索商家
    public void FindAMerchant(String userName, int userId) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        System.out.println("|===============搜索商家===============|");
        System.out.println("按商家名搜索");
        System.out.print("请输入你要搜索的店铺名：");
        String shopName = sc.nextLine();

        //调用服务层方法执行搜索
        List<business> merchants = shopServer.findMerchantsByName(shopName, userName, userId);

        //显示搜索结果
        if (!merchants.isEmpty()) {
            for (business merchant : merchants) {
                System.out.println("\n店铺名: " + merchant.getShopName() +
                        "\n店铺ID: " + merchant.getId() +
                        "\n地址: " + merchant.getAddress());
            }
            System.out.println("\n-1.退出");
            System.out.println("-2.返回主菜单");
            System.out.print("请输入您要进入的店铺ID：");
            int shopId = sc.nextInt();
            if (shopId == -1) {
                System.out.println("再见！");
            } else if (shopId == -2) {
                UserIndex(userName, userId);
            } else {
                //根据输入的店铺id查询店铺名字
                business b = shopServer.getShopInfoByShopID(shopId);
                if (b != null) {
                    //调用进入店铺方法-传参
                    EnterTheStore(String.valueOf(shopId), b.getShopName(), userName, userId);
                } else {
                    System.out.println("\n");
                    System.out.println(RED + "没找到ID为" + shopId + "的商家！" + RESET);
                    UserIndex(userName, userId);
                }
            }
        } else {
            System.out.println(RED + "|===============对不起，小二没能找到关于“" + shopName + "”的商家==============|" + RESET);
            ElseShop(userName, userId);
        }
    }

    //其他推荐商家
    private void ElseShop(String userName, int userId) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        Scanner sc = new Scanner(System.in);


        System.out.println(GREEN + "“为您推荐以下商家”" + RESET);

        //调用服务层方法执行搜索
        List<business> merchants = shopServer.findElseBusiness();

        if (!merchants.isEmpty()) {
            for (business merchant : merchants) {
                System.out.println("\n店铺名: " + merchant.getShopName() +
                        "\n店铺ID: " + merchant.getId() +
                        "\n地址: " + merchant.getAddress());
            }
            System.out.println("\n-1.退出");
            System.out.println("-2.返回主菜单");
            System.out.print("请输入您要进入的店铺ID：");
            int shopId = sc.nextInt();
            if (shopId == -1) {
                System.out.println("再见！");
            } else if (shopId == -2) {
                UserIndex(userName, userId);
            } else {
                //根据输入的店铺id查询店铺名字
                business b = shopServer.getShopInfoByShopID(shopId);
                if (b != null) {
                    //调用进入店铺方法-传参
                    EnterTheStore(String.valueOf(shopId), b.getShopName(), userName, userId);
                } else {
                    System.out.println("\n");
                    System.out.println(RED + "网络错误没找到商家！" + RESET);
                    UserIndex(userName, userId);
                }
            }
        } else {
            System.out.println("\n");
        }
    }


    //全部商家
    public void ALLBussiness(String userName, int userId) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        //调用服务层方法执行搜索
        List<business> merchants = shopServer.findALLBusiness();

        if (!merchants.isEmpty()) {
            for (business merchant : merchants) {
                System.out.println("\n店铺名: " + merchant.getShopName() +
                        "\n店铺ID: " + merchant.getId() +
                        "\n地址: " + merchant.getAddress());
            }
            System.out.println("\n-1.退出");
            System.out.println("-2.返回主菜单");
            System.out.print("请输入您要进入的店铺ID：");
            int shopId = sc.nextInt();
            if (shopId == -1) {
                System.out.println("再见！");
            } else if (shopId == -2) {
                UserIndex(userName, userId);
            } else {
                //根据输入的店铺id查询店铺名字
                business b = shopServer.getShopInfoByShopID(shopId);
                if (b != null) {
                    //调用进入店铺方法-传参
                    EnterTheStore(String.valueOf(shopId), b.getShopName(), userName, userId);
                } else {
                    System.out.println("\n");
                    System.out.println(RED + "没找到ID为" + shopId + "的商家！" + RESET);
                    UserIndex(userName, userId);
                }
            }
        } else {
            System.out.println("\n");
        }
    }


    //用户中心
    public void UserMy(String userName, int userId) {
        Scanner sc = new Scanner(System.in);

        System.out.println("|===============" + userName + "的用户中心===============|");
        System.out.println("1.查看订单");
        System.out.println("-2.返回");
        System.out.print("请输入你要进行的操作号码：");
        String op = sc.next();
        switch (op) {
            case "1":
                OrderStatus(userName, userId);
                break;
            case "-2":
                UserIndex(userName, userId);
                return;
            default:
                System.out.println("输入有误!");
                UserIndex(userName, userId);
        }
    }

    //搜索商品
    private void SearchGoods(String userName, int userId) {
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        Scanner sc = new Scanner(System.in);

        System.out.println("|===============搜索商品===============|");
        System.out.println("按商品名搜索");
        System.out.print("请输入你要搜索的商品名：");
        String goodsName = sc.nextLine();

        //调用服务层方法执行搜索
        List<Goods> merchants = shopServer.SearchGoods(goodsName, userName, userId);

        //显示搜索结果
        if (!merchants.isEmpty()) {
            for (Goods merchant : merchants) {
                System.out.println("\n店铺名: " + merchant.getShopName() +
                        "\t店铺ID: " + merchant.getShop_id() +
                        "\t\t商品名: " + GREEN + merchant.getGoodName() + RESET +
                        "\t价格: " + merchant.getPrice() +
                        "\t销量：" + merchant.getSales());
            }
            System.out.println("\n-1.退出");
            System.out.println("-2.返回菜单");
            System.out.print("请输入您要进入的店铺ID：");
            int shopId = sc.nextInt();
            if (shopId == -1) {
                System.out.println("再见！");
            } else if (shopId == -2) {
                UserIndex(userName, userId);
            } else {
                //根据输入的店铺id查询店铺名字
                business b = shopServer.getShopInfoByShopID(shopId);
                if (b != null) {
                    //调用进入店铺方法-传参
                    EnterTheStore(String.valueOf(shopId), b.getShopName(), userName, userId);
                } else {
                    System.out.println("\n");
                    System.out.println(RED + "没找到ID为" + shopId + "的商家！" + RESET);
                    UserIndex(userName, userId);
                }
            }
        } else {
            System.out.println(RED + "|===============对不起，小二没能找到关于“" + goodsName + "”的商品==============|" + RESET);
            ElseShop(userName, userId);
        }
    }


    //用户入店
    public void EnterTheStore(String shopId, String shopName, String userName, int userId) {

        Scanner sc = new Scanner(System.in);

        System.out.println("|===============" + shopName + "===============|");
        //调用服务层方法执行搜索
        List<Goods> merchants = shopServer.SearchShopAllGoodsByShopId(shopId);

        //显示搜索结果
        if (!merchants.isEmpty()) {
            displayMerchants(merchants);

            List<Integer> selectedItems = new ArrayList<>(); //存储用户选择的商品ID
            String op;
            do {
                System.out.print("请输入您要点餐ID：");
                op = sc.next();

                if ("-1".equals(op)) {
                    System.out.println("再见");
                    return;
                } else if ("-2".equals(op)) {
                    UserIndex(userName, userId);
                    return;
                } else if ("-3".equals(op)) {
                    if (selectedItems.isEmpty()) {
                        System.out.println("没有选择任何商品，无法下单！");
                    } else {
                        System.out.println("\n下单预览");
                        int shopID= Integer.parseInt(shopId);
                        confirmOrder(selectedItems, userId, userName, shopID);//确认下单调用shopServer
                        break;
                    }
                } else {
                    try {
                        int selectedId = Integer.parseInt(op);
                        selectedItems.add(selectedId); //添加用户选择的商品ID到列表
                        System.out.println("已选择商品ID：" + selectedItems);
                    } catch (NumberFormatException e) {
                        System.out.println("无效输入，请重新输入！");
                    }
                }
            } while (!"-3".equals(op));
        }
    }


    private static void displayMerchants(List<Goods> merchants) {
        for (Goods merchant : merchants) {
            System.out.println("\n商品名: " + merchant.getGoodName() +
                    "\t商品ID: " + merchant.getId() +
                    "\t价格: " + merchant.getPrice() +
                    "\t销量：" + merchant.getSales());
        }
        System.out.println("\n-1.退出");
        System.out.println("-2.返回菜单");
        System.out.println("-3.确认下单");
    }

    private void confirmOrder(List<Integer> itemIds, int userId, String userName,int shopId) {
        //下单操作
        Order discountedPrice = shopServer.confirmOrder(itemIds, userId,shopId);
        DePickPay(discountedPrice.getMoney(), userId, userName,shopId);
    }


    //接收pickPay返回值，根据返回值判断是微信还是支付宝
    private void DePickPay(double discountedPrice, int Id, String userName,int shopId) {
        String op = shopServer.pickPay(discountedPrice, Id);
        switch (op) {
            case "1":
                WechatPay(discountedPrice, userName, Id,shopId); //调起微信支付方法
                break;
            case "2":
                ZfbPay(discountedPrice, userName, Id,shopId); //调起支付宝支付方法
                return;
            case "-1":
                UserIndex(userName, Id);
                return;
            default:
                System.out.println("输入有误!");
        }
    }

    //微信支付方法
    public void WechatPay(double discountedPrice, String userName, int userId,int shopId) {
        String payType = "微信钱包";
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        System.out.println("|===================微信支付===================|");
        shopServer.load("微信支付调起中");
        System.out.println("  _      __    _______        __ \n" +
                " | | /| / /__ / ___/ /  ___ _/ /_\n" +
                " | |/ |/ / -_) /__/ _ \\/ _ `/ __/\n" +
                " |__/|__/\\__/\\___/_//_/\\_,_/\\__/ \n" +
                "                                ");
        try {
            Thread.sleep(500);
            System.out.println(GREEN + "人脸识别完成!" + RESET);
            System.out.println(String.format(GREEN + "微信支付：%1.2f 元"+ RESET,discountedPrice ));
            int i = userServer.setTheOrderByUserId(discountedPrice, userId, payType,shopId);
            if (i != 0) {
                System.out.println("订单可在个人中心查看订单状态，即将跳转");
                Thread.sleep(1300);
            } else {
                System.out.println(RED + "订单错误，钱款退款中！" + RESET);
                shopServer.load("退款中");
                System.out.println("金额" + discountedPrice + "已经原路退款！");
                System.out.println(RED + "请重新支付！" + RESET);
            }
            UserMy(userName, userId);
        } catch (InterruptedException e) {
            // 处理线程休眠期间的中断
            Thread.currentThread().interrupt(); // 恢复线程的中断状态
            System.err.println("支付过程中断: " + e.getMessage());

        }
    }

    //支付宝支付方法
    private void ZfbPay(double discountedPrice, String userName, int userId,int shopId) {
        String payType="支付宝余额";
        String GREEN = "\u001B[32m";
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        System.out.println("|===================微信支付===================|");
        shopServer.load("支付宝调起中");

        System.out.println("  ____________ ____  \n" +
                " |___  /  ____|  _ \\ \n" +
                "    / /| |__  | |_) |\n" +
                "   / / |  __| |  _ < \n" +
                "  / /__| |    | |_) |\n" +
                " /_____|_|    |____/ \n" +
                "                     \n");
        try {
            Thread.sleep(500);
            System.out.println(GREEN + "人脸识别完成!" + RESET);
            System.out.println(String.format(GREEN + "支付宝支付：%1.2f 元"+ RESET,discountedPrice ));
            int i = userServer.setTheOrderByUserId(discountedPrice, userId, payType,shopId);
            if (i != 0) {
                System.out.println("订单可在个人中心查看订单状态，即将跳转");
                Thread.sleep(1300);
            } else {
                System.out.println(RED + "订单错误，钱款退款中！" + RESET);
                shopServer.load("退款中");
                System.out.println("金额" + discountedPrice + "已经原路退款！");
                System.out.println(RED + "请重新支付！" + RESET);
            }
            UserMy(userName, userId);
        } catch (InterruptedException e) {
            // 处理线程休眠期间的中断
            Thread.currentThread().interrupt(); // 恢复线程的中断状态
            System.err.println("支付过程中断: " + e.getMessage());

        }

    }

    //订单状态
    public void OrderStatus(String userName, int userId) {
        List<Order> orderList = userServer.getOrderList(userId);
        Scanner sc = new Scanner(System.in);

        System.out.println("|===================" + userName + "的订单状态===================|");
        if (orderList.isEmpty()) {
            System.out.println("您还没有订单快去下单吧");
            UserIndex(userName, userId);
        }else {
            System.out.println("您共" + orderList.size() + "个订单");
            System.out.println("订单号\t订单金额\t收货人\t收货电话\t\t\t收货地址\t\t预计送达区间\t\t支付方式\t当前状态");
            //显示搜索结果
            if (!orderList.isEmpty()) {

                for (Order order : orderList) {
                    //当前时间+10分钟确保不会越过午夜
                    LocalTime firstDistributionTime = LocalTime.from(order.getFirst_distributionTime());
                    LocalTime latestDistributionTime = LocalTime.from(order.getLatest_distributionTime());

                    System.out.println(order.getId() + "\t\t" + order.getMoney() + "\t" + order.getConsignee() + "\t\t"
                            + order.getPhoneNum() + "\t\t" + order.getDeliveryAddress() + "\t\t" + firstDistributionTime + "--"
                            + latestDistributionTime + "\t" + order.getPayType() + "\t" + order.getState());
                }

                System.out.println("\n-1.退出");
                System.out.print("请输入您的操作：");
                String op = sc.nextLine();
                switch (op) {
                    case "-1":
                        UserIndex(userName, userId);
                        break;
                    default:
                        System.out.println("输入有误!");
                        UserIndex(userName, userId);
                }
            }
        }
    }
}
