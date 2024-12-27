package com.demo99.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Objects;
import java.util.Scanner;

@Controller
public class IndexController {
    @Autowired
    private RegisteredController registeredController;
    @Autowired
    private LoginController loginController;

    public void menuPhoto() {
        String RESET = "\u001B[0m";
        String BULLET = "\u001B[36m";
        System.out.print(BULLET+"\n" +
                "\t ______     __         __    __    \n" +
                "\t/\\  ___\\   /\\ \\       /\\ \"-./  \\   \n" +
                "\t\\ \\  __\\   \\ \\ \\____  \\ \\ \\-./\\ \\  \n" +
                "\t \\ \\_____\\  \\ \\_____\\  \\ \\_\\ \\ \\_\\ \n" +
                "\t  \\/_____/   \\/_____/   \\/_/  \\/_/ \n\n\t\t\t\t\t\t1.0.0\n\n"+RESET);
        menu();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);

        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        System.out.println("|===============" + "欢迎使用饿了妈外卖点餐系统" + "===============|");
        System.out.println("1. 用户注册");
        System.out.println("2. 商家入驻");
        System.out.println("3. 用户登录");
        System.out.println("4. 商家营业");
        System.out.println(RED + "-1. 退出" + RESET);
        System.out.print("请输入你要进行的操作号码：");

        //检查输入是否有效，根据输入调用相应的方法
        try {
            int op = Integer.parseInt(sc.next());
            System.out.println("\n");
            switch (op) {
                case 1:
                    registeredController.User();
                    menu();
                    break;
                case 2:
                    registeredController.shopRegister();
                    menu();
                    break;
                case 3:
                    loginController.UserLogin();
                    break;
                case 4:
                    loginController.shopPhoneNumLogin();
                    break;
                case -1:
                    System.out.println("感谢使用本系统，再见！");
                    return;
                default:
                    System.out.println(RED + "输入有误!" + RESET);
                    menu();
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "输入有误，请输入有效的数字!" + RESET);
            menu();
        }
    }
}
