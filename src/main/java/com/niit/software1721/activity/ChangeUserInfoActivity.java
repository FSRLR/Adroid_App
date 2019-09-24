package com.niit.software1721.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.niit.software1721.R;
import com.niit.software1721.utils.StatusUtils;

public class ChangeUserInfoActivity extends AppCompatActivity {
    private String title,value;
    private int flag;

    private EditText etNickname;
    private ImageView imDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_info);

        StatusUtils.initToolbar(this,"设置昵称",true,false);

        initData();
        initView();
    }

    private void initView() {
        etNickname=findViewById(R.id.et_nickname);
        etNickname.setText(value);
        imDelete=findViewById(R.id.im_delete);
        imDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNickname.setText("");
            }
        });

    }

    private void initData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle!=null){
            title=bundle.getString("title");
            value=bundle.getString("value");
            flag=bundle.getInt("flag");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_save:
            save();
            break;
            case R.id.item_cancel:
                ChangeUserInfoActivity.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        Intent intent=new Intent();
        String value =etNickname.getText().toString();
        switch (flag){
            case 1:
                if (TextUtils.isEmpty(value)){
                    Toast.makeText(ChangeUserInfoActivity.this,"昵称不能为空",Toast.LENGTH_LONG).show();
                }else {
                    intent.putExtra("nickname",value);
                    setResult(RESULT_OK,intent);
                    Toast.makeText(ChangeUserInfoActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                    ChangeUserInfoActivity.this.finish();
                }
                break;
            case 2:
                intent.putExtra("signature",value);
                setResult(RESULT_OK,intent);
                Toast.makeText(ChangeUserInfoActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                ChangeUserInfoActivity.this.finish();
                break;
        }

    }
}
