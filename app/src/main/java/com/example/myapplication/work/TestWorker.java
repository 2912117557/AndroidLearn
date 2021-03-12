package com.example.myapplication.work;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class TestWorker extends Worker {
    public TestWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String inputData = getInputData().getString("inputData");
        Log.i("TestWorker",inputData);
        for(int i=0;i<20;i++){
            if (isStopped()) {
                break;
            }
            Log.i("TestWorker","test");
            try {
                Thread.sleep(2000);
                Log.i("TestWorker","sleepOK");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("TestWorker","loopStop");
        return Result.success(new Data.Builder()
                .putString("outputData","outputSuccess")
                .build());
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i("TestWorker","stop");
    }
}
