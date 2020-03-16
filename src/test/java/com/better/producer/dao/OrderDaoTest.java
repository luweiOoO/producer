package com.better.producer.dao;

import com.better.producer.Redis.redisPoolUtil;
import com.better.producer.entity.Order;
//import org.bouncycastle.math.ec.custom.sec.SecT113Field;
import com.better.producer.entity.Users;
import com.better.producer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDaoTest {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserService userService;

    @Test
    public void testInsert(){
        for(int i = 2; i < 20; i++){
            orderDao.insertOrder(new BigDecimal(i),1L,"SUCCESS");
        }

    }

    @Test
    public void testSearch(){
        List<Long> ids = new ArrayList<>();
        ids.add(443781966945320960L);
        ids.add(443781966991458304L);
        ids.add(443781966018379777L);
        List<Map> map = orderDao.selectOrderByIds(ids);
        System.out.println(map);
    }

//    @Test
//    public void testMySelect(){
//        Long orderId = 443781966945320960L;
//        Order order = orderDao.selectByIDD(orderId);
//        System.out.println(order.toString());
//    }

    @Test
    public void test111(){
        List<Long> l = new LinkedList<>();
        l.add(1L);
        l.add(2L);
        l.add(1L);
        l.add(3L);
        System.out.println(l + "ssssssssssssss");
        for(Long i : l){
            System.out.print(i + " ");
        }
        Set<Long> s = new HashSet<>();
        s.addAll(l);
        for(Long i : s){
            System.out.print(i + " ");
        }
        l = new ArrayList<>(s);
        System.out.println(l + "aaaaaaaaaaaaaaaaa");
    }

    /**
     * 测试字符串类型
     * Redis中有哪些命令，Jedis中就有哪些方法
     */
    @Test
    public void testString(){
        //获取redis连接
        Jedis jedis = new Jedis("127.0.0.1",6379);

        jedis.set("strName","字符串的名称");
        System.out.println(jedis.get("strName"));
        jedis.close();

    }

    /**
     * Redis作用：为了减轻数据库(MySQL)的访问压力
     * 需求：判断某key是否存在，如果存在，就从Redis中查询
     * 若不存在，就查询数据库并将查询出来的数据存入Redis
     */
    @Test
    public void t2(){
        Jedis jedis = new Jedis("127.0.0.1",6379);

        String key = "applicationName";

        if(jedis.exists(key)){
            String result =  jedis.get(key);
            System.out.println("Redis数据库中查询得到: " + result);
        }else{
            //在数据库中查询
            String result = "微信开发会议达人";
            jedis.set(key,result);
            System.out.println("MySQL数据库中查询得到：" +result);
        }

        jedis.close();
    }

    /**
     * Jedis完成对Hash类型操作
     * 需求：hash存储一个对象
     * 判断Redis是否存在该Key，若存在，直接返回对应值
     * 如果不存在，查询数据库，存入Redis
     */
    @Test
    public void t3(){
        Jedis jedis = redisPoolUtil.getJedis();

        String key = "users";

        if(jedis.exists(key)){
            Map<String,String> map = jedis.hgetAll(key);
            System.out.println("从Redis中查询的结果是：" + map.get("id") + "\t" + map.get("name") + "\t" + map.get("age") + "\t" + map.get("remark"));
        }else{
            //赋值数据库并返回结果
            jedis.hset(key,"id","1");
            jedis.hset(key,"name","鲁威");
            jedis.hset(key,"age","22");
            jedis.hset(key,"remark","这是以恶搞");
            System.out.println("数据库中查询的结果是：");
        }

        redisPoolUtil.close(jedis);
    }

    @Test
    public void t4(){
        userService.listAdd();
        List<String> result = userService.listRange();
        System.out.println("结果是："+result);
        for(String a : result){
            System.out.println(a);
        }

    }



}