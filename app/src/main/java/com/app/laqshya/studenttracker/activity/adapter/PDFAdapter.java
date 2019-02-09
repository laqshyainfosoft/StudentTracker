package com.app.laqshya.studenttracker.activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.databinding.PdfmodelBinding;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.CustomHolder> {
    private List<PDFDoc> pdfDocList;

    public void setPdfDocList(List<PDFDoc> pdfDocList) {
        this.pdfDocList = pdfDocList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        PdfmodelBinding pdfmodelBinding=PdfmodelBinding.inflate(LayoutInflater.from(viewGroup.getContext()),viewGroup,false);
        return new CustomHolder(pdfmodelBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
            customHolder.bind(pdfDocList.get(i));

    }

    @Override
    public int getItemCount() {
        if(pdfDocList==null ){
            return 0;
        }
        else {
           return pdfDocList.size();
        }

    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        PdfmodelBinding pdfmodelBinding;
        public CustomHolder(@NonNull PdfmodelBinding pdfmodelBinding) {
            super(pdfmodelBinding.getRoot());
            this.pdfmodelBinding=pdfmodelBinding;
        }
        void  bind(PDFDoc pdfDoc){
            pdfmodelBinding.setPdf(pdfDoc);
            pdfmodelBinding.executePendingBindings();

        }
    }
}
