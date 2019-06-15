package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.utility.BusinessResult;

public interface ActionService {

    /**
     * 发表动态
     *
     * @param action 动态对象，需包含作者Id,标题，地区，价格，内容
     * @return data:保存的动态 errorCode:20001 errorMessage:参数无效:作者用户Id,标题,地区,价格,内容为空
     */
    BusinessResult CreateAction(Action action);

    /**
     * 查询动态
     *
     * @param actionId 动态Id
     * @return data:该动态对象 errorCode:20011 errorMessage:参数无效:参数无效:动态Id为空 errorCode:20012 errorMessage:该动态不存在
     */
    BusinessResult RetrieveAction(Integer actionId);

    /**
     * 修改动态
     *
     * @param action 动态对象,需包含动态Id，作者Id，标题，地区，价格，内容
     * @return errorCode:20021 errorMessage:参数无效:动态Id,作者用户Id,标题,地区,价格,内容为空 errorCode:20022 errorMessage:该动态不存在 errorCode:20023 errorMessage:没有权限修改，您不是该动态的作者
     */
    BusinessResult UpdateAction(Action action);

    /**
     * 删除动态
     *
     * @param action 动态对象，需包含动态Id，作者Id
     * @return errorCode:20031 errorMessage:参数无效:动态Id,作者Id为空 errorCode:20032 errorMessage:该动态不存在 errorCode:20033 errorMessage:没有权限删除，您不是该动态的作者
     */
    BusinessResult DeleteAction(Action action);

    /**
     * 分页条件查询动态
     *
     * @param pageIndex  查询页数
     * @param address    动态地址
     * @param authorType 作者类型
     * @param authorSex  作者性别
     * @param minCost    最低价
     * @param maxCost    最高价
     * @return data:Page<Action>对象 errorCode:20041 errorMessage:参数无效:页数为空
     */
    BusinessResult findActionByPage(Integer pageIndex, String address, String authorType, String authorSex, Integer minCost, Integer maxCost);

    /**
     * 分页获取用户的动态
     *
     * @param authorId  用户Id
     * @param pageIndex 第几页
     * @return data:Page<Action>对象 errorCode:20051 errorMessage:参数无效:作者用户Id,页数为空 errorCode:20052 errorMessage:不存在的用户
     */
    BusinessResult findActionByAuthor(Integer authorId, Integer pageIndex);

    /**
     * 分页获取用户收藏的动态
     *
     * @param collectorId 用户Id
     * @param pageIndex   第几页
     * @return data:Page<ActionCollection>对象 errorCode:20061 errorMessage:参数无效:参数无效:收藏者用户Id,页数为空 errorCode:20062 errorMessage:不存在的用户
     */
    BusinessResult findActionByCollection(Integer collectorId, Integer pageIndex);
}
