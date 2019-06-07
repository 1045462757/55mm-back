package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.utility.Result;

import java.util.List;

public interface ActionService {

    /**
     * 发表动态
     *
     * @param action 动态对象，需包含作者Id,标题，地区，价格，内容
     * @return data:保存的动态 message 1.参数无效
     */
    Result CreateAction(Action action);

    /**
     * 查询动态
     *
     * @param actionId 动态Id
     * @return data:该动态对象 message 1.参数错误 2.该动态不存在
     */
    Result RetrieveAction(Integer actionId);

    /**
     * 获取所有动态
     *
     * @return 存在返回动态集合，不存在返回null
     */
    List<Action> RetrieveAllAction();

    /**
     * 修改动态
     *
     * @param action 动态对象,需包含动态Id，作者Id，标题，地区，价格，内容
     * @return message 1.参数无效 2.该动态不存在 3.没有权限修改，您不是该动态的作者
     */
    Result UpdateAction(Action action);

    /**
     * 删除动态
     *
     * @param action 动态对象，需包含动态Id，作者Id
     * @return message  1.参数无效 2.该动态不存在 3.没有权限删除，您不是该动态的作者
     */
    Result DeleteAction(Action action);
}
