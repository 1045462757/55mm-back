package com.example.fivefivemm.controller.action;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.service.ActionService;
import com.example.fivefivemm.service.ActionWatchService;
import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

/**
 * 动态控制器
 * <p>
 * 添加获取用户是否对动态进行了约拍
 * <p>
 * 添加获取全部动态接口，修改获取动态参数userId为非必需，发表动态后返回该动态
 * 2019年5月21日18:19:42
 * <p>
 * 添加判断动态是否收藏
 * 2019年5月27日13:35:39
 * <p>
 * 添加获取用户收藏的动态
 * 2019年5月27日19:44:23
 *
 * @author tiga
 * @version 1.3
 * @since 2019年5月19日13:20:51
 */
//生产环境
@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
//@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class ActionController {

    @Resource
    private ActionService actionService;

    @Resource
    private UserService userService;

    @Resource
    private ActionWatchService actionWatchService;

    @Resource
    private CollectionService collectionService;


    /**
     * 发表动态
     *
     * @param action 动态对象
     * @return success: data:保存的动态
     * failed message
     * 101
     * 1.action对象为空
     * 2.作者Id为空
     */
    @PostMapping("/action")
    @ResponseBody
    public String CreateAction(@RequestBody Action action) {
        Result createActionResult = actionService.CreateAction(action);
        if (createActionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, Utility.ActionBody((Action) createActionResult.getData()));
        } else {
            return Utility.ResultBody(101, createActionResult.getMessage(), null);
        }
    }

    /**
     * 查询动态
     *
     * @param actionId 动态Id
     * @param userId   用户Id
     * @return success data:action对象
     * failed message
     * 102
     * 1.action对象为空
     * 2.该动态不存在
     */
    @GetMapping("/action")
    @ResponseBody
    public String RetrieveAction(@RequestParam Integer actionId, @RequestParam(required = false) Integer userId) {
        Result retrieveActionResult = actionService.RetrieveAction(actionId);
        if (retrieveActionResult.getStatus().equals(Result.success)) {
            Map<String, Object> actionMap = Utility.ActionBody((Action) retrieveActionResult.getData());
            if (userId != null) {
                actionMap.put("isWatched", actionWatchService.RetrieveActionWatch(new ActionWatch(new User(userId), new Action(actionId))));
                actionMap.put("isCollected", collectionService.findActionCollection(userId, actionId));
            }
            return Utility.ResultBody(200, null, actionMap);
        } else {
            return Utility.ResultBody(102, retrieveActionResult.getMessage(), null);
        }
    }

    /**
     * 修改动态
     *
     * @param action 动态对象
     * @return failed message
     * 103
     * 1.action对象为空
     * 2.该动态不存在
     * 3.没有权限修改，您不是该动态的作者
     */
    @PutMapping("/action")
    @ResponseBody
    public String UpdateAction(@RequestBody Action action) {
        Result updateActionResult = actionService.UpdateAction(action);
        if (updateActionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(103, updateActionResult.getMessage(), null);
        }
    }

    /**
     * 上传图片
     *
     * @param image 图片
     * @return success data:图片地址
     * failed message
     * 104
     * 1.图片保存失败
     */
    @PostMapping("/action/image")
    @ResponseBody
    public String UpdateImage(@RequestParam MultipartFile image) {
        String imageAddress = Utility.saveImage(image, 2);
        if (imageAddress != null) {
            return Utility.ResultBody(200, null, imageAddress);
        } else {
            return Utility.ResultBody(104, "图片保存失败", null);
        }
    }

    /**
     * 删除动态
     *
     * @param action 动态对象
     * @return failed message
     * 105
     * 1.action对象为空
     * 2.该动态不存在
     * 3.没有权限删除，您不是该动态的作者
     */
    @DeleteMapping("/action")
    @ResponseBody
    public String DeleteAction(@RequestBody Action action) {
        Result deleteActionResult = actionService.DeleteAction(action);
        if (deleteActionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(105, deleteActionResult.getMessage(), null);
        }
    }

    /**
     * 获取动态集合
     *
     * @param userId 用户Id
     * @param type   查询类型 1.用户自己的动态 2.所有动态 3.用户收藏的动态
     * @return success data:动态集合
     * failed message
     * 106 错误的查询类型
     */
    @GetMapping("/actions")
    @ResponseBody
    public String RetrieveActions(@RequestParam(required = false) Integer userId, @RequestParam Integer type) {
        if (type == 1) {
            Set<Action> userActionSets = userService.RetrieveUserActions(userId);
            if (userActionSets == null || userActionSets.isEmpty()) {
                return Utility.ResultBody(200, null, Utility.ActionListBody(null));
            }
            //对动态集合进行排序
            List<Action> ActionList = new ArrayList<>(userActionSets);
            Collections.sort(ActionList, new Comparator<Action>() {
                @Override
                public int compare(Action o1, Action o2) {
                    return o1.getActionId() > o2.getActionId() ? -1 : 1;
                }
            });
            return Utility.ResultBody(200, null, Utility.ActionListBody(ActionList));
        } else if (type == 2) {
            List<Action> actionList = actionService.RetrieveAllAction();
            Collections.sort(actionList, new Comparator<Action>() {
                @Override
                public int compare(Action o1, Action o2) {
                    return o1.getActionId() > o2.getActionId() ? -1 : 1;
                }
            });
            return Utility.ResultBody(200, null, Utility.ActionListBody(actionList));
        } else if (type == 3) {
            Set<Action> actionCollectionSets = userService.RetrieveActionCollection(userId);
            if (actionCollectionSets == null || actionCollectionSets.isEmpty()) {
                return Utility.ResultBody(200, null, Utility.ActionListBody(null));
            }
            List<Action> ActionList = new ArrayList<>(actionCollectionSets);
            //对动态集合进行排序
            Collections.sort(ActionList, new Comparator<Action>() {
                @Override
                public int compare(Action o1, Action o2) {
                    return o1.getActionId() > o2.getActionId() ? -1 : 1;
                }
            });
            return Utility.ResultBody(200, null, Utility.ActionListBody(ActionList));
        } else {
            return Utility.ResultBody(106, "错误的查询类型", null);
        }
    }

}
