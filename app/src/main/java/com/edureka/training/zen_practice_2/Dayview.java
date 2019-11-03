package com.edureka.training.zen_practice_2;

import java.util.ArrayList;

public class Dayview {
    private int id;
    private String name;
    private String description;
    private String scheduled;
    private int open = 0;
    private String path;
    private String what;

    public Dayview(int id,String title, String desc, String date, String what,String path){
        this.name = title;
        this.path = path;
        this.description = desc;
        this.scheduled = date;
        this.what = what;
        this.id=id;
    }

    public Dayview() {
    }

    public String getTitle(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return scheduled;
    }

    public String getPath(){ return path;}

    public String getWhat(){return what;}

    public void switchOpen(){
        if(this.open==0){
            this.open=1;
        } else {this.open=0;}
    }

    public int getOpen(){
        return this.open;
    }
    public int getId(){
        return this.id;
    }
    public void setOpen(int val){
        this.open = val;
    }
}
