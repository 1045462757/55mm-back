package com.example.fivefivemm.controller.relation;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.service.ActionWatchService;
import com.example.fivefivemm.service.MessageService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
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
 * <p>
 * 优化大代码
 * 2019年6月14日19:47:22
 *
 * @author tiga
 * @version 1.3
 * @since 2019年5月20日18:34:16
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ActionWatchController {

    @Resource
    private ActionWatchService actionWatchService;

    @Resource
    private MessageService messageService;

//    @Resource
//    private SendSmsService sendSmsService;

//    @Resource
//    private UserService userService;

    /**
     * 生成约拍记录
     *
     * @param actionWatch 约拍记录对象
     * @return errorCode:30001 errorMessage:参数无效:动态Id,约拍者用户Id为空
     * errorCode:30002 errorMessage:不存在的动态 errorCode:30003 errorMessage:不存在的用户 errorCode:30004 errorMessage:约拍记录已存在
     */
    @PostMapping("/actionWatch")
    @ApiOperation(value = "生成约拍记录", notes = "需提供动态Id，约拍者用户Id")
    public ResponseEntity CreateActionWatch(@RequestBody ActionWatch actionWatch) {
        BusinessResult createActionWatchResult = actionWatchService.CreateActionWatch(actionWatch);
        BusinessResult createMessageResult = messageService.CreateMessage(new Message(actionWatch.getAction(), actionWatch.getWatcher(), actionWatch.getWatcher().getUserId()));
        if (!createActionWatchResult.getStatus()) {
            return ResponseEntity.ok(new ResponseBody(createActionWatchResult.getErrorCode(), createActionWatchResult.getErrorMessage()));
        } else if (!createMessageResult.getStatus()) {
            return ResponseEntity.ok(new ResponseBody(createMessageResult.getErrorCode(), createMessageResult.getErrorMessage()));
        } else {
            //发送短信通知
//            BusinessResult retrieveInformationResult = userService.retrieveInformation(actionWatch.getWatcher().getUserId());
//            if (retrieveInformationResult.getStatus()) {
//                User existUser = (User) retrieveInformationResult.getData();
//                if (existUser != null) {
//                    sendSmsService.SendSmsForMessage(existUser.getPhone());
//                }
//            }
            return ResponseEntity.ok(null);
        }
    }

    /**
     * 删除约拍记录
     *
     * @param actionWatch 约拍记录对象
     * @return errorCode:30011 errorMessage:参数无效:动态Id,约拍者用户Id为空  errorCode:30012 errorMessage:该约拍记录不存在
     */
    @DeleteMapping("/actionWatch")
    @ApiOperation(value = "删除约拍记录", notes = "需提供动态Id，约拍者用户Id")
    public ResponseEntity DeleteActionWatch(@RequestBody ActionWatch actionWatch) {
        BusinessResult deleteActionWatchResult = actionWatchService.DeleteActionWatch(actionWatch);
        BusinessResult deleteMessageResult = messageService.DeleteMessage(new Message(actionWatch.getAction(), actionWatch.getWatcher()));
        if (!deleteActionWatchResult.getStatus()) {
            return ResponseEntity.ok(new ResponseBody(deleteActionWatchResult.getErrorCode(), deleteActionWatchResult.getErrorMessage()));
        } else if (!deleteMessageResult.getStatus()) {
            return ResponseEntity.ok(new ResponseBody(deleteMessageResult.getErrorCode(), deleteMessageResult.getErrorMessage()));
        } else {
            return ResponseEntity.ok(null);
        }
    }
}