package com.niit.software1721.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.niit.software1721.R;
import com.niit.software1721.adapter.TopicDetailAdapter;
import com.niit.software1721.entity.TopicDetail;
import com.niit.software1721.utils.IOUtils;
import com.niit.software1721.utils.StatusUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TopicDetailActivity extends AppCompatActivity implements TopicDetailAdapter.OnSelectListener {
    //获取fragment传过来的数据
    private int id;
    private String title;

    //从xml文件中获得
    private List<TopicDetail> details;

    //控件及Adapter
    private RecyclerView lvDetails;
    private TopicDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_detail);

        initData();
        initView();

        StatusUtils.initToolbar(this,title,true,false);
    }

    private void initView() {
        lvDetails=findViewById(R.id.lv_details);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        lvDetails.setLayoutManager(manager);
        adapter=new TopicDetailAdapter(details,this);
        lvDetails.setAdapter(adapter);
    }

    private void initData() {
        id=getIntent().getIntExtra("id",0);
        title=getIntent().getStringExtra("title");

        details=new ArrayList<>();
        try {
            InputStream is = getResources().getAssets().open("chapter"+id+".xml");
            details= IOUtils.getXmlContents(is);

            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectA(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        TopicDetail topicDetail=details.get(position);
        if (topicDetail.getAnswer()!=1){
            topicDetail.setSelect(1);
        }else {
            topicDetail.setSelect(0);
        }
        switch (topicDetail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 2:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivA.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }

        TopicDetailAdapter.setABCDEnable(true,ivA,ivB,ivC,ivD);
    }

    @Override
    public void onSelectB(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        TopicDetail topicDetail=details.get(position);
        if (topicDetail.getAnswer()!=2){
            topicDetail.setSelect(2);
        }else {
            topicDetail.setSelect(0);
        }
        switch (topicDetail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_right);
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
            case 2:
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivB.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
        TopicDetailAdapter.setABCDEnable(true,ivA,ivB,ivC,ivD);
    }

    @Override
    public void onSelectC(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        TopicDetail topicDetail=details.get(position);
        if (topicDetail.getAnswer()!=3){
            topicDetail.setSelect(3);
        }else {
            topicDetail.setSelect(0);
        }
        switch (topicDetail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_right);
                ivC.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
            case 2:
                ivC.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivC.setImageResource(R.drawable.ic_exercise_answer_error);
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
        TopicDetailAdapter.setABCDEnable(true,ivA,ivB,ivC,ivD);
    }

    @Override
    public void onSelectD(int position, ImageView ivA, ImageView ivB, ImageView ivC, ImageView ivD) {
        TopicDetail topicDetail=details.get(position);
        if (topicDetail.getAnswer()!=4){
            topicDetail.setSelect(4);
        }else {
            topicDetail.setSelect(0);
        }
        switch (topicDetail.getAnswer()){
            case 1:
                ivA.setImageResource(R.drawable.ic_exercise_answer_right);
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                break;
            case 2:
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                ivB.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 3:
                ivD.setImageResource(R.drawable.ic_exercise_answer_error);
                ivC.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
            case 4:
                ivD.setImageResource(R.drawable.ic_exercise_answer_right);
                break;
        }
        TopicDetailAdapter.setABCDEnable(true,ivA,ivB,ivC,ivD);
    }
}
