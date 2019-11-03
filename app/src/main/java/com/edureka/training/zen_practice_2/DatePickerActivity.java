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
import android.widget.DatePicker;

public class DatePickerActivity extends Activity implements View.OnClickListener{
    String title,des,title1,des1;
    String cls,from,what;
    DatePicker picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);
        Button confirm = findViewById(R.id.datePicker_confirm);
        confirm.setOnClickListener(DatePickerActivity.this);

        picker = findViewById(R.id.datePicker);
        title = getIntent().getStringExtra("TITLECONFIRM");
        title1 = getIntent().getStringExtra("NTITLE");
        des = getIntent().getStringExtra("DESCRIPTIONCONFIRM");
        des1 = getIntent().getStringExtra("NDES");
        cls = getIntent().getStringExtra("Class");
        from = getIntent().getStringExtra("FROM");
        what = getIntent().getStringExtra("WHAT");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.datePicker_confirm){
            Log.d("kaunsi",cls);
            if(cls.equals("Edit")){
                int id1 =getIntent().getIntExtra("ID",0);
            Intent intent = new Intent(getApplicationContext(),editNode_popupActivity.class);
            intent.putExtra("TITLE",title);
            intent.putExtra("DESCRIPTION",des);
            intent.putExtra("DATE",getCurrentDate());
            intent.putExtra("ID",id1);
            intent.putExtra("CLASS",from);
            intent.putExtra("WHAT",what);
            startActivity(intent);
            finish();
        } else if(cls.equals("Add")) {
                int id1 =getIntent().getIntExtra("ID",0);
                Intent intent = new Intent(getApplicationContext(),addNode_popupActivity.class);
                intent.putExtra("TITLE",title);
                intent.putExtra("NTITLE",title1);
                intent.putExtra("NDES",des1);
                intent.putExtra("DESCRIPTION",des);
                intent.putExtra("DATE",getCurrentDate());
                intent.putExtra("WHAT",what);
                intent.putExtra("FROM",from);
                intent.putExtra("ID",id1);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(),dayViewActivity.class);
                intent.putExtra("DATE",getCurrentDate());
                startActivity(intent);
                finish();
            }
    }}

    public String getCurrentDate(){
        StringBuilder builder=new StringBuilder();;
        builder.append(picker.getYear()+"-");
        builder.append((picker.getMonth() + 1)+"-");
        if(Integer.toString(picker.getDayOfMonth()).length()==1){
            builder.append("0"+picker.getDayOfMonth());
        } else {
            builder.append(picker.getDayOfMonth());
        }


        return builder.toString();
    }
}
