package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.databinding.BatchattendanceBinding;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class NotificationBatchAdapter extends RecyclerView.Adapter<NotificationBatchAdapter.MyBatchHolder> {
    private List<BatchInformationResponse.BatchInformation> batchInformationList;

    private LayoutInflater layoutInflater;
    private SessionManager sessionManager;
    private int indexSelected=RecyclerView.NO_POSITION;

    public NotificationBatchAdapter(Context context, SessionManager sessionManager) {
        layoutInflater = LayoutInflater.from(context);
        this.sessionManager = sessionManager;
        batchInformationList = new ArrayList<>();

    }

    @NonNull
    @Override
    public NotificationBatchAdapter.MyBatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BatchattendanceBinding batchattendanceBinding = BatchattendanceBinding.inflate(layoutInflater, parent, false);

//        batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.VISIBLE);
        return new NotificationBatchAdapter.MyBatchHolder(batchattendanceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationBatchAdapter.MyBatchHolder holder, int position) {
        holder.bind(batchInformationList.get(position));
        if(indexSelected==position){
            holder.batchattendanceBinding.linearLayout.setBackgroundColor(Color.parseColor("#f4e6e6"));
        }
        else {
            holder.batchattendanceBinding.linearLayout.setBackgroundColor(Color.WHITE);
        }



    }

    @Override
    public int getItemCount() {
        if (batchInformationList == null) {
            return 0;
        }
        return batchInformationList.size();
    }

    public void update(List<BatchInformationResponse.BatchInformation> batchInformations) {
        this.batchInformationList = batchInformations;
        notifyDataSetChanged();
    }


    class MyBatchHolder extends RecyclerView.ViewHolder {
        BatchattendanceBinding batchattendanceBinding;

        MyBatchHolder(BatchattendanceBinding batchAttendanceBinding) {
            super(batchAttendanceBinding.getRoot());
            this.batchattendanceBinding = batchAttendanceBinding;
        }

        void bind(BatchInformationResponse.BatchInformation batchInformation) {
            batchattendanceBinding.setBatchattendancemodel(batchInformation);
            batchattendanceBinding.sDataBatch.setVisibility(View.GONE);

            if(batchInformation.getCentername()!=null){
                batchattendanceBinding.teacherName.setVisibility(View.GONE);
                batchattendanceBinding.locationLine2.setText(batchInformation.getCentername());
            }
            else {
                batchattendanceBinding.locationLine2.setText(sessionManager.getLoggedInuserCenter());
            }
            batchattendanceBinding.linearLayout.setOnClickListener(v -> {
                batchattendanceBinding.linearLayout.setBackgroundColor(Color.parseColor("#f4e6e6"));
                indexSelected=getAdapterPosition();

                notifyItemRangeChanged(0,batchInformationList.size());

            });
            batchattendanceBinding.optionsLayout.setVisibility(View.GONE);

            batchattendanceBinding.executePendingBindings();
        }
    }

    public String getIndexSelected() {
        if(indexSelected!=-1)
        return batchInformationList.get(indexSelected).getBatchid();
        else {
            return "0";
        }
    }
    public String getSelectedFaculty()
    {
        if(indexSelected!=-1){
            return batchInformationList.get(indexSelected).getFaculty_id();
        }
        else {
            return null;
        }
    }
}
