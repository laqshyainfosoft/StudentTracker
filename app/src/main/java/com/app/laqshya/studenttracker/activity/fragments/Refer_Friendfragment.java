package com.app.laqshya.studenttracker.activity.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.app.laqshya.studenttracker.R;

public class Refer_Friendfragment extends Fragment implements View.OnClickListener {

    ImageView mWhatsappImg, mFacebookImg, mGmailImg;
    Button mWhatsapp, mFacebook, mGmail;
    Intent mSocialMediaIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_refer_friend, container, false);

        //binding social media icons
        mWhatsappImg = rootview.findViewById(R.id.whatsapp_icon);
        mFacebookImg = rootview.findViewById(R.id.fb_icon);
        mGmailImg = rootview.findViewById(R.id.gmail_icon);

        //Binding social media buttons
        mWhatsapp = rootview.findViewById(R.id.whatsapp_reference);
        mFacebook = rootview.findViewById(R.id.fb_reference);
        mGmail = rootview.findViewById(R.id.gmail_reference);

        //Calling event listeners for social media icons
        mWhatsappImg.setOnClickListener(this);
        mFacebookImg.setOnClickListener(this);
        mGmailImg.setOnClickListener(this);

        //Calling event listeners for social media buttons
        mWhatsapp.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mGmail.setOnClickListener(this);

        return rootview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.whatsapp_icon:
                callWhatsappFunc();
                break;
            case R.id.whatsapp_reference:
                callWhatsappFunc();
                break;
            case R.id.fb_icon:
                callFacebookFunc();
                break;
            case R.id.fb_reference:
                callFacebookFunc();
                break;
            case R.id.gmail_icon:
                callGmailFunc();
                break;
            case R.id.gmail_reference:
                callGmailFunc();
                break;
        }
    }

    public void callWhatsappFunc() {
        mSocialMediaIntent = new Intent(Intent.ACTION_SEND);
        mSocialMediaIntent.putExtra(Intent.EXTRA_TEXT, "Laqshya Infosoft Solutions....Please feel free to contact to know more about US" +
                "\n" + "http://www.laqshyainfosoft.com/");
        mSocialMediaIntent.setType("text/plain");
        mSocialMediaIntent.setPackage("com.whatsapp");
        startActivity(mSocialMediaIntent);
    }

    public void callFacebookFunc() {
        mSocialMediaIntent = new Intent("android.intent.action.VIEW");
        mSocialMediaIntent.setData(Uri.parse("https://www.facebook.com/Laqshya-Infosoft-Solutions-1789618061277540/?fref=ts"));
        startActivity(mSocialMediaIntent);
    }

    @SuppressLint("IntentReset")
    public void callGmailFunc() {
        mSocialMediaIntent = new Intent("android.intent.action.SEND");
        mSocialMediaIntent.setData(Uri.parse(":mailto"));
        mSocialMediaIntent.setType("message/rfc822");
        mSocialMediaIntent.putExtra("android.intent.extra.EMAIL", new String[]{"info@laqshyainfosoft.com"});
        startActivity(Intent.createChooser(mSocialMediaIntent, "Mail us"));
    }
}
