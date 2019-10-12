package com.niit.software1721.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.niit.software1721.R;
import com.niit.software1721.entity.Topic;

import java.util.List;

public class TopicAdapter2 extends RecyclerView.Adapter<TopicAdapter2.ViewHolder> {
    private  Context context;
    private  List<Topic> topics;
    private OnItemClickListener itemClickListener;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvOrder,tvTitle,tvSubTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvOrder=itemView.findViewById(R.id.tv_order);
            tvTitle=itemView.findViewById(R.id.tv_title);
            tvSubTitle=itemView.findViewById(R.id.tv_sub_title);
        }
    }

    public TopicAdapter2(Context context,List<Topic> topics){
        this.context=context;
        this.topics=topics;

    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_topic,viewGroup,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Topic topic=topics.get(i);
        viewHolder.tvOrder.setText(String.valueOf(i+1));
        viewHolder.tvTitle.setText(topic.getTitle());
        viewHolder.tvSubTitle.setText(topic.getSubTitle());
        //设置圆角背景的颜色
        GradientDrawable drawable= (GradientDrawable) viewHolder.tvOrder.getBackground();
        drawable.setColor(Color.parseColor(topic.getBgColor()));
        if ((itemClickListener!=null)){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(viewHolder.itemView,i);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    itemClickListener.onItemLongClick(viewHolder.itemView,i);
                    return true;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

}
