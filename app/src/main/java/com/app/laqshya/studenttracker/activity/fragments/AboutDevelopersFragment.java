package com.app.laqshya.studenttracker.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;

public class AboutDevelopersFragment extends Fragment {

    LinearLayout facebook, whatsapp;
    TextView mail, website;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.about_developers, container, false);
        mail = rootview.findViewById(R.id.email);
        website = rootview.findViewById(R.id.website);
        facebook = rootview.findViewById(R.id.facebook);
        whatsapp = rootview.findViewById(R.id.whatsapp);
        mail.setOnClickListener(view -> {
            Toast.makeText(getActivity(), "Please wait...", Toast.LENGTH_SHORT).show();
            Intent localIntent4 = new Intent("android.intent.action.SEND");
            localIntent4.setData(Uri.parse(":mailto"));
            localIntent4.setType("message/rfc822");
            localIntent4.putExtra("android.intent.extra.EMAIL", new String[]{"info@laqshyainfosoft.com"});
            startActivity(Intent.createChooser(localIntent4, "Mail us"));
        });

        website.setOnClickListener(view -> {
            Intent localIntent2 = new Intent("android.intent.action.VIEW");
            localIntent2.setData(Uri.parse("http://www.laqshyainfosoft.com"));
            startActivity(localIntent2);
        });

        facebook.setOnClickListener(view -> {
            Intent localIntent3 = new Intent("android.intent.action.VIEW");
            localIntent3.setData(Uri.parse("https://www.facebook.com/Laqshya-Infosoft-Solutions-1789618061277540/?fref=ts"));
            startActivity(localIntent3);
        });

        whatsapp.setOnClickListener(view -> {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Laqshya Infosoft Solutions....Please feel free to contact to know more about US" +
                    "\n" + "http://www.laqshyainfosoft.com/");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        });


        return rootview;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


}

