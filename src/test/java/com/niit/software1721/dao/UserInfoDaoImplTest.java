package com.niit.software1721.dao;

import com.niit.software1721.entity.User;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserInfoDaoImplTest {
    private UserInfoDao userInfoDao;

    @Test
    public void select() {
        User user=userInfoDao.select("AAA");
        System.out.println(user);
    }
}