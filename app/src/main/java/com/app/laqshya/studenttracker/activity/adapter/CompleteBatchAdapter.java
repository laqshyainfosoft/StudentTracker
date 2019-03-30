package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.listeners.MyBatchClickListener;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.databinding.BatchattendanceBinding;

import java.util.ArrayList;
import java.util.List;

public class CompleteBatchAdapter extends RecyclerView.Adapter<CompleteBatchAdapter.CustomViewHolder> {
    private List<BatchInformationResponse.BatchInformation> batchInformationList;
    private List<BatchInformationResponse.BatchInformation> batchInformationListFiltered;
    private LayoutInflater layoutInflater;
    private SessionManager sessionManager;

    public CompleteBatchAdapter(Context context, SessionManager sessionManager) {
        layoutInflater = LayoutInflater.from(context);
        this.sessionManager=sessionManager;
        batchInformationList = new ArrayList<>();
        batchInformationListFiltered=new ArrayList<>();

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
        holder.batchattendanceBinding.locationLine2.setText(batchInformationListFiltered.get(position).getCentername());
        holder.bind(batchInformationListFiltered.get(position));
    }
    public void update(List<BatchInformationResponse.BatchInformation> batchInformations) {
        this.batchInformationList= batchInformations;
        this.batchInformationListFiltered=batchInformationList;
        notifyDataSetChanged();
    }
    public void  setFilteredList(String centername){
        if (centername != null && !centername.isEmpty()) {
            List<BatchInformationResponse.BatchInformation> batchInformationListTemporary=new ArrayList<>();

            for (BatchInformationResponse.BatchInformation batchInformation:batchInformationList){
                if (batchInformation.getCentername().equals(centername)){
                    batchInformationListTemporary.add(batchInformation);
                }
            }
            batchInformationListFiltered=batchInformationListTemporary;
            notifyDataSetChanged();

        }

    }

    @Override
    public int getItemCount() {
        if (batchInformationListFiltered == null) {
            return 0;
        }
        return batchInformationListFiltered.size();
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
            batchattendanceBinding.viewRecordsBatch.setVisibility(View.GONE);
            batchattendanceBinding.completedRecords.setVisibility(View.GONE);
            if(sessionManager.getLoggedInType().equals(Constants.COUNSELLOR))
            batchattendanceBinding.locationLine2.setText(sessionManager.getLoggedInuserCenter());




        }
    }
}
