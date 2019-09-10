package com.niit.software1721;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.niit.software1721.utils.MD5Utils;

public class RegisterActivity extends AppCompatActivity {
    //1.获取界面上的控件
    //2.button的点击事件
    //3.处理点击事件
    //3.1获取控件的值
    //3.2检查数据的有效性
    //3.3将注册信息存储
    //3.4跳转到登录界面

    private EditText etUserName,etPassword,etPwdAgain;
    private Button btnRegister;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //1.获取界面上的控件
        initView();
        initToolbar();//初始化toolbar
        initData();//初始化传入的数据
        //2.
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3.
                String userName=etUserName.getText().toString();
                String password=etPassword.getText().toString();
                String pwdAgain=etPwdAgain.getText().toString();
                //3.2
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(RegisterActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }else if (!password.equals(pwdAgain)){
                    Toast.makeText(RegisterActivity.this,"两次密码必须一致",Toast.LENGTH_LONG).show();
                }else if (isExist(userName)){
                    Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_LONG).show();
                }else{
                    //注册成功之后
                    savePref(userName, MD5Utils.md5(password));
                    Intent intent=new Intent();
                    intent.putExtra("username",userName);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    private void initData() {
    }

    private void initToolbar() {
        Toolbar toolbar=findViewById(R.id.title_toolbar);
        toolbar.setTitle("注册");
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
    }

    private void savePref(String userName, String password) {
        SharedPreferences sp =getSharedPreferences("userInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
//        editor.putString("username",userName);
//        editor.putString("password",password);
        editor.putString(userName,password);
        editor.apply();
    }

    /**
     * 判断用户是否存在
     * @parm userName 用户名
     * @return true ：false
     */
    private boolean isExist(String userName){
        SharedPreferences sp=getSharedPreferences("userInfo",MODE_PRIVATE);
        String pwd=sp.getString(userName,"");
        return !TextUtils.isEmpty(pwd);
    }


    private void initView() {
        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_psw);
        etPwdAgain = findViewById(R.id.et_psw_again);
        btnRegister = findViewById(R.id.btn_register);
    }
}
