package com.example.fivefivemm.service;

import com.example.fivefivemm.utility.BusinessResult;

import java.util.List;

public interface ActionTagService {

    /**
     * 绑定动态和标签
     *
     * @param actionId 动态Id
     * @param tagList  标签Id集合
     * @return errorCode:70001 errorMessage:参数无效:动态Id,标签Id为空 errorCode:70002 errorMessage:不存在的动态 errorCode:70003 errorMessage:不存在的标签
     */
    BusinessResult CreateActionTag(Integer actionId, List<Integer> tagList);

    /**
     * 查询动态的所有标签
     *
     * @param actionId 动态Id
     * @return errorCode:70004 errorMessage:动态Id为空 errorCode:70005 errorMessage:不存在的动态
     */
    BusinessResult findTags(Integer actionId);
}
