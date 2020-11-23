package com.iris.ccpm.adapter;

public class Members {
    private String name;
    private int avatar;
    private String email;
    private String department;

    public Members(String name,String email,String department,int avatar){
        this.name=name;
        this.email=email;
        this.department=department;
        this.avatar=avatar;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment(){return department;}

    public int getImage(){return avatar;}
}
