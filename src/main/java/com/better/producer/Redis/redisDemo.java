package com.better.producer.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisDemo {
    public static void main(String args[]){

        //连接池 Redis POOL 基本配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(5);//最大连接数
        poolConfig.setMaxIdle(1);//最大空闲数

        //连接池
        JedisPool pool = new JedisPool(poolConfig,"127.0.0.1",6379);

        //连接Redis服务器
        Jedis jedis = pool.getResource();



        System.out.println(jedis.ping());
    }
}
