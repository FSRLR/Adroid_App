package com.niit.software1721.service;

import com.niit.software1721.entity.History;

import java.util.List;

public interface HistoryService {
    List<History> get(String username);
    History get(String username,String title);
    void save(History history);
    void modify(History history);
    void delete(History history);
}
