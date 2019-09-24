package com.niit.software1721.service;

import com.niit.software1721.entity.User;

public interface UserInfoService {
    User get(String username);
    void save(User user);
    void modify(User user);
    void delete(User user);
}
