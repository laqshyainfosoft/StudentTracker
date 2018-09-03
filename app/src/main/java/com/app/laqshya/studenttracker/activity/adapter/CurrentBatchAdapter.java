package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.databinding.BatchattendanceBinding;

import java.util.ArrayList;
import java.util.List;

public class CurrentBatchAdapter extends RecyclerView.Adapter<CurrentBatchAdapter.MyBatchHolder> {
    private BatchattendanceBinding batchattendanceBinding;
    private List<BatchInformationResponse.BatchInformation> batchInformationList;
    private LayoutInflater layoutInflater;

    public CurrentBatchAdapter(Context context) {
        layoutInflater=LayoutInflater.from(context);
        batchInformationList=new ArrayList<>();
    }

    @NonNull
    @Override
    public MyBatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        batchattendanceBinding=BatchattendanceBinding.inflate(layoutInflater,parent,false);
        return new MyBatchHolder(batchattendanceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBatchHolder holder, int position) {
        holder.bind(batchInformationList.get(position));

    }

    @Override
    public int getItemCount() {
        if(batchInformationList==null){
            return 0;
        }
        return batchInformationList.size();
    }
    public void update(List<BatchInformationResponse.BatchInformation> batchInformations){
        this.batchInformationList=batchInformations;
        notifyDataSetChanged();
    }

    public class MyBatchHolder extends RecyclerView.ViewHolder {
        BatchattendanceBinding batchattendanceBinding;
        public MyBatchHolder(BatchattendanceBinding batchAttendanceBinding) {
            super(batchAttendanceBinding.getRoot());
            this.batchattendanceBinding=batchAttendanceBinding;
        }
        void bind(BatchInformationResponse.BatchInformation batchInformation){
            batchattendanceBinding.setBatchattendancemodel(batchInformation);
            batchattendanceBinding.executePendingBindings();
        }
    }
}
