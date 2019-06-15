package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.utility.BusinessResult;

public interface MessageService {

    /**
     * 生成消息
     *
     * @param message 消息对象包含关注者用户Id，动态Id
     * @return data:Page<Message>对象 errorCode:40001 errorMessage:参数无效:关注者用户Id,动态Id为空 errorCode:40002 errorMessage:不存在的动态
     * errorCode:40003 errorMessage:不存在的用户 errorCode:40004 errorMessage:消息已存在
     */
    BusinessResult CreateMessage(Message message);

    /**
     * 通过约拍者Id查找消息
     * <p>
     * TODO 分页通过约拍者Id查找消息
     *
     * @param watcherId 约拍者用户Id
     * @param pageIndex 页数
     * @return data:Page<Message>对象 errorCode:40011 errorMessage:参数无效:约拍者Id,页数为空 errorCode:40012 errorMessage:不存在的用户
     */
    BusinessResult RetrieveForWatcher(Integer watcherId, Integer pageIndex);

    /**
     * 通过动态作者Id查找消息
     * <p>
     * TODO 分页通过动态作者Id查找消息
     *
     * @param actionAuthorId 动态的作者Id
     * @param pageIndex      页数
     * @return errorCode:40021 errorMessage:参数无效:动态作者Id,页数为空 errorCode:40022 errorMessage:不存在的用户
     */
    BusinessResult RetrieveForActionAuthorId(Integer actionAuthorId, Integer pageIndex);

    /**
     * 回复约拍请求
     *
     * @param messageId 消息Id
     * @param accept    消息状态
     * @return errorCode:40031 errorMessage:参数无效:消息Id,消息状态为空 errorCode:40032 errorMessage:消息不存在
     */
    BusinessResult UpdateIsAccept(Integer messageId, String accept);

    /**
     * 浏览消息
     *
     * @param messageId 消息Id
     * @return errorCode:40041 errorMessage:参数无效:消息Id为空 errorCode:40042 errorMessage:消息不存在
     */
    BusinessResult UpdateIsRead(Integer messageId);

    /**
     * 删除消息
     *
     * @param message 消息对象包含关注者用户Id，动态Id
     * @return errorCode:40051 errorMessage:参数无效:消息Id为空 errorCode:40052 errorMessage:消息不存在
     */
    BusinessResult DeleteMessage(Message message);
}