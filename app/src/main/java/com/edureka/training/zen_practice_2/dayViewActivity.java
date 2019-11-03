package com.edureka.training.zen_practice_2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class dayViewActivity extends AppCompatActivity {

//    String date = getCurrentDate();
    //DatePicker picker;
    List<Dayview> taskList;
    private dbHandler dbhelper;
    String date;
    RecyclerView recyclerView;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);
        Toast.makeText(this,"In Day View",Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbhelper = new dbHandler(this);


        date = getIntent().getStringExtra("DATE");
        if(date.equals("null")){
            date = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if(dbhelper.check()){loadTasks();}

        Log.d("date",date);

        taskList = dbhelper.getAll(date);

        Log.d("size",Integer.toString(taskList.size()));
        DayviewAdapter adapter = new DayviewAdapter(this,taskList);

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
        getMenuInflater().inflate(R.menu.dayview_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.change_date) {
            //Log.d("HOMETAG","clicked add");
            Intent intent = new Intent(getApplicationContext(),DatePickerActivity.class);
            intent.putExtra("TITLECONFIRM","");
            intent.putExtra("NTITLE","");
            intent.putExtra("NDES","");
            intent.putExtra("DESCRIPTIONCONFIRM","");
            intent.putExtra("Class","dayView");
            intent.putExtra("FROM","dayview");
            intent.putExtra("WHAT","Task");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
