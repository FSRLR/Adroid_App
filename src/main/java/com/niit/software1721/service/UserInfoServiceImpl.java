package com.niit.software1721.service;

import android.content.Context;

import com.niit.software1721.dao.UserInfoDao;
import com.niit.software1721.dao.UserInfoDaoImpl;
import com.niit.software1721.entity.User;

public class UserInfoServiceImpl implements UserInfoService{
    private UserInfoDao dao;
    public UserInfoServiceImpl(Context context){
        dao=new UserInfoDaoImpl(context);
    }
    @Override
    public User get(String username) {
        return dao.select(username);
    }

    @Override
    public void save(User user) {
        dao.insert(user);
    }

    @Override
    public void modify(User user) {
        dao.update(user);
    }

    @Override
    public void delete(User user) {
        dao.delete(user);
    }
}
