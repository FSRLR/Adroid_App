package com.niit.software1721.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.niit.software1721.R;
import com.niit.software1721.adapter.TopicAdapter;
import com.niit.software1721.entity.Topic;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class BlankFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private ListView lvExcise;
    private String [] data={"aaa","bbb","ccc","ddd"};
    private List<Topic> topics;

    private String mParam1;

    private OnFragmentInteractionListener mListener;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_blank,container,false);

        //准备的列表
        initData();

        //获取列表控件
        lvExcise=view.findViewById(R.id.list_view);
/*        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                getActivity(),android.R.layout.simple_list_item_1,data);*/
        TopicAdapter adapter=new TopicAdapter(getActivity(),topics);
        lvExcise.setAdapter(adapter);
        lvExcise.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Topic topic = (Topic) parent.getItemAtPosition(position);
            }
        });
        return view;
    }

    private void initData(){
        topics=new ArrayList<>();

        try {
            //从assets目录获取资源的输入流
            InputStream input=getResources().getAssets().open("exercise_title.json");
            StringBuffer sb=new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = input.read(b)) != -1;) {
                sb.append(new String(b, 0, n));
            }
            String json=sb.toString();
            topics= JSON.parseArray(json,Topic.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
/*            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
