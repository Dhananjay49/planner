package com.edureka.training.zen_practice_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import java.util.ArrayList;

public class dbHandler extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "tasks_database";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_SUBTASKS = "subtasks";
    private static final String KEY_NAME = "name";
    private static final String KEY_DESC = "description";
    private static final String KEY_DATE = "scheduled";
    private static final String KEY_ID = "id";
    private static final String KEY_SUBTASK_ID = "subtask_id";


    private static final String CREATE_TABLE_TASKS = "CREATE TABLE " + TABLE_TASKS + "("
            +KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," +KEY_DESC + " TEXT," + KEY_DATE + " TEXT );";
    private static final String CREATE_TABLE_SUBTASKS = "CREATE TABLE " + TABLE_SUBTASKS + "("
           + KEY_SUBTASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +KEY_ID + " INTEGER," +KEY_NAME + " TEXT," +KEY_DESC + " TEXT," + KEY_DATE + " TEXT );";


    public dbHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

       // Log.d("table",CREATE_TABLE_TASKS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TASKS);
        db.execSQL(CREATE_TABLE_SUBTASKS);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_TASKS + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SUBTASKS + "'");

        onCreate(db);
    }

    public boolean check(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM " + TABLE_TASKS;
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount>0)
            return false;
        else {
            String count1 = "SELECT count(*) FROM " + TABLE_SUBTASKS;
            Cursor m1cursor = db.rawQuery(count1,null);
            m1cursor.moveToFirst();
            int i1count = m1cursor.getInt(0);
            if(i1count>0){
                return false;
            } else {return true;}
        }
    }

    public void addTask(String name, String description, String date, ArrayList<Subtask> children){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME,name);
        values.put(KEY_DESC,description);
        values.put(KEY_DATE,date);
        long id = db.insertWithOnConflict(TABLE_TASKS,null,values,SQLiteDatabase.CONFLICT_IGNORE);



        for(int i=0;i<children.size();i++){
            ContentValues childrenValues = new ContentValues();
            childrenValues.put(KEY_ID,id);
            childrenValues.put(KEY_NAME,children.get(i).getTitle());
            childrenValues.put(KEY_DESC,children.get(i).getDescription());
            childrenValues.put(KEY_DATE,children.get(i).getDate());

            long new_id = db.insertWithOnConflict(TABLE_SUBTASKS,null,childrenValues,SQLiteDatabase.CONFLICT_IGNORE);

        }
    }

    public void addSubtask(String taskName, String name, String description, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        int id = 0;
        String Query = "SELECT " + KEY_ID + " FROM " + TABLE_TASKS + " WHERE " + KEY_NAME + " = '" + taskName + "';";
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor c = db1.rawQuery(Query,null);
        if(c.moveToFirst()){
            id = c.getInt(c.getColumnIndex(KEY_ID));
        }
        ContentValues values = new ContentValues();

        values.put(KEY_ID,id);
        values.put(KEY_NAME,name);
        values.put(KEY_DESC,description);
        values.put(KEY_DATE,date);

        long id1 = db.insertWithOnConflict(TABLE_SUBTASKS,null,values,SQLiteDatabase.CONFLICT_IGNORE);
    }

    public void updateTask(int id,String name,String description,String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME,name);
        values.put(KEY_DATE,date);
        //Log.d("updated date",date);
        values.put(KEY_DESC,description);
        db.update(TABLE_TASKS,values,KEY_ID + "= ?", new String[]{String.valueOf(id)});

    }
    public void updateSubTask(int id,String name,String description,String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DATE,date);
        values.put(KEY_DESC,description);
        values.put(KEY_NAME,name);
        db.update(TABLE_SUBTASKS,values,KEY_SUBTASK_ID + "= ?",new String[]{String.valueOf(id)});
    }

   public ArrayList<Task> getAlltasks(){
        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);
        if (c.moveToFirst()){
            do {
                Task task;
                task = new Task();
                task.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                task.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                task.setDescription(c.getString(c.getColumnIndex(KEY_DESC)));
                task.setScheduled(c.getString(c.getColumnIndex(KEY_DATE)));
                task.setSubtasks(getAllsubtasks(task.getId()));
                taskArrayList.add(task);
            } while (c.moveToNext());
        }
        return taskArrayList;
    }

    public ArrayList<Subtask> getAllsubtasks(int id){
        ArrayList<Subtask> subtaskArrayList = new ArrayList<Subtask>();
        String selectQuery = "SELECT * FROM " + TABLE_SUBTASKS + " WHERE " + KEY_ID + " = "+  Integer.toString(id);
//        String selectQuery2 = "SELECT " + KEY_NAME + " FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = " + Integer.toString(id);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery,null);
        if(c.moveToFirst()){
            do {
                Subtask subtask = new Subtask(
                c.getString(c.getColumnIndex(KEY_NAME)),
                c.getString(c.getColumnIndex(KEY_DESC)),
                c.getString(c.getColumnIndex(KEY_DATE)));
                subtask.setId(c.getInt(c.getColumnIndex(KEY_SUBTASK_ID)));
                subtaskArrayList.add(subtask);
            } while(c.moveToNext());
        }
        return subtaskArrayList;
    }

    public ArrayList<Dayview> getAll(String date){
        ArrayList<Dayview> dayviewArrayList = new ArrayList<Dayview>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_DATE + " = '" + date +"';" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("query",selectQuery);
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst()){
            do {
                Log.d("task_name",c.getString(c.getColumnIndex(KEY_NAME)));
                Dayview dayview = new Dayview(
                        c.getInt(c.getColumnIndex(KEY_ID)),
                        c.getString(c.getColumnIndex(KEY_NAME)),
                        c.getString(c.getColumnIndex(KEY_DESC)),
                        c.getString(c.getColumnIndex(KEY_DATE)),
                        "Task", "Root/" + c.getString(c.getColumnIndex(KEY_NAME)));
                dayviewArrayList.add(dayview);
            } while (c.moveToNext());
            }

        String selectQuery2 = "SELECT * FROM " + TABLE_SUBTASKS + " WHERE " + KEY_DATE + " = '" + date +"';" ;
        Cursor c1 = db.rawQuery(selectQuery2,null);

        if(c1.moveToFirst()){
            do{
                String selectQuery3 = "SELECT " + KEY_NAME +" FROM " + TABLE_TASKS + " WHERE " + KEY_ID + " = '" + c1.getString(c1.getColumnIndex(KEY_ID)) +"';";
                Cursor c2 = db.rawQuery(selectQuery3,null);
                if(c2.moveToFirst()){
                    Dayview dayview = new Dayview(
                            c1.getInt(c1.getColumnIndex(KEY_SUBTASK_ID)),
                            c1.getString(c1.getColumnIndex(KEY_NAME)),
                            c1.getString(c1.getColumnIndex(KEY_DESC)),
                            c1.getString(c1.getColumnIndex(KEY_DATE)),
                            "Subtask", "Root/" + c2.getString(c2.getColumnIndex(KEY_NAME)) + "/"+c1.getString(c1.getColumnIndex(KEY_NAME))
                    );
                    dayviewArrayList.add(dayview);
                }
            } while (c1.moveToNext());
        }
        return dayviewArrayList;
    }



}
