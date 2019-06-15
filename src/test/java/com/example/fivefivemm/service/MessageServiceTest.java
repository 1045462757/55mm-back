package com.example.fivefivemm.service;

import com.example.fivefivemm.entity.action.Action;
import com.example.fivefivemm.entity.message.Message;
import com.example.fivefivemm.entity.user.User;
import com.example.fivefivemm.utility.BusinessResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 消息业务测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月22日19:51:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {

    @Resource
    private MessageService messageService;

    @Test
    public void CreateMessageTest() {
        BusinessResult createMessageTestResult = messageService.CreateMessage(new Message(new Action(new User(1), 1), new User(2)));
        if (createMessageTestResult.getStatus()) {
            System.out.println("保存消息成功");
        } else {
            System.out.println("[保存消息失败:错误代码:" + createMessageTestResult.getErrorCode() + ",错误原因:" + createMessageTestResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void RetrieveForWatcherTest() {
        BusinessResult retrieveForWatcherResult = messageService.RetrieveForWatcher(3, 1);
        if (retrieveForWatcherResult.getStatus()) {
            Page<Message> messagePage = (Page<Message>) retrieveForWatcherResult.getData();
            System.out.println("总页数:" + messagePage.getTotalPages());
            System.out.println("总元素:" + messagePage.getTotalElements());
            System.out.println("当前页:" + (messagePage.getNumber() + 1));
            System.out.println("当前页元素个数:" + messagePage.getNumberOfElements());
            System.out.println("当前页元素:" + messagePage.getContent());
        } else {
            System.out.println("[按约拍者查找消息失败:错误代码:" + retrieveForWatcherResult.getErrorCode() + ",错误原因:" + retrieveForWatcherResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void RetrieveForActionAuthorIdTest() {
        BusinessResult retrieveForActionAuthorResult = messageService.RetrieveForActionAuthorId(3, 1);
        if (retrieveForActionAuthorResult.getStatus()) {
            Page<Message> messagePage = (Page<Message>) retrieveForActionAuthorResult.getData();
            System.out.println("总页数:" + messagePage.getTotalPages());
            System.out.println("总元素:" + messagePage.getTotalElements());
            System.out.println("当前页:" + (messagePage.getNumber() + 1));
            System.out.println("当前页元素个数:" + messagePage.getNumberOfElements());
            System.out.println("当前页元素:" + messagePage.getContent());
        } else {
            System.out.println("[按动态作者查找消息失败:错误代码:" + retrieveForActionAuthorResult.getErrorCode() + ",错误原因:" + retrieveForActionAuthorResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void UpdateIsAcceptTest() {
        BusinessResult updateIsAcceptResult = messageService.UpdateIsAccept(3, "接受");
        if (updateIsAcceptResult.getStatus()) {
            System.out.println("回复约拍成功");
        } else {
            System.out.println("[回复约拍失败:错误代码:" + updateIsAcceptResult.getErrorCode() + ",错误原因:" + updateIsAcceptResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void UpdateIsReadTest() {
        BusinessResult updateIsReadResult = messageService.UpdateIsRead(3);
        if (updateIsReadResult.getStatus()) {
            System.out.println("浏览消息成功");
        } else {
            System.out.println("[浏览消息失败:错误代码:" + updateIsReadResult.getErrorCode() + ",错误原因:" + updateIsReadResult.getErrorMessage() + "]");
        }
    }

    @Test
    public void DeleteMessageTest() {
        BusinessResult deleteMessageTestResult = messageService.DeleteMessage(new Message(new Action(new User(1), 1), new User(2)));
        if (deleteMessageTestResult.getStatus()) {
            System.out.println("删除消息成功");
        } else {
            System.out.println("[删除消息失败:错误代码:" + deleteMessageTestResult.getErrorCode() + ",错误原因:" + deleteMessageTestResult.getErrorMessage() + "]");
        }
    }
}
