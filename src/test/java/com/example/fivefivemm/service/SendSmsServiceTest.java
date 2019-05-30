package com.example.fivefivemm.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.example.fivefivemm.utility.Utility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 短信业务测试类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月29日21:54:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SendSmsServiceTest {

    @Resource
    public SendSmsService sendSmsService;

    @Test
    public void sendSmsTest() {
        SendSmsResponse response = sendSmsService.SendSmsForCode("15696136261", Utility.VerificationCode());
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
    }
}
