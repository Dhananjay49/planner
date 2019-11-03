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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class addNode_popupActivity extends Activity implements View.OnClickListener{
    EditText titletext,descriptiontext,datetext;
    String title="",des="",date="",what,from,title1,des1;
    int id;
    private dbHandler dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_node_popup);
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
        select.setOnClickListener(addNode_popupActivity.this);

        Button save = findViewById(R.id.save_popup_button);
        save.setOnClickListener(addNode_popupActivity.this);

        title = getIntent().getStringExtra("TITLE");
        title1 = getIntent().getStringExtra("NTITLE");
        des = getIntent().getStringExtra("DESCRIPTION");
        des1 = getIntent().getStringExtra("NDES");
        date = getIntent().getStringExtra("DATE");
        what = getIntent().getStringExtra("WHAT");
        from = getIntent().getStringExtra("FROM");
        id = getIntent().getIntExtra("ID",0);
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
            intent.putExtra("NTITLE",title1);
            intent.putExtra("DESCRIPTIONCONFIRM",des);
            intent.putExtra("NDES",des1);
            intent.putExtra("Class","Add");
            intent.putExtra("FROM",from);
            intent.putExtra("WHAT",what);
            intent.putExtra("ID",id);
            startActivity(intent);
            finish();
        }
        else if (i == R.id.save_popup_button){

            title = titletext.getText().toString();
            des = descriptiontext.getText().toString();
            date = datetext.getText().toString();
            if (!title.equals("")){
                if(from.equals("Home")){
                Intent intent = new Intent(addNode_popupActivity.this,homeActvity.class);
                intent.putExtra("NTITLE",title);
                intent.putExtra("NDESCRIPTION",des);
                intent.putExtra("NDATE",date);
                startActivity(intent);
                ArrayList<Subtask> children = new ArrayList<Subtask>();
                dbhelper.addTask(title,des,date,children);
                finish();} else {
                    Intent intent = new Intent(addNode_popupActivity.this,taskActivity.class);
                    intent.putExtra("TEXT",title1);
                    intent.putExtra("DES",des1);
                    intent.putExtra("ID",id);
                    startActivity(intent);
                    dbhelper.addSubtask(title1,title,des,date);
                    finish();
                }
            }
            else{
                titletext.setHint("This is a mandatory field");
            }

        }
    }
}
