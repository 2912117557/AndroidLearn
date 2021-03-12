package com.example.myapplication.work;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.concurrent.TimeUnit;

public class WorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

    }

    public void beginWork(View v){
        Toast.makeText(this,"beginWork",Toast.LENGTH_SHORT).show();
        OneTimeWorkRequest testWorkRequest =
                new OneTimeWorkRequest.Builder(TestWorker.class)
                        .setInitialDelay(5, TimeUnit.SECONDS)
                        .setBackoffCriteria(
                                BackoffPolicy.LINEAR,
                                OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                                TimeUnit.MILLISECONDS)
                        .addTag("testTag")
                        .setInputData(
                                new Data.Builder()
                                        .putString("inputData", "inputSuccess")
                                        .build()
                        )
                        .build();

        WorkManager.getInstance(getApplicationContext()).enqueueUniqueWork(
                "testName",
                ExistingWorkPolicy.KEEP,
                testWorkRequest);

        WorkManager.getInstance(getApplicationContext()).getWorkInfoByIdLiveData(testWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    String outputData = workInfo.getOutputData().getString("outputData");
                    Log.i("WorkActivity",outputData);
                }

            }
        });
    }

    public void endWork(View view){
        Toast.makeText(this,"endWork",Toast.LENGTH_SHORT).show();
        WorkManager.getInstance(getApplicationContext()).cancelAllWorkByTag("testTag");
        Log.i("WorkActivity","beginStop");
    }

}