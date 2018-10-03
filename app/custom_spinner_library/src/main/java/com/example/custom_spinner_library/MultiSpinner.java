package com.example.custom_spinner_library;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MultiSpinner extends TextView implements OnMultiChoiceClickListener {

    public enum AllSelectedDisplayMode {
        DisplayAllItems
    }
    private SpinnerAdapter mAdapter;
    private boolean[] mOldSelection;
    private boolean[] mSelected;
    private String mDefaultText="Alter Student List";
    private String mAllText;
    private boolean mAllSelected;
    private AllSelectedDisplayMode mAllSelectedDisplayMode;
    private MultiSpinnerListener mListener;
    AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());

    public MultiSpinner(Context context) {
        super(context);
        setText("Alter Student List");
    }

    public MultiSpinner(Context context, AttributeSet attr) {
        this(context,attr,R.attr.spinnerStyle);
    }

    public MultiSpinner(Context context, AttributeSet attr, int defStyle) {
        super(context,attr,defStyle);
    }

    public void onClick(final DialogInterface dialog1, final int which1, boolean isChecked) {
        mSelected[which1] = isChecked;

        if(!isChecked)
        {
        mListener.onDropoutStudent(which1, 2, "");
    }
//            final EditText edittext = new EditText(getContext());
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                builder.setTitle("Do you wish to remove this student?");
//                builder.setMessage("Please select desired option:");
//            builder.setCancelable(false);
//            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    mListener.onDropoutStudent(which1,2,"");
//                    dialog.dismiss();
//                    dialog1.dismiss();
////                    dialog.dismiss();
//
//                }
//            });
//                builder.setNegativeButton("Change Student's Batch?", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        mListener.onDropoutStudent(which1,0,"");
//                        dialog1.dismiss();
//                        dialog.dismiss();
//                    }
//                });
//                builder.setPositiveButton("Remove Student From Batch?", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        //
//                        dialog1.dismiss();
//                        dialog.dismiss();
//                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
//                        builder.setTitle("Reason???");
//                        builder.setView(edittext);
//                        builder.setNeutralButton("Submit", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String YouEditTextValue = edittext.getText().toString();
//                                Toast.makeText(getContext(), ""+YouEditTextValue, Toast.LENGTH_SHORT).show();
//                                mListener.onDropoutStudent(which1,1,YouEditTextValue);
//                                dialog.dismiss();
//                            }
//                        });
//                        builder.show();
//                    }
//            });
//            builder.show();
//        }

    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mAdapter.getCount()==0)
            {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
                builder.setTitle("No Student");
                builder.setMessage("No student left in batch!!! Delete Batch?");
                builder.setIcon(R.drawable.deleteicon);
                builder.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            else
            {
                builder1.setTitle("Alter Student List");
                builder1.setCancelable(false);
                String choices[] = new String[mAdapter.getCount()];
                for (int i = 0; i < choices.length; i++) {
                    choices[i] = mAdapter.getItem(i).toString();
                }
                for (int i = 0; i < mSelected.length; i++) {
                    mOldSelection[i] = mSelected[i];
                }
                builder1.setMultiChoiceItems(choices, mSelected, MultiSpinner.this);
                builder1.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < mSelected.length; i++) {
                            mSelected[i] = mOldSelection[i];
                        }
                        dialog.dismiss();
                    }
                });
                builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        refreshSpinner();
                        mListener.onItemsSelected(mSelected);
                        dialog.dismiss();
                    }
                });
                builder1.show();
            }
        }
    };

    public SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    DataSetObserver dataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            mOldSelection = new boolean[mAdapter.getCount()];
            mSelected = new boolean[mAdapter.getCount()];
            for (int i = 0; i < mSelected.length; i++) {
                mOldSelection[i] = false;
                mSelected[i] = mAllSelected;
            }
        }
    };


    public void setAdapter(SpinnerAdapter adapter, boolean allSelected, MultiSpinnerListener listener) {
        SpinnerAdapter oldAdapter = this.mAdapter;
        setOnClickListener(null);
        
        this.mAdapter = adapter;
        this.mListener = listener;
        this.mAllSelected = allSelected;
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(dataSetObserver);
        }
        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(dataSetObserver);
            mOldSelection = new boolean[mAdapter.getCount()];
            mSelected = new boolean[mAdapter.getCount()];
            for (int i = 0; i < mSelected.length; i++) {
                mOldSelection[i] = false;
                mSelected[i] = allSelected;
            }
            setOnClickListener(onClickListener);
        }
        setText("Alter Student List");
    }

    public void setOnItemsSelectedListener(MultiSpinnerListener listener) {
        this.mListener = listener;
    }

    public interface MultiSpinnerListener {
        public void onItemsSelected(boolean[] selected);
        public void onDropoutStudent(int which,int flag_dropout,String reason);
    }

    public boolean[] getSelected() {
        return this.mSelected;
    }

    public void setSelected(boolean[] selected) {
        if (this.mSelected.length != selected.length)
            return;
        this.mSelected = selected;
        refreshSpinner();
    }

    private void refreshSpinner() {
        // refresh text on spinner
        StringBuffer spinnerBuffer = new StringBuffer();
        boolean someUnselected = false;
        boolean allUnselected = true;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            if (mSelected[i]) {
                spinnerBuffer.append(mAdapter.getItem(i).toString());
                spinnerBuffer.append(", ");
                allUnselected = false;
            } else {
                someUnselected = true;
            }
        }
        String spinnerText="Alter Student List";
        if (!allUnselected) {

            if ((someUnselected && !(mAllText != null && mAllText.length() > 0)) || mAllSelectedDisplayMode == AllSelectedDisplayMode.DisplayAllItems) {
                spinnerText = "Alter Student List";
                if (spinnerText.length() > 2)
                    spinnerText = "Alter Student List";
            } else {

            }
        } else {
            spinnerText = mDefaultText;
        }

        setText("Alter Student List");
    }

    public String getDefaultText() {
        return mDefaultText;
    }

    public void setDefaultText(String defaultText) {
        this.mDefaultText = defaultText;
    }

    public String getAllText() {
        return mAllText;
    }

    public void setAllText(String allText) {
        this.mAllText = allText;
    }

    public AllSelectedDisplayMode getAllSelectedDisplayMode() {
        return mAllSelectedDisplayMode;
    }

    public void setAllSelectedDisplayMode(AllSelectedDisplayMode allSelectedDisplayMode) {
        this.mAllSelectedDisplayMode = allSelectedDisplayMode;
    }
}
