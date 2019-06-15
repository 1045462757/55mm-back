package com.example.fivefivemm.controller;

import com.example.fivefivemm.controller.message.MessageController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 消息控制器测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月22日19:53:03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerTest {

    @Resource
    private MessageController messageController;

    @Test
    public void RetrieveMessageForWatcherTest() {
        System.out.println(messageController.RetrieveMessageForWatcher(3,1));
    }

    @Test
    public void RetrieveMessageForActionAuthorTest() {
        System.out.println(messageController.RetrieveMessageForActionAuthor(3,1));
    }

    @Test
    public void UpdateMessageIsReadTest() {
        System.out.println(messageController.UpdateMessageIsAccept(4, "拒绝"));
    }

    @Test
    public void UpdateMessageIsAcceptTest() {
        System.out.println(messageController.UpdateMessageIsRead(4));
    }
}
