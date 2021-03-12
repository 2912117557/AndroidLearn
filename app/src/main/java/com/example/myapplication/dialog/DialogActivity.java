package com.example.myapplication.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class DialogActivity extends AppCompatActivity
        implements MyDialogFragment.MyDialogListener{
    MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

    boolean[] checkItems=null;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("dialog","create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        DialogFragment dialogFragment = new MyDialogFragment();
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, dialogFragment)
                .commit();
        fragmentManager.executePendingTransactions();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogFragment.dismiss();
                Log.i("dialog","remove");
                mutableLiveData.setValue("loading");
            }
        },3000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("dialog","threadBegin");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mutableLiveData.setValue("loaded");
                    }
                });
                Log.i("dialog","threadEnd");
            }
        }).start();
    }

    @Override
    protected void onStart() {
        Log.i("dialog","start");
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("dialog","resume");
        TextView textView = findViewById(R.id.dialogTextView1);
        mutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
    }



    public void showMyDialog() {
        DialogFragment dialogFragment = new MyDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBooleanArray("checkItems",checkItems);
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getSupportFragmentManager(), "MyDialogFragment");
    }


    @Override
    public void onDialogPositiveClick(MyDialogFragment dialog) {
        String[] dialogArray = getResources().getStringArray(R.array.dialogArray);
        int size = dialogArray.length;
        checkItems = new boolean[size];
        for(int i=0;i<size;i++){
            checkItems[i]=false;
        }
        List<Integer> selectedItems = dialog.selectedItems;
        for(int i=0;i<selectedItems.size();i++){
            Log.i("dialog"," "+selectedItems.get(i));
            checkItems[selectedItems.get(i)]=true;
        }
    }

    @Override
    public void onDialogNegativeClick(MyDialogFragment dialog) {
        Log.i("dialog","No");
    }

    public void onDialogButton1Click(View view){
        showMyDialog();
    }

}
