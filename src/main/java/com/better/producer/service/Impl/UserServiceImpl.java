package com.better.producer.service.Impl;

import com.better.producer.Redis.redisPoolUtil;
import com.better.producer.entity.Users;
import com.better.producer.service.UserService;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void add(Users users){
        Jedis jedis = redisPoolUtil.getJedis();
        jedis.hset("user",users.getId().toString(),users.toString());
        redisPoolUtil.close(jedis);
    }

    @Override
    public String selectById(Integer id){
        Jedis jedis = redisPoolUtil.getJedis();
        //先判断redis中是否存在，若存在则直接返回
        if(jedis.hexists("user",id.toString())){
            String result = jedis.hget("user",id.toString());
            redisPoolUtil.close(jedis);
            return result;
        }else{//若不存在则在数据库中查询并赋给redis
            Users users = new Users();
            users.setId(id);
            users.setAge(22);
            users.setName("张三");
            users.setUsername("admin");
            users.setPassword("123");
            jedis.hset("user",id.toString(),users.toString());
            redisPoolUtil.close(jedis);
            return "从Mysql数据库中查询数据:" +users.toString();

        }
    }

    @Override
    public void listAdd(){
        Jedis jedis = redisPoolUtil.getJedis();
        jedis.lpush("news:top10","news1","news2","news3","news4","news5");
        redisPoolUtil.close(jedis);
    }

    @Override
    public List<String> listRange(){
        Jedis jedis = redisPoolUtil.getJedis();
        List<String> lists = jedis.lrange("news:top10",0,-1);
        redisPoolUtil.close(jedis);
        return lists;
    }
}
