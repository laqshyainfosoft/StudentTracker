package com.app.laqshya.studenttracker.activity.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;

    public SessionManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences=sharedPreferences;
    }
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(Constants.isLoggedIn, false);

    }
    public String getLoggedInName() {
        return sharedPreferences.getString(Constants.NAME,"");
    }
    public String getLoggedInUserName() {
        return sharedPreferences.getString(Constants.USER_NAME, "");
    }
    public String getLoggedInType(){
        return sharedPreferences.getString(Constants.USER_TYPE,"");

    }
    public String getLogggedInPhone(){
        return sharedPreferences.getString(Constants.Phone,"");
    }
    public String getLoggedInuserCenter(){
        return sharedPreferences.getString(Constants.USER_CENTER,"");
    }
    public void saveUser(String username){
        sharedPreferences.edit().putString(Constants.USER_NAME,username).apply();
    }
    public void saveName(String name){
        sharedPreferences.edit().putString(Constants.NAME,name).apply();
    }
    public void saveType(String type){
        sharedPreferences.edit().putString(Constants.USER_TYPE,type).apply();
    }
    public void saveUserCenter(String center){
        sharedPreferences.edit().putString(Constants.USER_CENTER,center).apply();
    }
    public void saveLoggedInState(boolean isLoggedIn){
        sharedPreferences.edit().putBoolean(Constants.isLoggedIn,isLoggedIn).apply();
    }

}
