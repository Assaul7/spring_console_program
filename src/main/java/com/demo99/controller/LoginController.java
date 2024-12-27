package com.demo99.controller;

import com.demo99.Server.ShopServer;
import com.demo99.Server.UserServer;
import com.demo99.pojo.User;
import com.demo99.pojo.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;


@Controller
public class LoginController  {
    @Autowired
    private RegisteredController registeredController;
    @Autowired
    private UserServer userServer;
    @Autowired
    private UserController userController;
    @Autowired
    private ShopServer shopServer;
    @Autowired
    private ShopController shopController;

    public void UserLogin(){
        Scanner sc= new Scanner(System.in);
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        System.out.println("|===============欢迎登陆饿了妈==============|");
        System.out.println("1.手机号密码登录");
        System.out.println("2.用户名密码登录");
        System.out.println("3.注册新用户");
        System.out.println("-1.退出");
        System.out.print("请输入你要进行的操作号码：");

        //检查输入是否有效，根据输入调用相应的方法
        try {
            int op = Integer.parseInt(sc.next());
            switch (op) {
                case 1:
                    phoneNumLogin();
                    break;
                case 2:
                    System.out.println("\n");
                    userNameAndPasswordLogin();
                    break;
                case 3:
                    registeredController.User();
                    break;
                case -1:
                    System.out.println("感谢使用本系统，再见！");
                    return;
                default:
                    System.out.println(RED + "输入有误!" + RESET);
                    System.out.println("\n");
                    //为了让显示完全休眠1秒
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    UserLogin();
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "输入有误，请输入有效的数字!" + RESET);
            System.out.println("\n");
            UserLogin();
        }
    }

    /**
     * 用户电话号登录逻辑
     */
    public void phoneNumLogin(){
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        String phoneNum = null;
        String password = null;

        System.out.println("|===============手机号登陆（用户端）===============|");

        // 验证手机号
        while (true) {
            System.out.print("请输入手机号：");
            phoneNum = sc.next();
            if (ValidPhone(phoneNum)) {
                break;
            } else {
                System.out.println("手机号格式不正确，请重新输入！");
            }
        }

        //验证密码
        while (true) {
            System.out.print("请输入密码：");
            password = sc.next();
            if (ValidPaw(password)) {
                break;
            } else {
                System.out.println("密码不正确，请重新输入！");
            }
        }

        User i=userServer.UserLoginByPhoneNumAndPassword(phoneNum,password);
        if (i!=null){
            String username = i.getUserName();
            int Id = i.getId();
            System.out.println("\n");
            System.out.println(GREEN+"登陆成功！欢迎您"+RESET);
            userController.UserIndex(username,Id);
        }else {
            System.out.println("\n");
            System.out.println(RED+"账号或密码错误！"+RESET);
            phoneNumLogin();
        }
    }


    /**
     * 用户名密码登录逻辑
     */
    public void userNameAndPasswordLogin(){
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        String userName = null;
        String password = null;

        System.out.println("|===============用户名登陆（用户端）===============|");

        // 验证手机号
        while (true) {
            System.out.print("请输入账号用户名：");
            userName = sc.next();
            if (ValidUserName(userName)) {
                break;
            } else {
                System.out.println("用户名不能为空，请重新输入！");
            }
        }

        //验证密码
        while (true) {
            System.out.print("请输入密码：");
            password = sc.next();
            if (ValidPaw(password)) {
                break;
            } else {
                System.out.println("密码不能为空，请重新输入！");
            }
        }

        User u=userServer.UserLoginByUserNameAndPassword(userName,password);
        if (u!=null){
            String username = u.getUserName();
            int Id = u.getId();
            System.out.println("\n");
            System.out.println(GREEN+"登陆成功！欢迎您"+RESET);
            userController.UserIndex(username,Id);
        }else {
            System.out.println("\n");
            System.out.println(RED+"账号或密码错误！"+RESET);
            userNameAndPasswordLogin();
        }
    }

    /**
     * 用户验证方法
     */
    private static boolean ValidPhone(String phone) {
        //手机号验证逻辑
        return phone != null && !phone.isEmpty() && phone.matches("^1[3-9]\\d{9}$");
    }
    private static boolean ValidUserName(String userName) {
        //用户名验证逻辑
        return userName != null && !userName.isEmpty();
    }
    private boolean ValidPaw(String password) {
        //密码验证逻辑
        return password != null && !password.isEmpty();
    }


    public void shopLogin(){
        Scanner sc= new Scanner(System.in);
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";

        System.out.println("|===============饿了妈商家营业==============|");
        System.out.println("1.账号电话密码登录");
        System.out.println("2.商家入驻");
        System.out.println("-1.退出");
        System.out.print("请输入你要进行的操作号码：");

        //检查输入是否有效，根据输入调用相应的方法
        try {
            int op = Integer.parseInt(sc.next());
            switch (op) {
                case 1:
                    shopPhoneNumLogin();
                    break;
                case 2:
                    registeredController.shopRegister();
                    userNameAndPasswordLogin();
                    break;
                case -1:
                    System.out.println("感谢使用本系统，再见！");
                    return;
                default:
                    System.out.println(RED + "输入有误!" + RESET);
                    System.out.println("\n");
                    //为了让显示完全休眠1秒
                    try {
                        Thread.sleep(2000);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    shopLogin();
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "输入有误，请输入有效的数字!" + RESET);
            System.out.println("\n");
            shopLogin();
        }
    }

    //商家电话密码登录
    public void shopPhoneNumLogin(){
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        String phoneNum = null;
        String password = null;

        System.out.println("|===============账号密码登陆（商家端）===============|");

        // 验证手机号
        while (true) {
            System.out.print("请输入商家电话号：");
            phoneNum = sc.next();
            if (phoneNum!=null) {
                break;
            } else {
                System.out.println("电话号不能为空，请重新输入！");
            }
        }

        //验证密码
        while (true) {
            System.out.print("请输入密码：");
            password = sc.next();
            if (ValidPaw(password)) {
                break;
            } else {
                System.out.println("密码不正确，请重新输入！");
            }
        }

        business s=shopServer.shopLoginByPhoneNumAndPassword(phoneNum,password);
        if (s!=null){
            String shopName = s.getShopName();
            System.out.println("\n");
            System.out.println(GREEN+"商家你好，"+shopName+"欢迎上线！"+RESET);
           shopController.shopIndex(s.getId(),shopName);
        }else {
            System.out.println("\n");
            System.out.println(RED+"账号或密码错误！"+RESET);
            shopPhoneNumLogin();
        }
    }
}
