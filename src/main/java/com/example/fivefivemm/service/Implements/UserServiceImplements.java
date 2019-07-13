package com.example.fivefivemm.service.Implements;

import com.example.fivefivemm.entity.relation.UserCollection;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.repository.UserCollectionRepository;
import com.example.fivefivemm.repository.UserRepository;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.Setting;
import com.example.fivefivemm.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * 用户业务类
 * <p>
 * 优化代码
 * 2019年6月1日15:33:05
 * <p>
 * 又优化了代码
 * 2019年6月14日10:23:37
 *
 * @author tiga
 * @version 1.2
 * @since 2019年5月13日19:28:35
 */
@Service
public class UserServiceImplements implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserCollectionRepository userCollectionRepository;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Transactional
    public BusinessResult register(User user) {
        if (user == null || user.getAccount() == null || user.getPassword() == null || user.getEmail() == null) {
            return new BusinessResult(false, 10001, "参数无效:账号,密码或邮箱为空");
        }

        //TODO 验证账号密码邮箱逻辑...

        //验证账号唯一性
        User existAccount = userRepository.findByAccount(user.getAccount());
        if (existAccount != null) {
            return new BusinessResult(false, 10002, "该账号已被注册");
        }

        //验证邮箱唯一性
        User existEmail = userRepository.findByEmail(user.getEmail());
        if (existEmail != null) {
            return new BusinessResult(false, 10003, "该邮箱已被注册");
        }

        user.setPassword(Utility.md5(user.getPassword()));
        user.setName("新用户");
        user.setAvatar(Setting.defaultAvatar());
        userRepository.save(user);
        logger.info("注册新用户:[账号:" + user.getAccount() + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult login(User user) {
        if (user == null || user.getAccount() == null || user.getPassword() == null) {
            return new BusinessResult(false, 10011, "参数无效:账号,密码为空");
        }

        User existUser = userRepository.findByAccount(user.getAccount());
        if (existUser == null) {
            return new BusinessResult(false, 10012, "账号不存在");
        }

        if (!existUser.getPassword().equals(Utility.md5(user.getPassword()))) {
            return new BusinessResult(false, 10013, "密码错误");
        }

        existUser.setLastLoginTime(new Date());
        logger.info("登录用户:[账号:" + existUser.getAccount() + ",登录时间:" + existUser.getLastLoginTime() + "]");
        return new BusinessResult(true, existUser);
    }

    @Override
    public BusinessResult retrieveInformation(Integer userId) {
        if (userId == null) {
            return new BusinessResult(false, 10021, "参数无效:用户Id为空");
        }

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new BusinessResult(false, 10022, "该用户不存在");
        }

        logger.info("通过Id查询用户信息:[Id:" + existUser.getUserId() + "]");
        return new BusinessResult(true, existUser);
    }

    @Override
    @Transactional
    public BusinessResult updateInformation(User user) {
        if (user == null || user.getUserId() == null) {
            return new BusinessResult(false, 10031, "参数无效:用户Id为空");
        }

        User existUser = userRepository.findByUserId(user.getUserId());
        if (existUser == null) {
            return new BusinessResult(false, 10032, "该用户不存在");
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
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult updatePassword(Integer userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            return new BusinessResult(false, 10041, "参数无效:用户Id,旧密码或新密码为空");
        }

        //TODO 验证密码逻辑...

        User existUser = userRepository.findByUserId(userId);
        if (existUser == null) {
            return new BusinessResult(false, 10042, "该用户不存在");
        }

        if (!existUser.getPassword().equals(Utility.md5(oldPassword))) {
            return new BusinessResult(false, 10043, "旧密码错误");
        }

        existUser.setPassword(Utility.md5(newPassword));
        logger.info("修改用户密码:[用户Id:" + userId + "]");
        return new BusinessResult(true);
    }

    @Override
    @Transactional
    public BusinessResult updateAvatar(User user) {
        if (user == null || user.getUserId() == null || user.getAvatar() == null) {
            return new BusinessResult(false, 10051, "参数无效:用户Id或头像为空");
        }

        User existUser = userRepository.findByUserId(user.getUserId());
        if (existUser == null) {
            return new BusinessResult(false, 10052, "该用户不存在");
        }

        existUser.setAvatar(user.getAvatar());
        logger.info("修改用户头像:[用户Id:" + existUser.getUserId() + "]");
        return new BusinessResult(true, user.getAvatar());
    }

    @Override
    @Transactional
    public BusinessResult resetPassword(String email) {
        if (email == null) {
            return new BusinessResult(false, 10061, "参数无效:邮箱为空");
        }

        User existUser = userRepository.findByEmail(email);
        if (existUser == null) {
            return new BusinessResult(false, 10062, "该用户不存在");
        }

        String newPassword = Utility.randomPassword();
        existUser.setPassword(Utility.md5(newPassword));
        logger.info("通过邮箱重置用户密码:[邮箱:" + email + "]");
        return new BusinessResult(true, newPassword);
    }

    @Override
    public BusinessResult findFans(Integer focusId, Integer pageIndex) {
        if (focusId == null || pageIndex == null) {
            return new BusinessResult(false, 10071, "参数无效:关注者用户Id或页数为空");
        }

        if (userRepository.findByUserId(focusId) == null) {
            return new BusinessResult(false, 10072, "不存在的用户");
        }

        Page<UserCollection> userCollectionPage = userCollectionRepository.findAll((Root<UserCollection> root, CriteriaQuery<?> criteriaQuery
                        , CriteriaBuilder criteriaBuilder) -> (criteriaBuilder.equal(root.get("focus").get("userId"), focusId)),
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "createTime")));
        logger.info("获取用户粉丝:[用户Id:" + focusId + "]");
        return new BusinessResult(true, userCollectionPage);
    }

    @Override
    public BusinessResult findFocus(Integer fansId, Integer pageIndex) {
        if (fansId == null || pageIndex == null) {
            return new BusinessResult(false, 10081, "参数无效:粉丝用户Id或页数为空");
        }

        if (userRepository.findByUserId(fansId) == null) {
            return new BusinessResult(false, 10082, "不存在的用户");
        }

        Page<UserCollection> userCollectionPage = userCollectionRepository.findAll((Root<UserCollection> root, CriteriaQuery<?> criteriaQuery
                        , CriteriaBuilder criteriaBuilder) -> (criteriaBuilder.equal(root.get("fans").get("userId"), fansId)),
                PageRequest.of(pageIndex - 1, 10, new Sort(Sort.Direction.DESC, "createTime")));
        logger.info("获取用户关注:[用户Id:" + fansId + "]");
        return new BusinessResult(true, userCollectionPage);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll(new Sort(Sort.Direction.DESC, "registerTime"));
    }
}