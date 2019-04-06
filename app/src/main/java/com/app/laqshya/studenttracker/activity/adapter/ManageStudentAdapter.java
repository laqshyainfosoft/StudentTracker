package com.app.laqshya.studenttracker.activity.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.app.laqshya.studenttracker.activity.listeners.OnActionClickForStudentsListener;
import com.app.laqshya.studenttracker.activity.model.student_self.ManageStudentInfoResponse;
import com.app.laqshya.studenttracker.databinding.ManageBatchesBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

public class ManageStudentAdapter extends RecyclerView.Adapter<ManageStudentAdapter.CustomHolder> implements Filterable {
    private List<ManageStudentInfoResponse.ManageStudentInfo> manageStudentInfos;
    private List<ManageStudentInfoResponse.ManageStudentInfo> manageStudentInfosFiltered;
    private OnActionClickForStudentsListener onActionClickForStudentsListener;

    public ManageStudentAdapter(OnActionClickForStudentsListener onActionClickForStudentsListener) {
        manageStudentInfos = new ArrayList<>();
        this.onActionClickForStudentsListener = onActionClickForStudentsListener;
        manageStudentInfosFiltered = new ArrayList<>();
    }

    public void setManageStudentInfos(List<ManageStudentInfoResponse.ManageStudentInfo> manageStudentInfos) {
        this.manageStudentInfos = manageStudentInfos;
        manageStudentInfosFiltered = manageStudentInfos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManageBatchesBinding manageBatchesBinding = ManageBatchesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CustomHolder(manageBatchesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(manageStudentInfosFiltered.get(position));

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence message) {
                String charString = message.toString().toLowerCase();
                if (charString.isEmpty()) {
                    manageStudentInfosFiltered = manageStudentInfos;
                } else {
                    List<ManageStudentInfoResponse.ManageStudentInfo> filteredList = new ArrayList<>();
                    for (ManageStudentInfoResponse.ManageStudentInfo row : manageStudentInfos) {
//                        Timber.d(row.getMessage());
//                        Timber.d(message.toString());
//                        Timber.d(String.valueOf("test message for fees 1000000 please donate for MacBook pro wireless air".contains(message)));
//                        Timber.d(String.valueOf(row.getMessage().toLowerCase().contains(charString)));


                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getStudent_name().toLowerCase().contains(charString)) {
                            filteredList.add(row);
                            Timber.d("Message %s %s", row.getStudent_name(), message);
                        }
                    }

                    manageStudentInfosFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = manageStudentInfosFiltered;


                return filterResults;


            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                manageStudentInfosFiltered = (List<ManageStudentInfoResponse.ManageStudentInfo>) results.values;
                notifyDataSetChanged();

            }
        };
    }

    @Override
    public int getItemCount() {
        return manageStudentInfosFiltered == null ? 0 : manageStudentInfosFiltered.size();
    }

    public class CustomHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {

        ManageBatchesBinding manageBatchesBinding;

        public CustomHolder(@NonNull ManageBatchesBinding manageBatchesBinding) {
            super(manageBatchesBinding.getRoot());
            this.manageBatchesBinding = manageBatchesBinding;

        }

        protected void bind(ManageStudentInfoResponse.ManageStudentInfo manageStudentInfoResponse) {
            manageBatchesBinding.setStudent(manageStudentInfoResponse);
            manageBatchesBinding.executePendingBindings();
            manageBatchesBinding.emailId.setOnClickListener(v -> {
                onActionClickForStudentsListener.sendEmail(manageBatchesBinding.emailId.getText().toString());

            });
            manageBatchesBinding.mobileNo.setOnClickListener(v -> {
                onActionClickForStudentsListener.call(manageBatchesBinding.mobileNo.getText().toString());

            });


        }
    }

}
