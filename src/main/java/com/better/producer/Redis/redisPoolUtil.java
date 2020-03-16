package com.better.producer.Redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class redisPoolUtil {
    private static JedisPool jedisPool;
    static {
        //连接池Redis POOL 基本配置信息
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(1);//最大空闲数
        poolConfig.setMaxTotal(5);//最大连接数

        jedisPool = new JedisPool(poolConfig,"127.0.0.1",6379);
    }

    //获取连接
    public static Jedis getJedis(){
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    //关闭连接
    public static void close(Jedis jedis){
        jedis.close();
    }
}
