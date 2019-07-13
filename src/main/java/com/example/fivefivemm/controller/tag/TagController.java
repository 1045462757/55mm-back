package com.example.fivefivemm.controller.tag;

import com.example.fivefivemm.entity.tag.Tag;
import com.example.fivefivemm.service.TagService;
import com.example.fivefivemm.utility.BusinessResult;
import com.example.fivefivemm.utility.ResponseBody;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 标签控制器
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月13日10:33:44
 */
//生产环境
//@CrossOrigin(origins = "https://hylovecode.cn")
//本地测试
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 增加标签
     *
     * @param tagName 标签名称
     * @return data:保存的标签 errorCode:60001 errorMessage:参数无效:标签名称为空
     */
    @PostMapping("/tag")
    @ApiOperation(value = "新增标签", notes = "需提供标签名称")
    public ResponseEntity CreateTag(@RequestParam String tagName) {
        BusinessResult createTagResult = tagService.CreateTag(new Tag(tagName));
        if (createTagResult.getStatus()) {
            return ResponseEntity.ok(createTagResult.getData());
        }

        return ResponseEntity.ok(new ResponseBody(createTagResult.getErrorCode(), createTagResult.getErrorMessage()));
    }

    /**
     * 删除标签
     *
     * @param tagId 标签Id
     * @return errorCode:60002 errorMessage:参数无效:标签Id为空 errorCode:60003 errorMessage:不存在的标签
     */
    @DeleteMapping("/tag")
    @ApiOperation(value = "删除标签", notes = "需提供标签Id")
    public ResponseEntity DeleteTag(@RequestParam Integer tagId) {
        BusinessResult deleteTagResult = tagService.DeleteTag(new Tag(tagId));
        if (deleteTagResult.getStatus()) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(new ResponseBody(deleteTagResult.getErrorCode(), deleteTagResult.getErrorMessage()));
    }
}
