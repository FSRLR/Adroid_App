package com.niit.software1721.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.niit.software1721.fragment.BlankFragment;
import com.niit.software1721.fragment.CourseFragment;
import com.niit.software1721.fragment.MyInfoFragment;
import com.niit.software1721.R;
import com.niit.software1721.fragment.RecyclerViewFragment;
import com.niit.software1721.utils.StatusUtils;

public class MainActivity extends AppCompatActivity {
    private RadioGroup group;
    private Toolbar toolbar;

    private SparseArray<Fragment> fragments;
    private SparseArray<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusUtils.initToolbar(this, "我的", false, true);

        initView();
        initTitles();
        initFragment();
    }

    private void initFragment() {
        //创建fragment的列表
        fragments = new SparseArray<>();
        fragments.put(R.id.btn_me,MyInfoFragment.newInstance());
        fragments.put(R.id.btn_topic, RecyclerViewFragment.newInstance("Activity向Fragment传值"));
        fragments.put(R.id.btn_favor, CourseFragment.newInstance());
        //加载默认的Fragment
        replaceFragment(fragments.get(R.id.btn_me));
    }


    private void initTitles() {
        titles = new SparseArray<>();
        titles.put(R.id.btn_topic, "话题");
        titles.put(R.id.btn_favor, "喜欢");
        titles.put(R.id.btn_me, "我的");
    }

    private void setToolbar(int checkedId) {
        if (checkedId == R.id.btn_me) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            toolbar.setTitle(titles.get(checkedId));
        }
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_body,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void initView() {
        group = findViewById(R.id.btn_group);
        toolbar = findViewById(R.id.title_toolbar);
        setToolbar(group.getCheckedRadioButtonId());

        // RadioGroup的选项改变事件的监听
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(MainActivity.this, titles.
                        get(checkedId), Toast.LENGTH_SHORT).show();
                setToolbar(checkedId);
                replaceFragment(fragments.get(checkedId));
            }
        });
    }

}
