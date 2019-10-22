package com.niit.software1721.service;

import android.content.Context;

import com.niit.software1721.dao.HistoryDao;
import com.niit.software1721.dao.HistoryDaoImpl;
import com.niit.software1721.entity.History;

import java.util.List;

public class HistoryServiceImpl implements HistoryService{
    private HistoryDao dao;
    public HistoryServiceImpl(Context context){
        dao=new HistoryDaoImpl(context);
    }
    @Override
    public List<History> get(String username) {
        return dao.select(username);
    }

    @Override
    public History get(String username, String title) {
        return dao.select(username,title);
    }

    @Override
    public void save(History history) {
        dao.insert(history);
    }

    @Override
    public void modify(History history) {
        dao.update(history);
    }

    @Override
    public void delete(History history) {
        dao.delete(history);
    }
}
