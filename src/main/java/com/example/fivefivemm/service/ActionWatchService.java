package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.utility.BusinessResult;

public interface ActionWatchService {

    /**
     * 生成约拍记录
     *
     * @param actionWatch 约拍记录对象,需包含动态Id，约拍者用户Id
     * @return errorCode:30001 errorMessage:参数无效:动态Id,约拍者用户Id为空
     * errorCode:30002 errorMessage:不存在的动态 errorCode:30003 errorMessage:不存在的用户 errorCode:30004 errorMessage:约拍记录已存在
     */
    BusinessResult CreateActionWatch(ActionWatch actionWatch);

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
     * @return errorCode:30011 errorMessage:参数无效:动态Id,约拍者用户Id为空  errorCode:30012 errorMessage:该约拍记录不存在
     */
    BusinessResult DeleteActionWatch(ActionWatch actionWatch);
}