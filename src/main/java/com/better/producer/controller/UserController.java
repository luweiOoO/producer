package com.better.producer.controller;

import com.better.producer.Redis.redisPoolUtil;
import com.better.producer.entity.Users;
import com.better.producer.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;


@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping(value = "/login")
    @ResponseBody
    public HttpResponse logindd(@RequestBody Users users){
        Jedis jedis = redisPoolUtil.getJedis();
        /**
         * 判断用户名密码
         */
        String key = "user:login:count:" + users.getUsername();
        String lockKey = "login:lock";
        if(!jedis.exists(lockKey)||(jedis.exists(key) && !jedis.get(key).equals("5"))) {
            jedis.set(lockKey,"0");
        }
        if(users.getUsername().equals("luwei") && users.getPassword().equals("123")){
            jedis.del(key);
            redisPoolUtil.close(jedis);
            return HttpResponse.success("登录成功");
        }else{

            if(!jedis.exists(key)){
                jedis.set(key,"1");
                jedis.expire(key,300);
                redisPoolUtil.close(jedis);
                return HttpResponse.error("521","用户名或密码错误，请重新登录");
            }else if(Integer.parseInt(jedis.get(key)) < 4){
                jedis.incr(key);
                jedis.expire(key,300);
                redisPoolUtil.close(jedis);
                return HttpResponse.error("521","用户名或密码错误，请重新登录");
            }else{
                if(jedis.get(lockKey).equals("0")){
                    jedis.expire(key,3600);
                    jedis.incr(lockKey);
                }
                Long seconds = jedis.ttl(key);
                redisPoolUtil.close(jedis);
                return HttpResponse.error("522","连续五次输入错误，请" + seconds / 60 + "分" + seconds % 60 + "秒后重试");
            }
        }
    }
}
