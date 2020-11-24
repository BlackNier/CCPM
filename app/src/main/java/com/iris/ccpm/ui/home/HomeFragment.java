package com.iris.ccpm.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSONObject;
import com.iris.ccpm.MainActivity;
import com.iris.ccpm.R;

import com.loopj.android.http.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static String[] title = {"1", "2", "3", "4", "5", "6"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity activity = (MainActivity) getActivity();
        final ListView lvNews = root.findViewById(R.id.lv_news);
        lvNews.setAdapter(new Myadapter());
        Android_Async_Http_Get();

        return root;
    }

    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public Object getItem(int position) {
            return title[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.news_item_layout, parent, false);
            TextView titleText = (TextView) view.findViewById(R.id.title);
            titleText.setText(title[position]);
            return view;
        }
    }

    static class Music {
        String createdAt;
        int __v;
        String _id;
        String title;
        String type;
        List<String> scoreImg;
        String updatedAt;

        public Music() {

        }

        public Music(String createdAt, int __v, String _id, String title, String type, List<String> scoreImg, String updatedAt) {
            this.createdAt = createdAt;
            this.__v = __v;
            this._id = _id;
            this.title = title;
            this.type = type;
            this.scoreImg = scoreImg;
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<String> getScoreImg() {
            return scoreImg;
        }

        public void setScoreImg(List<String> scoreImg) {
            this.scoreImg = scoreImg;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }

    //Get请求
    private void Android_Async_Http_Get() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://ts.tcualhp.cn/api/ukulele/music?type=sing";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                JSONObject jsonObject = JSONObject.parseObject(response);
                String result = JSON.toJSONString(jsonObject.get("result"));
                List<Music> list = JSONArray.parseArray(result, Music.class);
                System.out.println("object: " + jsonObject);
                System.out.println("string: " + result);

                Iterator<Music> iterator = list.iterator();
                while (iterator.hasNext()){
                    Music next = iterator.next();
                    System.out.println(next.getTitle()+"---"+next.getType());

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "Get请求失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}