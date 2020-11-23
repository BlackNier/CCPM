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

public class ProjectMembersAdapter extends ArrayAdapter<Members> {
    private int resourceId;

    public ProjectMembersAdapter(Context context,int textViewResourceId, List<Members> objects){
        super(context, textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Members member=getItem(position);

        View view;
        ViewHolder viewHolder;
        if (convertView==null){

            view=LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.avatar=view.findViewById(R.id.member_avatar);
            viewHolder.name=view.findViewById(R.id.member_name);
            viewHolder.email=view.findViewById(R.id.member_email);
            viewHolder.department=view.findViewById(R.id.member_department);

            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.name.setText(member.getName());
        viewHolder.email.setText(member.getEmail());
        viewHolder.department.setText(member.getDepartment());
        viewHolder.avatar.setImageResource(member.getImage());
        return view;
    }

    class ViewHolder{
        TextView name;
        TextView email;
        TextView department;
        ImageView avatar;
    }
}

