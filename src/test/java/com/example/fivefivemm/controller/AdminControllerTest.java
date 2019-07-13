package com.example.fivefivemm.controller;

import com.example.fivefivemm.controller.admin.AdminController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminControllerTest {

    @Resource
    private AdminController adminController;

    @Test
    public void findAllUsersTest() {
        System.out.println(adminController.getAllUsers());
    }
}
