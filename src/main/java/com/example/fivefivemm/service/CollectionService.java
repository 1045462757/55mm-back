package com.example.fivefivemm.service;

import com.example.fivefivemm.utility.BusinessResult;

public interface CollectionService {

    /**
     * 收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return errorCode:50001 errorMessage:参数无效:用户Id,动态Id为空 errorCode:50002 errorMessage:不存在的用户
     * errorCode:50003 errorMessage:不存在的动态 errorCode:50004 errorMessage:已存在的收藏关系
     */
    BusinessResult addActionCollection(Integer userId, Integer actionId);

    /**
     * 取消收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return errorCode:50011 errorMessage:参数无效:用户Id,动态Id为空 errorCode:50012 errorMessage:不存在的用户
     * errorCode:50013 errorMessage:不存在的收藏关系
     */
    BusinessResult removeActionCollection(Integer userId, Integer actionId);

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
     * @return errorCode:50021 errorMessage:参数无效:关注者用户Id,粉丝用户Id为空 errorCode:50022 errorMessage:不存在的用户
     * errorCode:50023 errorMessage:已存在的关注关系
     */
    BusinessResult addUserCollection(Integer focusId, Integer fansId);

    /**
     * 取消关注用户
     *
     * @param focusId 关注的用户Id
     * @param fansId  粉丝用户Id
     * @return errorCode:50031 errorMessage:参数无效:关注者用户Id,粉丝用户Id为空 errorCode:50032 errorMessage:不存在的关注关系
     */
    BusinessResult removeUserCollection(Integer focusId, Integer fansId);

    /**
     * 查询用户关注关系
     *
     * @param focusId 关注的用户Id
     * @param fansId  粉丝用户Id
     * @return 存在关注返回true，不存在返回false
     */
    boolean findUserCollection(Integer focusId, Integer fansId);
}