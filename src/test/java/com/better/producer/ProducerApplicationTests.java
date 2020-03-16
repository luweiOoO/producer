package com.better.producer;

import com.better.producer.dao.OrderDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class ProducerApplicationTests {

    @Autowired
    private OrderDao orderDao;

    @Test
    void contextLoads() {
    }


}
