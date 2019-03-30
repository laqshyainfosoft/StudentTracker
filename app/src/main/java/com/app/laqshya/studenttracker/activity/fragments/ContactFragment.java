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

public class ContactFragment extends Fragment implements View.OnClickListener {

    TextView mMobileHead, mMobileNo, mMailHead, mMailId, mFacebookHead, mFacebook, mWhatsappHead, mWhatsapp, mAddressHead, mAddress;
    Intent sendIntent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_contact, container, false);

        //Binding views
        mMobileHead = rootview.findViewById(R.id.mobile_title);
        mMobileNo = rootview.findViewById(R.id.mobile_no);
//        mTelephoneHead = rootview.findViewById(R.id.telephone_header);
//        mTelephoneNo = rootview.findViewById(R.id.telephone_no);
        mMailHead = rootview.findViewById(R.id.mail_header);
        mMailId = rootview.findViewById(R.id.mail_id);
        mFacebookHead = rootview.findViewById(R.id.fb_header);
        mFacebook = rootview.findViewById(R.id.fb_follow);
        mWhatsappHead = rootview.findViewById(R.id.whatsapp_header);
        mWhatsapp = rootview.findViewById(R.id.whatsapp_follow);
        mAddressHead = rootview.findViewById(R.id.address_header);
        mAddress = rootview.findViewById(R.id.address);

        //Setting the onclicklistener
        mMobileHead.setOnClickListener(this);
        mMobileNo.setOnClickListener(this);
//        mTelephoneHead.setOnClickListener(this);
//        mTelephoneNo.setOnClickListener(this);
        mMailHead.setOnClickListener(this);
        mMailId.setOnClickListener(this);
        mFacebookHead.setOnClickListener(this);
        mFacebook.setOnClickListener(this);
        mWhatsappHead.setOnClickListener(this);
        mWhatsapp.setOnClickListener(this);
        mAddressHead.setOnClickListener(this);
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
            case R.id.mobile_title:
                mobileFunc();
                break;
            case R.id.mobile_no:
                mobileFunc();
                break;
//            case R.id.telephone_header:
//                telephoneFunc();
//                break;
//            case R.id.telephone_no:
//                telephoneFunc();
//                break;
            case R.id.mail_header:
                mailFunc();
                break;
            case R.id.mail_id:
                mailFunc();
                break;
            case R.id.fb_header:
                facebookFunc();
                break;
            case R.id.fb_follow:
                facebookFunc();
                break;
            case R.id.whatsapp_header:
                whatsappFunc();
                break;
            case R.id.whatsapp_follow:
                whatsappFunc();
                break;
            case R.id.address_header:
                addressFunc();
                break;
            case R.id.address:
                addressFunc();
                break;
        }
    }

    public void mobileFunc() {
        sendIntent = new Intent(Intent.ACTION_DIAL);
        sendIntent.setData(Uri.parse("tel:" + "986772552"));
        startActivity(sendIntent);
    }

//    public void telephoneFunc() {
//        sendIntent = new Intent(Intent.ACTION_DIAL);
//        sendIntent.setData(Uri.parse("tel:" + "1234567890"));
//    }

    @SuppressLint("IntentReset")
    public void mailFunc() {
        Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
        sendIntent = new Intent("android.intent.action.SEND");
        sendIntent.setDataAndType(Uri.parse(":mailto"),"message/rfc822");
        sendIntent.putExtra("android.intent.extra.EMAIL", new String[]{"lalaqshya@gmail.com"});
        startActivity(Intent.createChooser(sendIntent, "Mail us"));
    }

    public void facebookFunc() {
        sendIntent = new Intent("android.intent.action.VIEW");
        sendIntent.setData(Uri.parse("https://www.facebook.com/Laqshya-Infosoft-Solutions-1789618061277540/?fref=ts"));
        startActivity(sendIntent);
    }

    public void whatsappFunc() {
        sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Laqshya Infosoft Solutions....Please feel free to contact to know more about US" +
                "\n" + "http://www.laqshyainfosoft.com/");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);
    }

    public void addressFunc() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:19.1205157,72.8447169"));
        startActivity(intent);
    }
}
