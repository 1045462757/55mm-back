package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.tag.Tag;
import com.example.fivefivemm.utility.BusinessResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态标签业务测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月12日10:42:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActionTagServiceTest {

    @Resource
    private ActionTagService actionTagService;

    @Test
    public void CreateActionTagTest() {
        List<Integer> tagList = new ArrayList<>();
        tagList.add(1);
        tagList.add(2);
        tagList.add(99);
        BusinessResult createCreateActionTagResult = actionTagService.CreateActionTag(1, tagList);
        if (createCreateActionTagResult.getStatus()) {
            System.out.println("保存成功");
        } else {
            System.out.println("错误代码:" + createCreateActionTagResult.getErrorCode() + ",错误原因:" + createCreateActionTagResult.getErrorMessage());
        }
    }

    @Test
    public void findTagsTest() {
        BusinessResult businessResult = actionTagService.findTags(2);
        if (businessResult.getStatus()) {
            List<Tag> tags = (List<Tag>)businessResult.getData();
            System.out.println(tags);
        } else {
            System.out.println("错误代码:" + businessResult.getErrorCode() + ",错误原因:" + businessResult.getErrorMessage());
        }
    }
}
