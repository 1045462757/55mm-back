package com.example.fivefivemm.controller.user;

import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.service.SendMailService;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 * <p>
 * 添加通过邮箱重置密码
 * 2019年5月22日08:11:12
 * <p>
 * 添加获取其他用户信息接口
 * 2019年5月25日16:25:08
 * <p>
 * 添加获取是否关注用户
 * 2019年5月27日13:41:23
 * <p>
 * 添加获取用户关注和粉丝
 * 2019年5月27日20:09:55
 *
 * @author tiga
 * @version 1.3
 * @since 2019年5月13日20:06:27
 */

//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private SendMailService sendMailService;

    @Resource
    private CollectionService collectionService;

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return massage 101 1.参数无效 2.该账号已被注册 3.该邮箱已被注册
     */
    @PostMapping("/user/account")
    @ResponseBody
    public String register(@RequestBody User user) {
        Result registerResult = userService.register(user);
        if (registerResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(101, registerResult.getMessage(), null);
        }
    }

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return data:用户Id message 102 1.参数无效 2.账号不存在 3.密码错误
     */
    @PostMapping("/user")
    @ResponseBody
    public String login(@RequestBody User user) {
        Result loginResult = userService.login(user);
        if (loginResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, Utility.userBody((User) loginResult.getData(), 1));
        } else {
            return Utility.ResultBody(102, loginResult.getMessage(), null);
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户Id
     * @return data:user对象 message 103 1.userId为空 2.该用户不存在
     */
    @GetMapping("/user/information")
    @ResponseBody
    public String retrieveInformation(@RequestParam Integer userId) {
        Result retrieveInformationResult = userService.retrieveInformation(userId);
        if (retrieveInformationResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, Utility.userBody((User) retrieveInformationResult.getData(), 1));
        } else {
            return Utility.ResultBody(103, retrieveInformationResult.getMessage(), null);
        }
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return message 104 1.参数无效 2.该用户不存在
     */
    @PutMapping("/user/information")
    @ResponseBody
    public String updateInformation(@RequestBody User user) {
        Result updateInformationResult = userService.updateInformation(user);
        if (updateInformationResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(104, updateInformationResult.getMessage(), null);
        }
    }

    /**
     * 上传头像
     *
     * @param avatar 头像图片
     * @param userId 用户Id
     * @return data:头像图片地址 message 105 1.参数无效 2.该用户不存在
     */
    @PostMapping("/user/avatar")
    @ResponseBody
    public String updateAvatar(@RequestParam MultipartFile avatar, @RequestParam Integer userId) {
        Result updateAvatarResult = userService.updateAvatar(new User(userId, Utility.saveImage(avatar, 1)));
        if (updateAvatarResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, updateAvatarResult.getData());
        } else {
            return Utility.ResultBody(105, updateAvatarResult.getMessage(), null);
        }
    }

    /**
     * 修改密码
     *
     * @param userId      用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return message 106 1.参数无效 2.该用户不存在 3.旧密码错误
     */
    @PutMapping("/user/password")
    @ResponseBody
    public String updatePassword(@RequestParam Integer userId, @RequestParam String oldPassword, @RequestParam String newPassword) {
        Result updatePasswordResult = userService.updatePassword(userId, oldPassword, newPassword);
        if (updatePasswordResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(106, updatePasswordResult.getMessage(), null);
        }
    }

    /**
     * 通过邮箱重置密码
     *
     * @param email 用户注册的邮箱
     * @return message 107 1.参数无效 2.该用户不存在
     */
    @PostMapping("/user/password")
    @ResponseBody
    public String resetPassword(@RequestParam String email) {
        Result resetPasswordResult = userService.resetPassword(email);
        if (resetPasswordResult.getStatus().equals(Result.success)) {
            sendMailService.sendMailForResetPassWord(email, (String) resetPasswordResult.getData());
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(107, resetPasswordResult.getMessage(), null);
        }
    }

    /**
     * 获取其他用户信息
     *
     * @param userId 用户Id
     * @return data:user对象 message 108 1.userId为空 2.该用户不存在
     */
    @GetMapping("/user/information/others")
    @ResponseBody
    public String retrieveOtherInformation(@RequestParam Integer userId, @RequestParam(required = false) Integer watcherId) {
        Result retrieveInformationResult = userService.retrieveInformation(userId);
        if (retrieveInformationResult.getStatus().equals(Result.success)) {
            Map<String, Object> userMap = Utility.userBody((User) retrieveInformationResult.getData(), 2);
            if (watcherId != null) {
                userMap.put("isFocus", collectionService.findUserCollection(userId, watcherId));
            }
            return Utility.ResultBody(200, null, userMap);
        } else {
            return Utility.ResultBody(103, retrieveInformationResult.getMessage(), null);
        }
    }

    /**
     * 获取用户的关注
     *
     * @param userId 用户Id
     * @return data:用户关注的对象集合
     */
    @GetMapping("/user/focus")
    @ResponseBody
    public String retrieveFocus(@RequestParam Integer userId) {
        Result retrieveFocusResult = userService.retrieveFocus(userId);
        if (retrieveFocusResult.getStatus().equals(Result.success)) {
            List<User> FocusList = (List<User>) retrieveFocusResult.getData();
            return Utility.ResultBody(200, null, Utility.userListBody(FocusList));
        } else {
            return Utility.ResultBody(104, retrieveFocusResult.getMessage(), null);
        }
    }

    /**
     * 获取用户的粉丝
     *
     * @param userId 用户Id
     * @return data：用户的粉丝集合
     */
    @GetMapping("/user/fans")
    @ResponseBody
    public String retrieveFans(@RequestParam Integer userId) {
        Result retrieveFansResult = userService.retrieveFans(userId);
        if (retrieveFansResult.getStatus().equals(Result.success)) {
            List<User> FansList = (List<User>) retrieveFansResult.getData();
            return Utility.ResultBody(200, null, Utility.userListBody(FansList));
        } else {
            return Utility.ResultBody(104, retrieveFansResult.getMessage(), null);
        }
    }
}