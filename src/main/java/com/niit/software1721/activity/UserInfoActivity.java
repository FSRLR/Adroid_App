package com.niit.software1721.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niit.software1721.R;
import com.niit.software1721.entity.User;
import com.niit.software1721.service.UserInfoService;
import com.niit.software1721.service.UserInfoServiceImpl;
import com.niit.software1721.utils.SharedUtils;
import com.niit.software1721.utils.StatusUtils;

import java.util.Arrays;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MODIFY_NICKNAME = 1;
    private static final int MODIFY_SIGNATURE = 2;


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
        if (userInfo==null){
            userInfo=new User();
            userInfo.setUsername(spUsername);
            userInfo.setNickname("课程助手");
            userInfo.setSignature("课程助手");
            userInfo.setSex("男");

            service.save(userInfo);
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
                }
                break;
            case MODIFY_SIGNATURE:
                String newSignature = data.getStringExtra("signature");
                if(!TextUtils.isEmpty(newSignature)) {
                    tvSignature.setText(newSignature);
                    userInfo.setSignature(newSignature);
                    service.modify(userInfo);
                }
                break;
        }
    }
}
