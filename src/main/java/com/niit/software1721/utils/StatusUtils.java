package com.niit.software1721.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.niit.software1721.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatusUtils {
    /**
     * 设置沉浸式状态栏
     * @param activity
     */
    public static void setImmersionMode(Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 初始化工具栏
     * @param activity
     * @param titleName
     * @param isHomeUp
     * @param isHome
     */
    public static void initToolbar(final AppCompatActivity activity, String titleName,
                                   boolean isHomeUp, boolean isHome) {
        Toolbar toolbar = activity.findViewById(R.id.title_toolbar);
        toolbar.setTitle(titleName);
        activity.setSupportActionBar(toolbar);

        // 设置回退按钮，及点击事件
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(isHomeUp); // 设置返回键
            actionBar.setHomeButtonEnabled(isHome);    // 设置是否是首页
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public static String getTime(){
        Date dateTime = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(dateTime);
    }
}
