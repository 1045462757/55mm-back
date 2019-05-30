package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.Result;

import java.util.Set;

public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return success
     * failed message
     * 1.user对象为空
     * 2.该邮箱已被注册
     * 3.该账号已被注册
     */
    Result register(User user);

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return success data:userId
     * failed message
     * 1.user对象为空
     * 2.账号不存在
     * 3.密码错误
     */
    Result login(User user);

    /**
     * 查询用户信息
     *
     * @param userId 用户Id
     * @return success data:user
     * failed message
     * 1.userId为空
     * 2.该用户不存在
     */
    Result retrieveInformation(Integer userId);

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return success
     * failed message
     * 1.user对象为空
     * 2.不存在的用户
     */
    Result updateInformation(User user);

    /**
     * 修改用户密码
     *
     * @param userId      用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return success
     * failed message
     * 1.参数不合法
     * 2.不存在的用户
     * 3.旧密码错误
     */
    Result updatePassword(Integer userId, String oldPassword, String newPassword);

    /**
     * 修改用户头像
     *
     * @param user 用户对象
     * @return success data:头像地址
     * failed message
     * 1.user对象为空
     * 2.不存在的用户
     * 3.头像地址为空
     */
    Result updateAvatar(User user);

    /**
     * 获取用户的动态
     *
     * @param userId 用户Id
     * @return 用户的动态集合或null
     */
    Set<Action> RetrieveUserActions(Integer userId);

    /**
     * 通过邮箱重置密码
     *
     * @param email 邮箱
     * @return success data:新密码
     * failed message
     * 1.邮箱为空
     * 2.不存在的用户
     */
    Result resetPassword(String email);

    /**
     * 获取用户收藏的动态
     *
     * @param userId 用户Id
     * @return 用户的收藏动态集合或null
     */
    Set<Action> RetrieveActionCollection(Integer userId);

    /**
     * 获取用户的粉丝
     *
     * @param userId 用户Id
     * @return 用户的粉丝集合或null
     */
    Set<User> RetrieveFans(Integer userId);

    /**
     * 获取用户的关注
     *
     * @param userId 用户Id
     * @return 用户的关注集合或null
     */
    Set<User> RetrieveFocus(Integer userId);
}
