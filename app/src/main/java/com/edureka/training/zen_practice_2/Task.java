package com.edureka.training.zen_practice_2;

import android.util.EventLogTags;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Task implements Serializable {
    private int id;
    private String name;
    private String description;
    private String scheduled;
    private ArrayList<Subtask> subtasks = new ArrayList<Subtask>();
    private int open = 0;
    private String path;
    public long last_clicked ;


    public Task(String title, String desc, String date, ArrayList<Subtask> subtasks){
        this.name = title;
        this.path = "Root/"+title;
        this.description = desc;
        this.scheduled = date;
        this.subtasks=subtasks;
    }

    public Task() {
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

    public ArrayList<Subtask> getSubtasks(){
        return subtasks;
    }

    public int getId(){return id;}

    public void setId(int id){
        this.id = id;
    }

    public void setOpen(int val){
        this.open = val;
    }

    public void setName(String name){
        this.name = name;this.path = "Root/"+name;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setScheduled(String date){
        this.scheduled = date;
    }

    public void setSubtasks(ArrayList<Subtask> subtaskArrayList){
        this.subtasks = subtaskArrayList;
    }

    public void setPath(String path){
        this.path=path;
    }

    public void switchOpen(){
        if(this.open==0){
            this.open=1;
        } else {this.open=0;}
    }

    public String getSubtasksString(){
        if(this.subtasks.size()==0){
            return "";
        }
        String s="+";
        Log.d("size",Integer.toString(this.subtasks.size()));
        for(int i=0;i<this.subtasks.size();i++){

            s+=subtasks.get(i).getTitle();
            if(i!=subtasks.size()-1){
                s+="\n+";
            } else {s+="\n";}
        }
        return s;
    }

    public int getOpen(){
        return this.open;
    }
}
