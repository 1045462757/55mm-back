package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.relation.UserCollection;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.Constants;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户业务类
 * <p>
 * 优化代码
 * 2019年6月1日15:33:05
 *
 * @author tiga
 * @version 1.1
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
        if (user == null || user.getAccount() == null || user.getPassword() == null || user.getEmail() == null) {
            return new Result(Result.failed, "参数无效");
        }

        //TODO 验证账号密码邮箱逻辑...

        //验证账号唯一性
        User existAccount = userRepository.findByAccount(user.getAccount());
        if (existAccount != null) {
            return new Result(Result.failed, "该账号已被注册");
        }

        //验证邮箱唯一性
        User existEmail = userRepository.findByEmail(user.getEmail());
        if (existEmail != null) {
            return new Result(Result.failed, "该邮箱已被注册");
        }

        user.setPassword(Utility.md5(user.getPassword()));
        user.setName("新用户");
        user.setAvatar(Constants.defaultAvatar);
        userRepository.save(user);
        logger.info("注册新用户:[账号:" + user.getAccount() + "]");
        return new Result(Result.success);
    }

    @Override
    @Transactional
    public Result login(User user) {
        if (user == null || user.getAccount() == null || user.getPassword() == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByAccount(user.getAccount());
        if (existUser == null) {
            return new Result(Result.failed, "账号不存在");
        }

        if (!existUser.getPassword().equals(Utility.md5(user.getPassword()))) {
            return new Result(Result.failed, "密码错误");
        }

        existUser.setLastLoginTime(new Date());
        logger.info("登录用户:[账号:" + existUser.getAccount() + ",登录时间:" + existUser.getLastLoginTime() + "]");
        return new Result(Result.success, existUser);
    }

    @Override
    public Result retrieveInformation(Integer userId) {
        if (userId == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        logger.info("通过Id查询用户信息:[Id:" + existUser.getUserId() + "]");
        return new Result(Result.success, existUser);
    }

    @Override
    @Transactional
    public Result updateInformation(User user) {
        if (user == null || user.getUserId() == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(user.getUserId());
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        existUser.setAge(Utility.getAgeByBirth(user.getBirthday()));
        existUser.setBirthday(user.getBirthday());
        existUser.setEmail(user.getEmail());
        existUser.setName(user.getName());
        existUser.setPhone(user.getPhone());
        existUser.setQq(user.getQq());
        existUser.setSex(user.getSex());
        existUser.setType(user.getType());
        existUser.setWeChat(user.getWeChat());
        existUser.setIntroduction(user.getIntroduction());
        logger.info("修改用户信息:[用户Id:" + existUser.getUserId() + "]");
        return new Result(Result.success);
    }

    @Override
    @Transactional
    public Result updatePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            return new Result(Result.failed, "参数无效");
        }

        //TODO 验证密码逻辑...

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        if (!existUser.getPassword().equals(Utility.md5(oldPassword))) {
            return new Result(Result.failed, "旧密码错误");
        }

        existUser.setPassword(Utility.md5(newPassword));
        logger.info("修改用户密码:[用户Id:" + userId + "]");
        return new Result(Result.success);
    }

    @Override
    @Transactional
    public Result updateAvatar(User user) {
        if (user == null || user.getUserId() == null || user.getAvatar() == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(user.getUserId());
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        existUser.setAvatar(user.getAvatar());
        logger.info("修改用户头像:[用户Id:" + existUser.getUserId() + "]");
        return new Result(Result.success, user.getAvatar());
    }

    @Override
    @Transactional
    public Result resetPassword(String email) {
        if (email == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByEmail(email);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        String newPassword = Utility.randomPassword();
        existUser.setPassword(Utility.md5(newPassword));
        logger.info("通过邮箱重置用户密码:[邮箱:" + email + "]");
        return new Result(Result.success, newPassword);
    }

    @Override
    @Transactional
    public Result retrieveActions(Integer userId) {
        if (userId == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        List<Action> actionList = new ArrayList<>(existUser.getMyActions());
        Utility.ActionListSort(actionList);
        logger.info("获取用户动态:[用户Id:" + existUser.getUserId() + "]");
        return new Result(Result.success, actionList);
    }

    @Override
    @Transactional
    public Result retrieveActionCollection(Integer userId) {
        if (userId == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        List<ActionCollection> actionCollectionList = new ArrayList<>(existUser.getActionCollections());
        Utility.ActionCollecitonListSort(actionCollectionList);
        List<Action> collectActions = new ArrayList<>();
        for (ActionCollection actionCollection : actionCollectionList) {
            collectActions.add(actionCollection.getCollectAction());
        }
        logger.info("获取用户收藏动态:[用户Id:" + userId + "]");
        return new Result(Result.success, collectActions);
    }

    @Override
    @Transactional
    public Result retrieveFans(Integer userId) {
        if (userId == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        List<UserCollection> userCollectionList = new ArrayList<>(existUser.getFans());
        Utility.UserCollecitonListSort(userCollectionList);
        List<User> fans = new ArrayList<>();
        for (UserCollection userCollection : userCollectionList) {
            fans.add(userCollection.getFans());
        }
        logger.info("获取用户粉丝:[用户Id:" + userId + "]");
        return new Result(Result.success, fans);
    }

    @Override
    @Transactional
    public Result retrieveFocus(Integer userId) {
        if (userId == null) {
            return new Result(Result.failed, "参数无效");
        }

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new Result(Result.failed, "该用户不存在");
        }

        List<UserCollection> userCollectionList = new ArrayList<>(existUser.getFocus());
        Utility.UserCollecitonListSort(userCollectionList);
        List<User> focus = new ArrayList<>();
        for (UserCollection userCollection : userCollectionList) {
            focus.add(userCollection.getFocus());
        }
        logger.info("获取用户关注:[用户Id:" + userId + "]");
        return new Result(Result.success, focus);
    }
}