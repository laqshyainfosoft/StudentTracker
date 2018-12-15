package com.app.laqshya.studenttracker.activity.listeners;

import android.view.View;

import com.app.laqshya.studenttracker.activity.model.BatchInformationResponse;

public interface MyBatchClickListener {
    void OnClick(View view, int position, BatchInformationResponse.BatchInformation batchInformation);
    default void onDelete(int position, String bid, boolean deleteOrComplete){

    }


}
