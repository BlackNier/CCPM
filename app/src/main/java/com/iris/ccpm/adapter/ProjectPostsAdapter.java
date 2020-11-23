package com.iris.ccpm.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iris.ccpm.R;

import java.util.List;

public class ProjectPostsAdapter extends ArrayAdapter<Posts> {
    private int resourceId;

    public ProjectPostsAdapter(Context context,int textViewResourceId, List<Posts> objects){
        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Posts post=getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.avatar=view.findViewById(R.id.member_avatar);
            viewHolder.name=view.findViewById(R.id.member_name);
            viewHolder.time=view.findViewById(R.id.member_email);

            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.name.setText(post.getName());
        viewHolder.time.setText(post.getTime());
        viewHolder.avatar.setImageResource(post.getImage());
        return view;
    }

    class ViewHolder{
        TextView name;
        TextView time;
        ImageView avatar;
    }
}

