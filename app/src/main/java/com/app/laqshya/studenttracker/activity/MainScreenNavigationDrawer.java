package com.app.laqshya.studenttracker.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.RegistrationFactory;
import com.app.laqshya.studenttracker.activity.fragments.notifications.AllStudentsAllCentresFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.PendingFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SameBatchFragment;
import com.app.laqshya.studenttracker.activity.fragments.notifications.SingleStudentNotificationFragment;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.viewmodel.NavDrawerViewModel;
import com.app.laqshya.studenttracker.databinding.ActivityMainScreenDrawerBinding;
import com.app.laqshya.studenttracker.databinding.NavHeaderMainBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.StringTokenizer;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainScreenNavigationDrawer extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    static String CURRENT_TAG = Constants.TAG_HOME;
    ActivityMainScreenDrawerBinding activityMainScreenDrawerBinding;
    NavHeaderMainBinding navHeaderMainBinding;
    @Inject
    SessionManager sessionManager;
    @Inject
    RegistrationFactory registrationFactory;
    FragmentManager fragmentManager;
    int navItemIndex = 0;
    NavDrawerViewModel navDrawerViewModel;
    int lastSelectedBottomId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        activityMainScreenDrawerBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_screen_drawer);
        navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header_main,
                activityMainScreenDrawerBinding.navView, false);

        activityMainScreenDrawerBinding.navView.addHeaderView(navHeaderMainBinding.getRoot());
        setSupportActionBar(activityMainScreenDrawerBinding.appbarmain.toolbar);
        fragmentManager = getSupportFragmentManager();


        navDrawerViewModel = ViewModelProviders.of(this, registrationFactory).get(NavDrawerViewModel.class);
        chooseUser();
        loadNavHeader();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityMainScreenDrawerBinding.drawerLayout,
                activityMainScreenDrawerBinding.appbarmain.toolbar, R.string.opendrawer, R.string.closedrawer) {

        };
        activityMainScreenDrawerBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setUpnavigationitems();
        navDrawerViewModel.fragmentTitle.observe(this, s -> {
            if (s != null && !s.isEmpty())
                getSupportActionBar().setTitle(s);
        });
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = Constants.TAG_HOME;
            loadHomeFragment();
        }
        activityMainScreenDrawerBinding.appbarmain.bottomNavigationAdmin.setOnNavigationItemSelectedListener(this);


    }

    private void setUpnavigationitems() {
        if (activityMainScreenDrawerBinding.navView != null) {
            activityMainScreenDrawerBinding.navView.setNavigationItemSelectedListener(item ->
            {
                switch (sessionManager.getLoggedInType()) {
                    case Constants.ADMIN:
                        switch (item.getItemId()) {
                            case R.id.nav_home_admin:
                                navItemIndex = 0;
                                CURRENT_TAG = Constants.TAG_HOME;
                                break;
                            case R.id.add_consellor:
                                navItemIndex = 1;
                                CURRENT_TAG = Constants.TAG_ADD_COUNSELLOR;
                                break;
//
                            case R.id.add_faculty:
                                navItemIndex = 2;
                                CURRENT_TAG = Constants.TAG_ADD_FACULTY;
                                break;
                            case R.id.add_student:
                                navItemIndex = 3;
                                CURRENT_TAG = Constants.TAG_ADD_STUDENT;
                                break;
                            case R.id.add_syllabus:
                                navItemIndex = 4;
                                CURRENT_TAG=Constants.TAG_ADD_SYLLABUS;

                                break;
                            case R.id.managestudentfees:
                                navItemIndex = 5;
                                CURRENT_TAG = Constants.MANAGE_STUDENTS;
                                break;
                            case R.id.schedulebatches:
                                navItemIndex = 6;
                                CURRENT_TAG = Constants.SCHEDULE_BATCHES;
                                break;
                            case R.id.nav_notifications_admin:
                                navItemIndex = 7;
                                CURRENT_TAG = Constants.TAG_NOTIFICATIONS;
                                break;
                            case R.id.nav_broadcast_admin:
                                navItemIndex = 8;
                                CURRENT_TAG = Constants.TAG_BROADCAST;
                                break;
                            case R.id.about:
                                navItemIndex = 9;
                                CURRENT_TAG = Constants.TAGABOUT;
                                break;

                            case R.id.nav_privacy_policy_admin:
                                navItemIndex = 10;
                                CURRENT_TAG = Constants.TAG_PRIVACYPOLICY;
                                break;

                            default:
                                navItemIndex = 0;
                                break;
                        }
                        break;
                    case Constants.COUNSELLOR:
                        Timber.d("Counsellor %d", navItemIndex);
                        switch (item.getItemId()) {
                            case R.id.nav_home_officestaff:
                                navItemIndex = 0;
                                CURRENT_TAG = Constants.TAG_HOME;
                                break;
                            case R.id.nav_feesstatus_officestaff:
                                navItemIndex = 3;
                                CURRENT_TAG = Constants.TAG_FEESSTATUS;
                                break;

                            case R.id.nav_attendance_officestaff:
                                navItemIndex = 4;
                                CURRENT_TAG = Constants.TAG_ATTENDANCE;
                                break;
                            case R.id.completionbatches:
                                navItemIndex = 5;
                                CURRENT_TAG = Constants.TAG_BATCH;
                                break;
                            case R.id.deletebatches:
                                navItemIndex = 6;
                                CURRENT_TAG = Constants.TAG_DELETED;
                                break;
//                            case R.id.nav_notifications_officestaff:
//                                navItemIndex = 7;
//                                CURRENT_TAG = Constants.TAG_NOTIFICATIONS;
//                                break;

                            case R.id.nav_broadcastsms_officestaff:
                                navItemIndex = 8;
                                CURRENT_TAG = Constants.TAG_BROADCAST;
                                break;
                            case R.id.nav_settings_officestaff:
                                navItemIndex = 9;
                                CURRENT_TAG = Constants.TAG_ABOUT_DEVELOPERS;
                                break;
                            case R.id.add_faculty:
                                navItemIndex = 1;
                                CURRENT_TAG = Constants.TAG_ADD_FACULTY;
                                break;
                            case R.id.add_student:
                                navItemIndex = 2;
                                CURRENT_TAG = Constants.TAG_ADD_STUDENT;
                                break;


                            case R.id.nav_privacy_policy_officestaff:
                                navItemIndex = 9;
                                CURRENT_TAG = Constants.TAG_PRIVACYPOLICY;
                                break;
                            default:
                                navItemIndex = 0;
                                break;
                        }
                        break;
                    case Constants.FACULTY:
                        activityMainScreenDrawerBinding.appbarmain.fabAttendance.hide();
                        switch (item.getItemId()) {
                            case R.id.nav_home_faculty:
                                navItemIndex = 0;
                                CURRENT_TAG = Constants.TAG_HOME;
                                break;
                            case R.id.nav_attendance_faculty:
                                activityMainScreenDrawerBinding.appbarmain.fabAttendance.show();
                                navItemIndex = 1;
                                CURRENT_TAG = Constants.TAG_ATTENDANCE;
                                break;
//                            case R.id.nav_performance_faculty:
//                                navItemIndex = 2;
//                                CURRENT_TAG = Constants.TAG_PERFORMANCE;
//                                break;
                            case R.id.nav_notifications_faculty:
                                navItemIndex = 3;
                                CURRENT_TAG = Constants.TAG_NOTIFICATIONS;
                                break;
                            case R.id.nav_broadcast_faculty:
                                navItemIndex = 4;
                                CURRENT_TAG = Constants.TAG_BROADCAST;
                                break;
                            case R.id.nav_settings_faculty:
                                navItemIndex = 5;
                                CURRENT_TAG = Constants.TAG_ABOUT_DEVELOPERS;
                                break;
                            case R.id.nav_privacy_policy_faculty:
                                navItemIndex = 6;
                                CURRENT_TAG = Constants.TAG_PRIVACYPOLICY;
                                break;
                            default:
                                navItemIndex = 0;
                                break;
                        }
                        break;

                    case Constants.STUDENT:
                        switch (item.getItemId()) {
                            case R.id.nav_home_student:
                                navItemIndex = 0;
                                CURRENT_TAG = Constants.TAG_HOME;
                                break;

                            case R.id.nav_syllabus_student:
                                navItemIndex = 1;
                                CURRENT_TAG = Constants.TAG_SYLLABUS;
                                break;
                            case R.id.nav_notes:
                                navItemIndex=2;
                                CURRENT_TAG=Constants.TAG_NOTES;
                                break;
                            case R.id.nav_notifications_student:
                                navItemIndex = 3;
                                CURRENT_TAG = Constants.TAG_NOTIFICATIONS;
                                break;
                            case R.id.nav_refer_friend_student:
                                navItemIndex = 4;
                                CURRENT_TAG = Constants.TAG_refer_friend;
                                break;
                            case R.id.feedback:
                                navItemIndex = 5;
                                CURRENT_TAG = Constants.TAG_FEEDBACK;
                                break;
                            case R.id.payment:
                                navItemIndex = 6;
                                CURRENT_TAG = Constants.TAG_PAYMENT;
                                break;
                            case R.id.nav_contact_student:
                                navItemIndex = 7;
                                CURRENT_TAG = Constants.TAG_CONTACT;
                                break;
                            case R.id.nav_settings_student:
                                navItemIndex = 8;
                                CURRENT_TAG = Constants.TAG_ABOUT_DEVELOPERS;
                                break;
                            case R.id.nav_privacy_policy_student:
                                navItemIndex = 9;
                                CURRENT_TAG = Constants.TAG_PRIVACYPOLICY;
                                break;
                            default:
                                navItemIndex = 0;
                                break;
                        }
                        break;
                }
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                loadHomeFragment();
                return true;


            });

        }
    }

    private void loadNavHeader() {
        if (sessionManager.getLoggedInName() != null) {
            StringTokenizer stok = new StringTokenizer(sessionManager.getLoggedInName());
            String fname = null;
            if (stok.hasMoreTokens()) {
                fname = stok.nextToken();
            }

            Glide.with(this).load(Constants.urlProfileImg)
                    .thumbnail(0.5f).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                    .circleCrop().placeholder(android.R.drawable.sym_def_app_icon)).into(navHeaderMainBinding.imgProfileMainnav);

            navHeaderMainBinding.nameMainnav.setText(fname);
            navHeaderMainBinding.websiteMainnav.setText(R.string.website);
            Glide.with(this).load(Constants.urlNavHeaderBg)
                    .thumbnail(0.5f).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(android.R.drawable.sym_def_app_icon)).into(navHeaderMainBinding.imgHeaderBgMainnav);


        }
    }

    private void selectNavMenu() {
        if (activityMainScreenDrawerBinding.navView != null)
            activityMainScreenDrawerBinding.navView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void loadHomeFragment() {
        selectNavMenu();
        if (fragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            activityMainScreenDrawerBinding.drawerLayout.closeDrawers();
//            toggleFab();

        }
        Runnable pendingRunnable = () -> {
            Fragment fragment = null;
            switch (sessionManager.getLoggedInType()) {
                case Constants.ADMIN:

                    fragment = navDrawerViewModel.getAdminFragment(navItemIndex);
//                    fragment=new HomeFragmentAdmin();


                    break;
                case "counsellor":
//                    Timber.d("Index %d", navItemIndex);
                    fragment = navDrawerViewModel.getCounsellorFragment(navItemIndex);

                    break;
                case "student":
                    fragment = navDrawerViewModel.getStudentFragment(navItemIndex);
//
                    break;
                case "faculty":
                    fragment = navDrawerViewModel.getFacultyFragment(navItemIndex);
//                    fr
                    break;


            }

            if (fragment != null) {
                String name = fragment.getClass().getName();
                boolean fragPopped = fragmentManager.popBackStackImmediate(name, 0);
                if (!fragPopped && fragmentManager.findFragmentByTag(CURRENT_TAG) == null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                            android.R.animator.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG).addToBackStack(name);
//TODO fix saveinstancestate action error.
                    fragmentTransaction.commit();
                }
            }


        };
        Handler handler = new Handler();
        handler.post(pendingRunnable);
//        toggleFab();
        activityMainScreenDrawerBinding.drawerLayout.closeDrawers();
        invalidateOptionsMenu();
    }

    private void chooseUser() {
        Resources resources = getResources();
        if (sessionManager.getLoggedInType() != null) {
            switch (sessionManager.getLoggedInType()) {
                case Constants.ADMIN:

                    setUpNav(R.menu.menu_admin, resources.getStringArray(R.array.nav_item_admin_activity_titles));
                    break;
                case "counsellor":

                    setUpNav(R.menu.menu_counsellor, resources.getStringArray(R.array.nav_item_officestaff_activity_titles));
                    break;
                case "faculty":

                    setUpNav(R.menu.menu_faculty, resources.getStringArray(R.array.nav_item_faculty_activity_titles));
                    break;
                case "student":

                    setUpNav(R.menu.menu_student, resources.getStringArray(R.array.nav_item_student_activity_titles));

            }
        }
    }

    private void setUpNav(int id, String arr[]) {
        activityMainScreenDrawerBinding.navView.getMenu().clear();
        activityMainScreenDrawerBinding.navView.inflateMenu(id);


    }

    //TODO fix back button on navigation view.
    @Override
    public void onBackPressed() {
        if (activityMainScreenDrawerBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityMainScreenDrawerBinding.drawerLayout.closeDrawers();
        }
        if (lastSelectedBottomId == R.id.pending_admin || lastSelectedBottomId == R.id.sameBatch || lastSelectedBottomId == R.id.singleStudent
                || lastSelectedBottomId == R.id.allStudents) {
            fragmentManager.getFragments().clear();
            loadHomeFragment();
            lastSelectedBottomId = 0;
        } else if (fragmentManager.getBackStackEntryCount() > 1) {
            super.onBackPressed();
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Exit??Are you sure????");
            builder.setTitle("Exit?");
            builder.setPositiveButton("Okay", ((dialog, which) -> {
                finish();
            }));
            builder.setNegativeButton("No", ((dialog, which) -> {
                dialog.dismiss();
            }));
            builder.create().show();

        }
        if (navItemIndex != 0) {
            navItemIndex = 0;
            CURRENT_TAG = Constants.TAG_HOME;
            loadHomeFragment();
            super.onBackPressed();


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (lastSelectedBottomId == menuItem.getItemId()) {
            return true;

        }
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.singleStudent:
                fragment = new SingleStudentNotificationFragment();
                lastSelectedBottomId = R.id.singleStudent;
                break;

            case R.id.sameBatch:

                fragment = new SameBatchFragment();
                lastSelectedBottomId = R.id.sameBatch;
                break;
            case R.id.pending_admin:
                fragment = new PendingFragment();
                lastSelectedBottomId = R.id.pending_admin;
                break;
            case R.id.allStudents:
                fragment = new AllStudentsAllCentresFragment();
                lastSelectedBottomId = R.id.allStudents;
                break;


        }
        if (fragment != null) {
            if (fragmentManager != null) {
                fragmentManager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();
            }
        }
        return true;
    }
}

