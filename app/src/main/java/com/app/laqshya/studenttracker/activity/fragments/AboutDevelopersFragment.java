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
import android.widget.TextView;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;

public class AboutDevelopersFragment extends Fragment implements View.OnClickListener {

    TextView mEmailTitle, mEmailId, mMobileTitle, mMobileNo, mWhatsappTitle, mWhatsappNo,
            mFacebookTitle, mFacebook, mWebsiteTitle, mWebsiteLink, mAddressTitle, mAddress;

    Intent mCallIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.about_developers, container, false);

        //Binding the views
        mEmailTitle = rootview.findViewById(R.id.email_title);
        mEmailId = rootview.findViewById(R.id.email_id);
        mMobileTitle = rootview.findViewById(R.id.mobile_title);
        mMobileNo = rootview.findViewById(R.id.mobile_no);
        mWhatsappTitle = rootview.findViewById(R.id.whatsapp_title);
        mWhatsappNo = rootview.findViewById(R.id.whatsapp_no);
        mFacebookTitle = rootview.findViewById(R.id.facebook_title);
        mFacebook = rootview.findViewById(R.id.facebook_id);
        mWebsiteTitle = rootview.findViewById(R.id.website_title);
        mWebsiteLink = rootview.findViewById(R.id.website_link);
        mAddressTitle = rootview.findViewById(R.id.address_title);
        mAddress = rootview.findViewById(R.id.address);

        //Setting onClick listener
        mEmailTitle.setOnClickListener(this);
        mEmailId.setOnClickListener(this);
        mMobileTitle.setOnClickListener(this);
        mMobileNo.setOnClickListener(this);
        mWhatsappTitle.setOnClickListener(this);
        mWhatsappNo.setOnClickListener(this);
        mFacebookTitle.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mWebsiteTitle.setOnClickListener(this);
        mWebsiteLink.setOnClickListener(this);
        mAddressTitle.setOnClickListener(this);
        mAddress.setOnClickListener(this);

        return rootview;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_title:
                emailFunc();
                break;
            case R.id.email_id:
                emailFunc();
                break;
            case R.id.mobile_title:
                callFunc();
                break;
            case R.id.mobile_no:
                callFunc();
                break;
            case R.id.whatsapp_title:
                whatsappFunc();
                break;
            case R.id.whatsapp_no:
                whatsappFunc();
                break;
            case R.id.facebook_title:
                facebookFunc();
                break;
            case R.id.facebook_id:
                facebookFunc();
                break;
            case R.id.website_title:
                websiteFunc();
                break;
            case R.id.website_link:
                websiteFunc();
                break;
            case R.id.address_title:
                addressFunc();
                break;
            case R.id.address:
                addressFunc();
                break;
        }
    }

    @SuppressLint("IntentReset")
    public void emailFunc() {
        Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
        mCallIntent = new Intent("android.intent.action.SEND");
        mCallIntent.setData(Uri.parse(":mailto"));
        mCallIntent.setType("message/rfc822");
        mCallIntent.putExtra("android.intent.extra.EMAIL", new String[]{"lalaqshya@gmail.com"});
        startActivity(Intent.createChooser(mCallIntent, "Mail us"));
    }

    public void callFunc() {
        mCallIntent = new Intent(Intent.ACTION_DIAL);
        mCallIntent.setData(Uri.parse("tel:" + "1234567890"));
    }

    public void whatsappFunc() {
        mCallIntent = new Intent(Intent.ACTION_SEND);
        mCallIntent.putExtra(Intent.EXTRA_TEXT, "Laqshya Infosoft Solutions....Please feel free to contact to know more about US" +
                "\n" + "http://www.laqshyainfosoft.com/");
        mCallIntent.setType("text/plain");
        mCallIntent.setPackage("com.whatsapp");
        startActivity(mCallIntent);
    }

    public void facebookFunc() {
        mCallIntent = new Intent("android.intent.action.VIEW");
        mCallIntent.setData(Uri.parse("https://www.facebook.com/Laqshya-Infosoft-Solutions-1789618061277540/?fref=ts"));
        startActivity(mCallIntent);
    }

    public void websiteFunc() {
        mCallIntent = new Intent("android.intent.action.VIEW");
        mCallIntent.setData(Uri.parse("http://www.laqshyainfosoft.com"));
        startActivity(mCallIntent);
    }

    public void addressFunc() {
        mCallIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:19.1205157,72.8447169"));
        startActivity(mCallIntent);
    }
}

