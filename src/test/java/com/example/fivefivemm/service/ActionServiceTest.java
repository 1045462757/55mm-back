package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.BusinessResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态业务测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月19日13:57:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionServiceTest {

    @Resource
    private ActionService actionService;

    @Test
    public void CreateActionTest() {
        User user = new User();
        user.setUserId(1);
        BusinessResult createActionResult = actionService.CreateAction(new Action(user, "测试约拍信息", "重庆", 150, "快来拍呀"));
        if (createActionResult.getStatus()) {
            System.out.println("发表成功");
        } else {
            System.out.println("[发表动态失败:错误代码:" + createActionResult.getErrorCode() + ",错误原因:" + createActionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void RetrieveActionTest() {
        BusinessResult retrieveActionResult = actionService.RetrieveAction(3);
        if (retrieveActionResult.getStatus()) {
            System.out.println(retrieveActionResult.getData());
        } else {
            System.out.println("[查询动态失败:错误代码:" + retrieveActionResult.getErrorCode() + ",错误原因:" + retrieveActionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void UpdateActionTest() {
        User user = new User();
        user.setUserId(3);

        Action action = new Action(user, "修改测试约拍信息", "重庆南岸", 200, "快来拍呀啊啊啊啊啊啊");
        action.setActionId(18);

        BusinessResult updateActionResult = actionService.UpdateAction(action);
        if (updateActionResult.getStatus()) {
            System.out.println("修改成功");
        } else {
            System.out.println("[修改动态失败:错误代码:" + updateActionResult.getErrorCode() + ",错误原因:" + updateActionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void DeleteActionTest() {
        User user = new User();
        user.setUserId(3);

        Action action = new Action(user, 19);

        BusinessResult deleteActionResult = actionService.DeleteAction(action);
        if (deleteActionResult.getStatus()) {
            System.out.println("删除成功");
        } else {
            System.out.println("[删除动态失败:错误代码:" + deleteActionResult.getErrorCode() + ",错误原因:" + deleteActionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void findActionByPageTest() {

        BusinessResult result = actionService.findActionByPage(1, null, null, null, null, null);
        if (result.getStatus()) {
            Page<Action> actionPage = (Page<Action>) result.getData();

            System.out.println("总页数:" + actionPage.getTotalPages());
            System.out.println("总元素:" + actionPage.getTotalElements());
            System.out.println("当前页:" + (actionPage.getNumber() + 1));
            System.out.println("当前页元素个数:" + actionPage.getNumberOfElements());
            System.out.println("当前页元素:" + actionPage.getContent());

        } else {
            System.out.println("[分页查找动态失败:错误代码:" + result.getErrorCode() + ",错误原因:" + result.getErrorMessage() + "]");
        }
    }

    @Test
    public void findActionByAuthorTest() {
        BusinessResult result = actionService.findActionByAuthor(3, 1);
        if (result.getStatus()) {
            Page<Action> actionPage = (Page<Action>) result.getData();
            System.out.println("总页数:" + actionPage.getTotalPages());
            System.out.println("总元素:" + actionPage.getTotalElements());
            System.out.println("当前页:" + (actionPage.getNumber() + 1));
            System.out.println("当前页元素个数:" + actionPage.getNumberOfElements());
            System.out.println("当前页元素:" + actionPage.getContent());
        } else {
            System.out.println("[分页查找用户的动态失败:错误代码:" + result.getErrorCode() + ",错误原因:" + result.getErrorMessage() + "]");
        }
    }

    @Test
    public void findActionByCollectionTest() {
        BusinessResult result = actionService.findActionByCollection(3, 1);
        if (result.getStatus()) {
            Page<ActionCollection> actionPage = (Page<ActionCollection>) result.getData();

            List<Action> actionList = new ArrayList<>();
            for (ActionCollection actionCollection : actionPage) {
                actionList.add(actionCollection.getCollectAction());
            }

            System.out.println("总页数:" + actionPage.getTotalPages());
            System.out.println("总元素:" + actionPage.getTotalElements());
            System.out.println("当前页:" + (actionPage.getNumber() + 1));
            System.out.println("当前页元素个数:" + actionPage.getNumberOfElements());
            System.out.println("当前页元素:" + actionPage.getContent());

            for (Action action : actionList) {
                System.out.println(action);
            }
        } else {
            System.out.println("[分页查找用户收藏的动态失败:错误代码:" + result.getErrorCode() + ",错误原因:" + result.getErrorMessage() + "]");
        }
    }
}
