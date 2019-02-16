package com.app.laqshya.studenttracker.activity.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.SyllabusList;
import com.app.laqshya.studenttracker.databinding.SyllabusBinding;

public class SyllabusAdapter extends RecyclerView.Adapter<SyllabusAdapter.MyViewHolder> {
    private SyllabusList syllabusList;

    public void setFacultyNotificationList(SyllabusList syllabusList) {
        this.syllabusList = syllabusList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public SyllabusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        SyllabusBinding syllabusBinding = SyllabusBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new SyllabusAdapter.MyViewHolder(syllabusBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(syllabusList.getSyllabus().get(i));
    }


    @Override
    public int getItemCount() {
        if (syllabusList==null || syllabusList.getSyllabus() == null) return 0;
        else return syllabusList.getSyllabus().size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        SyllabusBinding syllabusBinding;

        MyViewHolder(@NonNull SyllabusBinding itemView) {
            super(itemView.getRoot());
            this.syllabusBinding = itemView;
        }

        void bind(SyllabusList.Syllabus syllabus) {
            syllabusBinding.setSyllabusItem(syllabus);
            if(syllabus.getStatus().equals("0")){
                syllabusBinding.syllabusstatus.setText("Absent");
                syllabusBinding.syllabusstatus.setBackgroundColor(Color.RED);
            }

            syllabusBinding.executePendingBindings();
//            notificationItemBinding.executePendingBindings();
        }
    }
}
