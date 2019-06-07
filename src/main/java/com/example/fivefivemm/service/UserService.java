package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.Result;

public interface UserService {

    /**
     * 用户注册
     * TODO 验证账号密码邮箱逻辑...
     *
     * @param user 用户对象,需包含账号，密码，邮箱
     * @return message 1.参数无效 2.该账号已被注册 3.该邮箱已被注册
     */
    Result register(User user);

    /**
     * 用户登录
     *
     * @param user 用户对象，需包含账号，密码
     * @return data:该用户对象 message 1.参数无效 2.账号不存在 3.密码错误
     */
    Result login(User user);

    /**
     * 查询用户信息
     *
     * @param userId 用户Id
     * @return data:该用户对象 message 1.userId为空 2.该用户不存在
     */
    Result retrieveInformation(Integer userId);

    /**
     * 修改用户信息
     *
     * @param user 用户对象，需包含用户Id
     * @return message 1.参数无效 2.该用户不存在
     */
    Result updateInformation(User user);

    /**
     * 修改用户密码
     * TODO 验证密码逻辑...
     *
     * @param userId      用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return message 1.参数无效 2.该用户不存在 3.旧密码错误
     */
    Result updatePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 修改用户头像
     *
     * @param user 用户对象，需包含用户Id,头像
     * @return data:头像地址 message 1.参数无效 2.该用户不存在
     */
    Result updateAvatar(User user);

    /**
     * 通过邮箱重置密码
     *
     * @param email 邮箱
     * @return success data:新密码 message 1.参数无效 2.该用户不存在
     */
    Result resetPassword(String email);

    /**
     * 获取用户的动态
     *
     * @param userId 用户Id
     * @return success data:该用户的动态 message 1.参数无效 2.该用户不存在
     */
    Result retrieveActions(Integer userId);

    /**
     * 获取用户收藏的动态
     *
     * @param userId 用户Id
     * @return success data:该用户收藏的动态 message 1.参数无效 2.该用户不存在
     */
    Result retrieveActionCollection(Integer userId);

    /**
     * 获取用户的粉丝
     *
     * @param userId 用户Id
     * @return success data:该用户的粉丝 message 1.参数无效 2.该用户不存在
     */
    Result retrieveFans(Integer userId);

    /**
     * 获取用户的关注
     *
     * @param userId 用户Id
     * @return success data:该用户的关注 message 1.参数无效 2.该用户不存在
     */
    Result retrieveFocus(Integer userId);
}