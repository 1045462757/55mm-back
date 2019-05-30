package com.example.fivefivemm.controller;

import com.example.fivefivemm.controller.user.UserController;
import com.example.fivefivemm.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 用户控制器测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月14日18:33:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Resource
    private UserController userController;

    @Test
    public void registerTest() {
        System.out.println(userController.register(new User("123456789", "1234567", "123456789@qq.com")));
    }

    @Test
    public void loginTest() {
        System.out.println(userController.login(new User("12345678", "12345678910")));
    }

    @Test
    public void retrieveInformationTest() {
        System.out.println(userController.retrieveInformation(1));
    }

    @Test
    public void updateInformationTest() {
        User user = new User();
        user.setUserId(2);
        user.setSex("女");
        user.setType("模特");
        user.setEmail("test@qq.com");
        user.setBirthday(new Date());
        System.out.println(userController.updateInformation(user));
    }

    @Test
    public void updateAvatarTest() {
        //...
    }

    @Test
    public void updatePasswordTest() {
        System.out.println(userController.updatePassword(2, "123456789", "12345678"));
    }

    @Test
    public void ResetPasswordTest() {
        System.out.println(userController.resetPassword("123456789@qq.com"));
    }

    @Test
    public void retrieveOtherInformationTest() {
        System.out.println(userController.retrieveOtherInformation(1, 2));
    }

    @Test
    public void RetrieveFocusTest() {
        System.out.println(userController.RetrieveFocus(4));
    }

    @Test
    public void RetrieveFansTest() {
        System.out.println(userController.RetrieveFans(4));
    }
}
