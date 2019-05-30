package com.example.fivefivemm.service.Implements;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.fivefivemm.service.SendSmsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 短信业务类
 *
 * @author tiga
 * @version 1.0
 * @since 2019年5月29日21:51:10
 */
@Service
public class SendSmsServiceImplements implements SendSmsService {

    @Value("${aliyun.sms.accessKeyId}")
    public String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    public String accessKeySecret;

    @Value("${aliyun.sms.product}")
    public String product;

    @Value("${aliyun.sms.domain}")
    public String domain;

    @Value("${aliyun.sms.regionId}")
    public String region;

    @Value("${aliyun.sms.endpointName}")
    public String endpointName;

    @Value("${aliyun.sms.defaultConnectTimeout}")
    public String defaultConnectTimeout;

    @Value("${aliyun.sms.defaultReadTimeout}")
    public String defaultReadTimeout;

    @Value("${aliyun.sms.signName}")
//    @Value("约拍网")
    public String signName;

    @Value("${aliyun.sms.templateCode}")
    public String templateCode;

    private Logger logger = LogManager.getLogger(this.getClass());

    @Override
    @Async
    public SendSmsResponse SendSmsForMessage(String phone) {
        //ToDo
        return null;
    }

    @Override
    @Async
    public SendSmsResponse SendSmsForCode(String phone, String code) {
        try {
            if (phone != null && phone.length() == 11 && code != null && code.length() == 6) {

                System.setProperty("sun.net.client.defaultConnectTimeout", defaultConnectTimeout);
                System.setProperty("sun.net.client.defaultReadTimeout", defaultReadTimeout);

                // 初始化acsClient,暂不支持region化
                IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
                DefaultProfile.addEndpoint(endpointName, region, product, domain);
                IAcsClient acsClient = new DefaultAcsClient(profile);

                // 组装请求对象
                SendSmsRequest request = new SendSmsRequest();
                // 发送手机号
                request.setPhoneNumbers(phone);
                // 短信签名
                request.setSignName(signName);
                // 短信模板
                request.setTemplateCode(templateCode);
                //模板中的变量替换
                request.setTemplateParam("{\"code\":" + code + "}");
                logger.info("为用户发送一条通知短信:", phone);
                return acsClient.getAcsResponse(request);
            } else {
                logger.info("手机号码或验证码无效:phone:" + phone + ",code:" + code);
            }
        } catch (ClientException e) {
            e.printStackTrace();
            logger.info("发送短信出现异常", e);
        }
        return null;
    }
}
