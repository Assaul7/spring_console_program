package com.demo99;

import com.demo99.Server.Impl.shopServerImpl;
import com.demo99.Server.ShopServer;
import com.demo99.controller.UserController;
import com.demo99.pojo.User;
import com.demo99.pojo.business;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class Demo99ApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
        userController.OrderStatus("张三", 23);
    }

}
