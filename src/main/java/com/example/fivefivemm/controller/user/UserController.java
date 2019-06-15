package com.example.fivefivemm.controller.user;

import com.example.fivefivemm.entity.relation.UserCollection;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.service.SendMailService;
import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import com.example.fivefivemm.utility.Utility;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
 * <p>
 * 代码大优化
 * 2019年6月14日11:26:29
 *
 * @author tiga
 * @version 1.4
 * @since 2019年5月13日20:06:27
 */

//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
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
     * @return errorCode:10001 errorMessage:参数无效:账号,密码或邮箱为空 errorCode:10002 errorMessage:该账号已被注册 errorCode:10003 errorMessage:该邮箱已被注册
     */
    @PostMapping("/user/account")
    @ApiOperation(value = "用户注册", notes = "需提供账号，密码，邮箱")
    public ResponseEntity register(@RequestBody User user) {
        BusinessResult registerResult = userService.register(user);
        if (registerResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(registerResult.getErrorCode(), registerResult.getErrorMessage()));
    }

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return data:该用户的信息 errorCode:10011 errorMessage:参数无效:账号,密码为空 errorCode:10012 errorMessage:账号不存在 errorCode:10013 errorMessage:密码错误
     */
    @PostMapping("/user")
    @ApiOperation(value = "用户登录", notes = "需提供账号，密码")
    public ResponseEntity login(@RequestBody User user) {
        BusinessResult loginResult = userService.login(user);
        if (loginResult.getStatus()) {
            return ResponseEntity.ok(Utility.userBody((User) loginResult.getData(), 1));
        }

        return ResponseEntity.ok(new ResponseBody(loginResult.getErrorCode(), loginResult.getErrorMessage()));
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户Id
     * @return data:该用户的信息 errorCode:10021 errorMessage:参数无效:用户Id为空 errorCode:10022 errorMessage:该用户不存在
     */
    @GetMapping("/user/information")
    @ApiOperation(value = "获取用户信息", notes = "需提供用户Id")
    public ResponseEntity retrieveInformation(@RequestParam Integer userId) {
        BusinessResult retrieveInformationResult = userService.retrieveInformation(userId);
        if (retrieveInformationResult.getStatus()) {
            return ResponseEntity.ok(Utility.userBody((User) retrieveInformationResult.getData(), 1));
        }

        return ResponseEntity.ok(new ResponseBody(retrieveInformationResult.getErrorCode(), retrieveInformationResult.getErrorMessage()));
    }

    /**
     * 修改用户信息
     *
     * @param user 用户对象
     * @return errorCode:10031 errorMessage:参数无效:用户Id为空 errorCode:10032 errorMessage:该用户不存在
     */
    @PutMapping("/user/information")
    @ApiOperation(value = "修改用户信息", notes = "需提供用户Id'")
    public ResponseEntity updateInformation(@RequestBody User user) {
        BusinessResult updateInformationResult = userService.updateInformation(user);
        if (updateInformationResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(updateInformationResult.getErrorCode(), updateInformationResult.getErrorMessage()));
    }

    /**
     * 上传头像
     *
     * @param avatar 头像图片
     * @param userId 用户Id
     * @return data:新头像地址 errorCode:10051 errorMessage 参数无效:用户Id或头像为空 errorCode:10052 errorMessage:该用户不存在
     */
    @PostMapping("/user/avatar")
    @ApiOperation(value = "上传头像", notes = "需提供用户Id，头像")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile avatar, @RequestParam Integer userId) {
        BusinessResult updateAvatarResult = userService.updateAvatar(new User(userId, Utility.saveImage(avatar, 1)));
        if (updateAvatarResult.getStatus()) {
            return ResponseEntity.ok(updateAvatarResult.getData());
        }

        return ResponseEntity.ok(new ResponseBody(updateAvatarResult.getErrorCode(), updateAvatarResult.getErrorMessage()));
    }

    /**
     * 修改密码
     *
     * @param userId      用户Id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return errorCode:10041 errorMessage:参数无效:用户Id,旧密码或新密码为空 errorCode:10042 errorMessage:该用户不存在 errorCode:10043 errorMessage:旧密码错误
     */
    @PutMapping("/user/password")
    @ApiOperation(value = "修改密码", notes = "需提供用户Id，旧密码，新密码")
    public ResponseEntity updatePassword(@RequestParam Integer userId, @RequestParam String oldPassword, @RequestParam String newPassword) {
        BusinessResult updatePasswordResult = userService.updatePassword(userId, oldPassword, newPassword);
        if (updatePasswordResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(updatePasswordResult.getErrorCode(), updatePasswordResult.getErrorMessage()));
    }

    /**
     * 通过邮箱重置密码
     *
     * @param email 用户注册的邮箱
     * @return data:新密码 errorCode:10061 errorMessage 参数无效:邮箱为空 errorCode:10062 errorMessage:该用户不存在
     */
    @PostMapping("/user/password")
    @ApiOperation(value = "通过邮箱重置密码", notes = "需提供邮箱")
    public ResponseEntity resetPassword(@RequestParam String email) {
        BusinessResult resetPasswordResult = userService.resetPassword(email);
        if (resetPasswordResult.getStatus()) {
            sendMailService.sendMailForResetPassWord(email, (String) resetPasswordResult.getData());
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(resetPasswordResult.getErrorCode(), resetPasswordResult.getErrorMessage()));
    }

    /**
     * 获取其他用户信息
     *
     * @param userId 用户Id
     * @return data:该用户的信息 errorCode:10021 errorMessage:参数无效:用户Id为空 errorCode:10022 errorMessage:该用户不存在
     */
    @GetMapping("/user/information/others")
    @ApiOperation(value = "获取其他用户信息", notes = "需提供用户Id，查看者用户Id")
    public ResponseEntity retrieveOtherInformation(@RequestParam Integer userId, @RequestParam(required = false) Integer watcherId) {
        BusinessResult retrieveInformationResult = userService.retrieveInformation(userId);
        if (retrieveInformationResult.getStatus()) {
            Map<String, Object> userMap = Utility.userBody((User) retrieveInformationResult.getData(), 2);
            //判断查看者是否关注该用户
            if (watcherId != null) {
                userMap.put("isFocus", collectionService.findUserCollection(userId, watcherId));
            }
            return ResponseEntity.ok(userMap);
        }

        return ResponseEntity.ok(new ResponseBody(retrieveInformationResult.getErrorCode(), retrieveInformationResult.getErrorMessage()));
    }

    /**
     * 分页获取用户的关注
     *
     * @param userId    用户Id
     * @param pageIndex 页数
     * @return data:Page<UserCollection>对象 errorCode:10071 errorMessage 参数无效:粉丝用户Id或页数为空 errorCode:10072 errorMessage:不存在的用户
     */
    @GetMapping("/user/focus")
    @ApiOperation(value = "分页获取用户的关注", notes = "需提供用户Id，页数")
    @SuppressWarnings("unchecked")
    public ResponseEntity retrieveFocus(@RequestParam Integer userId, @RequestParam Integer pageIndex) {
        BusinessResult retrieveFocusResult = userService.findFocus(userId, pageIndex);
        if (retrieveFocusResult.getStatus()) {
            return ResponseEntity.ok(Utility.PageUserMap((Page<UserCollection>) retrieveFocusResult.getData()));
        }

        return ResponseEntity.ok(new ResponseBody(retrieveFocusResult.getErrorCode(), retrieveFocusResult.getErrorMessage()));
    }

    /**
     * 分页获取用户的粉丝
     *
     * @param userId    用户Id
     * @param pageIndex 页数
     * @return data:Page<UserCollection>对象 errorCode:10071 errorMessage 参数无效:关注者用户Id或页数为空 errorCode:10072 errorMessage:不存在的用户
     */
    @GetMapping("/user/fans")
    @ApiOperation(value = "分页获取用户的粉丝", notes = "需提供用户Id，页数")
    @SuppressWarnings("unchecked")
    public ResponseEntity retrieveFans(@RequestParam Integer userId, @RequestParam Integer pageIndex) {
        BusinessResult retrieveFansResult = userService.findFans(userId, pageIndex);
        if (retrieveFansResult.getStatus()) {
            return ResponseEntity.ok(Utility.PageUserMap((Page<UserCollection>) retrieveFansResult.getData()));
        }

        return ResponseEntity.ok(new ResponseBody(retrieveFansResult.getErrorCode(), retrieveFansResult.getErrorMessage()));
    }
}