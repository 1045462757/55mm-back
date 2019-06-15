package com.example.fivefivemm.controller.relation;

import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 动态关注信息控制器
 * <p>
 * 优化大代码
 * 2019年6月15日10:28:57
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
public class ActionCollectionController {

    @Resource
    private CollectionService collectionService;

    /**
     * 收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return errorCode:50001 errorMessage:参数无效:用户Id,动态Id为空 errorCode:50002 errorMessage:不存在的用户
     * errorCode:50003 errorMessage:不存在的动态 errorCode:50004 errorMessage:已存在的收藏关系
     */
    @PostMapping(value = "/actionCollection")
    @ApiOperation(value = "收藏动态", notes = "需提供动态Id，收藏用户Id")
    public ResponseEntity CreateActionCollection(@RequestParam Integer userId, @RequestParam Integer actionId) {
        BusinessResult createActionCollectionResult = collectionService.addActionCollection(userId, actionId);
        if (createActionCollectionResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(createActionCollectionResult.getErrorCode(), createActionCollectionResult.getErrorMessage()));
    }

    /**
     * 取消收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return errorCode:50011 errorMessage:参数无效:用户Id,动态Id为空 errorCode:50012 errorMessage:不存在的用户
     * errorCode:50013 errorMessage:不存在的收藏关系
     */
    @DeleteMapping(value = "/actionCollection")
    @ApiOperation(value = "取消收藏动态", notes = "需提供动态Id，收藏用户Id")
    public ResponseEntity DeleteActionCollection(@RequestParam Integer userId, @RequestParam Integer actionId) {
        BusinessResult deleteActionCollectionResult = collectionService.removeActionCollection(userId, actionId);
        if (deleteActionCollectionResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(deleteActionCollectionResult.getErrorCode(), deleteActionCollectionResult.getErrorMessage()));
    }
}