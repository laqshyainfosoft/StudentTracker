package com.app.laqshya.studenttracker.activity.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.databinding.BatchattendanceBinding;

public class ScheduleBatchesFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String mSpinnerLabel = "";
    BatchattendanceBinding batchattendanceBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        batchattendanceBinding = BatchattendanceBinding.inflate(inflater, container, false);

        if (batchattendanceBinding.filterBatches != null) {
            batchattendanceBinding.filterBatches.setOnItemSelectedListener(this);
        }

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.schedule_batch_details,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (batchattendanceBinding.filterBatches != null) {
            batchattendanceBinding.filterBatches.setAdapter(arrayAdapter);
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerLabel = parent.getItemAtPosition(position).toString();

        switch (mSpinnerLabel) {
            case "All":

                break;
            case "Running":

                break;
            case "Completed":

                break;
            case "Deleted":

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
