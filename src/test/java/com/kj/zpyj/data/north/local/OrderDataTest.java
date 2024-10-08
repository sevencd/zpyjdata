package com.kj.zpyj.data.north.local;
/**
 * @author csz
 * @since 2024/2/20
 */

import com.kj.zpyj.data.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class OrderDataTest {

    @Resource
    private OrderDataService orderDataService; // 替换为你的实际服务类名


    @Before
    public void setUp() {

    }

    @Test
    public void testRecoveryOrderData() {
        orderDataService.recoveryOrderData();
    }

}
