package com.app.laqshya.studenttracker.activity.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.laqshya.studenttracker.R;
import com.app.laqshya.studenttracker.activity.utils.Constants;
import com.app.laqshya.studenttracker.activity.utils.Utils;
import com.app.laqshya.studenttracker.databinding.FragmentPdfBinding;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

import timber.log.Timber;

public class ReadPDFFragment extends Fragment implements OnLoadCompleteListener,OnPageErrorListener {
    FragmentPdfBinding fragmentPdfBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentPdfBinding=FragmentPdfBinding.inflate(inflater,container,false);
        return fragmentPdfBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String path=getArguments().getString("path");
        readPDF(path);

    }

    private void readPDF(String path) {
        path=Constants.PDF_SITE_URL+path;
        if(getActivity()!=null && Utils.isNetworkConnected(getActivity())) {
            fragmentPdfBinding.pdfViewProgressBar.setVisibility(View.VISIBLE);
            fragmentPdfBinding.pdfViewProgressBar.setIndeterminate(true);
            FileLoader.with(getActivity())
                    .load(path, true) //2nd parameter is optioal, pass true to force load from network
                    .asFile(new FileRequestListener<File>() {
                        @Override
                        public void onLoad(FileLoadRequest request, FileResponse<File> response) {
//                        pdfViewProgressBar.setVisibility(View.GONE);
                            File pdfFile = response.getBody();
                            try {
                                fragmentPdfBinding.pdfView.fromFile(pdfFile)
                                        .defaultPage(1)
                                        .enableAnnotationRendering(true)
                                        .onLoad(ReadPDFFragment.this)
                                        .scrollHandle(new DefaultScrollHandle(getActivity()))
                                        .swipeHorizontal(true)

                                        .pageSnap(true)
                                        .autoSpacing(true)
                                        .pageFling(true)
                                        .spacing(10) // in dp
                                        .onPageError(ReadPDFFragment.this)
                                        .load();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(FileLoadRequest request, Throwable t) {
                            Timber.d(t);
                            showSnackBar("Failed to load file.");
                            fragmentPdfBinding.pdfViewProgressBar.setVisibility(View.GONE);
//                        pdfViewProgressBar.setVisibility(View.GONE);
//                        Toast.makeText(PDFActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
        else {
            showSnackBar(getString(R.string.internet_connection));

        }
    }
    private void showSnackBar(String snackMessage) {
        Snackbar snackbar = Snackbar.make(fragmentPdfBinding.rootView,
                snackMessage, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }


    @Override
    public void loadComplete(int nbPages) {
        fragmentPdfBinding.pdfViewProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onPageError(int page, Throwable t) {
        fragmentPdfBinding.pdfViewProgressBar.setVisibility(View.GONE);

    }
}
