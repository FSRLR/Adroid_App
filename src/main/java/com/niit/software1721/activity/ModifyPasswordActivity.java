package com.niit.software1721.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.niit.software1721.R;
import com.niit.software1721.utils.MD5Utils;
import com.niit.software1721.utils.SharedUtils;
import com.niit.software1721.utils.StatusUtils;

public class ModifyPasswordActivity extends AppCompatActivity {
    private EditText oldPassword, newPassword, newPwdAgn;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);

        StatusUtils.initToolbar(ModifyPasswordActivity.this, "修改密码", true, false);
        initView();
    }


    private void initView() {
        oldPassword=findViewById(R.id.old_pwd);
        newPassword=findViewById(R.id.new_pwd);
        newPwdAgn=findViewById(R.id.new_pwd_again);
        btnSave=findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }


    private void save() {
        String p1 = oldPassword.getText().toString();
        String p2 = newPassword.getText().toString();
        String p3 = newPwdAgn.getText().toString();
        SharedPreferences sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        String username = sp.getString("LoginUser", "");
        String pwd = sp.getString(username, "");
        if (!MD5Utils.md5(p1).equals(pwd)) {
            Toast.makeText(ModifyPasswordActivity.this, "原密码错误", Toast.LENGTH_SHORT).show();
        } else {
            if (p2.equals("")) {
                Toast.makeText(ModifyPasswordActivity.this, "新密码不可为空", Toast.LENGTH_SHORT).show();
            } else if (!p2.equals(p3)) {
                Toast.makeText(ModifyPasswordActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            } else if (MD5Utils.md5(p2).equals(pwd)) {
                Toast.makeText(ModifyPasswordActivity.this, "新密码与旧密码相同", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ModifyPasswordActivity.this, "新密码设置成功", Toast.LENGTH_SHORT).show();
                SharedUtils.saveStrValue(this, username, MD5Utils.md5(p1));
                SharedUtils.clearLoginInfo(this);
                Intent intent = new Intent(ModifyPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                ModifyPasswordActivity.this.finish();
            }
        }
    }
}
