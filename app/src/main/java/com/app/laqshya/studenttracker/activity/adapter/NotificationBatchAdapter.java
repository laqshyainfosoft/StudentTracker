package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
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

public class NotificationBatchAdapter extends RecyclerView.Adapter<NotificationBatchAdapter.MyBatchHolder> {
    private List<BatchInformationResponse.BatchInformation> batchInformationList;

    private LayoutInflater layoutInflater;
    private SessionManager sessionManager;

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

    public void batchChanged(int position) {
        batchInformationList.remove(position);
        notifyItemRemoved(position);
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
            batchattendanceBinding.optionsLayout.setVisibility(View.GONE);
            batchattendanceBinding.locationLine2.setText(sessionManager.getLoggedInuserCenter());

            batchattendanceBinding.executePendingBindings();
        }
    }
}
