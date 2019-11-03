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



public class subtaskActivity extends AppCompatActivity  {
    String newNodeTitle,newNodeDes,newNodeDate;
    TextView title,desc;
    int id;

    List<Subtask> taskList;
    private dbHandler dbhelper;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);
        Toast.makeText(this,"In Home",Toast.LENGTH_SHORT).show();

        newNodeTitle = getIntent().getStringExtra("TEXT");
        newNodeDes = getIntent().getStringExtra("DESC");
        newNodeDate = getIntent().getStringExtra("DATE");
        id = getIntent().getIntExtra("ID",0);

        title = findViewById(R.id.taskname);
        title.setText(newNodeTitle);
        desc = findViewById(R.id.desc);
        desc.setText(newNodeDes);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_subtask) {
            //Log.d("HOMETAG","clicked add");
            Intent intent = new Intent(getApplicationContext(),editNode_popupActivity.class);

            intent.putExtra("TITLE",newNodeTitle);
            intent.putExtra("DESCRIPTION",newNodeDes);

            intent.putExtra("DATE",newNodeDate);
            intent.putExtra("ID",id);
            intent.putExtra("WHAT","Subtask");
            intent.putExtra("CLASS","new");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
