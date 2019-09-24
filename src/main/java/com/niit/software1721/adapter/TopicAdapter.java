package com.niit.software1721.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.niit.software1721.R;
import com.niit.software1721.entity.Topic;

import java.util.List;

public class TopicAdapter extends BaseAdapter {
    private final Context context;
    private List<Topic> topics;

    public TopicAdapter(Context context,List<Topic> topics){
        this.context=context;
        this.topics=topics;

    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Topic getItem(int i) {
        return topics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //根据数据的记录数，加载每一个view
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        //加载xml布局
        if ((view==null)){
            holder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.item_topic,null);
            //获取控件对象
            holder.tvOrder=view.findViewById(R.id.tv_order);
            holder.tvTitle=view.findViewById(R.id.tv_title);
            holder.tvSubTitle=view.findViewById(R.id.tv_sub_title);

            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        //加载数据
        final Topic topic=getItem(i);
        if (topic!=null){
            holder.tvOrder.setText(String.valueOf(i+1));
/*            holder.tvOrder.setBackgroundResource(topic.getBackground());*/
            holder.tvTitle.setText(topic.getTitle());
            holder.tvSubTitle.setText(topic.getSubTitle());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        return view;
    }

    //存放item_topic.xml布局的所有控件
    static class ViewHolder{
        TextView tvOrder,tvTitle,tvSubTitle;
    }
}
