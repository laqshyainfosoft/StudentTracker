package com.app.laqshya.studenttracker.activity.fragments;

import android.annotation.SuppressLint;
import androidx.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.databinding.FragmentPaymentBinding;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class PaymentFragment extends Fragment {
    FragmentPaymentBinding fragmentPaymentBinding;

    ProgressBar progressBar;


    @SuppressLint("SetJavaScriptEnabled")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPaymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        fragmentPaymentBinding.webview.loadUrl(Constants.PAYMENT_URL);
        fragmentPaymentBinding.webview.setWebViewClient(new myWebClient());
        progressBar=fragmentPaymentBinding.progressBar;
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

    // To handle "Back" key press event for WebView to go back to previous screen.
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)
//    {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
//            web.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//}
