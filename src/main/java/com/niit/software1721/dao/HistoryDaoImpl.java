package com.niit.software1721.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.niit.software1721.entity.History;
import com.niit.software1721.utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryDaoImpl implements HistoryDao {
    private DBHelper helper;  //用于创建数据库
    private SQLiteDatabase db;  //用于执行SQL语句

    public HistoryDaoImpl(Context context){
        helper=DBHelper.getInstance(context);
    }

    @Override
    public List<History> select(String username) {
        List<History> histories = new ArrayList<>();
//        String sql = "select * from " + DBHelper.TBL_NAME_HISTORY + " where user_name = ? ORDER BY play_time DESC";
        db =  helper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(sql, new String[]{username});
        Cursor cursor = db.query(DBHelper.TBL_NAME_HISTORY,null,"user_name=?",new String[]{username},null,null,"play_time desc");
        if(cursor != null && cursor.moveToFirst()) {
            do {
                History history = new History();
                history.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
                history.setPlayTime(cursor.getString(cursor.getColumnIndex("play_time")));
                history.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                history.setVideoTitle(cursor.getString(cursor.getColumnIndex("video_title")));
                histories.add(history);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return histories;
    }

    @Override
    public History select(String username, String title) {
        History history=null;
        db=helper.getReadableDatabase();
        Cursor cursor=db.query(DBHelper.TBL_NAME_HISTORY,null,"user_name=? AND video_title=?",new String[]{username,title},null,null,null);
        if (cursor!=null && cursor.moveToFirst()){
            history=new History();
            history.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            history.setPlayTime(cursor.getString(cursor.getColumnIndex("play_time")));
            history.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            history.setVideoTitle(cursor.getString(cursor.getColumnIndex("video_title")));
            cursor.close();
        }
        db.close();
        return history;
    }

    @Override
    public void insert(History history) {
        ContentValues values = new ContentValues();
        values.put("user_name", history.getUsername());
        values.put("play_time", history.getPlayTime());
        values.put("title", history.getTitle());
        values.put("video_title", history.getVideoTitle());
        db = helper.getWritableDatabase();
        db.insert(DBHelper.TBL_NAME_HISTORY, null, values);
        db.close();
    }

    @Override
    public void update(History history) {
        ContentValues values = new ContentValues();
        values.put("user_name", history.getUsername());
        values.put("play_time", history.getPlayTime());
        values.put("title", history.getTitle());
        values.put("video_title", history.getVideoTitle());
        //根据用户修改播放时间
        db = helper.getWritableDatabase();
        db.update(DBHelper.TBL_NAME_HISTORY, values, "user_name =? AND title=? ", new String[]{history.getUsername(),history.getTitle()});
        db.close();
    }

    @Override
    public void delete(History history) {
        db=helper.getWritableDatabase();

        db.delete(DBHelper.TBL_NAME_HISTORY,"user_name=? AND title=?",new String[]{history.getUsername(),history.getTitle()});
        db.close();
    }
}
