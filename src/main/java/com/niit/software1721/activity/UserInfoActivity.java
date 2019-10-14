package com.niit.software1721.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.niit.software1721.R;
import com.niit.software1721.entity.User;
import com.niit.software1721.service.UserInfoService;
import com.niit.software1721.service.UserInfoServiceImpl;
import com.niit.software1721.utils.SharedUtils;
import com.niit.software1721.utils.StatusUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MODIFY_NICKNAME = 1;
    private static final int MODIFY_SIGNATURE = 2;

    private static final String FILE_NAME="userInfo.txt";


    private TextView tvNickname,tvSignature,tvUserName,tvSex;
    private LinearLayout nicknameLayout,signatureLayout,sexLayout;

    private String spUsername;
    private UserInfoService service;
    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        StatusUtils.initToolbar(this,"个人信息",true,false);

        initData();
        initView();

    }

    private void initData() {
        spUsername= SharedUtils.readValue(this,"LoginUser");
        service=new UserInfoServiceImpl(this);
        userInfo= service.get(spUsername);
        userInfo=readFromInternal();
        userInfo=readPrivateExStorage();
        userInfo=readPublicExternalStorage();
        if (userInfo==null){
            userInfo=new User();
            userInfo.setUsername(spUsername);
            userInfo.setNickname("课程助手");
            userInfo.setSignature("课程助手");
            userInfo.setSex("男");

            service.save(userInfo);
            saveToInternal(userInfo);
            savePrivateExStorage(userInfo);
            savePublicExternalStorage(userInfo);
        }
    }

    private void initView() {
        tvUserName=findViewById(R.id.tv_user);
        tvNickname=findViewById(R.id.tv_nickname);
        tvSex=findViewById(R.id.tv_sex);
        tvSignature=findViewById(R.id.tv_signature);
        nicknameLayout=findViewById(R.id.nickname_layout);
        sexLayout=findViewById(R.id.sex_layout);
        signatureLayout=findViewById(R.id.signature_layout);

        tvUserName.setText(userInfo.getUsername());
        tvNickname.setText(userInfo.getNickname());
        tvSex.setText(userInfo.getSex());
        tvSignature.setText(userInfo.getSignature());

        nicknameLayout.setOnClickListener(this);
        sexLayout.setOnClickListener(this);
        signatureLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nickname_layout:
                modifyNickname();
                break;
            case R.id.sex_layout:
                modifySex();
                break;
            case R.id.signature_layout:
                modifySignature();
                break;
        }
    }

    private void modifySignature() {
        String signature = tvSignature.getText().toString();
        Intent intent = new Intent(UserInfoActivity.this, ChangeUserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "设置签名");
        bundle.putString("value", signature);
        bundle.putInt("flag", 2);
        intent.putExtras(bundle);
        startActivityForResult(intent, 2);
    }

    private void modifySex() {
        final String[] datas={"男","女"};
        String sex=tvSex.getText().toString();
        final List<String> sexs= Arrays.asList(datas);
        int selected=sexs.indexOf(sex);
        new AlertDialog.Builder(this)
                .setTitle("性别")
                .setSingleChoiceItems(datas, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sex=datas[i];
                        tvSex.setText(sex);
                        userInfo.setSex(sex);
                        service.modify(userInfo);
                        saveToInternal(userInfo);
                        savePrivateExStorage(userInfo);
                        dialogInterface.dismiss();
                    }
                }).show();

    }

    private void modifyNickname() {
        String nickname=tvNickname.getText().toString();
        Intent intent=new Intent(UserInfoActivity.this,ChangeUserInfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("title","设置昵称");
        bundle.putString("value",nickname);
        bundle.putInt("flag",1);
        intent.putExtras(bundle);

        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null || resultCode != RESULT_OK) {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (requestCode) {
            case MODIFY_NICKNAME:
                String newNick = data.getStringExtra("nickname");
                if(!TextUtils.isEmpty(newNick)) {
                    tvNickname.setText(newNick);
                    userInfo.setNickname(newNick);
                    service.modify(userInfo);
                    saveToInternal(userInfo);
                    savePrivateExStorage(userInfo);
                }
                break;
            case MODIFY_SIGNATURE:
                String newSignature = data.getStringExtra("signature");
                if(!TextUtils.isEmpty(newSignature)) {
                    tvSignature.setText(newSignature);
                    userInfo.setSignature(newSignature);
                    service.modify(userInfo);
                    saveToInternal(userInfo);
                    savePrivateExStorage(userInfo);
                }
                break;
        }
    }

    private User readFromInternal() {
        User user = null;
        try {
            FileInputStream in = this.openFileInput(FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data, User.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void saveToInternal(User user){
        try {
            FileOutputStream out=this.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(JSON.toJSONString(user));
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private User readPrivateExStorage() {
        User user = null;
        try {
            File file=new File(getExternalFilesDir(""),FILE_NAME);
            if (!file.exists()){
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data, User.class);
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void savePrivateExStorage(User user){
        try {
            File file=new File(getExternalFilesDir(""),FILE_NAME);
            FileOutputStream out=new FileOutputStream(file);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(JSON.toJSONString(user));
            writer.flush();
            writer.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static final int REQUEST_READ_USERINFO=101;
    private static final int REQUEST_WRITE_USERINFO=102;
    private void savePublicExternalStorage(User user){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_USERINFO);
                return;
            }
        }
        saveUserInfo(user);
    }

    private User readPublicExternalStorage(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_READ_USERINFO);
                return null;
            }
        }
        return readUserInfo();

    }

    private void saveUserInfo(User user){
        try {
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
            FileOutputStream out=new FileOutputStream(file);
            BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(JSON.toJSONString(user));
            writer.flush();
            writer.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private User readUserInfo(){
        User user=null;
        try {
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),FILE_NAME);
            if (!file.exists()){
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data = reader.readLine();
            user = JSON.parseObject(data, User.class);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length==0 || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"申请权限被拒绝，无法执行操作",Toast.LENGTH_SHORT).show();
            return;
        }
        if (requestCode==REQUEST_READ_USERINFO){
            userInfo=readUserInfo();
        }else if (requestCode==REQUEST_WRITE_USERINFO){
            saveUserInfo(userInfo);
        }
    }
}
