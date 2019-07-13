package com.example.fivefivemm.controller.admin;

import com.example.fivefivemm.service.UserService;
import com.example.fivefivemm.utility.Utility;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 后台管理控制器
 *
 * @author tiga
 * @version 1.0
 * @since 2019年6月20日09:01:55
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class AdminController {

    @Resource
    private UserService userService;

    /**
     * 获取所有用户信息
     *
     * @return userList
     */
    @GetMapping("/admin/users")
    @ApiOperation(value = "获取所有用户信息")
    public ResponseEntity getAllUsers() {
        return ResponseEntity.ok(Utility.adminUserListBody(userService.findAllUsers()));
    }
}
