package com.example.fivefivemm.utility;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilityTest {

    @Test
    public void imgTest() {
        String res[] = Utility.getImageAddress("<p><img src=\"http://127.0.0.1:8888/action/b30a4e63-6a83-43d3-b4b9-94894a26599fwallhaven-228915.jpg\"></p><p><br></p><p><img sc=\"http://127.0.0.1:8888/action/f70446fd-e2da-494c-9b59-542653ca8007wallhaven-171358.jpg\"></p><p><br></p><p><img sc=\"http://127.0.0.1:8888/action/91874684-720e-4a63-b04f-fc6c8f6a965dwallhaven-142400.jpg\"></p>");

        if (res.length == 0) {
            System.out.println("为空");
        }
        for (String s : res) {
            System.out.println(s);
        }
    }

    @Test
    public void VerificationCodeTest(){
        System.out.println(Utility.VerificationCode());
    }
}
