package com.edureka.training.zen_practice_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class editNode_popupActivity extends Activity implements View.OnClickListener{
    EditText titletext,descriptiontext,datetext;

    int id;
    String title="",des="",date="",edit="Edit",cls="",what="",olddate="";
    private dbHandler dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_node_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dbhelper = new dbHandler(this);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

        Button select = findViewById(R.id.open_datePicker);
        select.setOnClickListener(editNode_popupActivity.this);

        Button save = findViewById(R.id.save_popup_button);
        save.setOnClickListener(editNode_popupActivity.this);

        title = getIntent().getStringExtra("TITLE");
        des = getIntent().getStringExtra("DESCRIPTION");
        date = getIntent().getStringExtra("DATE");
        olddate = date;
        id = getIntent().getIntExtra("ID",0);
        cls = getIntent().getStringExtra("CLASS");
        what = getIntent().getStringExtra("WHAT");

        titletext = findViewById(R.id.popup_title);
        descriptiontext = findViewById(R.id.popup_description);
        datetext = findViewById(R.id.popup_date);
        if (!title.equals(""))
            titletext.setText(title);
        if (!des.equals(""))
            descriptiontext.setText(des);
        if (!date.equals(""))
            datetext.setText(date);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.open_datePicker){
            Log.d("TAG","clicked");
            title = titletext.getText().toString();
            des = descriptiontext.getText().toString();
            Intent intent = new Intent(getApplicationContext(),DatePickerActivity.class);
            intent.putExtra("TITLECONFIRM",title);
            intent.putExtra("NTITLE","");
            intent.putExtra("NDES","");
            intent.putExtra("DESCRIPTIONCONFIRM",des);
            intent.putExtra("Class",edit);
            intent.putExtra("ID",id);
            intent.putExtra("FROM",cls);
            intent.putExtra("WHAT",what);
            startActivity(intent);
            finish();
        }
        else if (i == R.id.save_popup_button){

            title = titletext.getText().toString();
            des = descriptiontext.getText().toString();
            date = datetext.getText().toString();
            if (!title.equals("")){
                if(cls.equals("Home")){
                    dbhelper.updateTask(id,title,des,date);
                    Intent intent = new Intent(editNode_popupActivity.this,homeActvity.class);
                    intent.putExtra("NTITLE",title);
                    intent.putExtra("NDESCRIPTION",des);
                    intent.putExtra("NDATE",date);
                    startActivity(intent);
                    finish();
                } else if(cls.equals("Dayview")){
                    if(what.equals("Task")){
                        dbhelper.updateTask(id,title,des,date);
                        Intent intent = new Intent(editNode_popupActivity.this,dayViewActivity.class);
                        intent.putExtra("DATE",olddate);
                        startActivity(intent);
                        finish();
                    } else {
                        dbhelper.updateSubTask(id,title,des,date);
                        Intent intent = new Intent(editNode_popupActivity.this,dayViewActivity.class);
                        intent.putExtra("DATE",olddate);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    dbhelper.updateSubTask(id,title,des,date);
                    Intent intent = new Intent(editNode_popupActivity.this,subtaskActivity.class);
                    intent.putExtra("TEXT",title);
                    intent.putExtra("DESC",des);
                    intent.putExtra("DATE",date);
                    intent.putExtra("ID",id);
                    startActivity(intent);
                    finish();
                }
            }
            else{
                titletext.setHint("This is a mandatory field");
            }

        }
    }
}
