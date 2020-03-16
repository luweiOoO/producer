package com.better.producer.service;

import com.better.producer.entity.Users;

import java.util.List;

public interface UserService {

    /**
     * Redis的hash操作
     * 根据用户主键插入用户数据
     * @param users
     */
    void add(Users users);
    /**
     * 根据用户主键查询用户数据
     */
    String selectById(Integer id);
    /**
     * List类型操作
     */
    void listAdd();

    List<String> listRange();

}
