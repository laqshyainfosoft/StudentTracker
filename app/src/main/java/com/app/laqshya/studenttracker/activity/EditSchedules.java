package com.app.laqshya.studenttracker.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.databinding.EditscheduleBinding;

public class EditSchedules extends AppCompatActivity {
    EditscheduleBinding editscheduleBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editscheduleBinding= DataBindingUtil.setContentView(this,R.layout.editschedule);
        editscheduleBinding.myToolbar.setTitle("Edit Batches");
        checkIntent();

    }

    private void checkIntent() {
        Intent intent=getIntent();
        if(intent!=null){
            editscheduleBinding.calenderbatchstartdate.setText(intent.getStringExtra(Constants.BATCHSTARTDATE));
        }
    }
}
