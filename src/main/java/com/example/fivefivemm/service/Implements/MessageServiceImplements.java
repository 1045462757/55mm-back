package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.ActionRepository;
import com.example.fivefivemm.repository.MessageRepository;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.MessageService;
import com.example.fivefivemm.utility.BusinessResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 * 消息业务类
 * <p>
 * 优化代码
 * 2019年6月7日11:27:39
 * <p>
 * 优化大代码
 * 2019年6月14日20:44:49
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
    public BusinessResult CreateMessage(Message message) {
        if (message == null || message.getAction() == null || message.getUser() == null || message.getAction().getActionId() == null
                || message.getUser().getUserId() == null || message.getAction().getAuthor().getUserId() == null) {
            return new BusinessResult(false, 40001, "参数无效:关注者用户Id,动态Id为空");
        }

        Action existAction = actionRepository.findByActionId(message.getAction().getActionId());
        if (existAction == null) {
            return new BusinessResult(false, 40002, "不存在的动态");
        }

        User existUser = userRepository.findByUserId(message.getUser().getUserId());
        if (existUser == null) {
            return new BusinessResult(false, 40003, "不存在的用户");
        }

        Message existMessage = messageRepository.findByActionAndWatcher(message.getAction(), message.getUser());
        if (existMessage != null) {
            return new BusinessResult(false, 40004, "消息已存在");
        }

        //设置消息发送的对象Id
        message.setActionAuthorId(message.getAction().getAuthor().getUserId());
        messageRepository.save(message);
        logger.info("生成新的消息:[动态Id:" + message.getAction().getActionId() + ",约拍者Id:" + message.getUser().getUserId());
        return new BusinessResult(true);
    }

    @Override
    public BusinessResult RetrieveForWatcher(Integer watcherId, Integer pageIndex) {
        if (watcherId == null || pageIndex == null) {
            return new BusinessResult(false, 40011, "参数无效:约拍者Id,页数为空");
        }

        if (userRepository.findByUserId(watcherId) == null) {
            return new BusinessResult(false, 40012, "不存在的用户");
        }

        Page<Message> messagePage = messageRepository.findAll((Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
                        -> (criteriaBuilder.equal(root.get("watcher").get("userId"), watcherId)),
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "createTime")));

        return new BusinessResult(true, messagePage);
    }

    @Override
    public BusinessResult RetrieveForActionAuthorId(Integer actionAuthorId, Integer pageIndex) {
        if (actionAuthorId == null || pageIndex == null) {
            return new BusinessResult(false, 40021, "参数无效:动态作者Id,页数为空");
        }

        if (userRepository.findByUserId(actionAuthorId) == null) {
            return new BusinessResult(false, 40022, "不存在的用户");
        }

        Page<Message> messagePage = messageRepository.findAll((Root<Message> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder)
                        -> (criteriaBuilder.equal(root.get("action").get("author").get("userId"), actionAuthorId)),
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "createTime")));

        return new BusinessResult(true, messagePage);
    }

    @Override
    @Transactional
    public BusinessResult UpdateIsAccept(Integer messageId, String accept) {
        if (messageId == null || accept == null) {
            return new BusinessResult(false, 40031, "参数无效:消息Id,消息状态为空");
        }

        Message existMessage = messageRepository.findByMessageId(messageId);
        if (existMessage == null) {
            return new BusinessResult(false, 40032, "消息不存在");
        }

        existMessage.setAccept(accept);
        logger.info("已回复约拍请求:[消息Id:" + existMessage.getMessageId() + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult UpdateIsRead(Integer messageId) {
        if (messageId == null) {
            return new BusinessResult(false, 40041, "参数无效:消息Id为空");
        }

        Message existMessage = messageRepository.findByMessageId(messageId);
        if (existMessage == null) {
            return new BusinessResult(false, 40042, "消息不存在");
        }

        existMessage.setRead(true);
        logger.info("修改消息为已读:[消息Id:" + existMessage.getMessageId() + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult DeleteMessage(Message message) {
        if (message == null || message.getAction() == null || message.getUser() == null) {
            return new BusinessResult(false, 40051, "参数无效:关注者用户Id,动态Id为空");
        }

        Message existMessage = messageRepository.findByActionAndWatcher(message.getAction(), message.getUser());
        if (existMessage == null) {
            return new BusinessResult(false, 40052, "消息不存在");
        }

        messageRepository.deleteById(existMessage.getMessageId());
        logger.info("删除消息:[消息Id:" + existMessage.getMessageId() + "]");
        return new BusinessResult(true);
    }
}