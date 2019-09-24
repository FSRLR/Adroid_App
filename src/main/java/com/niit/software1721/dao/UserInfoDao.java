package com.niit.software1721.dao;

import com.niit.software1721.entity.User;

import java.util.List;

public interface UserInfoDao {
    List<User> select();
    User select(String username);
    void insert(User user);
    void update(User user);
    void delete(User user);
}
