package com.example.fivefivemm.service;

import com.example.fivefivemm.utility.BusinessResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 收藏业务测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月27日11:54:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectionServiceTest {

    @Resource
    private CollectionService collectionService;

    @Test
    public void addActionCollectionTest() {
        BusinessResult addActionCollectionResult = collectionService.addActionCollection(3, 19);
        if (addActionCollectionResult.getStatus()) {
            System.out.println("收藏动态成功");
        } else {
            System.out.println("[收藏动态失败:错误代码:" + addActionCollectionResult.getErrorCode() + ",错误原因:" + addActionCollectionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void removeActionCollectionTest() {
        BusinessResult removeActionCollectionResult = collectionService.removeActionCollection(3, 19);
        if (removeActionCollectionResult.getStatus()) {
            System.out.println("取消收藏动态成功");
        } else {
            System.out.println("[取消收藏动态失败:错误代码:" + removeActionCollectionResult.getErrorCode() + ",错误原因:" + removeActionCollectionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void findActionCollectionTest() {
        System.out.println(collectionService.findActionCollection(3, 10));
    }

    @Test
    public void addUserCollectionTest() {
        BusinessResult addUserCollectionResult = collectionService.addUserCollection(3, 4);
        if (addUserCollectionResult.getStatus()) {
            System.out.println("关注用户成功");
        } else {
            System.out.println("[关注用户失败:错误代码:" + addUserCollectionResult.getErrorCode() + ",错误原因:" + addUserCollectionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void removeUserCollectionTest() {
        BusinessResult removeUserCollectionResult = collectionService.removeUserCollection(3, 4);
        if (removeUserCollectionResult.getStatus()) {
            System.out.println("取消关注用户成功");
        } else {
            System.out.println("[取消关注用户失败:错误代码:" + removeUserCollectionResult.getErrorCode() + ",错误原因:" + removeUserCollectionResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void findUserCollectionTest() {
        System.out.println(collectionService.findUserCollection(4, 4));
    }
}
