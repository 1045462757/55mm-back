package com.example.fivefivemm.controller.message;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.service.MessageService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息控制器
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月22日19:02:20
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 根据约拍者Id查找消息
     *
     * @param watcherId 约拍者Id
     * @return success data:List<Message>
     * failed message
     * 101
     * 1.1.watcherId为空
     */
    @GetMapping("/message/watcherId")
    @ResponseBody
    public String RetrieveMessageForWatcher(@RequestParam Integer watcherId) {
        Result retrieveForWatcherResult = messageService.RetrieveForWatcher(watcherId);
        if (retrieveForWatcherResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, Utility.MessageListBody((List<Message>) retrieveForWatcherResult.getData(), 2));
        } else {
            return Utility.ResultBody(101, retrieveForWatcherResult.getMessage(), null);
        }
    }

    /**
     * 根据动态作者Id查找消息
     *
     * @param actionAuthorId 动态作者Id
     * @return success data:List<Message>
     * failed message
     * 102
     * 1.actionAuthorId为空
     */
    @GetMapping("/message/actionAuthorId")
    @ResponseBody
    public String RetrieveMessageForActionAuthor(@RequestParam Integer actionAuthorId) {
        Result retrieveForActionAuthorResult = messageService.RetrieveForActionAuthorId(actionAuthorId);
        if (retrieveForActionAuthorResult.getStatus().equals(Result.success)) {
            List<Message> messageList = (List<Message>) retrieveForActionAuthorResult.getData();
            int notReadNum = Utility.getNotReadMessage(messageList);
            JSONArray jsonArray = Utility.MessageListBody(messageList, 1);
            Map<String, Object> map = new HashMap<>();
            map.put("notReadNum", notReadNum);
            map.put("messageList", jsonArray);
            return Utility.ResultBody(200, null, map);
        } else {
            return Utility.ResultBody(102, retrieveForActionAuthorResult.getMessage(), null);
        }
    }

    /**
     * 修改消息为已读
     *
     * @param messageId 消息Id
     * @return failed message
     * 103
     * 1.参数不合法
     * 2.消息不存在
     */
    @PutMapping("/message/read")
    @ResponseBody
    public String UpdateMessageIsRead(@RequestParam Integer messageId) {
        Result updateMessageIsReadResult = messageService.UpdateIsRead(messageId);
        if (updateMessageIsReadResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(103, updateMessageIsReadResult.getMessage(), null);
        }
    }

    /**
     * 回复约拍请求
     *
     * @param messageId 消息Id
     * @return failed message
     * 104
     * 1.参数不合法
     * 2.消息不存在
     */
    @PutMapping("/message/accept")
    @ResponseBody
    public String UpdateMessageIsAccept(@RequestParam Integer messageId, @RequestParam String accept) {
        Result updateMessageIsAcceptResult = messageService.UpdateIsAccept(messageId, accept);
        if (updateMessageIsAcceptResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(104, updateMessageIsAcceptResult.getMessage(), null);
        }
    }
}
