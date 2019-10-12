package com.niit.software1721.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.niit.software1721.R;
import com.niit.software1721.adapter.AdViewPagerAdapter;
import com.niit.software1721.adapter.CourseRecyclerAdapter;
import com.niit.software1721.entity.AdImage;
import com.niit.software1721.entity.Course;
import com.niit.software1721.utils.HttpsUtil;
import com.niit.software1721.utils.IOUtils;
import com.niit.software1721.utils.NetworkUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class CourseFragment extends Fragment implements ViewPager.OnPageChangeListener {
    public  static final int MSG_AD_ID=1;
    private ViewPager viewPager;
    private TextView tvDesc;
    private LinearLayout llPoint;

    private List<AdImage> adImages;
    private List<ImageView> imageViews;
    private int lastPos;
    private List<Course> courses;
    private RecyclerView rvCourse;

    public CourseFragment() {
        // Required empty public constructor
    }

    private static CourseFragment fragment;
    public static CourseFragment newInstance() {
        if (fragment==null){
            fragment=new CourseFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_course, container, false );
//        initToolBar(view);
        initAdData();
        initAdView(view);
        initIndicator(view);

        //设置ViewPager的初始状态
        lastPos = 0;
        llPoint.getChildAt(0).setEnabled(true);
        tvDesc.setText(adImages.get(0).getDesc());
        viewPager.setAdapter(new AdViewPagerAdapter(imageViews));
        adHandler = new AdHandler(viewPager);
//        adHandler.sendEmptyMessageDelayed(MSG_AD_ID, 5000);
        new AdSlideThread().start();

        rvCourse=view.findViewById(R.id.gv_courses);
//        initCourse();
//        loadCourseByNet();
        loadCourseByOKHttp();
        return view;
    }

    private void update(final List<Course> courses) {
        CourseRecyclerAdapter adapter=new CourseRecyclerAdapter(courses);
        rvCourse.setLayoutManager(new GridLayoutManager(getContext(),2));
        rvCourse.setAdapter(adapter);

        adapter.setOnItemClickListener(new CourseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Course course = courses.get(position);
                // 跳转到课程详情界面
                Toast.makeText(getContext(), "点击了：" + course.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initIndicator(View view) {
        llPoint = view.findViewById(R.id.ll_point);

        View pointView;
        for(int i = 0; i < adImages.size(); i ++) {
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.drawable.indicator_bg);
            pointView.setEnabled(false);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            if(i != 0) {
                params.leftMargin = 10;
            }
            llPoint.addView(pointView, params);
        }
    }


/*    private void initCourse(){
        courses=new ArrayList<>();
        try {
            InputStream input=getResources().getAssets().open("chapter_intro.json");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                String json = IOUtils.convert(input, StandardCharsets.UTF_8);
                courses= JSON.parseArray(json,Course.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void initAdView(View view) {
        tvDesc = view.findViewById(R.id.tv_desc);
        viewPager = view.findViewById(R.id.vp_banner);
        viewPager.addOnPageChangeListener(this);

        imageViews = new ArrayList<>();
        for(int i = 0; i < adImages.size(); i++) {
            AdImage adImage = adImages.get(i);
            ImageView iv = new ImageView(getContext());
            if("banner_1".equals(adImage.getImg())) {
                iv.setBackgroundResource(R.drawable.banner_1);
            } else if("banner_2".equals(adImage.getImg())) {
                iv.setBackgroundResource(R.drawable.banner_2);
            } else if("banner_3".equals(adImage.getImg())) {
                iv.setBackgroundResource(R.drawable.banner_3);
            }
            imageViews.add(iv);
        }
    }

    private void initAdData() {
        adImages = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            AdImage adImage = new AdImage();
            adImage.setId(i + 1);
            switch (i) {
                case 0:
                    adImage.setImg("banner_1");
                    adImage.setDesc("新一代 Apple Watch 发布");
                    break;
                case 1:
                    adImage.setImg("banner_2");
                    adImage.setDesc("寒武纪发布 AI 芯片");
                    break;
                case 2:
                    adImage.setImg("banner_3");
                    adImage.setDesc("Google 发布语音助手");
                    break;
                default:
                    break;
            }
            adImages.add(adImage);
        }
    }

 /*   public void initToolBar(View view) {
        Toolbar toolbar = view.findViewById(R.id.title_toolbar);
        toolbar.setTitle("课程");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }*/

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        int currentPos = i % adImages.size();
        tvDesc.setText(adImages.get(currentPos).getDesc());
        llPoint.getChildAt(lastPos).setEnabled(false);
        llPoint.getChildAt(currentPos).setEnabled(true);
        lastPos = currentPos;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    /**
     * 处理广告栏消息的Handler类
     */
    private AdHandler adHandler;
    private static class AdHandler extends Handler {
        private WeakReference<ViewPager> reference;

        public AdHandler(ViewPager viewPager) {
            reference = new WeakReference<>(viewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ViewPager viewPager = reference.get();
            if (viewPager == null) {
                return;
            }
            if (msg.what == MSG_AD_ID) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
/*                sendEmptyMessageDelayed(MSG_AD_ID, 5000);*/
            }
        }
    }

    /**
     * 使用多线程实现广告自动切换
     */
    private class AdSlideThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (adHandler != null) {
                    adHandler.sendEmptyMessage(MSG_AD_ID);
                }
            }
        }
    }

/*    private static class CourseHandler extends Handler{
        private WeakReference<CourseFragment> ref;

        public  CourseHandler(CourseFragment fragment){
            this.ref=new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            CourseFragment target=ref.get();

            if (msg.what==MSG_AD_ID){
                List<Course>courses = (List<Course>) msg.obj;
                if(courses!=null){

                    target.update(courses);
                }
            }
        }
    }
        private Handler courseHandler = new CourseHandler(this);

    private void loadCourseByNet(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String json= NetworkUtils.get("https://www.fastmock.site/mock/b46332ceba020b46458f016deac2c275/course/chapter");
                List<Course> courses= JSON.parseArray(json,Course.class);
                if (courses!=null){
                    Message msg=new Message();
                    msg.what=MSG_AD_ID;
                    msg.obj=courses;
                    courseHandler.sendMessage(msg);
                }
            }
        }).start();
    }*/

    private void loadCourseByOKHttp(){
        Request request=new Request.Builder()
                .url("https://www.fastmock.site/mock/b46332ceba020b46458f016deac2c275/course/chapter")
                .addHeader("Accept","application/json")
                .method("GET",null)
                .build();

        HttpsUtil.handleSSLHandshakeByOkHttp().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        String json=response.body().string();
                        final List<Course> courses=JSON.parseArray(json,Course.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                update(courses);
                            }
                        });
                    }
                }
            }
        });
    }


}
