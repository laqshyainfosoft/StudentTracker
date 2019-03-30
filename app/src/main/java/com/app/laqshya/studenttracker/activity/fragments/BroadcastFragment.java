package com.app.laqshya.studenttracker.activity.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.fragments.notifications.AllStudentsAllCentresFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.PendingFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SameBatchFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SingleStudentNotificationFragment;
import com.app.laqshya.studenttracker.databinding.FragmentBroadcastAdminBinding;

public class BroadcastFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    FragmentBroadcastAdminBinding fragmentBroadcastAdminBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBroadcastAdminBinding = FragmentBroadcastAdminBinding.inflate(inflater, container, false);
        fragmentBroadcastAdminBinding.bottomNavigationAdmin.setOnNavigationItemSelectedListener(this);
        return fragmentBroadcastAdminBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity()!=null){
            BottomNavigationView b=getActivity().findViewById(R.id.bottom_navigation_admin);
            b.setSelectedItemId(R.id.singleStudent);
        }
//        loadHome();




    }
//


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment=null;

        switch (menuItem.getItemId()){
         case    R.id.singleStudent:
            fragment=new SingleStudentNotificationFragment();

            break;

            case R.id.sameBatch:

                fragment=new SameBatchFragment();

                break;
            case R.id.pending_admin:
                fragment=new PendingFragment();
                break;
            case R.id.allStudents:
                fragment=new AllStudentsAllCentresFragment();
                break;


        }
        if (fragment != null) {
            FragmentManager fragmentManager=getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().replace(R.id.frame,fragment).addToBackStack(null).commit();
            }
        }
        return true;
    }
}
