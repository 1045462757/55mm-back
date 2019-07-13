package com.example.fivefivemm.controller;

import com.example.fivefivemm.controller.action.ActionController;
import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 动态控制器测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月19日13:59:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionControllerTest {

    @Resource
    private ActionController actionController;

    @Test
    public void CreateActionTest() {
        User user = new User();
        user.setUserId(1);
//        System.out.println(actionController.CreateAction(new Action(user, "测试约拍信息", "重庆", 150, "快来拍呀")));
    }

    @Test
    public void RetrieveActionTest() {
        System.out.println(actionController.RetrieveAction(20, 3));
    }

    @Test
    public void UpdateAction() {
        User user = new User();
        user.setUserId(3);

        Action action = new Action(user, "修改测试约拍信息", "重庆南岸", 200, "快来拍呀啊啊啊啊啊啊");
        action.setActionId(20);
        System.out.println(actionController.UpdateAction(action));
    }

    @Test
    public void UpdateImageTest() {
        //...
    }

    @Test
    public void DeleteActionTest() {
        User user = new User();
        user.setUserId(3);

        Action action = new Action(user, 17);
        System.out.println(actionController.DeleteAction(action));
    }

    @Test
    public void RetrieveActionsTest() {
        System.out.println(actionController.RetrieveActions(3, 2, 3));
    }

    @Test
    public void RetrieveActionsConditionsTest() {
        System.out.println(actionController.RetrieveActionsConditions(1, null, null, null, null, null));
    }
}