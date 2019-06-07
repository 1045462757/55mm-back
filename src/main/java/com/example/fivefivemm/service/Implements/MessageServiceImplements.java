package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.repository.MessageRepository;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.MessageService;
import com.example.fivefivemm.utility.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 消息业务类
 * <p>
 * 优化代码
 * 2019年6月7日11:27:39
 *
 * @author tiga
 * @version 1.1
 * @since 2019年5月22日19:01:31
 */
@Service
public class MessageServiceImplements implements MessageService {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private ActionRepository actionRepository;

    @Resource
    private UserRepository userRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public Result CreateMessage(Message message) {
        if (message == null || message.getAction() == null || message.getUser() == null || message.getAction().getActionId() == null
                || message.getUser().getUserId() == null || message.getAction().getAuthor().getUserId() == null) {
            return new Result(Result.failed, "参数无效");
        }

        Action existAction = actionRepository.findByActionId(message.getAction().getActionId());
        if (existAction == null) {
            return new Result(Result.failed, "不存在的动态");
        }

        User existUser = userRepository.findByUserId(message.getUser().getUserId());
        if (existUser == null) {
            return new Result(Result.failed, "不存在的用户");
        }

        Message existMessage = messageRepository.findByActionAndWatcher(message.getAction(), message.getUser());
        if (existMessage != null) {
            return new Result(Result.failed, "消息已存在");
        }

        //设置消息发送的对象Id
        message.setActionAuthorId(message.getAction().getAuthor().getUserId());
        messageRepository.save(message);
        logger.info("生成新的消息:[动态Id:" + message.getAction().getActionId() + ",约拍者Id:" + message.getUser().getUserId());
        return new Result(Result.success);
    }

    @Override
    public Result RetrieveForWatcher(Integer watcherId) {
        if (watcherId == null) {
            return new Result(Result.failed, "参数无效");
        }
        List<Message> messageList = messageRepository.findByWatcher(new User(watcherId));
        return new Result(Result.success, messageList);
    }

    @Override
    public Result RetrieveForActionAuthorId(Integer actionAuthorId) {
        if (actionAuthorId == null) {
            return new Result(Result.failed, "参数无效");
        }
        List<Message> messageList = messageRepository.findByActionAuthorId(actionAuthorId);
        return new Result(Result.success, messageList);
    }

    @Override
    @Transactional
    public Result UpdateIsAccept(Integer messageId, String accept) {
        if (messageId == null || accept == null) {
            return new Result(Result.failed, "参数无效");
        }

        Message existMessage = messageRepository.findByMessageId(messageId);
        if (existMessage == null) {
            return new Result(Result.failed, "消息不存在");
        }

        existMessage.setAccept(accept);
        logger.info("已回复约拍请求:[消息Id:" + existMessage.getMessageId() + "]");
        return new Result(Result.success);
    }

    @Override
    @Transactional
    public Result UpdateIsRead(Integer messageId) {
        if (messageId == null) {
            return new Result(Result.failed, "参数无效");
        }

        Message existMessage = messageRepository.findByMessageId(messageId);
        if (existMessage == null) {
            return new Result(Result.failed, "消息不存在");
        }

        existMessage.setRead(true);
        logger.info("修改消息为已读:[消息Id:" + existMessage.getMessageId() + "]");
        return new Result(Result.success);
    }

    @Override
    @Transactional
    public Result DeleteMessage(Message message) {
        if (message == null || message.getMessageId() == null || message.getAction() == null || message.getUser() == null) {
            return new Result(Result.failed, "参数无效");
        }

        Message existMessage = messageRepository.findByActionAndWatcher(message.getAction(), message.getUser());
        if (existMessage == null) {
            return new Result(Result.failed, "消息不存在");
        }

        messageRepository.deleteById(existMessage.getMessageId());
        logger.info("删除消息:[消息Id:" + existMessage.getMessageId() + "]");
        return new Result(Result.success);
    }
}
