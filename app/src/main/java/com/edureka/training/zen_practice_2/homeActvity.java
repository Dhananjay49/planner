package com.edureka.training.zen_practice_2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class homeActvity extends AppCompatActivity  {
    String newNodeTitle,newNodeDes,newNodeDate;
    private static final String TAG = "homeActivity";
    List<Task> taskList;
    private dbHandler dbhelper;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_actvity);
        Toast.makeText(this,"In Home",Toast.LENGTH_SHORT).show();
        newNodeTitle = getIntent().getStringExtra("NTITLE");
        newNodeDes = getIntent().getStringExtra("NDESCRIPTION");
        newNodeDate = getIntent().getStringExtra("NDATE");
        //TextView hometext = findViewById(R.id.homeText);
        //hometext.setText(newNodeTitle);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbhelper = new dbHandler(this);

        if(dbhelper.check()){loadTasks();}

        taskList = dbhelper.getAlltasks();

        TaskAdapter adapter = new TaskAdapter(this,taskList);

        recyclerView.setAdapter(adapter);


    }



    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("task-tree.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void loadTasks(){
        try {

            JSONObject jsonObject = new JSONObject(readJSONFromAsset());
            String root = jsonObject.getString("name");
            String root_description = jsonObject.getString("description");

            JSONArray jArray = jsonObject.getJSONArray("tasks");

            for (int i = 0; i < jArray.length(); ++i) {
                JSONObject task = jArray.getJSONObject(i);

                String task_name = task.getString("name");
                String task_description = task.getString("description");
                String task_schedule = task.getString("scheduled");
                ArrayList<Subtask> task_subtasks = new ArrayList<Subtask>();
                JSONArray subtaskArray = task.getJSONArray("subtasks");

                for(int j=0; j<subtaskArray.length(); ++j){
                    JSONObject subtask = subtaskArray.getJSONObject(j);
                    task_subtasks.add(new Subtask(subtask.getString("name"),
                                                    subtask.getString("description"),
                                                subtask.getString("scheduled")));
                }

                dbhelper.addTask(task_name,task_description,task_schedule,task_subtasks);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home_addNode) {
            Log.d("HOMETAG","clicked add");
            Intent intent = new Intent(getApplicationContext(),addNode_popupActivity.class);
            intent.putExtra("TITLE","");
            intent.putExtra("NTITLE","");
            intent.putExtra("DESCRIPTION","");
            intent.putExtra("NDES","");
            intent.putExtra("DATE","");
            intent.putExtra("WHAT","Task");
            intent.putExtra("FROM","Home");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
