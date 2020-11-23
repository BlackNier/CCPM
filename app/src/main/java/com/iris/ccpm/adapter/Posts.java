package com.iris.ccpm.adapter;

public class Posts {
    private String name;
    private int avatar;
    private String time;

    public Posts(String name,String time,int avatar){
        this.name=name;
        this.time=time;
        this.avatar=avatar;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public int getImage(){return avatar;}
}
