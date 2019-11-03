package com.edureka.training.zen_practice_2;

import java.io.Serializable;
import java.util.ArrayList;

public class Subtask implements Serializable {
    private String name;
    private String description;
    private String scheduled;
    private int id;
    private int open =0 ;
    private String path;
    public long last_clicked ;

    public Subtask(String title, String desc, String date){
        this.name = title;
        this.description = desc;
        this.scheduled = date;

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

    public int getId(){return id;}

    public void switchOpen(){
        if(this.open==0){
            this.open=1;
        } else {this.open=0;}
    }
    public void setPath(String Path){
        this.path = Path;
    }

    public void setOpen(int val){
        this.open = val;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setScheduled(String date){
        this.scheduled = date;
    }

    public void setId(int id){this.id = id;}
    public int getOpen(){
        return this.open;
    }
}
