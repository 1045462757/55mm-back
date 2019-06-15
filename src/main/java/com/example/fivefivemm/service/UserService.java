package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.BusinessResult;

public interface UserService {

    /**
     * 用户注册
     * TODO 验证账号密码邮箱逻辑...
     *
     * @param user 用户对象,需包含账号，密码，邮箱
     * @return errorCode:10001 errorMessage:参数无效:账号,密码或邮箱为空 errorCode:10002 errorMessage:该账号已被注册 errorCode:10003 errorMessage:该邮箱已被注册
     */
    BusinessResult register(User user);

    /**
     * 用户登录
     *
     * @param user 用户对象，需包含账号，密码
     * @return data:该用户的信息 errorCode:10011 errorMessage:参数无效:账号,密码为空 errorCode:10012 errorMessage:账号不存在 errorCode:10013 errorMessage:密码错误
     */
    BusinessResult login(User user);

    /**
     * 查询用户信息
     *
     * @param userId 用户Id
     * @return data:该用户的信息 errorCode:10021 errorMessage:参数无效:用户Id为空 errorCode:10022 errorMessage:该用户不存在
     */
    BusinessResult retrieveInformation(Integer userId);

    /**
     * 修改用户信息
     *
     * @param user 用户对象，需包含用户Id
     * @return errorCode:10031 errorMessage:参数无效:用户Id为空 errorCode:10032 errorMessage:该用户不存在
     */
    BusinessResult updateInformation(User user);

    /**
     * 修改用户密码
     * TODO 验证密码逻辑...
     *
     * @param userId      用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return errorCode:10041 errorMessage:参数无效:用户Id,旧密码或新密码为空 errorCode:10042 errorMessage:该用户不存在 errorCode:10043 errorMessage:旧密码错误
     */
    BusinessResult updatePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 修改用户头像
     *
     * @param user 用户对象，需包含用户Id,头像
     * @return data:新头像地址 errorCode:10051 errorMessage 参数无效:用户Id或头像为空 errorCode:10052 errorMessage:该用户不存在
     */
    BusinessResult updateAvatar(User user);

    /**
     * 通过邮箱重置密码
     *
     * @param email 邮箱
     * @return data:新密码 errorCode:10061 errorMessage 参数无效:邮箱为空 errorCode:10062 errorMessage:该用户不存在
     */
    BusinessResult resetPassword(String email);

    /**
     * 分页获取用户的粉丝
     *
     * @param focusId   用户Id
     * @param pageIndex 第几页
     * @return data:Page<UserCollection>对象 errorCode:10071 errorMessage 参数无效:关注者用户Id或页数为空 errorCode:10072 errorMessage:不存在的用户
     */
    BusinessResult findFans(Integer focusId, Integer pageIndex);

    /**
     * 分页获取用户的关注
     *
     * @param fansId    用户Id
     * @param pageIndex 第几页
     * @return data:Page<UserCollection>对象 errorCode:10081 errorMessage 参数无效:粉丝用户Id或页数为空 errorCode:10082 errorMessage:不存在的用户
     */
    BusinessResult findFocus(Integer fansId, Integer pageIndex);
}