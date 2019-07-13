package com.example.fivefivemm.controller;

import com.example.fivefivemm.controller.tag.TagController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 标签控制器测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年7月13日10:43:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagControllerTest {

    @Resource
    private TagController tagController;

    @Test
    public void CreateTagTest() {
        System.out.println(tagController.CreateTag("vue"));
    }

    @Test
    public void DeleteTagTest() {
        System.out.println(tagController.DeleteTag(17));
    }
}
