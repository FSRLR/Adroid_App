package com.niit.software1721;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;

public class MainActivity extends AppCompatActivity {
    private SparseArray<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initTitles();
        initFragment();
    }

    private void initTitles() {
        //创建fragment的列表
        fragments = new SparseArray<>();
        fragments.put(R.id.btn_me,MyInfoFragment.newInstance());

        //加载默认的Fragment
        replaceFragment(fragments.get(R.id.btn_me));
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_body,fragment);
        ft.commit();
    }

    private void initView() {
    }

    private void initFragment() {
    }

}
