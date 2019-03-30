package com.app.laqshya.studenttracker.activity;

import android.app.ProgressDialog;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.factory.LoginFactory;
import com.app.laqshya.studenttracker.activity.service.EduTrackerService;
import com.app.laqshya.studenttracker.activity.utils.SessionManager;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.activity.viewmodel.LoginViewModel;
import com.app.laqshya.studenttracker.databinding.ActivityMainBinding;
import com.app.laqshya.studenttracker.databinding.LoginActivityBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Inject
    SessionManager sessionManager;
    @Inject
    EduTrackerService eduTrackerService;
    @Inject
    LoginFactory loginFactory;
    ActivityMainBinding activityMainBinding;
    LoginActivityBinding loginActivityBinding;
    LoginViewModel loginVieModel;

    private AlertDialog.Builder dialog;
    private int flag = 0;
    private AlertDialog alertDialog;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        Timber.d("%s", "I was called");
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        LayoutInflater inflate = getLayoutInflater();
        loginActivityBinding = DataBindingUtil.inflate(inflate, R.layout.login_activity, null, false);
        loginVieModel = ViewModelProviders.of(this, loginFactory).get(LoginViewModel.class);
        activityMainBinding.setLoginmodel(loginVieModel);
        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setCancelable(true);
        dialog.setView(loginActivityBinding.getRoot());
        activityMainBinding.counsellorlayout.setOnClickListener(this);
        activityMainBinding.llAdmin.setOnClickListener(this);
        activityMainBinding.llFaculty.setOnClickListener(this);
        activityMainBinding.llStudent.setOnClickListener(this);
        loginActivityBinding.etPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (!Utils.isValidPassword(loginActivityBinding.etPassword.getText().toString())) {
                loginActivityBinding.password.setError(getString(R.string.login_error));
            } else {
                loginActivityBinding.password.setError(null);
            }

        });
        loginActivityBinding.etUsername.setOnFocusChangeListener((v, hasFocus) -> {
            if (!Utils.isValidPassword(loginActivityBinding.etUsername.getText().toString())) {
                loginActivityBinding.username.setError(getString(R.string.login_error));

            } else {
                loginActivityBinding.username.setError(null);
            }

        });
        loginActivityBinding.etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().isEmpty()) {
                    loginActivityBinding.password.setError(null);
                } else {
                    loginActivityBinding.password.setError(getString(R.string.login_error));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        loginActivityBinding.etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !s.toString().isEmpty()) {
                    loginActivityBinding.username.setError(null);
                } else {
                    loginActivityBinding.username.setError(getString(R.string.login_error));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.setPositiveButton("Login", (dialog, which) -> {
            String username = loginActivityBinding.etUsername.getText().toString();
            String password = loginActivityBinding.etPassword.getText().toString();
            if (!username.isEmpty() && !password.isEmpty()) {

                if (Utils.isValidPassword(password) && Utils.isValidPhone(username)) {
                    progressDialog=ProgressDialog.show(MainActivity.this,"Logging in","Please wait");
                    showProgressDialog();


                    loginVieModel.loginUser(username, password, flag).observe(this,

                            integer -> {
                        hidedialog();
                        if(integer!=null)
                        if(integer==1){
                            startIntenttoHome();
                        }
                        else {
                            Toast.makeText(this, "Failed to Login", Toast.LENGTH_SHORT).show();
                        }

                            });
                } else {
                    Toast.makeText(this, getString(R.string.invalid_details), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
            }


        });
        playAnimation();


    }



    private void playAnimation() {
        long delayBetweenAnimations = 1000L;
        View[] views = new View[]{activityMainBinding.llLogo, activityMainBinding.llStudent, activityMainBinding.llFaculty,
                activityMainBinding.counsellorlayout, activityMainBinding.llAdmin};
        for (int i = 0; i < views.length; i++) {
            final View view = views[i];
            long delay = i * delayBetweenAnimations;
            view.postDelayed(() -> {
                view.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                view.startAnimation(animation);

            }, delay);
        }
    }

    private void prepareDialog() {
        if (alertDialog == null) {
            alertDialog = dialog.create();
        }
        if (!alertDialog.isShowing()) {
            showAlertDialog("Login");
        }
        resetView();
    }

    public void showAlertDialog(String body) {
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.setMessage(body);
            alertDialog.show();
        }

    }

    private void resetView() {
        loginActivityBinding.etPassword.setText("");
        loginActivityBinding.etUsername.setText("");

    }


    @Override
    public void onClick(View v) {
        prepareDialog();
        switch (v.getId()) {
            case R.id.counsellorlayout:
                flag = 2;
                break;
            case R.id.ll_Admin:
                flag = 1;
                break;
            case R.id.ll_faculty:
                flag = 3;
                break;
            case R.id.ll_student:
                flag = 4;
                break;
        }

    }

    private void checkLoggedin() {
        if (sessionManager.isLoggedIn()) {
            startIntenttoHome();
            finish();
        }

    }

    @Override
    protected void onResume() {
        checkLoggedin();
        super.onResume();
    }

    public void startIntenttoHome() {
        Intent intent = new Intent(MainActivity.this, MainScreenNavigationDrawer.class);
        startActivity(intent);

    }
    private void showProgressDialog(){
        if(progressDialog!=null && !progressDialog.isShowing()){
            progressDialog.show();
        }
    }
    private void hidedialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}

