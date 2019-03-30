package com.app.laqshya.studenttracker.activity.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.app.laqshya.studenttracker.activity.model.Installments;
import com.app.laqshya.studenttracker.activity.model.student_self.StudentDetailsModel;
import com.app.laqshya.studenttracker.databinding.FeesInstallmentsItemBinding;

import java.util.List;


public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.CustomHolder> {
    private List<Installments> installmentsList;
//    private InstallmentsList installmentsList;
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FeesInstallmentsItemBinding feesInstallmentsItemBinding=FeesInstallmentsItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup,false);
        return new CustomHolder(feesInstallmentsItemBinding);
    }





    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
        customHolder.bind(installmentsList.get(i));

    }

    public void setInstallmentsList(List<Installments> installmentsList) {
        this.installmentsList = installmentsList;
    }

    @Override
    public int getItemCount() {
        if(installmentsList!=null){
            return installmentsList.size();
        }
        else {
            return 0;
        }

    }

    public class CustomHolder extends RecyclerView.ViewHolder {
        FeesInstallmentsItemBinding feesInstallmentsItemBinding;
        public CustomHolder(@NonNull FeesInstallmentsItemBinding feesInstallmentsItemBinding) {
            super(feesInstallmentsItemBinding.getRoot());
            this.feesInstallmentsItemBinding=feesInstallmentsItemBinding;
        }
        private void bind(Installments installments){
            feesInstallmentsItemBinding.setInstallments(installments);
            feesInstallmentsItemBinding.executePendingBindings();

//            feesInstallmentsItemBinding.exe
///

        }
    }
}
