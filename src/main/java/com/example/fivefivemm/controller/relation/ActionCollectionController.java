package com.example.fivefivemm.controller.relation;

import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.utility.Result;
import com.example.fivefivemm.utility.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 动态关注信息控制器
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
public class ActionCollectionController {

    @Resource
    private CollectionService collectionService;

    /**
     * 收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return status:200,151
     * @since 2019年4月5日17:23:08
     */
    @PostMapping(value = "/actionCollection")
    @ResponseBody
    public String CreateActionCollection(@RequestParam Integer userId, @RequestParam Integer actionId) {
        Result createActionCollectionResult = collectionService.addActionCollection(userId, actionId);
        if (createActionCollectionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(101, createActionCollectionResult.getMessage(), null);
        }
    }

    /**
     * 取消收藏动态
     *
     * @param userId   用户Id
     * @param actionId 动态Id
     * @return status:200,161
     * @since 2019年4月5日17:44:20
     */
    @DeleteMapping(value = "/actionCollection")
    @ResponseBody
    public String DeleteActionCollection(@RequestParam Integer userId, @RequestParam Integer actionId) {
        Result deleteActionCollectionResult = collectionService.removeActionCollection(userId, actionId);
        if (deleteActionCollectionResult.getStatus().equals(Result.success)) {
            return Utility.ResultBody(200, null, null);
        } else {
            return Utility.ResultBody(102, deleteActionCollectionResult.getMessage(), null);
        }
    }
}
