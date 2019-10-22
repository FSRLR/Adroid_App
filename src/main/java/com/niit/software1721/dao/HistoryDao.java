package com.niit.software1721.dao;

import com.niit.software1721.entity.History;

import java.util.List;

public interface HistoryDao {
    List<History> select(String username);
    History select(String username,String title);
    void insert(History history);
    void update(History history);
    void delete(History history);
}
