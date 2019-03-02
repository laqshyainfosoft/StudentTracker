package com.app.laqshya.studenttracker.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.listeners.OnBookDeleteListener;
import com.app.laqshya.studenttracker.activity.model.PDFDoc;
import com.app.laqshya.studenttracker.databinding.UploadSyllabusItemBinding;

import java.util.List;

public class AddSyllabusAdapter extends RecyclerView.Adapter<AddSyllabusAdapter.CustomHolder> {


    private PDFDoc.PDFList pdfDocList;
    private OnBookDeleteListener onBookDeleteListener;

    public AddSyllabusAdapter(OnBookDeleteListener onBookDeleteListener) {
        this.onBookDeleteListener = onBookDeleteListener;
    }

    @android.support.annotation.NonNull
    @Override
    public CustomHolder onCreateViewHolder(@android.support.annotation.NonNull ViewGroup viewGroup, int i) {
        UploadSyllabusItemBinding uploadSyllabusItemBinding=UploadSyllabusItemBinding.inflate(LayoutInflater.from(
                viewGroup.getContext()
        ),viewGroup,false);



//        UploadSyllabusItemBinding uploadSyllabusItemBinding;
        return new CustomHolder(uploadSyllabusItemBinding);
    }

    public void setPdfDocList(List<PDFDoc> pdfDocListitem) {

        pdfDocList=new PDFDoc.PDFList(pdfDocListitem);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull CustomHolder customHolder, int i) {
        customHolder.bind(pdfDocList.getPdfDocList().get(i));


    }


    @Override
    public int getItemCount() {
        if(pdfDocList ==null || pdfDocList.getThrowable()!=null)
        return 0;
        else {
            return pdfDocList.getPdfDocList().size();
        }
    }


    public class CustomHolder extends RecyclerView.ViewHolder {
        UploadSyllabusItemBinding uploadSyllabusItemBinding;
        public CustomHolder(UploadSyllabusItemBinding uploadSyllabusItemBinding) {
            super(uploadSyllabusItemBinding.getRoot());
            this.uploadSyllabusItemBinding=uploadSyllabusItemBinding;
        }
        void bind(PDFDoc pdfDoc){
            uploadSyllabusItemBinding.setPdf(pdfDoc);
            uploadSyllabusItemBinding.executePendingBindings();
            uploadSyllabusItemBinding.deleteSyllabusFilesBtn.setOnClickListener(v -> onBookDeleteListener.deleteItem(getAdapterPosition()));


        }
    }
}
