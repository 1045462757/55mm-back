package com.example.fivefivemm.service;


import com.example.fivefivemm.utility.Result;

/**
 * 收藏业务类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月27日11:18:49
 */
public interface CollectionService {

    /**
     * 收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return message 1.参数无效 2.不存在的用户 3.不存在的动态 4.已存在的收藏关系
     */
    Result addActionCollection(Integer userId, Integer actionId);

    /**
     * 取消收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return message 1.参数无效 2.不存在的动态 3.已存在的收藏关系
     */
    Result removeActionCollection(Integer userId, Integer actionId);

    /**
     * 查询动态收藏关系
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return 存在收藏返回true，不存在返回false
     */
    boolean findActionCollection(Integer userId, Integer actionId);

    /**
     * 关注用户
     *
     * @param focusId 关注的用户Id
     * @param fansId  粉丝用户Id
     * @return message 1.参数无效 2.不存在的用户 3.已存在的关注关系
     */
    Result addUserCollection(Integer focusId, Integer fansId);

    /**
     * 取消关注用户
     *
     * @param focusId 关注的用户Id
     * @param fansId  粉丝用户Id
     * @return message 1.参数无效 2.不存在的关注关系
     */
    Result removeUserCollection(Integer focusId, Integer fansId);

    /**
     * 查询用户关注关系
     *
     * @param focusId 关注的用户Id
     * @param fansId  粉丝用户Id
     * @return 存在关注返回true，不存在返回false
     */
    boolean findUserCollection(Integer focusId, Integer fansId);
}
