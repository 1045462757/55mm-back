package com.example.fivefivemm.controller.relation;

import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户关注信息控制器
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月27日11:39:26
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@Controller
public class UserCollectionController {

    @Resource
    private CollectionService collectionService;

    /**
     * 关注用户
     *
     * @param focusId 关注用户Id
     * @param fansId  粉丝Id
     * @return status:200,161
     * @since 2019年4月7日17:19:24
     */
    @PostMapping(value = "/userCollection")
    @ResponseBody
    public String CreateUserCollection(@RequestParam Integer focusId, @RequestParam Integer fansId) {
        Result createUserCollectionResult = collectionService.addUserCollection(focusId, fansId);
        if (createUserCollectionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(101, createUserCollectionResult.getMessage(), null);
        }
    }

    /**
     * 取消关注用户
     *
     * @param focusId 关注用户Id
     * @param fansId  粉丝Id
     * @return status:200,171
     * @since 2019年4月7日17:21:40
     */
    @DeleteMapping(value = "/userCollection")
    @ResponseBody
    public String DeleteUserCollection(@RequestParam Integer focusId, @RequestParam Integer fansId) {
        Result deleteUserCollectionResult = collectionService.removeUserCollection(focusId, fansId);
        if (deleteUserCollectionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(102, deleteUserCollectionResult.getMessage(), null);
        }
    }
}
