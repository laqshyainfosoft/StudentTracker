package com.app.laqshya.studenttracker.activity.adapter;

import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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

public class AllBatchAdapter extends RecyclerView.Adapter<AllBatchAdapter.CustomHolder> {
    private List<BatchInformationResponse.BatchInformation> batchInformationList;
    private List<BatchInformationResponse.BatchInformation> batchInformationListFiltered;

    private LayoutInflater layoutInflater;

    public AllBatchAdapter() {
        this.batchInformationList = new ArrayList<>();
        this.batchInformationListFiltered=new ArrayList<>();
    }

    public void setBatchInformationList(List<BatchInformationResponse.BatchInformation> batchInformationList) {
        this.batchInformationList = batchInformationList;
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

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(viewGroup.getContext());
        BatchattendanceBinding batchattendanceBinding = BatchattendanceBinding.inflate(layoutInflater, viewGroup, false);

//        batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.VISIBLE);
        return new AllBatchAdapter.CustomHolder(batchattendanceBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int position) {
        customHolder.bind(batchInformationListFiltered.get(position));

    }

    @Override
    public int getItemCount() {
        return batchInformationListFiltered == null ? 0 : batchInformationListFiltered.size();
    }

    class CustomHolder extends RecyclerView.ViewHolder {
        private BatchattendanceBinding batchattendanceBinding;

        CustomHolder(@NonNull BatchattendanceBinding batchattendanceBinding) {
            super(batchattendanceBinding.getRoot());
            this.batchattendanceBinding = batchattendanceBinding;
        }

        void bind(BatchInformationResponse.BatchInformation batchInformation) {
            batchattendanceBinding.setBatchattendancemodel(batchInformation);

            batchattendanceBinding.deletebatchCounsellor.setVisibility(View.GONE);
            batchattendanceBinding.editbatchCounsellor.setVisibility(View.GONE);
            batchattendanceBinding.completebatchcounsellor.setVisibility(View.GONE);
            batchattendanceBinding.viewRecordsBatch.setVisibility(View.GONE);
            batchattendanceBinding.takeAttendance.setVisibility(View.GONE);
            batchattendanceBinding.scheduleSeperator.setVisibility(View.GONE);

            batchattendanceBinding.locationLine2.setText(batchInformation.getCentername());

            batchattendanceBinding.optionsLayout.setVisibility(View.VISIBLE);
            batchattendanceBinding.exclBatch.setVisibility(View.GONE);
            batchattendanceBinding.locationLine2.setVisibility(View.VISIBLE);
//            batchattendanceBinding.locationIcon.setVisibility(View.GONE);

                TogglebuttonlayoutBinding togglebuttonlayoutBinding;
                batchattendanceBinding.sDataBatch.setVisibility(View.GONE);
                batchattendanceBinding.scheduleLayoutBatch.removeAllViews();



                for (Schedule schedule : batchInformation.getSchedule()) {
                    togglebuttonlayoutBinding = DataBindingUtil.inflate(layoutInflater, R.layout.togglebuttonlayout, null, false);

                    batchattendanceBinding.scheduleLayoutBatch.addView(togglebuttonlayoutBinding.getRoot());
                    String time = schedule.getStartTime();
                    String day = schedule.getDay();
                    togglebuttonlayoutBinding.batchTiming.setText(time);
                    togglebuttonlayoutBinding.batchNameDay.setText(day);

                }

                   batchattendanceBinding.scheduleLayoutBatch.setVisibility(View.VISIBLE);
            batchattendanceBinding.executePendingBindings();
        }
    }
}
