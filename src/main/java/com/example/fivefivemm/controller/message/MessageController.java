package com.example.fivefivemm.controller.message;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.service.MessageService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import com.example.fivefivemm.utility.Utility;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息控制器
 * <p>
 * 优化大代码
 * 2019年6月14日20:55:35
 *
 * @author tiga
 * @version 1.1
 * @since 2019年5月22日19:02:20
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 根据约拍者Id查找消息
     *
     * @param watcherId 约拍者Id
     * @param pageIndex 页数
     * @return data:Page<Message>对象 errorCode:40011 errorMessage:参数无效:约拍者Id,页数为空 errorCode:40012 errorMessage:不存在的用户
     */
    @GetMapping("/message/watcherId")
    @ApiOperation(value = "根据约拍者Id查找消息", notes = "需提供约拍者用户Id")
    @SuppressWarnings("unchecked")
    public ResponseEntity RetrieveMessageForWatcher(@RequestParam Integer watcherId, @RequestParam Integer pageIndex) {
        BusinessResult retrieveForWatcherResult = messageService.RetrieveForWatcher(watcherId, pageIndex);
        if (retrieveForWatcherResult.getStatus()) {
            return ResponseEntity.ok(Utility.PageMessageMap((Page<Message>) retrieveForWatcherResult.getData(), 2));
        }

        return ResponseEntity.ok(new ResponseBody(retrieveForWatcherResult.getErrorCode(), retrieveForWatcherResult.getErrorMessage()));
    }

    /**
     * 根据动态作者Id查找消息
     *
     * @param actionAuthorId 动态作者Id
     * @param pageIndex      页数
     * @return data:Page<Message>对象 errorCode:40021 errorMessage:参数无效:动态作者Id,页数为空 errorCode:40022 errorMessage:不存在的用户
     */
    @GetMapping("/message/actionAuthorId")
    @ApiOperation(value = "根据动态作者Id查找消息", notes = "需提供动态作者用户Id")
    @SuppressWarnings("unchecked")
    public ResponseEntity RetrieveMessageForActionAuthor(@RequestParam Integer actionAuthorId, @RequestParam Integer pageIndex) {
        BusinessResult retrieveForActionAuthorResult = messageService.RetrieveForActionAuthorId(actionAuthorId, pageIndex);
        if (retrieveForActionAuthorResult.getStatus()) {
            Page<Message> messagePage = (Page<Message>) retrieveForActionAuthorResult.getData();
            int notReadNum = Utility.getNotReadMessage(messagePage.getContent());
            Map<String, Object> map = new HashMap<>();
            map.put("notReadNum", notReadNum);
            map.put("messageList", Utility.PageMessageMap(messagePage, 1));
            return ResponseEntity.ok(map);
        }

        return ResponseEntity.ok(new ResponseBody(retrieveForActionAuthorResult.getErrorCode(), retrieveForActionAuthorResult.getErrorMessage()));
    }

    /**
     * 修改消息为已读
     *
     * @param messageId 消息Id
     * @return errorCode:40041 errorMessage:参数无效:消息Id为空 errorCode:40042 errorMessage:消息不存在
     */
    @PutMapping("/message/read")
    @ApiOperation(value = "修改消息为已读", notes = "需提供消息Id")
    public ResponseEntity UpdateMessageIsRead(@RequestParam Integer messageId) {
        BusinessResult updateMessageIsReadResult = messageService.UpdateIsRead(messageId);
        if (updateMessageIsReadResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(updateMessageIsReadResult.getErrorCode(), updateMessageIsReadResult.getErrorMessage()));
    }

    /**
     * 回复约拍请求
     *
     * @param messageId 消息Id
     * @return errorCode:40031 errorMessage:参数无效:消息Id,消息状态为空 errorCode:40032 errorMessage:消息不存在
     */
    @PutMapping("/message/accept")
    @ApiOperation(value = "回复约拍请求", notes = "需提供消息Id")
    public ResponseEntity UpdateMessageIsAccept(@RequestParam Integer messageId, @RequestParam String accept) {
        BusinessResult updateMessageIsAcceptResult = messageService.UpdateIsAccept(messageId, accept);
        if (updateMessageIsAcceptResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(updateMessageIsAcceptResult.getErrorCode(), updateMessageIsAcceptResult.getErrorMessage()));
    }
}