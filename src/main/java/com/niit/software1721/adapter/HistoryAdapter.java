package com.niit.software1721.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.niit.software1721.R;
import com.niit.software1721.entity.History;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> histories;

    //创建自定义viewHolder类
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSubTitle, tvTime;
        ImageView ivHead;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvSubTitle = itemView.findViewById(R.id.tv_sub_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            ivHead = itemView.findViewById(R.id.iv_head);
        }
    }

    public HistoryAdapter(List<History> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_history, viewGroup, false);
        HistoryAdapter.ViewHolder holder = new HistoryAdapter.ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        History history = histories.get(i);
        viewHolder.tvTitle.setText(history.getTitle());
        viewHolder.tvSubTitle.setText(history.getVideoTitle());
        viewHolder.tvTime.setText(history.getPlayTime());
//        viewHolder.ivHead.setBackgroundResource(R.drawable.avatar);

    }

    @Override
    public int getItemCount() {
        return histories.size();
    }
}