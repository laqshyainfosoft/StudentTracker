package com.app.laqshya.studenttracker.activity.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.laqshya.studenttracker.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddSyllabusFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddSyllabusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddSyllabusFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.upload_syllabus, container, false);

        return rootview;
    }
}
