package com.app.laqshya.studenttracker.activity.fragments;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.databinding.FragmentPaymentBinding;

public class PrivacyPolicyFragment extends Fragment {

    FragmentPaymentBinding fragmentPaymentBinding;

    ProgressBar progressBar;


    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        fragmentPaymentBinding.webview.loadUrl(Constants.WEBSITE_URL);
        fragmentPaymentBinding.webview.setWebViewClient(new PrivacyPolicyFragment.myWebClient());
        progressBar = fragmentPaymentBinding.progressBar;
        WebSettings webSettings = fragmentPaymentBinding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        return fragmentPaymentBinding.getRoot();
    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            progressBar.setVisibility(View.GONE);
        }
    }
}
