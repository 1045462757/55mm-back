package com.example.fivefivemm.controller.relation;

import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户关注信息控制器
 * <p>
 * 优化大代码
 * 2019年6月15日10:31:12
 *
 * @author tiga
 * @version 1.1
 * @since 2019年5月27日11:39:26
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class UserCollectionController {

    @Resource
    private CollectionService collectionService;

    /**
     * 关注用户
     *
     * @param focusId 关注用户Id
     * @param fansId  粉丝Id
     * @return errorCode:50021 errorMessage:参数无效:关注者用户Id,粉丝用户Id为空 errorCode:50022 errorMessage:不存在的用户
     * errorCode:50023 errorMessage:已存在的关注关系
     */
    @PostMapping(value = "/userCollection")
    @ApiOperation(value = "关注用户", notes = "需提供关注者用户Id，粉丝用户Id")
    public ResponseEntity CreateUserCollection(@RequestParam Integer focusId, @RequestParam Integer fansId) {
        BusinessResult createUserCollectionResult = collectionService.addUserCollection(focusId, fansId);
        if (createUserCollectionResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(createUserCollectionResult.getErrorCode(), createUserCollectionResult.getErrorMessage()));
    }

    /**
     * 取消关注用户
     *
     * @param focusId 关注用户Id
     * @param fansId  粉丝Id
     * @return errorCode:50031 errorMessage:参数无效:关注者用户Id,粉丝用户Id为空 errorCode:50032 errorMessage:不存在的关注关系
     */
    @DeleteMapping(value = "/userCollection")
    @ApiOperation(value = "取消关注用户", notes = "需提供关注者用户Id，粉丝用户Id")
    public ResponseEntity DeleteUserCollection(@RequestParam Integer focusId, @RequestParam Integer fansId) {
        BusinessResult deleteUserCollectionResult = collectionService.removeUserCollection(focusId, fansId);
        if (deleteUserCollectionResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(deleteUserCollectionResult.getErrorCode(), deleteUserCollectionResult.getErrorMessage()));
    }
}