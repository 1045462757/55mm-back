package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

/**
 * 用户业务类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月13日19:28:35
 */
@Service
public class UserServiceImplements implements UserService {

    @Resource
    private UserRepository userRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public Result register(User user) {
        if (user != null) {
            if (user.getAccount() == null || user.getAccount().length() < 8) {
                return new Result(Result.failed, "账号必须大于8位");
            }
            if (user.getPassword() == null || user.getPassword().length() < 8) {
                return new Result(Result.failed, "密码必须大于8位");
            }

            //可添加新的账号密码限制逻辑...

            User existAccountUser = userRepository.findByAccount(user.getAccount());
            User existEmailUser = userRepository.findByEmail(user.getEmail());
            if (existAccountUser != null) {
                return new Result(Result.failed, "该账号已被注册");
            } else if (existEmailUser != null) {
                return new Result(Result.failed, "该邮箱已被注册");
            } else {
                user.setPassword(Utility.md5(user.getPassword()));
                userRepository.save(user);
                logger.info("注册新用户:" + user.getAccount());
                return new Result(Result.success);
            }
        } else {
            return new Result(Result.failed, "user对象为空");
        }
    }

    @Override
    @Transactional
    public Result login(User user) {
        if (user != null) {
            User existUser = userRepository.findByAccount(user.getAccount());
            if (existUser != null) {
                if (existUser.getPassword().equals(Utility.md5(user.getPassword()))) {
                    existUser.setLastLoginTime(new Date());
                    logger.info("登录用户:" + existUser.getAccount());
                    return new Result(Result.success, existUser.getUserId());
                } else {
                    return new Result(Result.failed, "密码错误");
                }
            } else {
                return new Result(Result.failed, "账号不存在");
            }
        } else {
            return new Result(Result.failed, "user对象为空");
        }
    }

    @Override
    public Result retrieveInformation(Integer userId) {
        if (userId != null) {
            User existUser = userRepository.findByUserId(userId);
            if (existUser != null) {
                logger.info("查询用户信息:" + existUser.getUserId());
                return new Result(Result.success, existUser);
            } else {
                return new Result(Result.failed, "该用户不存在");
            }
        } else {
            return new Result(Result.failed, "userId为空");
        }
    }

    @Override
    @Transactional
    public Result updateInformation(User user) {
        if (user != null) {
            User existUser = userRepository.findByUserId(user.getUserId());
            if (existUser != null) {
                existUser.setAge(Utility.getAgeByBirth(user.getBirthday()));
                existUser.setBirthday(user.getBirthday());
                existUser.setEmail(user.getEmail());
                existUser.setName(user.getName());
                existUser.setPhone(user.getPhone());
                existUser.setQq(user.getQq());
                existUser.setSex(user.getSex());
                existUser.setType(user.getType());
                existUser.setWeChat(user.getWeChat());
                logger.info("修改用户信息:" + existUser.getUserId());
                return new Result(Result.success);
            } else {
                return new Result(Result.failed, "不存在的用户");
            }
        } else {
            return new Result(Result.failed, "user对象为空");
        }
    }

    @Override
    @Transactional
    public Result updatePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId != null && oldPassword != null && oldPassword.length() != 0 && newPassword != null && newPassword.length() != 0) {
            if (newPassword.length() < 8) {
                return new Result(Result.failed, "密码必须大于8位");
            }

            //可添加新的密码限制逻辑...

            User existUser = userRepository.findByUserId(userId);
            if (existUser != null) {
                if (existUser.getPassword().equals(Utility.md5(oldPassword))) {
                    existUser.setPassword(Utility.md5(newPassword));
                    logger.info("修改用户密码:" + userId);
                    return new Result(Result.success);
                } else {
                    return new Result(Result.failed, "旧密码错误");
                }
            } else {
                return new Result(Result.failed, "不存在的用户");
            }
        } else {
            return new Result(Result.failed, "参数不合法");
        }
    }

    @Override
    @Transactional
    public Result updateAvatar(User user) {
        if (user != null) {
            User existUser = userRepository.findByUserId(user.getUserId());
            if (existUser != null) {
                existUser.setAvatar(user.getAvatar());
                logger.info("修改用户头像:" + existUser.getUserId());
                return new Result(Result.success, null, user.getAvatar());
            } else {
                return new Result(Result.failed, "不存在的用户");
            }
        } else {
            return new Result(Result.failed, "user对象为空");
        }
    }
}
