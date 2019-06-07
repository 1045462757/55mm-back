package com.example.fivefivemm.controller.relation;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.service.ActionWatchService;
import com.example.fivefivemm.service.MessageService;
import com.example.fivefivemm.service.SendSmsService;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 约拍记录控制器
 * <p>
 * 增加信息生成
 * 2019年5月23日10:29:16
 * <p>
 * 增加短信通知
 * 2019年5月29日22:09:55
 *
 * @author tiga
 * @version 1.2
 * @since 2019年5月20日18:34:16
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class ActionWatchController {

    @Resource
    private ActionWatchService actionWatchService;

    @Resource
    private MessageService messageService;

    @Resource
    private SendSmsService sendSmsService;

    @Resource
    private UserService userService;

    /**
     * 生成约拍记录
     *
     * @param actionWatch 约拍记录对象
     * @return failed message
     * 101
     * 1.actionWatch对象为空
     * 2.用户Id或动态Id为空
     * 102
     * 1.参数不合法
     * 2.消息已存在
     */
    @PostMapping("/actionWatch")
    @ResponseBody
    public String CreateActionWatch(@RequestBody ActionWatch actionWatch) {
        Result createActionWatchResult = actionWatchService.CreateActionWatch(actionWatch);
        Result createMessageResult = messageService.CreateMessage(new Message(actionWatch.getAction(), actionWatch.getWatcher(), actionWatch.getWatcher().getUserId()));
        if (!createActionWatchResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(101, createActionWatchResult.getMessage(), null);
        } else if (!createMessageResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(102, createMessageResult.getMessage(), null);
        } else {
            //发送短信通知
//            Result retrieveInformationResult = userService.retrieveInformation(actionWatch.getWatcher().getUserId());
//            if (retrieveInformationResult.getStatus().equals(Result.success)) {
//                User existUser = (User) retrieveInformationResult.getData();
//                if (existUser != null) {
//                    sendSmsService.SendSmsForMessage(existUser.getPhone());
//                }
//            }
            return Utility.ResultBody(200, null, null);
        }
    }

    /**
     * 删除约拍记录
     *
     * @param actionWatch 约拍记录对象
     * @return failed message
     * 102
     * 1.actionWatch对象为空
     * 2.用户Id或动态Id为空
     * 3.该约拍记录不存在
     */
    @DeleteMapping("/actionWatch")
    @ResponseBody
    public String DeleteActionWatch(@RequestBody ActionWatch actionWatch) {
        Result deleteActionWatchResult = actionWatchService.DeleteActionWatch(actionWatch);
        Result deleteMessageResult = messageService.DeleteMessage(new Message(actionWatch.getAction(), actionWatch.getWatcher()));
        if (!deleteActionWatchResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(102, deleteActionWatchResult.getMessage(), null);
        } else if (!deleteMessageResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(103, deleteMessageResult.getMessage(), null);
        } else {
            return Utility.ResultBody(200, null, null);
        }
    }
}
