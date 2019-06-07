package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.utility.Result;

public interface MessageService {

    /**
     * 生成消息
     *
     * @param message 消息对象包含关注者用户Id，动态Id
     * @return message 1.参数无效 2.不存在的动态 3.不存在的用户 4.消息已存在
     */
    Result CreateMessage(Message message);

    /**
     * 通过约拍者Id查找消息
     *
     * @param watcherId 约拍者用户Id
     * @return message 1.参数无效
     */
    Result RetrieveForWatcher(Integer watcherId);

    /**
     * 通过动态的作者Id查找消息
     *
     * @param actionAuthorId 动态的作者Id
     * @return message 1.参数无效
     */
    Result RetrieveForActionAuthorId(Integer actionAuthorId);

    /**
     * 回复约拍请求
     *
     * @param messageId 消息Id
     * @param accept    消息状态
     * @return message 1.参数无效 2.消息不存在
     */
    Result UpdateIsAccept(Integer messageId, String accept);

    /**
     * 浏览消息
     *
     * @param messageId 消息Id
     * @return message 1.参数无效 2.消息不存在
     */
    Result UpdateIsRead(Integer messageId);

    /**
     * 删除消息
     *
     * @param message 消息对象包含messageId
     * @return message 1.参数无效 2.消息不存在
     */
    Result DeleteMessage(Message message);
}
