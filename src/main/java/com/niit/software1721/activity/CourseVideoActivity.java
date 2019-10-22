package com.niit.software1721.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.niit.software1721.R;
import com.niit.software1721.adapter.VideoAdapter;
import com.niit.software1721.entity.Course;
import com.niit.software1721.entity.History;
import com.niit.software1721.entity.Video;
import com.niit.software1721.service.HistoryService;
import com.niit.software1721.service.HistoryServiceImpl;
import com.niit.software1721.utils.IOUtils;
import com.niit.software1721.utils.SharedUtils;
import com.niit.software1721.utils.StatusUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CourseVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private ImageView ivVideo;
    private TextView tvIntro;
    private RecyclerView rvVideo;

    private Course course;
    private List<Video> videos;
    private VideoAdapter adapter;

    private MediaController controller;  //多媒体播放进度条控制

    private HistoryService historyService = new HistoryServiceImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.setImmersionMode(this);
        setContentView(R.layout.activity_course_video);

        StatusUtils.initToolbar(this, "视频资源", true, false);

        initData();
        initView();
        loadFirstFrame();
    }

    // 加载视频的首帧图像
    private void  loadFirstFrame() {
        Bitmap bitmap = null;
        Uri uri=Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video101);
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(this, uri);
        bitmap = retriever.getFrameAtTime();
        ivVideo.setImageBitmap(bitmap);
    }

    private void initView() {
        videoView = findViewById(R.id.video_view);
        videoView.setMediaController(new MediaController(this));
        controller = new MediaController(this);
        videoView.setMediaController(controller);

        ivVideo = findViewById(R.id.iv_video);
        tvIntro = findViewById(R.id.tv_intro);
        rvVideo = findViewById(R.id.rv_video);

        tvIntro.setText(course.getIntro());

        adapter = new VideoAdapter(videos);
        rvVideo.setLayoutManager(new LinearLayoutManager(this));
        rvVideo.setAdapter(adapter);

        adapter.setOnItemClickListener(new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 设置选中项，并通过notifyDataSetChanged()更新UI
                adapter.setSelected(position);
                adapter.notifyDataSetChanged();

                Video video = videos.get(position);
                if(videoView.isPlaying()) {
                    videoView.stopPlayback();
                }

                if(TextUtils.isEmpty(video.getVideoPath())) {
                    Toast.makeText(CourseVideoActivity.this, "本地没有此视频，暂时无法播放", Toast.LENGTH_SHORT).show();
                    return;
                }
                videoView.setVisibility(View.VISIBLE);
                ivVideo.setVisibility(View.GONE);
                String uri="android.resource://" + getPackageName() + "/" + R.raw.video101;
                videoView.setVideoPath(uri);
                videoView.start();
/*                play();*/
                if (checkLoginStatus()){
                    String username = SharedUtils.readValue(CourseVideoActivity.this, "LoginUser");
                    String playTime=StatusUtils.getTime();
                    History history=new History(username,playTime,video.getTitle(),video.getVideoTitle());
                    if (historyService.get(username,video.getVideoTitle())==null){
                        historyService.save(history);
                    }else {
                        historyService.modify(history);
                    }
                }
            }
        });
    }

    //获取登录状态
    private boolean checkLoginStatus() {
        SharedPreferences sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin", false);
    }

    // 播放视频
    private void play() {
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.video101;
        videoView.setVideoPath(uri);
        videoView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView != null) {
            videoView.stopPlayback();
            videoView = null;
        }
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            course = (Course) bundle.getSerializable("course");
        }

        videos = new ArrayList<>();
        try {
            InputStream is = getResources().getAssets().open("course.json");
            String json = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                json = IOUtils.convert(is, StandardCharsets.UTF_8);
            }
            videos = IOUtils.convert(json, Video.class);

            Iterator<Video> it = videos.iterator();
            while(it.hasNext()) {
                Video video = it.next();
                if(video.getChapterId() != course.getId()) {
                    it.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
