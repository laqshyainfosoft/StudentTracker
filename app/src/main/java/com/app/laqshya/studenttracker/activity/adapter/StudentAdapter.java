package com.app.laqshya.studenttracker.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.listeners.StudentAttendanceListener;
import com.app.laqshya.studenttracker.activity.model.StudentInfo;
import com.app.laqshya.studenttracker.databinding.StudentattendanceRowBinding;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private List<StudentInfo> list;
    private StudentAttendanceListener studentAttendanceListener;

    public StudentAdapter(StudentAttendanceListener studentAttendanceListener) {
        this.studentAttendanceListener = studentAttendanceListener;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        StudentattendanceRowBinding studentattendanceRowBinding = StudentattendanceRowBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new StudentHolder(studentattendanceRowBinding);
    }

    public void setList(List<StudentInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder studentHolder, int i) {
        StudentInfo studentInfo = list.get(i);
        studentHolder.bind(studentInfo);

    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 0;
        } else {
            return list.size();
        }
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        StudentattendanceRowBinding studentattendanceRowBinding;

        StudentHolder(@NonNull StudentattendanceRowBinding itemView) {
            super(itemView.getRoot());
            this.studentattendanceRowBinding = itemView;
        }

        private void bind(StudentInfo studentInfo) {
            studentattendanceRowBinding.setStudent(studentInfo);
            studentattendanceRowBinding.executePendingBindings();
            studentattendanceRowBinding.studentLogo.setOnCheckedChangeListener((buttonView, isChecked) -> {
                studentAttendanceListener.markAttendance(getAdapterPosition(),studentInfo);



            });
//


        }
    }
}
