package com.iris.ccpm.ui.home;

import android.os.Bundle;
import android.util.Log;
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

import com.iris.ccpm.MainActivity;
import com.iris.ccpm.R;

import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private static String[] title= {"1", "2", "3", "4", "5", "6"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        MainActivity activity = (MainActivity)getActivity();
        final ListView lvNews = root.findViewById(R.id.lv_news);
        lvNews .setAdapter(new Myadapter());
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
            TextView titleText = (TextView)view.findViewById(R.id.title);
            titleText.setText(title[position]);
            return view;
        }
    }

    //Get请求
    private void Android_Async_Http_Get(){
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://ts.tcualhp.cn/api/ukulele/music?type=sing";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = new String(responseBody);
                Log.d("content", content);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "Get请求失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}