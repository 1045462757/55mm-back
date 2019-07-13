package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.relation.UserCollection;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.BusinessResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
 * <p>
 * 又优化了代码
 * 2019年6月14日10:26:58
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
            BusinessResult registerResult = userService.register(users.get(i));
            if (registerResult.getStatus()) {
                System.out.println("[注册成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[注册失败:编号:" + (i + 1) + ",错误代码:" + registerResult.getErrorCode() + ",错误原因:" + registerResult.getErrorMessage() + "]");
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
            BusinessResult loginResult = userService.login(users.get(i));
            if (loginResult.getStatus()) {
                System.out.println("[登录成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[登录失败:编号:" + (i + 1) + ",错误代码:" + loginResult.getErrorCode() + ",错误原因:" + loginResult.getErrorMessage() + "]");

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
            BusinessResult retrieveInformationResult = userService.retrieveInformation(users.get(i));
            if (retrieveInformationResult.getStatus()) {
                System.out.println("[查询用户信息成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[查询用户信息失败:编号:" + (i + 1) + ",错误代码:" + retrieveInformationResult.getErrorCode() + ",错误原因:" + retrieveInformationResult.getErrorMessage() + "]");

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
            BusinessResult updateInformationResult = userService.updateInformation(users.get(i));
            if (updateInformationResult.getStatus()) {
                System.out.println("[修改用户信息成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[修改用户信息失败:编号:" + (i + 1) + ",原因:" + updateInformationResult.getErrorMessage() + "]");
            }
        }
    }

    @Test
    public void updatePassword() {

        //TODO 待测试

//        BusinessResult updatePasswordResult = userService.updatePassword(1, "12345678", "12345678910");
//        if (updatePasswordResult.getStatus()) {
//            System.out.println("修改密码成功");
//        } else {
//            System.out.println("[修改用户信息失败:原因:" + updatePasswordResult.getErrorMessage() + "]");
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
            BusinessResult updateAvatarResult = userService.updateAvatar(users.get(i));
            if (updateAvatarResult.getStatus()) {
                System.out.println("[修改头像成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[修改头像失败:编号:" + (i + 1) + ",错误代码:" + updateAvatarResult.getErrorCode() + ",错误原因:" + updateAvatarResult.getErrorMessage() + "]");

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
            BusinessResult resetPasswordResult = userService.resetPassword(users.get(i));
            if (resetPasswordResult.getStatus()) {
                System.out.println("[重置密码成功:编号:" + (i + 1) + "]");
            } else {
                System.out.println("[重置密码失败:编号:" + (i + 1) + ",错误代码:" + resetPasswordResult.getErrorCode() + ",错误原因:" + resetPasswordResult.getErrorMessage() + "]");

            }
        }
    }

    @Test
    public void findFansTest() {
        BusinessResult result = userService.findFans(3, 1);
        if (result.getStatus()) {
            Page<UserCollection> userCollectionPage = (Page<UserCollection>) result.getData();

            List<User> userList = new ArrayList<>();
            for (UserCollection userCollection : userCollectionPage) {
                userList.add(userCollection.getFans());
            }

            System.out.println("总页数:" + userCollectionPage.getTotalPages());
            System.out.println("总元素:" + userCollectionPage.getTotalElements());
            System.out.println("当前页:" + (userCollectionPage.getNumber() + 1));
            System.out.println("当前页元素个数:" + userCollectionPage.getNumberOfElements());
            System.out.println("当前页元素:" + userCollectionPage.getContent());

            for (User user : userList) {
                System.out.println(user);
            }
        } else {
            System.out.println("[获取用户粉丝失败:" + "错误代码:" + result.getErrorCode() + ",错误原因:" + result.getErrorMessage() + "]");
        }
    }

    @Test
    public void findFocusTest() {
        BusinessResult result = userService.findFocus(3, 1);
        if (result.getStatus()) {
            Page<UserCollection> userCollectionPage = (Page<UserCollection>) result.getData();

            List<User> userList = new ArrayList<>();
            for (UserCollection userCollection : userCollectionPage) {
                userList.add(userCollection.getFocus());
            }

            System.out.println("总页数:" + userCollectionPage.getTotalPages());
            System.out.println("总元素:" + userCollectionPage.getTotalElements());
            System.out.println("当前页:" + (userCollectionPage.getNumber() + 1));
            System.out.println("当前页元素个数:" + userCollectionPage.getNumberOfElements());
            System.out.println("当前页元素:" + userCollectionPage.getContent());

            for (User user : userList) {
                System.out.println(user);
            }
        } else {
            System.out.println("[获取用户关注失败:" + "错误代码:" + result.getErrorCode() + ",错误原因:" + result.getErrorMessage() + "]");
        }
    }

    @Test
    public void findAllUsersTest() {
        System.out.println(userService.findAllUsers());
    }
}