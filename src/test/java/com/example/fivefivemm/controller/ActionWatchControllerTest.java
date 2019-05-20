package com.example.fivefivemm.controller;

import com.example.fivefivemm.controller.relation.ActionWatchController;
import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 约拍记录控制器测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月20日19:53:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionWatchControllerTest {

    @Resource
    private ActionWatchController actionWatchController;

    @Test
    public void CreateActionWatchTest() {
        User user = new User();
        user.setUserId(1);

        Action action = new Action();
        action.setActionId(3);

        System.out.println(actionWatchController.CreateActionWatch(new ActionWatch(user, action)));
    }

    @Test
    public void DeleteActionWatchTest() {
        User user = new User();
        user.setUserId(1);

        Action action = new Action();
        action.setActionId(3);

        System.out.println(actionWatchController.DeleteActionWatch(new ActionWatch(user, action)));
    }
}
