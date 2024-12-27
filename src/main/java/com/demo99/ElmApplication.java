package com.demo99;

import com.demo99.Server.ShopServer;
import com.demo99.controller.IndexController;
import com.demo99.pojo.business;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ElmApplication implements CommandLineRunner {

    @Autowired
    private ShopServer shopService;
    @Autowired
    private IndexController indexController;

    public static void main(String[] args) {
        SpringApplication.run(ElmApplication.class, args);
    }

    /**
     * 程序入口
     *
     */

    @Override
    public void run(String... args) throws Exception {
        indexController.menuPhoto();
    }
}