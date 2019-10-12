package com.niit.software1721.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class AdViewPagerAdapter extends PagerAdapter {
    private List<ImageView> imageViews;


    public AdViewPagerAdapter() {
        this(null);
        imageViews=new ArrayList<>();
    }

    public AdViewPagerAdapter(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView=imageViews.get(position % imageViews.size());

        ViewParent parent=imageView.getParent();
        if (parent!=null){
            ((ViewGroup) parent).removeView(imageView);
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public int getSize(){
        return imageViews.size();
    }
}
