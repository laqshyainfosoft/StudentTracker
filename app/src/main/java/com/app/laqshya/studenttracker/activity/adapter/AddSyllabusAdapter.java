package com.app.laqshya.studenttracker.activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.laqshya.studenttracker.activity.model.SyllabusModel;

import java.util.List;

import androidx.annotation.NonNull;

public class AddSyllabusAdapter extends ArrayAdapter<SyllabusModel> {

    private Context context;
    private List<SyllabusModel> syllabusModelList;
    public AddSyllabusAdapter(@NonNull Context context, int resource, List<SyllabusModel> syllabusModelList) {
        super(context, resource, syllabusModelList);
        this.context = context;
        this.syllabusModelList = syllabusModelList;
    }

    @Override
    public void add(SyllabusModel syllabusModel) {
        this.syllabusModelList.add(syllabusModel);
    }


    @Override
    public int getCount() {
        return syllabusModelList.size();
    }

    @Override
    public SyllabusModel getItem(int position) {
        return syllabusModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(syllabusModelList.get(position).getCourse_module_name());
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setText(syllabusModelList.get(position).getCourse_module_name());
        return label;
    }
}
