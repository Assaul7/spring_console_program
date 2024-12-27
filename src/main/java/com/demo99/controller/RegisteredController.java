package com.demo99.controller;


import com.demo99.Server.ShopServer;
import com.demo99.Server.UserServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Controller
public class RegisteredController {
    @Autowired
    private UserServer userServer;
    @Autowired
    private ShopServer shopServer;

    /**
     * 用户注册方法
     * phoneNum手机号
     * UserName用户名
     * address地址
     */
    public void User(){
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        String phoneNum = null;
        String userName = null;
        String address = null;
        String password = null;

        System.out.println("|===============用户注册===============|");

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

        // 验证用户名
        while (true) {
            System.out.print("请输入用户名：");
            userName = sc.next();
            if (ValidUserName(userName)) {
                break;
            } else {
                System.out.println("用户名格式不正确，请重新输入！");
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


        // 验证地址
        while (true) {
            System.out.print("请输入地址：");
            address = sc.next();
            if (ValidAddress(address)) {
                System.out.println("|==========注册成功！==========|");
                System.out.println("手机号：" + phoneNum);
                System.out.println("用户名：" + userName);
                System.out.println("地址：" + address);

                //调用注册写入数据库
                int a=userServer.setUserInfo(phoneNum,userName,password,address);
                if (a!=0){
                    System.out.println(GREEN+"欢迎您！"+userName+",账号"+phoneNum+",注册成功请牢记密码！准备跳转！"+RESET);

                    //为了让显示完全休眠0.5秒
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //休眠1秒，循环3次等待3秒
                    for (int i = 3; i >= 0; i--) {
                        if (i==0){
                            System.out.println("跳转中...");
                        }else {
                            System.out.println("跳转中，预计等待" + GREEN + i + RESET + "秒.....");
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("\n");

                    break;

                }else {
                    System.out.println(RED+"注册失败，请检查网络重新注册！"+RESET);
                    User();
                }
            } else {
                System.out.println("地址不能为空，请重新输入！");
            }
        }
    }


    /**
     * 商家注册方法
     * phoneNum电话号
     * shopName店铺名
     * address地址
     */
    public void shopRegister(){
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        String phoneNum = null;
        String shopName = null;
        String address = null;
        String password = null;

        System.out.println("|===============商家入驻===============|");

        // 验证电话号
        while (true) {
            System.out.print("请输入电话号：");
            phoneNum = sc.next();
            if (ValidPhone(phoneNum)) {
                break;
            } else {
                System.out.println("电话号格式不正确，请重新输入！");
            }
        }

        // 验证店铺名
        while (true) {
            System.out.print("请输入店铺名：");
            shopName = sc.next();
            if (ValidUserName(shopName)) {
                break;
            } else {
                System.out.println("店铺格式不正确，请重新输入！");
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


        //验证地址
        while (true) {
            System.out.print("请输入店铺地址：");
            address = sc.next();
            if (ValidAddress(address)) {
                System.out.println("|==========入驻成功！==========|");
                System.out.println("电话号：" + phoneNum);
                System.out.println("店铺名：" + shopName);
                System.out.println("店铺地址：" + address);

                //调用注册写入数据库
                int b=shopServer.setShopInfo(phoneNum,shopName,password,address);
                if (b!=0){
                    System.out.println(GREEN+"商家你好！"+shopName+",账号"+phoneNum+",入驻成功请牢记密码！准备跳转！"+RESET);

                    //为了让显示完全休眠0.5秒
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //休眠1秒，循环3次等待3秒
                    for (int i = 3; i >= 0; i--) {
                        if (i==0){
                            System.out.println("前往中...");
                        }else {
                            System.out.println("前往营业中，预计等待" + GREEN + i + RESET + "秒.....");
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("\n");

                    break;

                }else {
                    System.out.println(RED+"注册失败，请检查网络重新注册！"+RESET);
                    User();
                }
            } else {
                System.out.println("地址不能为空，请重新输入！");
            }
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
    private static boolean ValidAddress(String address) {
        //地址验证逻辑
        return address != null;
    }
    private boolean ValidPaw(String password) {
        //密码验证逻辑
        return password != null && !password.isEmpty();
    }


}
