package com.example.fivefivemm.controller.action;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.relation.ActionCollection;
import com.example.fivefivemm.entity.relation.ActionWatch;
import com.example.fivefivemm.entity.tag.Tag;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.service.ActionService;
import com.example.fivefivemm.service.ActionTagService;
import com.example.fivefivemm.service.ActionWatchService;
import com.example.fivefivemm.service.CollectionService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import com.example.fivefivemm.utility.Utility;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 动态控制器
 * <p>
 * 添加获取用户是否对动态进行了约拍
 * <p>
 * 添加获取全部动态接口，修改获取动态参数userId为非必需，发表动态后返回该动态
 * 2019年5月21日18:19:42
 * <p>
 * 添加判断动态是否收藏
 * 2019年5月27日13:35:39
 * <p>
 * 添加获取用户收藏的动态
 * 2019年5月27日19:44:23
 * <p>
 * 优化大代码
 * 2019年6月14日18:45:43
 *
 * @author tiga
 * @version 1.4
 * @since 2019年5月19日13:20:51
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ActionController {

    @Resource
    private ActionService actionService;

    @Resource
    private ActionWatchService actionWatchService;

    @Resource
    private CollectionService collectionService;

    @Resource
    private ActionTagService actionTagService;

    /**
     * 发表动态
     *
     * @param map 动态对象
     * @return data:保存的动态 errorCode:20001 errorMessage:参数无效:作者用户Id,标题,地区,价格,内容为空
     */
    @PostMapping("/action")
    @ApiOperation(value = "发表动态", notes = "需提供作者Id,标题，地区，价格，内容")
    @SuppressWarnings("unchecked")
    @Transactional
    public ResponseEntity CreateAction(@RequestBody Map<String, Object> map) {
        BusinessResult createActionResult = actionService.CreateAction(new Action(new User((Integer) map.get("authorId")), map.get("title").toString(),
                map.get("address").toString(), (Integer) map.get("cost"), map.get("content").toString()));
        if (!createActionResult.getStatus()) {
            return ResponseEntity.ok(new ResponseBody(createActionResult.getErrorCode(), createActionResult.getErrorMessage()));
        }

        //保存动态成功后获取动态Id和标签Id集合
        Action action = (Action) createActionResult.getData();

        List<Integer> tagsId = (List<Integer>) map.get("tags");

        //绑定动态和标签
        BusinessResult createActionTagResult = actionTagService.CreateActionTag(action.getActionId(), tagsId);
        if (createActionTagResult.getStatus()) {
            return ResponseEntity.ok(null);
        } else {
            return ResponseEntity.ok(new ResponseBody(createActionTagResult.getErrorCode(), createActionTagResult.getErrorMessage()));
        }
    }

    /**
     * 查询动态
     *
     * @param actionId 动态Id
     * @param userId   用户Id
     * @return data:该动态对象 errorCode:20011 errorMessage:参数无效:参数无效:动态Id为空 errorCode:20012 errorMessage:该动态不存在
     */
    @GetMapping("/action")
    @ApiOperation(value = "查询动态", notes = "需提供动态Id")
    @SuppressWarnings("unchecked")
    public ResponseEntity RetrieveAction(@RequestParam Integer actionId, @RequestParam(required = false) Integer userId) {
        BusinessResult retrieveActionResult = actionService.RetrieveAction(actionId);
        if (retrieveActionResult.getStatus()) {
            Map<String, Object> actionMap = Utility.ActionBody((Action) retrieveActionResult.getData());
            //查询动态的标签
            BusinessResult findTagsResult = actionTagService.findTags(actionId);
            if (findTagsResult.getStatus()) {
                List<Tag> tags = (List<Tag>) findTagsResult.getData();
                actionMap.put("tags", tags);
            }
            if (userId != null) {
                actionMap.put("isWatched", actionWatchService.RetrieveActionWatch(new ActionWatch(new User(userId), new Action(actionId))));
                actionMap.put("isCollected", collectionService.findActionCollection(userId, actionId));
            }
            return ResponseEntity.ok(actionMap);
        }

        return ResponseEntity.ok(new ResponseBody(retrieveActionResult.getErrorCode(), retrieveActionResult.getErrorMessage()));
    }

    /**
     * 修改动态
     *
     * @param action 动态对象
     * @return errorCode:20021 errorMessage:参数无效:动态Id,作者用户Id,标题,地区,价格,内容为空 errorCode:20022 errorMessage:该动态不存在 errorCode:20023 errorMessage:没有权限修改，您不是该动态的作者
     */
    @PutMapping("/action")
    @ApiOperation(value = "修改动态", notes = "需提供动态Id，作者Id，标题，地区，价格，内容")
    public ResponseEntity UpdateAction(@RequestBody Action action) {
        BusinessResult updateActionResult = actionService.UpdateAction(action);
        if (updateActionResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(updateActionResult.getErrorCode(), updateActionResult.getErrorMessage()));
    }

    /**
     * 上传图片
     *
     * @param image 图片
     * @return data:图片地址 errorCode:20000 errorMessage:图片保存失败
     */
    @PostMapping("/action/image")
    @ApiOperation(value = "上传图片", notes = "需提供jpg,png文件")
    public ResponseEntity UpdateImage(@RequestParam MultipartFile image) {
        String imageAddress = Utility.saveImage(image, 2);
        if (imageAddress != null) {
            return ResponseEntity.ok(imageAddress);
        }

        return ResponseEntity.ok(new ResponseBody(20000, "图片保存失败"));
    }

    /**
     * 删除动态
     *
     * @param action 动态对象
     * @return errorCode:20031 errorMessage:参数无效:动态Id,作者Id为空 errorCode:20032 errorMessage:该动态不存在 errorCode:20033 errorMessage:没有权限删除，您不是该动态的作者
     */
    @DeleteMapping("/action")
    @ApiOperation(value = "删除动态", notes = "需提供动态Id,作者用户Id")
    public ResponseEntity DeleteAction(@RequestBody Action action) {
        BusinessResult deleteActionResult = actionService.DeleteAction(action);
        if (deleteActionResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(deleteActionResult.getErrorCode(), deleteActionResult.getErrorMessage()));
    }

    /**
     * 获取动态集合
     *
     * @param userId    用户Id
     * @param type      查询类型 1.用户自己的动态 2.所有动态 3.用户收藏的动态
     * @param pageIndex 页数
     * @return data:动态集合
     */
    @GetMapping("/actions")
    @ApiOperation(value = "获取动态集合", notes = "需提供动态Id,查询类型,页数")
    @SuppressWarnings("unchecked")
    public ResponseEntity RetrieveActions(@RequestParam(required = false) Integer userId, @RequestParam Integer type, @RequestParam Integer pageIndex) {
        if (type == 1) {
            BusinessResult retrieveActionsResult = actionService.findActionByAuthor(userId, pageIndex);
            if (retrieveActionsResult.getStatus()) {
                return ResponseEntity.ok(Utility.PageActionMap((Page<Action>) retrieveActionsResult.getData()));
            }

            return ResponseEntity.ok(new ResponseBody(retrieveActionsResult.getErrorCode(), retrieveActionsResult.getErrorMessage()));
        } else if (type == 2) {
            BusinessResult result = actionService.findActionByPage(pageIndex, null, null, null, null, null);
            if (result.getStatus()) {
                return ResponseEntity.ok(Utility.PageActionMap((Page<Action>) result.getData()));
            }

            return ResponseEntity.ok(new ResponseBody(result.getErrorCode(), result.getErrorMessage()));
        } else if (type == 3) {
            BusinessResult retrieveActionCollectionResult = actionService.findActionByCollection(userId, pageIndex);
            if (retrieveActionCollectionResult.getStatus()) {
                return ResponseEntity.ok(Utility.PageActionCollectionMap((Page<ActionCollection>) retrieveActionCollectionResult.getData()));
            }

            return ResponseEntity.ok(new ResponseBody(retrieveActionCollectionResult.getErrorCode(), retrieveActionCollectionResult.getErrorMessage()));
        } else {
            return ResponseEntity.ok(new ResponseBody(20000, "错误的查询类型"));
        }
    }

    /**
     * 条件分页查询动态
     *
     * @param pageIndex  页数
     * @param address    地区
     * @param authorType 作者类型
     * @param authorSex  作者性别
     * @param minCost    最低价
     * @param maxCost    最高价
     * @return data:动态集合
     */
    @GetMapping("/actions/conditions")
    @ApiOperation(value = "条件分页查询动态", notes = "需提供页数,查询条件如地区,作者类型,作者性别,最低价,最高价")
    @SuppressWarnings("unchecked")
    public ResponseEntity RetrieveActionsConditions(@RequestParam Integer pageIndex, String address, String authorType, String authorSex, Integer minCost, Integer maxCost) {
        BusinessResult result = actionService.findActionByPage(pageIndex, address, authorType, authorSex, minCost, maxCost);
        if (result.getStatus()) {
            return ResponseEntity.ok(Utility.PageActionMap((Page<Action>) result.getData()));
        }

        return ResponseEntity.ok(new ResponseBody(result.getErrorCode(), result.getErrorMessage()));
    }
}