package com.example.mis_internee.atendence_app_android.Adapter.LeavePending;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mis_internee.atendence_app_android.Model.Leaves.Result;
import com.example.mis_internee.atendence_app_android.R;


import java.util.List;

/**
 * Created by mRashid on 05/04/18.
 */

public class LeavePendingAdopter extends  RecyclerView.Adapter<LeavePendingViewHolder> {




    private List<Result> mTasks;
//  public   Context mContex;



    public LeavePendingAdopter(List<Result> tasks) {
        mTasks = tasks;


    }



    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull LeavePendingViewHolder holder, int position) {
        Result task = mTasks.get(position);
        //  holder.mStudentName.setText(task.getmUserBoardName());



        holder.bindTask(task, position);

    }


    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public LeavePendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.leave_pending_item_view, parent, false);


        return new LeavePendingViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void updateList(List<Result> list){
        mTasks.addAll(list);
    }

}

