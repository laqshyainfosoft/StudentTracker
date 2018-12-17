package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.listeners.MyBatchClickListener;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.databinding.BatchattendanceBinding;

import java.util.ArrayList;
import java.util.List;

public class CompleteBatchAdapter extends RecyclerView.Adapter<CompleteBatchAdapter.CustomViewHolder> {
    private List<BatchInformationResponse.BatchInformation> batchInformationList;
    private MyBatchClickListener batchClickListener;
    private LayoutInflater layoutInflater;
    private SessionManager sessionManager;

    public CompleteBatchAdapter(Context context, MyBatchClickListener batchClickListener, SessionManager sessionManager) {
        layoutInflater = LayoutInflater.from(context);
        this.sessionManager=sessionManager;
        batchInformationList = new ArrayList<>();
        this.batchClickListener=batchClickListener;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BatchattendanceBinding batchattendanceBinding = BatchattendanceBinding.inflate(layoutInflater, parent, false);

//        batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.VISIBLE);
        return new CustomViewHolder(batchattendanceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bind(batchInformationList.get(position));
    }
    public void update(List<BatchInformationResponse.BatchInformation> batchInformations) {
        this.batchInformationList = batchInformations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (batchInformationList == null) {
            return 0;
        }
        return batchInformationList.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        BatchattendanceBinding batchattendanceBinding;
        CustomViewHolder(BatchattendanceBinding batchAttendanceBinding) {
            super(batchAttendanceBinding.getRoot());
            this.batchattendanceBinding = batchAttendanceBinding;
        }
        void bind(BatchInformationResponse.BatchInformation batchInformationResponse){
            batchattendanceBinding.setBatchattendancemodel(batchInformationResponse);
            batchattendanceBinding.optionsLayout.setVisibility(View.GONE);
            batchattendanceBinding.completedRecords.setVisibility(View.VISIBLE);
            batchattendanceBinding.locationLine2.setText(sessionManager.getLoggedInuserCenter());



        }
    }
}
