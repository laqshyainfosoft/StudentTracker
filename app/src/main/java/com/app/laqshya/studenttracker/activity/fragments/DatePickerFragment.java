package com.app.laqshya.studenttracker.activity.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {
    public MutableLiveData<String> setDate = new MutableLiveData<>();
    private DatePickerDialog datePickerDialog;

    private DatePickerDialog.OnDateSetListener dateSetListener =
            (view, year, month, day) -> {
                setDate.setValue(view.getYear() +
                        "-" + (view.getMonth() + 1) +
                        "-" + view.getDayOfMonth());

            };

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (datePickerDialog == null) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        }
        return datePickerDialog;

    }


}
