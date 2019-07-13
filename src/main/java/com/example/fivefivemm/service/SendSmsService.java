package com.example.fivefivemm.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;

public interface SendSmsService {

//    /**
//     * 发送通知短信
//     *
//     * @param phone 手机号码
//     * @return 。。。
//     */
//    SendSmsResponse SendSmsForMessage(String phone);

    /**
     * 发送验证码短信
     *
     * @param phone 手机号码
     * @param code  验证码
     * @return 。。。
     */
    SendSmsResponse SendSmsForCode(String phone, String code);
}
