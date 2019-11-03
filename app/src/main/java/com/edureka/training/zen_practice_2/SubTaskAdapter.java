package com.edureka.training.zen_practice_2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.edureka.training.zen_practice_2.R;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class SubTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mCtx;

    private List<Subtask> taskList;

    private View view;

    private static final long DOUBLE_PRESS_INTERVAL = 750;


    public SubTaskAdapter(Context mCtx, List<Subtask> taskList){
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @Override
    public int getItemViewType(int position){
        return taskList.get(position).getOpen();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(viewType==0){
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.task_layout, null);
            return new TaskViewHolder(view);} else
        {
            LayoutInflater inflater = LayoutInflater.from(mCtx);
            View view = inflater.inflate(R.layout.task_layout_2,null);
            return new TaskViewHolder2(view);
        }
    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position){
        if(holder.getItemViewType()==0) {
            TaskViewHolder taskViewHolder = (TaskViewHolder)holder;
            Subtask task = taskList.get(position);

            taskViewHolder.task_title.setText(task.getTitle());
            if (!task.getDate().equals("null")) {
                taskViewHolder.task_date.setText(task.getDate());
            } else {
                taskViewHolder.task_date.setText("");
            }
        } else {
            TaskViewHolder2 taskViewHolder2 = (TaskViewHolder2)holder;
            Subtask task = taskList.get(position);

            taskViewHolder2.task_title.setText(task.getTitle());
            if (!task.getDate().equals("null")) {
                taskViewHolder2.task_date.setText(task.getDate());
            } else {
                taskViewHolder2.task_date.setText("");
            }
            taskViewHolder2.task_desc.setText(task.getDescription());
            taskViewHolder2.task_subtasks.setText("");
        }

    }

    @Override
    public int getItemCount(){
        return taskList.size();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView task_title, task_date;

        public TaskViewHolder(View itemView){
            super(itemView);
            view = itemView;


            task_title = itemView.findViewById(R.id.task_title);
            task_date = itemView.findViewById(R.id.task_date);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
//                    Log.d("card","clicked");
                    long pressTime = System.currentTimeMillis();
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        Subtask clickedtask = taskList.get(pos);
//                        Log.d("taskName",clickedtask.getTitle());
                        clickedtask.switchOpen();
                        for (int i = 0; i < taskList.size(); i++) {
                            if (i != pos) {
                                taskList.get(i).setOpen(0);
                            }
                        }
                        clickedtask.last_clicked=pressTime;
//                        Log.d("clicked_task_open",Integer.toString(clickedtask.getOpen()));
                        notifyDataSetChanged();
                    }


                }
            });

        }


    }
    public class TaskViewHolder2 extends RecyclerView.ViewHolder{
        TextView task_title,task_desc,task_date,task_subtasks;
        Button button;

        public TaskViewHolder2(View itemView){
            super(itemView);
            view = itemView;
            task_title = itemView.findViewById(R.id.task_title);
            task_date = itemView.findViewById(R.id.task_date);
            task_desc = itemView.findViewById(R.id.task_description);
            task_subtasks = itemView.findViewById(R.id.task_subtasks);
            button = (Button) itemView.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Subtask clickedtask = taskList.get(pos);
                    Intent intent = new Intent(mCtx,editNode_popupActivity.class);
                    intent.putExtra("TITLE",clickedtask.getTitle());
                    intent.putExtra("DESCRIPTION",clickedtask.getDescription());
                    intent.putExtra("DATE",clickedtask.getDate());
                    intent.putExtra("ID",clickedtask.getId());
                    intent.putExtra("WHAT","Subtask");
                    intent.putExtra("CLASS","new");
                    mCtx.startActivity(intent);
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
//                    Log.d("card","clicked");
                    long pressTime = System.currentTimeMillis();

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        Subtask clickedtask = taskList.get(pos);
//                        Log.d("taskName",clickedtask.getTitle());
                        if (pressTime - clickedtask.last_clicked > DOUBLE_PRESS_INTERVAL) {
                            clickedtask.switchOpen();
//                        Log.d("clicked_task_open",Integer.toString(clickedtask.getOpen()));
                            notifyDataSetChanged();
                        } else {
                            Log.d("doubleclicked", "yes");
                            Intent intent = new Intent(mCtx, subtaskActivity.class);
                            intent.putExtra("TEXT", clickedtask.getTitle());
                            intent.putExtra("DESC", clickedtask.getDescription());
                            intent.putExtra("DATE", clickedtask.getDate());
                            intent.putExtra("ID", clickedtask.getId());
                            mCtx.startActivity(intent);
                        }
                    }
                }

            });
            }
    }


}
