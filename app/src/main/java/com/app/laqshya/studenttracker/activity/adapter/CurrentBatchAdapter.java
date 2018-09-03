package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;
import com.app.laqshya.studenttracker.activity.model.Schedule;
import com.app.laqshya.studenttracker.databinding.BatchattendanceBinding;
import com.app.laqshya.studenttracker.databinding.TogglebuttonlayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CurrentBatchAdapter extends RecyclerView.Adapter<CurrentBatchAdapter.MyBatchHolder> {
    private BatchattendanceBinding batchattendanceBinding;

    private List<BatchInformationResponse.BatchInformation> batchInformationList;
    private LayoutInflater layoutInflater;

    public CurrentBatchAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        batchInformationList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyBatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        batchattendanceBinding = BatchattendanceBinding.inflate(layoutInflater, parent, false);

        batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.VISIBLE);
        return new MyBatchHolder(batchattendanceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBatchHolder holder, int position) {
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

    public class MyBatchHolder extends RecyclerView.ViewHolder {
        BatchattendanceBinding batchattendanceBinding;

        public MyBatchHolder(BatchattendanceBinding batchAttendanceBinding) {
            super(batchAttendanceBinding.getRoot());
            this.batchattendanceBinding = batchAttendanceBinding;
        }

        void bind(BatchInformationResponse.BatchInformation batchInformation) {
            batchattendanceBinding.setBatchattendancemodel(batchInformation);
            TogglebuttonlayoutBinding togglebuttonlayoutBinding;
            batchattendanceBinding.sDataBatch.setVisibility(View.GONE);

            for (Schedule schedule : batchInformation.getSchedule()) {
                togglebuttonlayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.togglebuttonlayout, null, false);

                batchattendanceBinding.scheduleLayoutBatch.addView(togglebuttonlayoutBinding.getRoot());
                String time = schedule.getStartTime();
                String day = schedule.getDay();
                togglebuttonlayoutBinding.batchTiming.setText(time);
                togglebuttonlayoutBinding.batchNameDay.setText(day);
            }
            batchattendanceBinding.exclBatch.setOnClickListener(v -> {
                if (batchattendanceBinding.scheduleLayoutBatch.getVisibility() == View.VISIBLE) {
                    batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.GONE);
                } else {
                    batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.VISIBLE);
                }

            });
//            togglebuttonlayoutBinding=TogglebuttonlayoutBinding.
            batchattendanceBinding.executePendingBindings();
        }
    }
}
