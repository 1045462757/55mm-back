package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.utility.Result;

public interface ActionWatchService {

    /**
     * 生成约拍记录
     *
     * @param actionWatch 约拍记录对象,需包含动态Id，约拍者用户Id
     * @return message 1.参数无效 2.不存在的动态 3.不存在的用户 4.约拍记录已存在
     */
    Result CreateActionWatch(ActionWatch actionWatch);

    /**
     * 查找约拍记录
     *
     * @param actionWatch 约拍记录对象,需包含动态Id，约拍者用户Id
     * @return 存在记录返回true
     */
    boolean RetrieveActionWatch(ActionWatch actionWatch);

    /**
     * 删除约拍记录
     *
     * @param actionWatch 约拍记录对象,需包含动态Id，约拍者用户Id
     * @return message 1.参数无效 2.该约拍记录不存在
     */
    Result DeleteActionWatch(ActionWatch actionWatch);
}
