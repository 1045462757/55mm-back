package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 用户业务测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月14日18:30:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Resource
    private UserService userService;

    @Test
    public void registerTest() {
        Result registerResult = userService.register(new User("1234567890", "12345678", "123456789@qq.com"));
        if (registerResult.getStatus().equals(Result.success)) {
            System.out.println("注册成功");
        } else {
            System.out.println(registerResult.getMessage());
        }
    }

    @Test
    public void loginTest() {
        Result loginResult = userService.login(new User("12345678", "123456789"));
        if (loginResult.getStatus().equals(Result.success)) {
            System.out.println("登录成功");
        } else {
            System.out.println(loginResult.getMessage());
        }
    }

    @Test
    public void retrieveInformation() {
        Result retrieveInformationResult = userService.retrieveInformation(2);
        if (retrieveInformationResult.getStatus().equals(Result.success)) {
            System.out.println(retrieveInformationResult.getData());
        } else {
            System.out.println(retrieveInformationResult.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void updateInformation() {
        User user = new User();
        user.setUserId(3);
        user.setEmail("修改@qq.com");
        user.setAvatar("头像地址0");
        user.setSex("男");
        Result updateInformationResult = userService.updateInformation(user);
        if (updateInformationResult.getStatus().equals(Result.success)) {
            System.out.println("修改成功");
        } else {
            System.out.println(updateInformationResult.getMessage());
        }

    }

    @Test
    @Rollback(false)
    public void updatePassword() {
        Result updatePasswordResult = userService.updatePassword(1, "12345678", "12345678910");
        if (updatePasswordResult.getStatus().equals(Result.success)) {
            System.out.println("修改密码成功");
        } else {
            System.out.println(updatePasswordResult.getMessage());
        }
    }

    @Test
    @Rollback(false)
    public void updateAvatar() {
        User user = new User();
        user.setUserId(1);
        user.setAvatar("头像地址5");
        Result updateAvatarResult = userService.updateAvatar(user);
        if (updateAvatarResult.getStatus().equals(Result.success)) {
            System.out.println(updateAvatarResult.getData());
        } else {
            System.out.println(updateAvatarResult.getMessage());
        }
    }

    @Test
    public void RetrieveUserActionsTest() {
        System.out.println(userService.RetrieveUserActions(3));
    }

    @Test
    public void resetPasswordTest() {
        Result resetPasswordResult = userService.resetPassword("2020118011@qq.com");
        if (resetPasswordResult.getStatus().equals(Result.success)) {
            System.out.println("重置密码成功,新密码:" + resetPasswordResult.getData());
        } else {
            System.out.println(resetPasswordResult.getMessage());
        }
    }

    @Test
    public void RetrieveActionCollectionTest() {
        System.out.println(userService.RetrieveActionCollection(3));
    }

    @Test
    public void RetrieveFansTest() {
        System.out.println(userService.RetrieveFans(4));
    }

    @Test
    public void RetrieveFocusTest() {
        System.out.println(userService.RetrieveFocus(4));
    }
}
