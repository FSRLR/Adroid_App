package com.niit.software1721.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.niit.software1721.entity.User;
import com.niit.software1721.utils.DBHelper;

import java.util.List;

public class UserInfoDaoImpl implements UserInfoDao {
    private DBHelper helper;  //用于创建数据库
    private SQLiteDatabase db;  //用于执行SQL语句

    public UserInfoDaoImpl(Context context){
        helper=DBHelper.getInstance(context);
    }
    @Override
    public List<User> select() {
        return null;
    }

    @Override
    public User select(String username) {
        User user=null;
        db=helper.getReadableDatabase();
        Cursor cursor=db.query(DBHelper.TBL_NAME_USER,null,"user_name=?",new String[]{username},null,null,null);
        if (cursor!=null && cursor.moveToFirst()){
            user=new User();
            user.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            user.setNickname(cursor.getString(cursor.getColumnIndex("nick_name")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            user.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
            cursor.close();
        }
        db.close();
        return user;
    }

    @Override
    public void insert(User user) {
        db=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("user_name",user.getUsername());
        values.put("nick_name",user.getNickname());
        values.put("sex",user.getSex());
        values.put("signature",user.getSignature());
        db.insert(DBHelper.TBL_NAME_USER,null,values);

        db.close();
    }

    @Override
    public void update(User user) {
        db=helper.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("user_name",user.getUsername());
        values.put("nick_name",user.getNickname());
        values.put("sex",user.getSex());
        values.put("signature",user.getSignature());

        db.update(DBHelper.TBL_NAME_USER,values,"user_name=?",new String[]{user.getUsername()});
        db.close();
    }

    @Override
    public void delete(User user) {
        db=helper.getWritableDatabase();

        db.delete(DBHelper.TBL_NAME_USER,"user_name=?",new String[]{user.getUsername()});
        db.close();
    }
}
