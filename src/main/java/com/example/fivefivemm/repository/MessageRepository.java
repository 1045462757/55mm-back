package com.example.fivefivemm.repository;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息仓库
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月22日18:55:57
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>, JpaSpecificationExecutor<Message> {

    /**
     * 通过动态和约拍者查找消息
     *
     * @param action  动态
     * @param watcher 约拍者
     * @return 存在消息返回Message，不存在返回null
     */
    Message findByActionAndWatcher(Action action, User watcher);

    /**
     * 通过约拍者查找消息
     *
     * @param watcher 约拍者
     * @return 存在返回消息集合，不存在返回null
     */
    List<Message> findByWatcher(User watcher);

    /**
     * 通过动态作者Id查找消息
     *
     * @param actionAuthorId 动态作者Id
     * @return 存在返回消息集合，不存在返回null
     */
    List<Message> findByActionAuthorId(Integer actionAuthorId);

    /**
     * 通过消息Id查找消息
     *
     * @param messageId 消息Id
     * @return 存在消息返回Message, 不存在返回null
     */
    Message findByMessageId(Integer messageId);
}
