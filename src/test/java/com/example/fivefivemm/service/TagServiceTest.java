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
 * 标签测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年6月29日14:54:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Resource
    private TagService tagService;

    @Test
    public void CreateTagTest() {
        BusinessResult businessResult = tagService.CreateTag(new Tag("java"));
        if (businessResult.getStatus()) {
            System.out.println("保存成功");
        } else {
            System.out.println("错误代码:" + businessResult.getErrorCode() + ",错误原因:" + businessResult.getErrorMessage());
        }
    }

    @Test
    public void DeleteTagTest() {
        BusinessResult businessResult = tagService.DeleteTag(new Tag(16));
        if (businessResult.getStatus()) {
            System.out.println("删除成功");
        } else {
            System.out.println("错误代码:" + businessResult.getErrorCode() + ",错误原因:" + businessResult.getErrorMessage());
        }
    }
}
