package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户业务测试类
 * <p>
 * 优化代码
 * 2019年6月1日15:33:22
 *
 * @author tiga
 * @version 1.1
 * @since 2019年5月14日18:30:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void registerTest() {
        List<User> users = new ArrayList<>();

        //参数无效
        users.add(null);
        users.add(new User(null, "12345678", "12345678@qq.com"));
        users.add(new User("12345678", null, "12345678@qq.com"));
        users.add(new User("12345678", "12345678", null));

        //账号，邮箱唯一性
        users.add(new User("12345678", "12345678", "12345678@qq.com"));
        users.add(new User("12345678", "12345678", "123456789@qq.com"));
        users.add(new User("123456789", "12345678", "12345678@qq.com"));

        for (int i = 0; i < users.size(); i++) {
            Result registerResult = userService.register(users.get(i));
            if (registerResult.getStatus().equals(Result.success)) {
                System.out.println("[注册成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[注册失败:编号:" + (i + 1) + ",原因:" + registerResult.getMessage() + "]");
            }
        }
    }

    @Test
    public void loginTest() {
        List<User> users = new ArrayList<>();

        //参数无效
        users.add(null);
        users.add(new User(null, "12345678", null));
        users.add(new User("12345678", null));

        //账号不存在
        users.add(new User("99999999", "12345678", "12345678@qq.com"));

        //密码错误
        users.add(new User("12345678", "123456789", "123456789@qq.com"));

        users.add(new User("12345678", "12345678", "123456789@qq.com"));

        for (int i = 0; i < users.size(); i++) {
            Result loginResult = userService.login(users.get(i));
            if (loginResult.getStatus().equals(Result.success)) {
                System.out.println("[登录成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[登录失败:编号:" + (i + 1) + ",原因:" + loginResult.getMessage() + "]");
            }
        }
    }

    @Test
    public void retrieveInformation() {
        List<Integer> users = new ArrayList<>();

        //参数无效
        users.add(null);

        //不存在
        users.add(99999999);

        users.add(3);

        for (int i = 0; i < users.size(); i++) {
            Result retrieveInformationResult = userService.retrieveInformation(users.get(i));
            if (retrieveInformationResult.getStatus().equals(Result.success)) {
                System.out.println("[查询用户信息成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[查询用户信息失败:编号:" + (i + 1) + ",原因:" + retrieveInformationResult.getMessage() + "]");
            }
        }
    }

    @Test
    public void updateInformation() {
        List<User> users = new ArrayList<>();

        //参数无效
        users.add(null);
        users.add(new User(null));

        //不存在
        users.add(new User(99999999));

        User user = new User();
        user.setUserId(6);
        user.setIntroduction("测试修改");
        user.setName("测试修改");
        user.setBirthday(new Date());
        user.setType("测试修改");
        user.setWeChat("测试修改");
        user.setQq("测试修改");
        user.setPhone("测试修改");
        user.setEmail("修改@qq.com");
        user.setAvatar("头像地址0");
        user.setSex("男");
        users.add(user);

        for (int i = 0; i < users.size(); i++) {
            Result updateInformationResult = userService.updateInformation(users.get(i));
            if (updateInformationResult.getStatus().equals(Result.success)) {
                System.out.println("[修改用户信息成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[修改用户信息失败:编号:" + (i + 1) + ",原因:" + updateInformationResult.getMessage() + "]");
            }
        }
    }

    @Test
    public void updatePassword() {

        //TODO 待测试

//        Result updatePasswordResult = userService.updatePassword(1, "12345678", "12345678910");
//        if (updatePasswordResult.getStatus().equals(Result.success)) {
//            System.out.println("修改密码成功");
//        } else {
//            System.out.println(updatePasswordResult.getMessage());
//        }
    }

    @Test
    public void updateAvatar() {
        List<User> users = new ArrayList<>();

        //参数无效
        users.add(null);
        users.add(new User(1, null));

        //不存在
        users.add(new User(99999999, "新头像地址"));

        users.add(new User(6, "新头像地址"));

        for (int i = 0; i < users.size(); i++) {
            Result updateAvatarResult = userService.updateAvatar(users.get(i));
            if (updateAvatarResult.getStatus().equals(Result.success)) {
                System.out.println("[修改头像成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[修改头像失败:编号:" + (i + 1) + ",原因:" + updateAvatarResult.getMessage() + "]");
            }
        }
    }

    @Test
    public void resetPasswordTest() {
        List<String> users = new ArrayList<>();

        //参数无效
        users.add(null);

        //不存在
        users.add("99999999@qq.com");

        users.add("12345678@qq.com");

        for (int i = 0; i < users.size(); i++) {
            Result resetPasswordResult = userService.resetPassword(users.get(i));
            if (resetPasswordResult.getStatus().equals(Result.success)) {
                System.out.println("[重置密码成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[重置密码失败:编号:" + (i + 1) + ",原因:" + resetPasswordResult.getMessage() + "]");
            }
        }
    }

    @Test
    public void retrieveActionsTest() {
        Result retrieveActionsResult = userService.retrieveActions(3);
        if (retrieveActionsResult.getStatus().equals(Result.success)) {
            System.out.println("[获取用户动态成功:" + retrieveActionsResult.getData() + "]");
        } else {
            System.out.println("[获取用户动态失败:原因:" + retrieveActionsResult.getMessage() + "]");
        }
    }

    @Test
    public void retrieveActionCollectionTest() {
        Result retrieveActionCollectionResult = userService.retrieveActionCollection(6);
        if (retrieveActionCollectionResult.getStatus().equals(Result.success)) {
            System.out.println("[获取用户收藏动态成功:" + retrieveActionCollectionResult.getData() + "]");
        } else {
            System.out.println("[获取用户收藏动态失败:原因:" + retrieveActionCollectionResult.getMessage() + "]");
        }
    }

    @Test
    public void retrieveFansTest() {
        Result retrieveFansResult = userService.retrieveFans(3);
        if (retrieveFansResult.getStatus().equals(Result.success)) {
            System.out.println("[获取用户粉丝成功:" + retrieveFansResult.getData() + "]");
        } else {
            System.out.println("[获取用户粉丝失败:原因:" + retrieveFansResult.getMessage() + "]");
        }
    }

    @Test
    public void retrieveFocusTest() {
        Result retrieveFocusResult = userService.retrieveFocus(3);
        if (retrieveFocusResult.getStatus().equals(Result.success)) {
            System.out.println("[获取用户关注成功:" + retrieveFocusResult.getData() + "]");
        } else {
            System.out.println("[获取用户关注失败:原因:" + retrieveFocusResult.getMessage() + "]");
        }
    }
}