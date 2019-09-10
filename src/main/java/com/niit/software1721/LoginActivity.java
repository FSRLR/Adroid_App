package com.niit.software1721;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.niit.software1721.utils.MD5Utils;

public class LoginActivity extends AppCompatActivity {
    private EditText userName,password;
    private Button btnLogin;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initToolbar();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3.
                String username=userName.getText().toString();
                String pwd=password.getText().toString();
                //3.2 检查控件的有效性
/*                SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                String name = pref.getString("username", "");
                String psw = pref.getString("password", "");*/
                //3.2
                if (TextUtils.isEmpty(username)){
                    Toast.makeText(LoginActivity.this,"用户名不能为空",Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(pwd)){
                    Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_LONG).show();
                }/*else if (!MD5Utils.md5(pwd).equals(psw)){
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                }else if (!username.equals(name)){
                    Toast.makeText(LoginActivity.this,"用户名未注册",Toast.LENGTH_LONG).show();
                }*/else{
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_LONG).show();
                    saveLoginStatus(username,true);
                    Intent intent=new Intent();
                    intent.putExtra("isLogin",true);
                    //setResult(RESULT_OK,intent);
                    //finish();
                    setResult(RESULT_OK,intent);
                    LoginActivity.this.finish();
                }
            }
        });
    }

    private void saveLoginStatus(String username,boolean isLogin){
        getSharedPreferences("userInfo",MODE_PRIVATE)
                .edit()
                .putString("LoginUser",username)
                .putBoolean("isLogin",isLogin)
                .apply();
    }

    private void initView() {
        userName=findViewById(R.id.et_username);
        password=findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        TextView tvRegister=findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar=findViewById(R.id.title_toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1  &&  resultCode==RESULT_OK  &&  data!=null);
        String username=data.getStringExtra("username");
        userName.setText(username);
    }
}
