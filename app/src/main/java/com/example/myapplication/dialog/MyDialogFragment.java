package com.example.myapplication.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyDialogFragment extends DialogFragment {
    public interface MyDialogListener {
        public void onDialogPositiveClick(MyDialogFragment dialog);
        public void onDialogNegativeClick(MyDialogFragment dialog);
    }

    MyDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        Log.i("dialog","Fattach");

        super.onAttach(context);
        try {
            listener = (MyDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(requireActivity().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("dialog","Fcreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("dialog","FcreateView");
        return inflater.inflate(R.layout.activity_welcome,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("dialog","FViewCreate");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("dialog","Fstart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("dialog","Fresume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("dialog","Fdestory");

    }

    List<Integer> selectedItems = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.i("dialog","FCreateDialog");
        boolean[] checkItems = getArguments().getBooleanArray("checkItems");
        if(checkItems!=null){
            for(int i = 0; i<checkItems.length; i++){
                if(checkItems[i]){
                    selectedItems.add(i);
                }
            }
        }
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("DialogTitle")
//                .setMessage("DialogMessage")
//                .setView(inflater.inflate(R.layout.dialog_signin, null))
                .setMultiChoiceItems(R.array.dialogArray, checkItems,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    selectedItems.add(which);
                                } else if (selectedItems.contains(which)) {
                                    selectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(MyDialogFragment.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogNegativeClick(MyDialogFragment.this);

                    }
                })
                .setNeutralButton("Other", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("dialog","other");
                    }
                });
        return builder.create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.i("dialog","dismiss");
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        Log.i("dialog","cancel");
    }
}
